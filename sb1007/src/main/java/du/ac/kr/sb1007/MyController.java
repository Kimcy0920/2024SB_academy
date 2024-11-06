package du.ac.kr.sb1007;

import du.ac.kr.sb1007.simpleDAO.ISimpleDAO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyController {

    @Autowired
    ISimpleDAO dao;

    @GetMapping("/")
    public String index() {
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("list", dao.selectDAO());
        return "list";
    }

    @GetMapping("/view")
    public String view(String id, Model model) {
        model.addAttribute("dto", dao.selectOneDAO(id));
        return "view";
    }

    @GetMapping("/writeForm")
    public String writeForm() {
        return "writeForm";
    }
//    @PostMapping("/write")
//    public String writeForm(HttpServletRequest request) {
//        dao.insertDAO(request.getParameter("writer"),
//                request.getParameter("title"),
//                request.getParameter("content"));
//        return "redirect:/list";
//    }
    @PostMapping("/write")
    public String writeForm(HttpServletRequest request, Model model) {
        model.addAttribute("dto", dao.insertDAO(
                request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("content")));
        return "redirect:/list";
    }

    @GetMapping("/updateForm")
    public String updateForm(String id, Model model) {
        model.addAttribute("dto", dao.selectOneDAO(id));
        return "updateForm";
    }
    @PostMapping("/update")
    public String update(HttpServletRequest request) {
        dao.updateDAO(request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("content"),
                request.getParameter("id"));
        return "redirect:/list";
    }

    @GetMapping("/delete")
    public String delete(String id) {
        dao.deleteDAO(id);
        return "redirect:/list";
    }
}
