package edu.du.api_test.test02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BusinessController {

    @Autowired
    private BusinessValidationService businessValidationService;

    @Autowired
    private BusinessRepository businessRepository;

    @GetMapping("/validate")
    public String showValidationPage() {
        return "validate";
    }

    @PostMapping("/validate")
    public String validateBusinessNumber(@RequestParam("businessNumber") String businessNumber, Model model) {
        boolean isValid = businessValidationService.validateBusinessNumber(businessNumber);

        if (isValid) {
            model.addAttribute("message", "인증 완료!");
            // DB 저장
            BusinessEntity entity = new BusinessEntity();
            entity.setBusinessNumber(businessNumber);
            businessRepository.save(entity);
        } else {
            model.addAttribute("message", "없는 정보입니다.");
        }

        return "validate";
    }
}
