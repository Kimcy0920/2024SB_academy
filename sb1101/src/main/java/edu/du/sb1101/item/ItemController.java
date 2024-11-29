package edu.du.sb1101.item;

import edu.du.sb1101.registerMember.entity.Member;
import edu.du.sb1101.registerMember.repository.MemberRepository;
import edu.du.sb1101.registerMember.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/item")
@Controller
public class ItemController {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final TitleRepository titleRepository;

    @GetMapping("/itemShop")
    public String itemShop(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            return "redirect:/sample/login";
        }
        model.addAttribute("username", member.getUsername());
        model.addAttribute("role", member.getRole());
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "/item/itemShop";
    }

    @GetMapping("/itemBuy")
    public String itemBuy(HttpSession session, Model model) {

        return "redirect:/item/itemShop";
    }
}
