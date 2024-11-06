package edu.du.sb1024.controller;

import edu.du.sb1024.entity.Member;
import edu.du.sb1024.survey.AnsweredData;
import edu.du.sb1024.survey.Question;
import edu.du.sb1024.survey.Respondent;
import edu.du.sb1024.survey.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/survey")
public class SurveyController {

	@Autowired
	SurveyService surveyService;

	@GetMapping("/surveyForm")
	public String form(Member member, Model model) {
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
		Question q1 = new Question("당신의 역할은 무엇입니까?",
				Arrays.asList("서버", "프론트", "풀스택"));
		Question q2 = new Question("많이 사용하는 개발도구는 무엇입니까?",
				Arrays.asList("이클립스", "인텔리J", "서브라임"));
		Question q3 = new Question("하고 싶은 말을 적어주세요.");
		return Arrays.asList(q1, q2, q3);
	}

	@PostMapping("/submitted")
	public String submit(@ModelAttribute("ansData") AnsweredData data,
						 @AuthenticationPrincipal Member member) {
		data.setMember(member); // 로그인한 멤버 설정

		Respondent respondent = data.getRes();
		respondent.setMember(member); // Respondent에도 Member 설정

		surveyService.save(data); // 저장
		return "/survey/submitted";
	}
//	@GetMapping("/submitted")
//	public String submit2(Model model) {
//
//		return "/survey/submitted";
//	}
}
