package rickmorty.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiCharacterDto {
    private Long id;
    private String name;
    private String status;
    private String gender;
}
