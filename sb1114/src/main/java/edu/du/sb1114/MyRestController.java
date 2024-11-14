package edu.du.sb1114;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MyRestController {

    @GetMapping("/hello")
    public String hello() {
        return "<h1>ㅎㅇ?</h1>";
    }

    @GetMapping("/account/{name}")
    public String account(@PathVariable("name") String name) {
        // @PathVariable는 primary key 값을 넣을 때 주로 사용함.
        log.info(name);
        return name;
    }

    @PostMapping("/account")
    public Account account(@RequestBody Account account) {
        log.info(account.toString());
        return account;
    }
//    @PostMapping("/account")
//    public ResponseEntity<?> account(@RequestBody Account account) {
//        log.info(account.toString());
//        return ResponseEntity.ok(account);
//    }
}
