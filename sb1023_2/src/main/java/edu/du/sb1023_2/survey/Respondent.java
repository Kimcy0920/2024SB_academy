package edu.du.sb1023_2.survey;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Respondent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private int age;

	private String location;

	@OneToMany(mappedBy = "res")
	private List<AnsweredData> answeredDataList;

}
