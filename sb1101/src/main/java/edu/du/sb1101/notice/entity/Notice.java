package edu.du.sb1101.notice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 60, message = "제목은 50자 이내로 작성해주세요.")
    @Column(nullable = false, length = 60)
    private String title;

    @NotBlank
    @Size(max = 1200, message = "내용은 1000자 이내로 작성해주세요.")
    @Column(nullable = false)
    @Lob  // TEXT 타입으로 매핑
    private String content;

    private String username;

    private LocalDateTime regdate;

}
