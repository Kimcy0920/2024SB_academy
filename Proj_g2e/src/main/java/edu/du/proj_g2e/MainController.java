package edu.du.proj_g2e;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        return "/layout/main";  // main.html을 렌더링
    }
}