package edu.du.sb1024.survey;

import edu.du.sb1024.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

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

	@ManyToOne // 여러 Respondent가 하나의 Member에 속할 수 있음
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
}



