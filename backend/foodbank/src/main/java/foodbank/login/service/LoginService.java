package foodbank.login.service;

import foodbank.login.dto.LoginDTO;
import foodbank.login.entity.ResetToken;
import foodbank.user.dto.UserDTO;

public interface LoginService {

	void authenticate(final LoginDTO loginDetails);
	
	void authenticateVolunteers(final String dailyPassword);
	
	void resetPassword(final UserDTO user) throws Exception;
	
	void changeForgottenPassword(final ResetToken token) throws Exception;
	
}
