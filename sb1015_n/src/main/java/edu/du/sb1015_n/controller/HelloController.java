package edu.du.sb1015_n.controller;

import edu.du.sb1015_n.entity.DataObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HelloController {

    @GetMapping("/month/{month}")
    public String index(@PathVariable int month, Model model) {
        int m = Math.abs(month) % 12;
        m = m == 0 ? 12 : m;
        model.addAttribute("month", m);
        model.addAttribute("check", Math.floor(m/3));
        return "index";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("msg", "message 1<hr/>message 2<br/>message 3");
        DataObject obj = new DataObject(1001, "홍길동", "hong@korea.com");
        model.addAttribute("object", obj);
        return "index";
    }
}
