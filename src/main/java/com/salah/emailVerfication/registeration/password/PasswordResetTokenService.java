package com.salah.emailVerfication.registeration.password;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.salah.emailVerfication.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {
	private final PasswordResetTokenRepository passwordResetTokenRepository;

	public void createPasswordResetTokenForUser(User user, String passwordToken) {
		PasswordResetToken passwordRestToken = new PasswordResetToken(passwordToken, user);
		passwordResetTokenRepository.save(passwordRestToken);
	}

	public String validatePasswordResetToken(String passwordResetToken) {
		PasswordResetToken passwordToken = passwordResetTokenRepository.findByToken(passwordResetToken);
		if (passwordToken == null) {
			return "Invalid password reset token";
		}
		User user = passwordToken.getUser();
		Calendar calendar = Calendar.getInstance();
		if ((passwordToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
			return "Link already expired, resend link";
		}
		return "valid";
	}

	public Optional<User> findUserByPasswordToken(String passwordResetToken) {
		return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordResetToken).getUser());
	}

	public PasswordResetToken findPasswordResetToken(String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

}
