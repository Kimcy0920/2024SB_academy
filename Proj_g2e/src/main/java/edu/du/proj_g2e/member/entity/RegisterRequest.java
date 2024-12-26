package edu.du.proj_g2e.member.entity;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	// 이메일 형식 검증
	@NotEmpty(message = "이메일은 필수입니다.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	private String email;

	// 이름 필드
	@NotEmpty(message = "이름은 필수입니다.")
	@Pattern(regexp = "^[가-힣a-zA-Z\\s]{2,30}$",
			message = "성명은 2~30자의 한글, 영문, 공백만 허용됩니다.")
	private String name;

	@NotEmpty(message = "비밀번호는 필수입니다.")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$",
			message = "비밀번호는 8자 이상, 숫자, 특수문자를 포함해야 합니다.")
	private String password;

	// 비밀번호 확인 필드
	@NotEmpty(message = "확인 비밀번호는 필수입니다.")
	private String confirmPassword;

	private String role;

	// 약관 동의
	private boolean agreeTerms;
	private boolean agreePrivacy;

	// 비밀번호와 비밀번호 확인이 일치하는지 체크하는 메서드
	public boolean isPasswordEqualToConfirmPassword() {
		return password.equals(confirmPassword);
	}
}
