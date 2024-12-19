package edu.du.proj_g2e.member.controller;

import edu.du.proj_g2e.member.entity.RegisterRequest;
import edu.du.proj_g2e.member.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/auth")
//public class RegisterController {
//
//	private final RegisterService registerService;
//
//	// 1. Step 1
//	@GetMapping("/regStep01")
//	public ResponseEntity<String> root() {
//		return ResponseEntity.ok("regStep01");  // Vue.js에서 이 정보를 통해 페이지를 관리
//	}
//
//	// 2. Step 2 (GET)
//	@GetMapping("/regStep02")
//	public ResponseEntity<Map<String, Object>> showTerms(@RequestParam("roleType") String roleType) {
//		Map<String, Object> response = new HashMap<>();
//		response.put("roleType", roleType);
//		return ResponseEntity.ok(response);
//	}
//
//	// 3. Step 2 (POST)
//	@PostMapping("/regStep02")
//	public ResponseEntity<Map<String, Object>> handleStep2(@RequestParam("roleType") String roleType) {
//		Map<String, Object> response = new HashMap<>();
//		response.put("roleType", roleType);
//
//		if ("USER".equals(roleType)) {
//			return ResponseEntity.ok(Map.of("redirect", "/regStep03_User"));
//		} else if ("CHECK".equals(roleType)) {
//			return ResponseEntity.ok(Map.of("redirect", "/regStep03_Check"));
//		}
//		return ResponseEntity.badRequest().body(Map.of("error", "Invalid roleType"));
//	}
//
//	// 4. Step 3 (GET)
//	@GetMapping("/regStep03/{roleType}")
//	public ResponseEntity<Map<String, Object>> handleStepGet(@PathVariable String roleType) {
//		Map<String, Object> response = new HashMap<>();
//		response.put("registerRequest", new RegisterRequest());
//		response.put("roleType", roleType);  // 역할 정보를 응답에 포함시킬 수 있음
//		return ResponseEntity.ok(response);
//	}
//
//	// 5. Step 3 (POST)
//	@PostMapping("/regStep03/{roleType}")
//	public ResponseEntity<String> handleStepPost(@Valid @RequestBody RegisterRequest registerRequest, @PathVariable String roleType) {
//		if ("USER".equals(roleType)) {
//			registerService.register_User(registerRequest);
//		} else if ("CHECK".equals(roleType)) {
//			registerService.register_Check(registerRequest);
//		} else {
//			return ResponseEntity.badRequest().body("Invalid role type");
//		}
//		return ResponseEntity.ok("Registration successful for " + roleType);
//	}
//
//	// 6. Step 4 (GET)
//	@GetMapping("/regStep04")
//	public ResponseEntity<Map<String, Object>> handleStep4() {
//		Map<String, Object> response = new HashMap<>();
//		response.put("registerRequest", new RegisterRequest());
//		return ResponseEntity.ok(response);
//	}
//
//	// 7. Step 4 (POST)
//	@PostMapping("/regStep04")
//	public ResponseEntity<String> handleStep4(@Valid @RequestBody RegisterRequest registerRequest, @RequestParam("roleType") String roleType) {
//		if (roleType.equals("USER")) {
//			registerService.register_User(registerRequest);
//		} else if (roleType.equals("CHECK")) {
//			registerService.register_Check(registerRequest);
//		} else {
//			return ResponseEntity.badRequest().body("Invalid roleType");
//		}
//		return ResponseEntity.ok("Registration step 4 complete");
//	}
//}


@RequiredArgsConstructor
@Controller
public class RegisterController {

	private final RegisterService registerService;

	@GetMapping("/regStep01")
	public String root() {
		return "/auth/regStep01";
	}

	@GetMapping("/regStep02")
	public String showTerms(@RequestParam("roleType") String roleType, Model model) {
		model.addAttribute("roleType", roleType); // 일반/의료업 정보 전달
		System.out.println("Role Type: " + roleType);
		return "auth/regStep02";
	}
	@PostMapping("/regStep02")
	public String handleStep2(@RequestParam("roleType") String roleType, Model model) {

		System.out.println("Role Type: " + roleType);
		model.addAttribute("roleType", roleType);

		if (roleType.equals("USER")) {
			return "redirect:/regStep03_User";
		} else if (roleType.equals("CHECK")) {
			return "redirect:/regStep03_Check";
		}
		return "auth/regStep01";
	}

	@GetMapping("/regStep03_User")
	public String handleStep2Get_User() {
		return "/auth/regStep03_User";
	}
	@PostMapping("/regStep03_User")
	public String handleStep3_User(@RequestParam("roleType") String roleType,
							  @RequestParam(value = "agreeTerms", defaultValue = "false") Boolean agreeTerms,
							  @RequestParam(value = "agreePrivacy", defaultValue = "false") Boolean agreePrivacy,
							  Model model) {
		if (!agreeTerms || !agreePrivacy) {
			return "auth/regStep01";
		}
		model.addAttribute("roleType", roleType);
		model.addAttribute("registerRequest", new RegisterRequest());
		return "auth/regStep03_User";
	}

	@GetMapping("/regStep03_Check")
	public String handleStep2Get_Check(Model model) {
		model.addAttribute("registerRequest", new RegisterRequest());
		return "/auth/regStep03_Check";
	}
	@PostMapping("/regStep03_Check")
	public String handleStep3_Check(@RequestParam("roleType") String roleType,
							  @RequestParam(value = "agreeTerms", defaultValue = "false") Boolean agreeTerms,
							  @RequestParam(value = "agreePrivacy", defaultValue = "false") Boolean agreePrivacy,
							  Model model) {
		if (!agreeTerms || !agreePrivacy) {
			return "auth/regStep01";
		}
		model.addAttribute("roleType", roleType);
		model.addAttribute("registerRequest", new RegisterRequest());
		return "/auth/regStep03_Check";
	}

	@GetMapping("/regStep04")
	public String handleStep4(Model model) {
		model.addAttribute("registerRequest", new RegisterRequest());
		return "/auth/regStep04";
	}
	@PostMapping("/regStep04")
	public String handleStep4(@RequestParam String email,
							  @RequestParam String name,
							  @RequestParam String password,
							  @RequestParam String confirmPassword,
							  @RequestParam("roleType") String roleType) {
		RegisterRequest regReq = new RegisterRequest(email, name, password, confirmPassword);

		if (roleType.equals("USER")) {
			registerService.register_User(regReq);
		} else if(roleType.equals("CHECK")) {
			registerService.register_Check(regReq);
		} else {
			return "redirect:auth/regStep01";
		}
		return "auth/regStep04";
	}

}
