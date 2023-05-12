package com.salah.emailVerfication.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salah.emailVerfication.entity.User;
import com.salah.emailVerfication.exceptions.UserAlreadyExistsException;
import com.salah.emailVerfication.registeration.RegistrationRequest;
import com.salah.emailVerfication.registeration.password.PasswordResetTokenService;
import com.salah.emailVerfication.registeration.token.VerificationToken;
import com.salah.emailVerfication.registeration.token.VerificationTokenRepository;
import com.salah.emailVerfication.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

	   private final UserRepository userRepository;
	    private final PasswordEncoder passwordEncoder;
	    private final VerificationTokenRepository tokenRepository;
	    private final PasswordResetTokenService passwordResetTokenService;
	    @Override
	    public List<User> getUsers() {
	        return userRepository.findAll();
	    }

	    @Override
	    public User registerUser(RegistrationRequest request) {
	       Optional<User> user = this.findByEmail(request.email());
	       if (user.isPresent()){
	           throw new UserAlreadyExistsException(
	                   "User with email "+request.email() + " already exists");
	       }
	       var newUser = new User();
	       newUser.setFirstName(request.firstName());
	       newUser.setLastName(request.lastName());
	       newUser.setEmail(request.email());
	       newUser.setPassword(passwordEncoder.encode(request.password()));
	       newUser.setRole(request.role());
	        return userRepository.save(newUser);
	    }

	    @Override
	    public Optional<User> findByEmail(String email) {
	        return userRepository.findByEmail(email);
	    }

	    @Override
	    public void saveUserVerificationToken(User theUser, String token) {
	        var verificationToken = new VerificationToken(token, theUser);
	        tokenRepository.save(verificationToken);
	    }
	    @Override
	    public String validateToken(String theToken) {
	        VerificationToken token = tokenRepository.findByToken(theToken);
	        if(token == null){
	            return "Invalid verification token";
	        }
	        User user = token.getUser();
	        Calendar calendar = Calendar.getInstance();
	        if ((token.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
	            return "Verification link already expired," +
	                    " Please, click the link below to receive a new verification link";
	        }
	        user.setEnabled(true);
	        userRepository.save(user);
	        return "valid";
	    }

	    @Override
	    public VerificationToken generateNewVerificationToken(String oldToken) {
	       VerificationToken verificationToken = tokenRepository.findByToken(oldToken);
	       var tokenExpirationTime = new VerificationToken();
	        verificationToken.setToken(UUID.randomUUID().toString());
	        verificationToken.setExpirationTime(tokenExpirationTime.getTokenExpirationTime());
	        return tokenRepository.save(verificationToken);
	    }

	    public void resetPassword(User theUser, String newPassword) {
	        theUser.setPassword(passwordEncoder.encode(newPassword));
	        userRepository.save(theUser);
	    }

	    @Override
	    public String validatePasswordResetToken(String token) {
	        return passwordResetTokenService.validatePasswordResetToken(token);
	    }

	    @Override
	    public User findUserByPasswordToken(String token) {
	        return passwordResetTokenService.findUserByPasswordToken(token).get();
	    }

	    @Override
	    public void createPasswordResetTokenForUser(User user, String passwordResetToken) {
	        passwordResetTokenService.createPasswordResetTokenForUser(user, passwordResetToken);
	    }

}
