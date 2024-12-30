package edu.du.proj_g2e.member.controller;

import edu.du.proj_g2e.member.entity.RegisterRequest;
import edu.du.proj_g2e.member.repository.MemberRepository;
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

	private final MemberRepository memberRepository;
	private final RegisterService registerService;

	@PostMapping("/regStep02")
	public ResponseEntity<String> handleStep2(@Valid @RequestBody RegisterRequest regReq) {
		// 요청 데이터 로그 찍기
		System.out.println("약관 동의 상태: " + regReq.isAgreeTerms() + ", 개인정보 동의 상태: " + regReq.isAgreePrivacy());
		if (!regReq.isAgreeTerms() || !regReq.isAgreePrivacy()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("모든 약관에 동의해야 합니다.");
		}
		return ResponseEntity.ok("약관 동의가 완료되었습니다. 다음 단계로 이동.");
	}

	// 세 번째 단계 사용자 처리
	@PostMapping("/regStep03_User")
	public ResponseEntity<String> handleStep3_User(@Valid @RequestBody RegisterRequest req, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력한 정보가 유효하지 않습니다.");
		}
		if (!req.isPasswordEqualToConfirmPassword()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
		}
		registerService.register(req);
		return ResponseEntity.ok("회원가입 완료");
	}

	// 세 번째 단계 체크 처리
	@PostMapping("/regStep03_Check")
	public ResponseEntity<String> handleStep3_Check(@Valid @RequestBody RegisterRequest req, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
		}
		if (!req.isPasswordEqualToConfirmPassword()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
		}
		registerService.register(req);
		return ResponseEntity.ok("회원가입 완료");
	}

	// 네 번째 단계
	@PostMapping("/regStep04")
	public ResponseEntity<String> handleStep4(@Valid @RequestBody RegisterRequest req, @RequestParam("roleType") String roleType) {
		if (roleType != null) {
			registerService.register(req);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role type");
		}
		return ResponseEntity.ok("regStep04 페이지로 이동");
	}
}
