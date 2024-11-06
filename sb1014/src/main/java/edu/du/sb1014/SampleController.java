package edu.du.sb1014;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @Controller // 주소창 /hello 면 hello 라는 이름의 view 로 보내는 것
@RestController// 웹페이지 생산(view)가 아닌 값 자체를 브라우저에 보냄
public class SampleController {

    @GetMapping("/hello")
    public String[] hello() {
        return new String[]{"Hello", "World"};
    }
}
