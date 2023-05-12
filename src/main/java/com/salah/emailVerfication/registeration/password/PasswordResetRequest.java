package com.salah.emailVerfication.registeration.password;

import lombok.Data;

@Data
public class PasswordResetRequest {
	private String email;
	private String oldPassword;
	private String newPassword;

}
