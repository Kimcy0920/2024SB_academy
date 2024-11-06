package edu.du.sb1021.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BeginController {

    // 서비스(sb) 실행 시 로그인 창 나옴.
    // ID:user(기본값), PASSWORD: 콘솔창에 나옴 / 재구동 할 때마다 비번 바뀜.
    
    @GetMapping("/")
    public String root() {
        return "/sample/all";
        // Security 로 인해 로그인해야 /sample/all 로 넘어가짐.
    }
}
