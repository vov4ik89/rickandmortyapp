package rickmorty.app.service.impl;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rickmorty.app.dto.ApiCharacterDto;
import rickmorty.app.dto.ApiResponseDto;
import rickmorty.app.model.MovieCharacter;
import rickmorty.app.repository.MovieCharacterRepository;
import rickmorty.app.service.HttpClient;
import rickmorty.app.service.MovieCharacterService;
import rickmorty.app.service.mapper.MovieCharacterMapper;

@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private static final int MIN_RANDOM_ID = 1;
    @Value("${character.external.api.url}")
    private String externalApiUrl;
    private final HttpClient httpClient;
    private final MovieCharacterRepository movieCharacterRepository;
    private final MovieCharacterMapper mapper;

    public MovieCharacterServiceImpl(HttpClient httpClient,
                                     MovieCharacterRepository movieCharacterRepository,
                                     MovieCharacterMapper mapper) {
        this.httpClient = httpClient;
        this.movieCharacterRepository = movieCharacterRepository;
        this.mapper = mapper;
    }

    @PostConstruct
    @Scheduled(cron = "0 0 8 * * ?")
    @Override
    public void syncExternalCharacters() {
        ApiResponseDto apiResponseDto = httpClient.get(externalApiUrl, ApiResponseDto.class);
        saveDtosToDb(apiResponseDto);
        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient
                    .get(apiResponseDto.getInfo().getNext(), ApiResponseDto.class);
            saveDtosToDb(apiResponseDto);
        }
    }

    @Override
    public MovieCharacter getRandomCharacter() {
        long count = movieCharacterRepository.count();
        long randomId = (long) (Math.random() * count) + MIN_RANDOM_ID;
        return movieCharacterRepository.findById(randomId).get();
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String namePart) {
        return movieCharacterRepository.findAllByNameContains(namePart);
    }

    private void saveDtosToDb(ApiResponseDto apiResponseDto) {
        Map<Long, ApiCharacterDto> externalDtos = Arrays.stream(apiResponseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));
        Set<Long> externalIds = externalDtos.keySet();
        List<MovieCharacter> existingCharacters = movieCharacterRepository
                .findAllByExternalIdIn(externalIds);
        Map<Long, MovieCharacter> existingCharactersWithIds = existingCharacters.stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));
        Set<Long> existingIds = existingCharactersWithIds.keySet();
        externalIds.removeAll(existingIds);

        List<MovieCharacter> charactersToSave = externalIds.stream()
                .map(i -> mapper.parseApiCharacterResponseDto(externalDtos.get(i)))
                .toList();
        movieCharacterRepository.saveAll(charactersToSave);
    }
}
