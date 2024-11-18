package edu.du.sb1101.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/store")
@Controller
public class ItemController {

    @GetMapping("/storeList")
    public String storeList() {
        return "/store/storeList";
    }
}
