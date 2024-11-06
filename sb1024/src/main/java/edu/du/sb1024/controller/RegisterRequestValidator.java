package edu.du.sb1024.controller;

import edu.du.sb1024.entity.RegisterRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterRequestValidator implements Validator {

    // 상수 String / 고정형 값
    private static final String emailRegExp = // 정규 표현식(이메일 체크)
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;

    // 생성자 emailRegExp 정규표현식을 패턴에 넣음
    public RegisterRequestValidator() {
        pattern = Pattern.compile(emailRegExp);
        System.out.println("RegisterRequestValidator#new(): " + this);
    }

    @Override
    public boolean supports(Class<?> clazz) { // 타입 변환 가능 여부(T/F)
        return RegisterRequest.class.isAssignableFrom(clazz); // RegisterRequest 커맨드 객체 검증
    }

    @Override
    public void validate(Object target, Errors errors) { // target <- RegisterRequest 필드값
        System.out.println("RegisterRequestValidator#validate(): " + this);
        RegisterRequest regReq = (RegisterRequest) target;
        if (regReq.getEmail() == null || regReq.getEmail().trim().isEmpty()) {
            // rejectValue는 에러 발생 시 해당 키워드를 찾아서 view에 보여주는 역할
            errors.rejectValue("email", "required"); // required=필수항목입니다.
        } else {
            Matcher matcher = pattern.matcher(regReq.getEmail());
            if (!matcher.matches()) {
                errors.rejectValue("email", "bad"); // bad.email=이메일이 올바르지 않습니다.
            }
        }
        // 유틸리티를 만듦.          비어있거나 공백인 경우
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name", "required"); // errors 객체의 getFieldValue("name")
        ValidationUtils.rejectIfEmpty(errors, "password", "required"); // 커맨드 객체를 직접 전달하지 않아도 값 검증O
        ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "required");
        if(!regReq.getPassword().isEmpty()) {
            errors.rejectValue("confirmPassword", "nomatch"); // nomatch.confirmPassword=비밀번호와 확인이 일치하지 않습니다.
            //						 (1)프로퍼티   , (2)에러
        }
    }
}
