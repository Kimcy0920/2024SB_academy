package edu.du.proj_g2e.member.controller;

import edu.du.proj_g2e.member.entity.RegisterRequest;
import edu.du.proj_g2e.member.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class RegisterController {

	private final RegisterService registerService;

	@PostMapping("/regStep02")
	public ResponseEntity<String> handleStep2(@RequestBody Map<String, String> request) {
		String roleType = request.get("roleType");

		if (roleType != null) {
			// RegisterRequest 객체에 roleType 설정
			RegisterRequest req = new RegisterRequest();
			req.setRoleType(roleType);

			// 이 요청을 후속 단계로 넘김
			return ResponseEntity.ok("redirect:/regStep03_" + roleType);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role type");
	}

	// 세 번째 단계 사용자 처리
	@PostMapping("/regStep03_User")
	public ResponseEntity<String> handleStep3_User(@RequestBody RegisterRequest req) {
		if (!req.isPasswordEqualToConfirmPassword()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
		}
		registerService.register(req);
		return ResponseEntity.ok("회원가입 완료");
	}

	// 세 번째 단계 체크 처리
	@PostMapping("/regStep03_Check")
	public ResponseEntity<String> handleStep3_Check(@RequestBody RegisterRequest req) {
		if (!req.isPasswordEqualToConfirmPassword()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
		}
		registerService.register(req);
		return ResponseEntity.ok("회원가입 완료");
	}

	// 네 번째 단계
	@PostMapping("/regStep04")
	public ResponseEntity<String> handleStep4(@RequestBody RegisterRequest req, @RequestParam("roleType") String roleType) {
		if (roleType != null) {
			registerService.register(req);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role type");
		}
		return ResponseEntity.ok("regStep04 페이지로 이동");
	}
}


//@RequiredArgsConstructor
//@Controller
//public class RegisterController {
//
//	private final RegisterService registerService;
//
//	@GetMapping("/regStep01")
//	public String root() {
//		return "/auth/regStep01";
//	}
//
//	@GetMapping("/regStep02")
//	public String showTerms(@RequestParam("roleType") String roleType, Model model) {
//		model.addAttribute("roleType", roleType); // 일반/의료업 정보 전달
//		System.out.println("Role Type: " + roleType);
//		return "auth/regStep02";
//	}
//	@PostMapping("/regStep02")
//	public String handleStep2(@RequestParam("roleType") String roleType, Model model) {
//
//		System.out.println("Role Type: " + roleType);
//		model.addAttribute("roleType", roleType);
//
//		if (roleType.equals("USER")) {
//			return "redirect:/regStep03_User";
//		} else if (roleType.equals("CHECK")) {
//			return "redirect:/regStep03_Check";
//		}
//		return "auth/regStep01";
//	}
//
//	@GetMapping("/regStep03_User")
//	public String handleStep2Get_User() {
//		return "/auth/regStep03_User";
//	}
//	@PostMapping("/regStep03_User")
//	public String handleStep3_User(@RequestParam("roleType") String roleType,
//							  @RequestParam(value = "agreeTerms", defaultValue = "false") Boolean agreeTerms,
//							  @RequestParam(value = "agreePrivacy", defaultValue = "false") Boolean agreePrivacy,
//							  Model model) {
//		if (!agreeTerms || !agreePrivacy) {
//			return "auth/regStep01";
//		}
//		model.addAttribute("roleType", roleType);
//		model.addAttribute("registerRequest", new RegisterRequest());
//		return "auth/regStep03_User";
//	}
//
//	@GetMapping("/regStep03_Check")
//	public String handleStep2Get_Check(Model model) {
//		model.addAttribute("registerRequest", new RegisterRequest());
//		return "/auth/regStep03_Check";
//	}
//	@PostMapping("/regStep03_Check")
//	public String handleStep3_Check(@RequestParam("roleType") String roleType,
//							  @RequestParam(value = "agreeTerms", defaultValue = "false") Boolean agreeTerms,
//							  @RequestParam(value = "agreePrivacy", defaultValue = "false") Boolean agreePrivacy,
//							  Model model) {
//		if (!agreeTerms || !agreePrivacy) {
//			return "auth/regStep01";
//		}
//		model.addAttribute("roleType", roleType);
//		model.addAttribute("registerRequest", new RegisterRequest());
//		return "/auth/regStep03_Check";
//	}
//
//	@GetMapping("/regStep04")
//	public String handleStep4(Model model) {
//		model.addAttribute("registerRequest", new RegisterRequest());
//		return "/auth/regStep04";
//	}
//	@PostMapping("/regStep04")
//	public String handleStep4(@RequestParam String email,
//							  @RequestParam String name,
//							  @RequestParam String password,
//							  @RequestParam String confirmPassword,
//							  @RequestParam("roleType") String roleType) {
//		RegisterRequest regReq = new RegisterRequest(email, name, password, confirmPassword);
//
//		if (roleType.equals("USER")) {
//			registerService.register_User(regReq);
//		} else if(roleType.equals("CHECK")) {
//			registerService.register_Check(regReq);
//		} else {
//			return "redirect:auth/regStep01";
//		}
//		return "auth/regStep04";
//	}
//
//}
