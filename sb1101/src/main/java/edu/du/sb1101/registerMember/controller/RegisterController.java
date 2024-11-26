package edu.du.sb1101.registerMember.controller;

import edu.du.sb1101.registerMember.entity.Member;
import edu.du.sb1101.registerMember.repository.MemberRepository;
import edu.du.sb1101.registerMember.service.MemberService;
import edu.du.sb1101.registerMember.spring.DuplicateMemberException;
import edu.du.sb1101.registerMember.spring.MemberRegisterService;
import edu.du.sb1101.registerMember.spring.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
public class RegisterController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberRegisterService memberRegisterService;

	@GetMapping("/register")
	public String root() {
		return "redirect:/register/step1";
	}

	@RequestMapping("/register/step1")
	public String handleStep1(Model model, HttpSession session) {
		return "register/step1";
	}

	@PostMapping("/register/step2")
	public String handleStep2(
			@RequestParam(value = "agree", defaultValue = "false") Boolean agree,
			Model model, HttpSession session) {
		if (!agree) {
			return "register/step1";
		}
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register/step2";
	}

	@GetMapping("/register/step2")
	public String handleStep2Get() {
		return "redirect:/register/step1";
	}

@PostMapping("/register/step3")
public String handleStep3(@Validated RegisterRequest regReq, Errors errors,
						  Model model, HttpSession session) {
	if (errors.hasErrors())
		return "register/step2";

	try {
		memberRegisterService.regist(regReq);

		// 등록된 회원 정보로 Member 객체를 가져오기 (등록 후 DB에서 조회)
		Member registeredMember = memberRepository.findByUsername(regReq.getName());

		// 포인트 적립 (회원가입 후 포인트 적립)
		memberService.addPoints(registeredMember.getUsername(), 100, "회원가입 +100포인트 적립");

		return "register/step3";

	} catch (DuplicateMemberException ex) {
		errors.rejectValue("email", "duplicate");
//		errors.reject("notMatchingPassword");
		return "register/step2";
	}
}

	// 특정 컨트롤러에서 바인딩 또는 검증 설정을 변경하고 싶을 때 사용
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new RegisterRequestValidator());
	}

}
