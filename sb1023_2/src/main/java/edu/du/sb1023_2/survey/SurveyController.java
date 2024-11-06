package edu.du.sb1023_2.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class SurveyController {

	@Autowired
	private AnsweredDataRepository answeredDataRepository;

	@GetMapping("/survey")
	public String form(@ModelAttribute("ansData") AnsweredData data, Model model) {
		List<Question> questions = createQuestions();
		model.addAttribute("questions", questions);
		model.addAttribute("ansData", data);
		return "survey/surveyForm"; // 템플릿 경로
	}

	private List<Question> createQuestions() {
		Question q1 = new Question("당신의 역할은 무엇입니까?", Arrays.asList("서버", "프론트", "풀스택"));
		Question q2 = new Question("많이 사용하는 개발도구는 무엇입니까?", Arrays.asList("이클립스", "인텔리J", "서브라임"));
		Question q3 = new Question("하고 싶은 말을 적어주세요.");
		return Arrays.asList(q1, q2, q3);
	}

	@PostMapping("/survey2") // 경로 추가
	public String submit(@ModelAttribute("ansData") AnsweredData data) {
		// Respondent 객체 생성
		Respondent respondent = new Respondent();
		respondent.setAge(data.getRes().getAge());
		respondent.setLocation(data.getRes().getLocation());

		// Respondent와 AnsweredData 연결
		data.setRes(respondent);

		// 응답 데이터를 데이터베이스에 저장
		answeredDataRepository.save(data);

		return "survey/submitted"; // 응답 후 페이지
	}
}
