package du.ac.kr.sb1006.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SimpleBbsDto {
    private int id;
    private String writer;
    private String title;
    private String content;
}
