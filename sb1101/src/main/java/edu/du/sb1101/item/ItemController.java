package edu.du.sb1101.item;

import edu.du.sb1101.registerMember.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Slf4j
@RequestMapping("/store")
@Controller
public class ItemController {

    @GetMapping("/storeList")
    public String storeList(@RequestParam(value = "keyword", required = false) String keyword, // 검색 키워드
                            Model model,
                            @PageableDefault(page = 0, size = 10, sort = "itemId", direction = Sort.Direction.DESC) Pageable pageable,
                            HttpSession session) throws Exception {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "redirect:/sample/login";
        }
        model.addAttribute("username", member.getUsername());
        model.addAttribute("role", member.getRole());

        return "/store/storeList";
    }
}
