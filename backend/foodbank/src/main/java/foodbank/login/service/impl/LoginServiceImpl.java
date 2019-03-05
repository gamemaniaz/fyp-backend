package foodbank.login.service.impl;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

import foodbank.login.dto.LoginDTO;
import foodbank.login.entity.ResetToken;
import foodbank.login.repository.ResetTokenRepository;
import foodbank.login.service.LoginService;
import foodbank.user.dto.UserDTO;
import foodbank.user.entity.User;
import foodbank.user.repository.UserRepository;
import foodbank.util.AutomatedEmailer;
import foodbank.util.MessageConstants.EmailMessages;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.InvalidLoginException;
import foodbank.util.exceptions.InvalidTokenException;
import foodbank.util.exceptions.UserException;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ResetTokenRepository resetRepository;
	
	@Autowired
	private ServletContext servletContext;
	
	private static final String HOST_ADDRESS = "http://foodbank-client.s3-website-ap-southeast-1.amazonaws.com";
	
	@Override
	public void authenticate(LoginDTO loginDetails) {
		// TODO Auto-generated method stub
		User dbUser = userRepository.findByUsernameIgnoreCase(loginDetails.getUsername());
		if(dbUser == null) {
			throw new UserException(ErrorMessages.NO_SUCH_USER);
		}
		if(!BCrypt.checkpw(loginDetails.getPassword(), dbUser.getPassword())) {
			throw new InvalidLoginException(ErrorMessages.INVALID_CREDENTIALS);
		}
		loginDetails.setUsertype(dbUser.getUsertype());
	}

	@Override
	public void authenticateVolunteers(String dailyPassword) {
		// TODO Auto-generated method stub
		User volunteer = userRepository.findByUsernameIgnoreCase("volunteer");
		if(!BCrypt.checkpw(dailyPassword, volunteer.getPassword())) {
			throw new InvalidLoginException(ErrorMessages.INVALID_CREDENTIALS);
		}
	}

	@Override
	public void resetPassword(UserDTO user) throws Exception {
		// TODO Auto-generated method stub
		User dbUser = userRepository.findByEmailIgnoreCase(user.getEmail());
		if(dbUser == null) {
			throw new UserException(ErrorMessages.NO_SUCH_USER_EMAIL);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 30);
		UUID uuid = UUID.randomUUID();
		// servletContext.getContextPath() + 
		URL url = new URL("http://foodbank-client.s3-website-ap-southeast-1.amazonaws.com/reset-password/" + uuid.toString());
		ResetToken resetRequest = new ResetToken(dbUser.getUsername(), uuid.toString(), calendar.getTime());
		resetRepository.save(resetRequest);
		new AutomatedEmailer(user.getEmail(), EmailMessages.RESET_PASSWORD_SUBJECT, EmailMessages.FORGOT_PASSWORD_STARTER 
				+ dbUser.getName().toUpperCase() + EmailMessages.FORGOT_PASSWORD_MESSAGE + url.toString());
	}
	
	@Override
	public void changeForgottenPassword(ResetToken token) throws Exception {
		ResetToken resetRequest = resetRepository.findByToken(token.getToken());
		if(resetRequest == null) {
			throw new InvalidTokenException(ErrorMessages.INCORRECT_RESET_TOKEN);
		}
		Date expirationDate = resetRequest.getExpirationDate();
		if(expirationDate.after(new Date())) {
			User dbUser = userRepository.findByUsernameIgnoreCase(resetRequest.getUsername());
			String newPassword = RandomStringUtils.random(8, true, true).toUpperCase();
			new AutomatedEmailer(dbUser.getEmail(), EmailMessages.RESET_PASSWORD_SUBJECT, EmailMessages.RESET_PASSWORD_MESSAGE1 + newPassword + EmailMessages.RESET_PASSWORD_MESSAGE2);
			dbUser.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
			userRepository.save(dbUser);
			resetRepository.delete(resetRequest);
		} else {
			throw new InvalidTokenException(ErrorMessages.EXPIRED_TOKEN);
		}
	}
	
}
