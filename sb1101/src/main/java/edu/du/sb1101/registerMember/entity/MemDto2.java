package edu.du.sb1101.registerMember.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class MemDto2 {

    @NotBlank(message = "아이디(이메일)를 입력해주세요.")
    private String email;

    @NotBlank(message = "성명을 입력해주세요.")
    private String username;
}
