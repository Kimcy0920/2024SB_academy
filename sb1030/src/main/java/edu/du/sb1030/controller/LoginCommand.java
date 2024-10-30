package edu.du.sb1030.controller;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class LoginCommand {

	private String email;
	private String password;
	private boolean rememberEmail;

}
