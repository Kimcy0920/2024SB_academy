package edu.du.sb1030.controller;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ChangePwdCommand {

	private String currentPassword;
	private String newPassword;

}
