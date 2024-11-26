package edu.du.sb1101.survey;

import edu.du.sb1101.registerMember.entity.Member;
import edu.du.sb1101.registerMember.repository.MemberRepository;
import edu.du.sb1101.registerMember.service.MemberService;
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
import java.util.Map;

@Controller
@RequestMapping("/survey")
public class SurveyController {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberService memberService;

	@Autowired
	SurveyService surveyService;

	@Autowired
	AnsweredDataRepository ansRepository;

	@GetMapping("/surveyForm")
	public String form(Member member, Model model, HttpSession session) {
		Member mem = (Member) session.getAttribute("member");
		if (mem == null) {
			return "redirect:/sample/login";
		}

		// 회원이 이미 설문에 참여한 경우
		AnsweredData existingAnswer = ansRepository.findByMemberId(mem.getId());
		if (existingAnswer != null) {
			return "redirect:/survey/submitted";  // 설문결과 페이지로 리다이렉팅
		}

		model.addAttribute("username", mem.getUsername());
		model.addAttribute("role", mem.getRole());
		List<Question> questions = createQuestions();
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
		Question q2 = new Question("몇 개의 서브컬쳐 게임을 플레이 하고 계신가요?",
				Arrays.asList("1개", "2개", "3개", "4개", "5개 이상"));
		Question q3 = new Question("(기타 선택 시) 게임명을 작성해주세요.");
		return Arrays.asList(q1, q2, q3);
	}

	@GetMapping("/submitted") // 이미 설문에 참여한 경우에 나오는 결과페이지
	public String submitted(HttpSession session, Model model) {
		Member mem = (Member) session.getAttribute("member");
		if (mem == null) {
			return "redirect:/sample/login";
		}

		AnsweredData existingAnswer = ansRepository.findByMemberId(mem.getId());
		if (existingAnswer == null) {
			return "redirect:/survey/surveyForm";  // 설문을 아직 응답하지 않았다면 설문 폼으로 리다이렉트
		}
		Map<String, Double> statistics = surveyService.getStatistics();
		model.addAttribute("statistics", statistics); // 통계 자료 전달

		model.addAttribute("ansData", existingAnswer);
		model.addAttribute("username", mem.getUsername());
		model.addAttribute("role", mem.getRole());

		return "/survey/submitted";  // 설문 결과 페이지로 이동
	}
	@PostMapping("/submitted")
	public String submit(@ModelAttribute("ansData") AnsweredData data,
						 HttpSession session, Model model) {
		Member mem = (Member) session.getAttribute("member");
		if (mem == null) {
			return "redirect:/sample/login";
		}

		model.addAttribute("username", mem.getUsername());
		model.addAttribute("role", mem.getRole());
		data.setMember(mem); // Member 설정

		// 서비스 계층에 저장 위임
		surveyService.save(data);

		// 포인트 적립 로직 추가
		// 동일한 설문조사 응답에 대해 중복 적립되지 않도록 로직 추가
		AnsweredData existingAnswer = ansRepository.findByMemberId(mem.getId());
		if (existingAnswer == null) {
			memberService.addPoints(mem.getUsername(), 50, "설문조사 +50포인트 적립");

			// 업데이트된 Member 정보 세션에 반영
			Member updatedMember = memberRepository.findByUsername(mem.getUsername());
			if (updatedMember != null) {
				session.setAttribute("member", updatedMember);
			}
		}

		return "/survey/submitted";
	}
}

