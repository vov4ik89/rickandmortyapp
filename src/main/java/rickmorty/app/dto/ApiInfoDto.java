package rickmorty.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiInfoDto {
    private int count;
    private int pages;
    private String next;
    private String prev;
}
