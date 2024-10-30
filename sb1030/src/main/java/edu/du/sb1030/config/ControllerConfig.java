package edu.du.sb1030.config;

import edu.du.sb1030.spring.AuthService;
import edu.du.sb1030.spring.ChangePasswordService;
import edu.du.sb1030.spring.MemberRegisterService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Configuration
public class ControllerConfig {

	@Autowired
	private MemberRegisterService memberRegSvc;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private ChangePasswordService changePasswordService;

}
