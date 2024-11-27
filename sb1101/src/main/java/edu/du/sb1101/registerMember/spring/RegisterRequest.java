package edu.du.sb1101.registerMember.spring;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class RegisterRequest {

	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	private String password;
	@NotEmpty
	private String confirmPassword;
	@NotEmpty
	private String name;
	@NotEmpty
	private String address;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isPasswordEqualToConfirmPassword() {
		return password.equals(confirmPassword);
	}
}
