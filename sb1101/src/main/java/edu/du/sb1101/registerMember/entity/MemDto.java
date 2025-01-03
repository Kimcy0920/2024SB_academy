package edu.du.sb1101.registerMember.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemDto {

    @NotBlank(message = "아이디(이메일)를 입력해주세요.")
    private String email;

    @NotBlank(message = "성명을 입력해주세요.")
    private String username;

    @NotBlank(message = "주소를 입력해주세요.") // 필요 여부에 따라 제거 가능
    private String address;
}