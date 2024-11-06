package edu.du.sb1030.spring;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class AuthInfo {

	private Long id;
	private String email;
	private String name;

}
