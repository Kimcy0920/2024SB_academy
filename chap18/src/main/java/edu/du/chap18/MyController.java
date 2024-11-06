package edu.du.chap18;

import edu.du.chap18.model.Article;
import edu.du.chap18.model.ArticleListModel;
import edu.du.chap18.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyController {

    @Autowired
    ListArticleService listSerivce;

    @Autowired
    ReadArticleService readSerivce;

    @Autowired
    DeleteArticleService deleteSerivce;

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String list(Model model, HttpServletRequest request) {
        String pageNumberString = request.getParameter("p");
        int pageNumber = 1;
        if (pageNumberString != null && pageNumberString.length() > 0) {
            pageNumber = Integer.parseInt(pageNumberString);
        }
        ArticleListModel articleListModel = listSerivce.getArticleList(pageNumber);
        model.addAttribute("listModel", articleListModel);

        if (articleListModel.getTotalPageCount() > 0) {
            int beginPageNumber =
                    (articleListModel.getRequestPage() - 1) / 10 * 10 + 1;
            int endPageNumber = beginPageNumber + 9;
            if (endPageNumber > articleListModel.getTotalPageCount()) {
                endPageNumber = articleListModel.getTotalPageCount();
            }
            model.addAttribute("beginPage", beginPageNumber);
            model.addAttribute("endPage", endPageNumber);
        }
        return "list_view";
    }

    @GetMapping("/read")
    public String read(Model model, HttpServletRequest request) {
        int articleId = Integer.parseInt(request.getParameter("articleId"));
        try {
            Article article = readSerivce.readArticle(articleId);
            model.addAttribute("article", article);
            return "read_view";
        } catch (ArticleNotFoundException ex) {
            return "/article_not_found";
        }
    }

    @GetMapping("/writeForm")
    public String writeForm() {
        return "writeForm";
    }
    @PostMapping("/write")
    public String write() {

        return "redirect:/list";
    }

    @GetMapping("/delete_form")
    public String delete() {
        return "delete_Form";
    }
    @GetMapping("/delete")
    public String delete(Model model, HttpServletRequest request) {
        int articleId = Integer.parseInt(request.getParameter("articleId"));
        try {
            Article article = deleteSerivce.deleteArticle(articleId);
            return "/delete_success";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "/delete_error";
        }
    }
    @GetMapping("/delete_success")
    public String deleteSuccess() {
        return "redirect:/list";
    }
    @GetMapping("/delete_error")
    public String deleteError() {
        return "delete_error";
    }

    @GetMapping("/article_not_found")
    public String articleNotFound() {
        return "article_not_found";
    }
}
