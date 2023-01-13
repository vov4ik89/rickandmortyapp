package rickmorty.app.service;

import java.util.List;
import rickmorty.app.model.MovieCharacter;

public interface MovieCharacterService {
    void syncExternalCharacters();

    MovieCharacter getRandomCharacter();

    List<MovieCharacter> findAllByNameContains(String namePart);
}
