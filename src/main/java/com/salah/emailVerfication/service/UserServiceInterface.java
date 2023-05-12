package com.salah.emailVerfication.service;

import java.util.List;
import java.util.Optional;

import com.salah.emailVerfication.entity.User;
import com.salah.emailVerfication.registeration.RegistrationRequest;
import com.salah.emailVerfication.registeration.token.VerificationToken;

public interface UserServiceInterface {
	List<User> getUsers();

	User registerUser(RegistrationRequest request);

	Optional<User> findByEmail(String email);

	void saveUserVerificationToken(User theUser, String verificationToken);

	String validateToken(String theToken);

	VerificationToken generateNewVerificationToken(String oldToken);

	void resetPassword(User theUser, String newPassword);

	String validatePasswordResetToken(String token);

	User findUserByPasswordToken(String token);

	void createPasswordResetTokenForUser(User user, String passwordResetToken);

}
