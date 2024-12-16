package edu.du.proj_g2e.member.controller;

import edu.du.proj_g2e.member.entity.Member;
import edu.du.proj_g2e.member.entity.RegisterRequest;
import edu.du.proj_g2e.member.exception.DuplicateMemberException;
import edu.du.proj_g2e.member.repository.MemberRepository;
import edu.du.proj_g2e.member.service.MemberService;
import edu.du.proj_g2e.member.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


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
		return "auth/regStep02";
	}
	@PostMapping("/regStep02")
	public String handleStep2(
			@RequestParam(value = "agreeTerms", defaultValue = "false") Boolean agreeTerms,
			@RequestParam(value = "agreePrivacy", defaultValue = "false") Boolean agreePrivacy,
			@RequestParam("roleType") String roleType,
			Model model) {

		model.addAttribute("registerRequest", new RegisterRequest());
		if ("USER".equals(roleType)) {
			return "auth/regStep03_User";
		} else if ("CHECK".equals(roleType)) {
			return "auth/regStep03_Check";
		}
	}


	@PostMapping("/regStep03")
	public String handleStep3(@Validated RegisterRequest regReq, Errors errors,
						  Model model, HttpSession session) {
		if (errors.hasErrors())
			return "/auth/regStep02";

		try {
			registerService.register(regReq);

			// 등록된 회원 정보로 Member 객체를 가져오기 (등록 후 DB에서 조회)
			Member registeredMember = memberRepository.findByName(regReq.getName());

			return "/auth/regStep04";

		} catch (DuplicateMemberException ex) {
			errors.rejectValue("email", "duplicate");
			return "/auth/regStep02";
		}
	}

	// 특정 컨트롤러에서 바인딩 또는 검증 설정을 변경하고 싶을 때 사용
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new RegisterRequestValidator());
	}

}
