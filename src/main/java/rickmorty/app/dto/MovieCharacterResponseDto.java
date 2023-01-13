package rickmorty.app.dto;

import lombok.Getter;
import lombok.Setter;
import rickmorty.app.model.Gender;
import rickmorty.app.model.Status;

@Getter
@Setter
public class MovieCharacterResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private Status status;
    private Gender gender;
}
