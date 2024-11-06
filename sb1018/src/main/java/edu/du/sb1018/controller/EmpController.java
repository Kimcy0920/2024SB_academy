package edu.du.sb1018.controller;

import edu.du.sb1018.entity.Emp;
import edu.du.sb1018.service.EmpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class EmpController {

    final EmpService empService;

    @GetMapping("/")
    public String dept() {
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Emp> list = empService.find_Emp();
        model.addAttribute("datalist", list);
        return "list";
    }

    @GetMapping("/insert")
    public String insert() {
        return "insert";
    }

    @PostMapping("/insert2")
    public String insert2(@ModelAttribute Emp emp) {
        empService.insert_Emp(emp);
        return "redirect:/list";
    }

    @GetMapping("/update/{empno}")
    public String update(@PathVariable Integer empno, Model model) {
        Optional<Emp> emp = empService.find_Empno(empno);
        model.addAttribute("obj", emp.get());  // 한 줄의 정보만 반환
        return "update";
    }

    @PostMapping("/edit")
    public String edit(Emp emp) {
        empService.update_Emp(emp);
        return "redirect:/list";
    }

    @PostMapping("/delete/{empno}")
    public String delete(@PathVariable Integer empno) {
        empService.remove_Emp(empno);
        return "redirect:/list";
    }

}
