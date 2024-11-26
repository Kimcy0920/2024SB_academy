package edu.du.sb1101.notice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Setter
@Getter
public class NoticeDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

}

