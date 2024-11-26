package edu.du.sb1101.comment.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CommentDto {

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    private Integer boardIdx; // 게시글 ID
}