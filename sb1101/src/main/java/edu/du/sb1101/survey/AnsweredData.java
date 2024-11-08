package edu.du.sb1101.survey;

import edu.du.sb1101.registerMember.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnsweredData { // AnsweredData 클래스를 두 개로 쪼개놓음

	@Id @GeneratedValue
	@Column(name = "ANSWERED_ID") // line25-29. responses "MEMBER_ID"와 조인됨.
	private int id;

	// * responses (부속)테이블을 만드는 코드 {
	@ElementCollection // collection = list, hashMap 같은걸 의미함
	@CollectionTable( // 1:N / 이 어노테이션을 쓰면 '부속 테이블'을 만들어줌
			name = "responses", // 테이블명, 아래 리스트형식의 responses를 테이블로 만드는 것
			joinColumns = @JoinColumn(name = "ANSWERED_ID") // JoinColumn을 사용해 AnsweredData 테이블의 "MEMBER_ID"와 조인한 것.
	)
	@OrderColumn // 배열을 입력받는데 순번을 정해줌.
	@Column(name = "seq") // responses 테이블에 "seq" 칼럼이 만들어짐
	private List<String> responses = new ArrayList<>(); // ArrayList 형식의 responses
	// } *

	@OneToOne // 1:1관계
	@JoinColumn(name = "RESPONDENT_ID") // respondent와 "RESPONDENT_ID" 조인함
	private Respondent res;

	// Cascade 추가: Member 저장 시 AnsweredData도 함께 저장
	@ManyToOne(cascade = CascadeType.ALL) // 여러 AnsweredData가 하나의 Member에 속할 수 있음
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
}



