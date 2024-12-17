package edu.du.proj_g2e.member.controller;

import edu.du.proj_g2e.member.entity.RegisterRequest;
import edu.du.proj_g2e.member.repository.MemberRepository;
import edu.du.proj_g2e.member.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class RegisterController {

	private final MemberRepository memberRepository;

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
	public String handleStep4() {
			return "auth/regStep04";
	}

}
