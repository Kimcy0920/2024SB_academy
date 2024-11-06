package edu.du.sb1014;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample") // url: http://localhost:8881/sample/ex1 /sample - 기본적으로 앞에 붙여야함
@Log4j2
public class SampleController2 {

    @Autowired
    MemoRepository memoRepository; // DB memo 테이블 연동

    @GetMapping("/ex1")
    public void sample(Model model) {
        for (Memo memo : memoRepository.findAll()) {
        log.info(memo);
        }
        // [thymeleaf] resources.templates.sample.ex1.html
        model.addAttribute("msg", memoRepository.findById(3L)); // 하나의 자료 조회
        model.addAttribute("list", memoRepository.findAll()); // 모든 자료 조회
    }
}
