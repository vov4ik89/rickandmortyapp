package rickmorty.app.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rickmorty.app.dto.MovieCharacterResponseDto;
import rickmorty.app.model.MovieCharacter;
import rickmorty.app.service.MovieCharacterService;
import rickmorty.app.service.mapper.MovieCharacterMapper;

@RestController
@RequestMapping("/movie-characters")
public class MovieCharacterController {
    private final MovieCharacterMapper movieCharacterMapper;
    private final MovieCharacterService movieCharacterService;

    public MovieCharacterController(MovieCharacterMapper movieCharacterMapper,
                                    MovieCharacterService movieCharacterService) {
        this.movieCharacterMapper = movieCharacterMapper;
        this.movieCharacterService = movieCharacterService;
    }

    @GetMapping("/random")
    @ApiOperation("Get random character from DB")
    public MovieCharacterResponseDto getRandom() {
        MovieCharacter character = movieCharacterService.getRandomCharacter();
        return movieCharacterMapper.toResponseDto(character);
    }

    @GetMapping("/by-name")
    @ApiOperation("Get character by part of name")
    public List<MovieCharacterResponseDto> findAllByName(@RequestParam("name") String namePart) {
        return movieCharacterService.findAllByNameContains(namePart)
                .stream()
                .map(movieCharacterMapper::toResponseDto)
                .toList();
    }
}
