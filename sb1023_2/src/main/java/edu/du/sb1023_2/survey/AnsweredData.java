package edu.du.sb1023_2.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AnsweredData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ElementCollection
	private List<String> responses;

	@ManyToOne
	@JoinColumn(name = "respondent_id")
	private Respondent res;

}
