package edu.du.sb1101.registerMember.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class MemDto2 {

    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String newPassword;

    @NotBlank(message = "확인 비밀번호를 입력해주세요.")
    private String confirmPassword;
}
