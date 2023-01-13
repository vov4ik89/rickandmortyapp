package rickmorty.app.service.mapper;

import org.springframework.stereotype.Component;
import rickmorty.app.dto.ApiCharacterDto;
import rickmorty.app.dto.MovieCharacterResponseDto;
import rickmorty.app.model.Gender;
import rickmorty.app.model.MovieCharacter;
import rickmorty.app.model.Status;

@Component
public class MovieCharacterMapper {
    public MovieCharacter parseApiCharacterResponseDto(ApiCharacterDto dto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setExternalId(dto.getId());
        movieCharacter.setName(dto.getName());
        movieCharacter.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        movieCharacter.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        return movieCharacter;
    }

    public MovieCharacterResponseDto toResponseDto(MovieCharacter movieCharacter) {
        MovieCharacterResponseDto responseDto = new MovieCharacterResponseDto();
        responseDto.setId(movieCharacter.getId());
        responseDto.setExternalId(movieCharacter.getExternalId());
        responseDto.setName(movieCharacter.getName());
        responseDto.setStatus(movieCharacter.getStatus());
        responseDto.setGender(movieCharacter.getGender());
        return responseDto;
    }
}
