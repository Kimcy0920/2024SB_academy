package edu.du.sb1015.controller;

import edu.du.sb1015.entity.DataObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class HelloController {

//    @GetMapping("/") // 아래 라인43에 같은 매핑을 추가해 주석처리함.
    public String hello(Model model) {
        model.addAttribute("msg", "이름 입력란:");
        return "index";
    }
//  views 에 있는 name 속성 이름을 입력하는 것. <input type="text" name="text1" th:value="${value}" />
    @PostMapping("/")
    public String post(@RequestParam("text1") String name, Model model) { // 방식1 *추천*
        model.addAttribute("value", name);
        model.addAttribute("msg", name + "님 반갑습니다.");
        return "index";
//    public String post(@RequestParam String text1, Model model) { // 방식2
//        model.addAttribute("value", text1);
//        model.addAttribute("msg", text1 + "님 반갑습니다.");

//    public String post(String text1, Model model) { // 방식3
//        model.addAttribute("value", text1);
//        model.addAttribute("msg", text1 + "님 반갑습니다.");
    }

//    @RequestMapping("/") // 옛날코드 아래 방식으로 변경함
//    public ModelAndView index(ModelAndView mav) {
//        mav.setViewName("index"); // index -> return 으로 이 코드는 삭제
//        mav.addObject("msg", "current data."); // model 로 변경
//        DataObject obj = new DataObject("123", "lee", "lee@flower");
//        mav.addObject("object", obj); // model 로 변경
//        return mav;
//    }
    @GetMapping("/")
    public String index(Model model) {
//        model.addAttribute("msg", "current data.");
        model.addAttribute("msg", "message 1<hr/>message 2<br/>message 3"); // utext로 태그 사용
        DataObject obj = new DataObject(1001, "홍길동", "hong@korea.com");
        model.addAttribute("object", obj);
        return "index";
    }

    @GetMapping("/id/{id}")
    public String index(@PathVariable int id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("check", id >= 0); // index.html view - ${check}
        model.addAttribute("trueVal", "POSITIVE!");
        model.addAttribute("falseVal", "NEGATIVE!");
        // @PathVariable 사용. attribute는 객체, 리스트, 참거짓 다 사용할 수 있음

        DataObject obj = new DataObject(1001, "홍길동", "hong@korea.com");
        model.addAttribute("object", obj);
        return "index";
    }

    @RequestMapping("/month/{month}")
    public ModelAndView index(@PathVariable int month, ModelAndView mav) {
        mav.setViewName("index2");
        int m = Math.abs(month) % 12; // 절대값 month
        m = m == 0 ? 12 : m;
        mav.addObject("month", m);
        mav.addObject("check", Math.floor(m / 3)); // index2.html view - ${check}
        return mav;
    }

    @RequestMapping("/index3")
    public ModelAndView index3(ModelAndView mav) {
        mav.setViewName("index3");
        ArrayList<String[]> data = new ArrayList<>();
        data.add(new String[]{"park", "park@yamada", "090-999-999"});
        data.add(new String[]{"lee", "lee@flower", "080-888-888"});
        data.add(new String[]{"choi", "choi@happy", "070-777-777"});
        mav.addObject("data", data);
        return mav;
    }

    @RequestMapping("/index4")
    public ModelAndView index4(ModelAndView mav) {
        mav.setViewName("index4");
        ArrayList<String[]> data = new ArrayList<>();
        data.add(new String[]{"park", "park@yamada", "090-999-999"});
        data.add(new String[]{"lee", "lee@flower", "080-888-888"});
        data.add(new String[]{"choi", "choi@happy", "070-777-777"});
        mav.addObject("data", data);
        return mav;
    }

    @RequestMapping("/tax/{tax}")
    public ModelAndView index5(@PathVariable int tax, ModelAndView mav) {
        mav.setViewName("index5");
        mav.addObject("tax", tax); // 자바스크립트로 보냄
        return mav;
    }
}
