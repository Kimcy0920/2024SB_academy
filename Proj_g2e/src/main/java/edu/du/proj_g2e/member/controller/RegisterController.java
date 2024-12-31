package edu.du.proj_g2e.member.controller;

import edu.du.proj_g2e.member.entity_dto.RegisterRequest;
import edu.du.proj_g2e.member.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class RegisterController {

	private final RegisterService registerService;

	// 약관 동의 처리
	@PostMapping("/regStep02")
	public ResponseEntity<String> handleStep2(@RequestBody RegisterRequest regReq) {
		// 요청 데이터
		System.out.println("약관 동의 상태: " + regReq.isAgreeTerms() + ", 개인정보 동의 상태: " + regReq.isAgreePrivacy());
		if (!regReq.isAgreeTerms() || !regReq.isAgreePrivacy()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("모든 약관에 동의해야 합니다.");
		}
		return ResponseEntity.ok("약관 동의가 완료되었습니다. 다음 단계로 이동.");
	}

	// 일반&의료업 회원정보 처리
	@PostMapping("/regStep03")
	public ResponseEntity<String> handleStep3(@Valid @RequestBody RegisterRequest req, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
		}
		if (!req.isPasswordEqualToConfirmPassword()) {
			bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "비밀번호가 일치하지 않습니다.");
		}
		registerService.register(req);
		return ResponseEntity.ok("회원가입 완료");
	}

	// 가입완료
	@PostMapping("/regStep04")
	public ResponseEntity<String> handleStep4(@Valid @RequestBody RegisterRequest req, @RequestParam("role") String role) {
		if (role != null) {
			registerService.register(req);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role type");
		}
		return ResponseEntity.ok("regStep04 페이지로 이동");
	}
}
