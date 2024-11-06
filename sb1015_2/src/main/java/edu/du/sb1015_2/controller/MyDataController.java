package edu.du.sb1015_2.controller;

import edu.du.sb1015_2.entity.MyData;
import edu.du.sb1015_2.repository.MyDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class MyDataController {

    final MyDataRepository repository; // @Autowired없이 주입하는 방식

    @GetMapping("/")
    public String index(@ModelAttribute("formModel") MyData mydata, Model model) {
        model.addAttribute("msg", "오늘은 치킨 먹는 날입니다.");
        List<MyData> list = repository.findAll();
        model.addAttribute("datalist", list);
        return "index";
    }

    @PostMapping("/")
    public String form(MyData mydata) {
        repository.save(mydata);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}") // id값 넘기기
    public String edit(@PathVariable Long id, @ModelAttribute MyData mydata, Model model) {
        Optional<MyData> mydata1 = repository.findById(id); // findById는 Optional을 사용해 null을 방지해야함
        model.addAttribute("formModel", mydata1.get()); // Optional를 사용해서 .get()를 붙여야함
        return "edit";
    }
    @PostMapping("/edit")
    public String edit(@ModelAttribute MyData mydata) {
        repository.saveAndFlush(mydata);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, @ModelAttribute MyData mydata, Model model) {
        Optional<MyData> mydata1 = repository.findById(id);
        model.addAttribute("formModel", mydata1.get());
        return "delete";
    }
    @PostMapping("/delete")
    public String delete(@ModelAttribute MyData mydata) {
        repository.delete(mydata);
        return "redirect:/";
    }
}
