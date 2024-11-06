package du.ac.kr.sb1006;

import du.ac.kr.sb1006.dao.ISimpleBbsDao;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyController {

    @Autowired
    ISimpleBbsDao dao;

    @GetMapping("/") // 요청명. /localhost:8087/test <<
    public String root() {
        return "redirect:/list"; // 파일명. /WEB-INF/views/test1.jsp <<
        // redirect: /list를 시작페이지로 설정한 것
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("list", dao.listDao());
        return "list";
    }

    @GetMapping("/view")
    public String view(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        model.addAttribute("dto", dao.viewDao(id));
        return "view";
    }

    @GetMapping("/writeForm")
    public String writeForm() {
        return "writeForm";
    }

//    @PostMapping("/write")
//    public String write(HttpServletRequest request, Model model) {
//        String writer = request.getParameter("writer");
//        String title = request.getParameter("title");
//        String content = request.getParameter("content");
//        model.addAttribute("dto", dao.writeDao(writer, title, content));
//        return "redirect:/list";
//    }

    @PostMapping("/write")
    public String write(HttpServletRequest request) {
        dao.writeDao(request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("content"));
        return "redirect:/list";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        dao.deleteDao(id);
//        model.addAttribute("dto", dao.deleteDao(id));
        return "redirect:/list";
    }

    @GetMapping("/updateForm")
    public String updateForm(String id, Model model) { // id를 파라미터로 받아도 됨
        model.addAttribute("dto", dao.viewDao(id));
        return "updateForm";
    }
//    @GetMapping("/updateForm")
//    public String updateForm(HttpServletRequest request, Model model) {
//        String id = request.getParameter("id");
//        model.addAttribute("dto", dao.viewDao(id));
//        return "updateForm";
//    }

    @PostMapping("/update")
    public String update(HttpServletRequest request, Model model) {
//        String writer = request.getParameter("writer");
//        String title = request.getParameter("title");
//        String content = request.getParameter("content");
//        String id = request.getParameter("id");
//        model.addAttribute("dto", dao.updateDao(writer, title, content, id));
        dao.updateDao(request.getParameter("writer"),
                request.getParameter("title"),
                request.getParameter("content"),
                request.getParameter("id"));
        return "redirect:/list";
    }
}
