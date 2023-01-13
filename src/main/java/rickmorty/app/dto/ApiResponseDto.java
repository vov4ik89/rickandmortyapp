package rickmorty.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDto {
    private ApiInfoDto info;
    private ApiCharacterDto[] results;
}
