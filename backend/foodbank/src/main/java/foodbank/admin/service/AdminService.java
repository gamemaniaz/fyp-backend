package foodbank.admin.service;

import java.text.ParseException;

import foodbank.admin.dto.AdminSettingsDTO;
import foodbank.admin.dto.WindowDataDTO;
import foodbank.user.dto.UserDTO;

public interface AdminService {
	
	WindowDataDTO retrieveWindowData();
	
	Boolean getWindowStatus();
	
	void modifyDecayRate(final AdminSettingsDTO adminSettings);

	void modifyMultiplierRate(final AdminSettingsDTO adminSettings);
	
	void modifyClosingDate(final AdminSettingsDTO adminSettings) throws ParseException;
	
	String toggleWindow(final AdminSettingsDTO adminSettings) throws ParseException;
	
	void insertPastRequests();
	
	void generateEmails() throws Exception;
	
	void generateDailyPassword();
	
	void resetPassword(final UserDTO user) throws Exception;
	
	void clearWindowData();
	
}
