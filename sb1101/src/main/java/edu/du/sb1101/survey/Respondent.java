package edu.du.sb1101.survey;

import edu.du.sb1101.registerMember.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Respondent {

	@Id @GeneratedValue
	@Column(name = "RESPONDENT_ID") // answeredData 테이블과 "RESPONDENT_ID" 조인함.
	private int id;

	private int age;
	private String location;

	// Cascade 추가: Member 저장 시 Respondent도 함께 저장
	@ManyToOne(cascade = CascadeType.ALL) // 여러 Respondent가 하나의 Member에 속할 수 있음
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

}



