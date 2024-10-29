package edu.du.sb1024.controller;

import edu.du.sb1024.entity.DuplicateMemberException;
import edu.du.sb1024.entity.MemberRegisterService;
import edu.du.sb1024.entity.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class MemberController {

    @Autowired
    private MemberRegisterService memberRegisterService;

    public void setMemberRegisterService(
            MemberRegisterService memberRegisterService) {
        this.memberRegisterService = memberRegisterService;
    }

    @RequestMapping("/register/step1")
    public String handleStep1() {
        return "register/step1";
    }

    @PostMapping("/register/step2")
    public String handleStep2(
            @RequestParam(value = "agree", defaultValue = "false") Boolean agree,
            Model model) {
        if (!agree) {
            return "register/step1";
        }
        RegisterRequest regReq = new RegisterRequest();
        model.addAttribute("registerRequest", regReq);
        model.addAttribute("agree", agree);
        return "register/step2";
    }

    @GetMapping("/register/step2")
    public String handleStep2Get() {
        return "redirect:/register/step1";
    }

    @PostMapping("/register/step3") // RegisterRequest 커맨드 객체
    public String handleStep3(@Validated RegisterRequest regReq, BindingResult errors) { // (regReq, errors)프레임워크가 알아서 객체 생성해줌
        // BindingResult errors = Errors errors
        // RegisterRequestValidator -> @Override public void validate(Object target(1), Errors errors(2)) {
//        new RegisterRequestValidator().validate(regReq, errors);
        if (errors.hasErrors())
            return "register/step2"; // 에러 발생시 step2로 리턴
        try {
            memberRegisterService.regist(regReq);
            return "register/step3";
        } catch (DuplicateMemberException ex) {
//			errors.rejectValue("email", "duplicate"); // label.properties -> duplicate.email=중복된 이메일입니다.
            errors.reject("notMatchingPassword"); // p.335
            return "register/step2";
        }
    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.setValidator(new RegisterRequestValidator());
//    }
}