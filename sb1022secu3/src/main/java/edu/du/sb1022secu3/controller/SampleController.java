package edu.du.sb1022secu3.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/sample/") // 디렉토리 구조의 컨트롤러
public class SampleController {

    @GetMapping("/accessDenied")
    public void accessDenied() {
        log.info("---------Access denied---------");
    } // void라 return없음 templates 경로에 맞춰 accessDenied view를 보여줌

    @GetMapping("/member")
    public void member() {
        log.info("---------Member---------");
    }

    @GetMapping("/all")
    public void all() {
        log.info("---------All---------");
    }

    @GetMapping("/admin")
    public void admin() {
        log.info("---------Admin---------");
    }
}
