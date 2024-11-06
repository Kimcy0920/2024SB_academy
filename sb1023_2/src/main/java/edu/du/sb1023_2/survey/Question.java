package edu.du.sb1023_2.survey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String title;

	@ElementCollection
	private List<String> options;

	public Question(String title, List<String> options) {
		this.title = title;
		this.options = options;
	}

	public Question(String title) {
		this(title, Collections.emptyList());
	}

	public String getTitle() {
		return title;
	}

	public List<String> getOptions() {
		return options;
	}

	public boolean isChoice() {
		return options != null && !options.isEmpty();
	}

}
