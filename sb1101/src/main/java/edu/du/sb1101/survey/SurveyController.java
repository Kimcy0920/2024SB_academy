package edu.du.sb1101.survey;

import edu.du.sb1101.registerMember.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/survey")
public class SurveyController {

	@Autowired
	SurveyService surveyService;

	@GetMapping("/surveyForm")
	public String form(Member member, Model model, HttpSession session) {
		Member mem = (Member) session.getAttribute("member");
		if (mem == null) {
			return "redirect:/sample/login";
		}
		model.addAttribute("username", mem.getUsername());
		List<Question> questions = createQuestions();
		for (Question question : questions) {
			System.out.println(question);
		}
		model.addAttribute("questions", questions);
		// 기본 AnsweredData 객체 생성 및 초기화
		AnsweredData ansData = new AnsweredData();
		ansData.setResponses(new ArrayList<>());  // 빈 리스트로 초기화
		ansData.setRes(new Respondent());         // Respondent 객체도 초기화
		model.addAttribute("ansData", ansData);   // 모델에 추가
		return "/survey/surveyForm";
	}

	private List<Question> createQuestions() {
		Question q1 = new Question("당신이 주로 즐겨하는 서브컬쳐 게임은 무엇입니까?",
				Arrays.asList("니케", "명조", "블루아카이브", "트릭컬 리바이브", "기타"));
		Question q2 = new Question("여러 종류의 서브컬쳐 게임을 플레이 하고 계신가요?",
				Arrays.asList("1개", "2개", "3개", "4개", "5개 이상"));
		Question q3 = new Question("(기타 선택 시) 게임명을 작성해주세요.");
		return Arrays.asList(q1, q2, q3);
	}

	@PostMapping("/submitted")
	public String submit(@ModelAttribute("ansData") AnsweredData data,
						 HttpSession session, Model model) {
		Member mem = (Member) session.getAttribute("member");
		if (mem == null) {
			return "redirect:/sample/login";
		}

		model.addAttribute("username", mem.getUsername());
		data.setMember(mem); // Member 설정

		// 서비스 계층에 저장 위임
		surveyService.save(data);

		return "/survey/submitted";
	}
}

