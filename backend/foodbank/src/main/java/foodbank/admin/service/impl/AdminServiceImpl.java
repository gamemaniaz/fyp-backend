package foodbank.admin.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import foodbank.admin.dto.AdminSettingsDTO;
import foodbank.admin.dto.WindowDataDTO;
import foodbank.admin.entity.AdminSettings;
import foodbank.admin.repository.AdminRepository;
import foodbank.admin.service.AdminService;
import foodbank.allocation.entity.AllocatedFoodItem;
import foodbank.allocation.entity.Allocation;
import foodbank.allocation.repository.AllocatedFoodItemRepository;
import foodbank.allocation.repository.AllocationRepository;
import foodbank.beneficiary.entity.Beneficiary;
import foodbank.history.entity.PastRequest;
import foodbank.history.entity.RequestHistory;
import foodbank.history.repository.HistoryRepository;
import foodbank.inventory.entity.FoodItem;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;
import foodbank.security.model.Role;
import foodbank.security.model.repository.RoleRepository;
import foodbank.user.dto.UserDTO;
import foodbank.user.entity.User;
import foodbank.user.repository.UserRepository;
import foodbank.util.AutomatedEmailer;
import foodbank.util.MessageConstants;
import foodbank.util.MessageConstants.EmailMessages;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.UserException;

@Service
public class AdminServiceImpl implements AdminService {

	private static final Long idKey = Long.valueOf(1);
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private AllocationRepository allocationRepository;
	
	@Autowired
	private HistoryRepository historyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AllocatedFoodItemRepository allocatedFoodItemRepository;
	
	@Override
	public WindowDataDTO retrieveWindowData() {
		// TODO Auto-generated method stub
		AdminSettings currentSettings = adminRepository.findById(idKey);
		Date startDate = currentSettings.getWindowStartDateTime();
		Date endDate = currentSettings.getWindowsEndDateTime();
		String startDateString = startDate == null ? "The window is currently inactive" : startDate.toString();
		String endDateString = "The window is currently inactive";
		if(!startDateString.toString().equals("The window is currently inactive")) {
			endDateString = endDate.toString();
		}
		Boolean windowStatus = currentSettings.getWindowStatus();
		Double decayRate = currentSettings.getDecayRate();
		Double multiplierRate = currentSettings.getMultiplierRate();
		String dailyPassword = currentSettings.getDailyPassword();
		List<Request> requests = requestRepository.findAll();
		Set<Beneficiary> beneficiarySet = new HashSet<Beneficiary>();
		for(Request request : requests) {
			beneficiarySet.add(request.getBeneficiary());
		}
		Integer uniqueBeneficiaryCount = beneficiarySet.size();
		WindowDataDTO results = new WindowDataDTO(windowStatus, startDateString, endDateString, multiplierRate, decayRate, dailyPassword, uniqueBeneficiaryCount);
		return results;
	}

	@Override
	public void modifyDecayRate(AdminSettingsDTO adminSettings) {
		// TODO Auto-generated method stub
		Double decayRate = adminSettings.getDecayRate();
		AdminSettings currentSettings = adminRepository.findById(idKey);
		currentSettings.setDecayRate(decayRate);
		adminRepository.saveAndFlush(currentSettings);
	}

	@Override
	public void modifyMultiplierRate(AdminSettingsDTO adminSettings) {
		// TODO Auto-generated method stub
		Double multiplierRate = adminSettings.getMultiplierRate();
		AdminSettings currentSettings = adminRepository.findById(idKey);
		currentSettings.setMultiplierRate(multiplierRate);
		adminRepository.saveAndFlush(currentSettings);
	}

	@Override
	public String toggleWindow(AdminSettingsDTO adminSettings) throws ParseException {
		// TODO Auto-generated method stub
		AdminSettings currentSettings = adminRepository.findById(idKey);
		String returnString = MessageConstants.WINDOW_OPEN_SUCCESS;
		String endDateString = adminSettings.getEndDate();
		if(endDateString == null || endDateString.isEmpty()) {
			// Code block to close the window
			currentSettings.setLastStartDate(currentSettings.getWindowStartDateTime());
			currentSettings.setLastEndDate(currentSettings.getWindowsEndDateTime());
			currentSettings.setWindowStartDateTime(null);
			currentSettings.setWindowsEndDateTime(null);
			currentSettings.setWindowStatus(Boolean.FALSE);
			returnString = MessageConstants.WINDOW_CLOSE_SUCCESS;
		} else {
			// Code block to open the window
			currentSettings.setWindowStartDateTime(new Date());
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
			currentSettings.setWindowsEndDateTime(format.parse(endDateString));
			currentSettings.setWindowStatus(Boolean.TRUE);
		}
		adminRepository.save(currentSettings);
		return returnString;
	}

	@Override
	public void modifyClosingDate(AdminSettingsDTO adminSettings) throws ParseException {
		// TODO Auto-generated method stub
		String endDateString = adminSettings.getEndDate();
		AdminSettings currentSettings = adminRepository.findById(idKey);
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
		currentSettings.setWindowsEndDateTime(format.parse(endDateString));
		adminRepository.save(currentSettings);
	}

	@Override
	public Boolean getWindowStatus() {
		// TODO Auto-generated method stub
		AdminSettings currentSettings = adminRepository.findById(idKey);
		return currentSettings.getWindowStatus();
	}

	@Override
	public void insertPastRequests() {
		// TODO Auto-generated method stub
		List<Allocation> allocations = allocationRepository.findAll();
		for(Allocation allocation : allocations) {
			Beneficiary dbBeneficiary = allocation.getBeneficiary();
			RequestHistory previousRequestsByBeneficiary = historyRepository.findByBeneficiaryUserUsername(dbBeneficiary.getUser().getUsername());
			List<AllocatedFoodItem> allocatedItems = allocation.getAllocatedItems();
			Map<FoodItem, Integer> allocatedQuantityItemMap = new HashMap<FoodItem, Integer>();
			for(AllocatedFoodItem allocatedItem : allocatedItems) {
				FoodItem dbFoodItem = allocatedItem.getFoodItem();
				allocatedQuantityItemMap.put(dbFoodItem, allocatedItem.getAllocatedQuantity());
			}
			if(previousRequestsByBeneficiary == null) {
				List<PastRequest> pastRequests = new ArrayList<PastRequest>();
				previousRequestsByBeneficiary = new RequestHistory(dbBeneficiary, pastRequests);
			} else {
				List<PastRequest> pastRequests = previousRequestsByBeneficiary.getPastRequests();
				List<Request> requestsByBeneficiary = requestRepository.findByBeneficiaryUserUsername(dbBeneficiary.getUser().getUsername());
				for(Request request : requestsByBeneficiary) {
					FoodItem dbFoodItem = request.getFoodItem();
					Date requestCreationDate = request.getRequestCreationDate();
					Integer allocatedQuantity = allocatedQuantityItemMap.get(dbFoodItem);
					if(allocatedQuantity != null) {
						PastRequest newPastRequest = new PastRequest(dbFoodItem, request.getRequestedQuantity(), allocatedQuantity, requestCreationDate);
						newPastRequest.setRequestHistory(previousRequestsByBeneficiary);
						pastRequests.add(newPastRequest);
					}
				}
			}
			historyRepository.save(previousRequestsByBeneficiary);
		}
	}
	
	@Override
	@Transactional
	public void clearWindowData() {
		/*
		List<Request> requests = requestRepository.findAll();
		for(Request request : requests) {
			request.setFoodItem(null);
		}
		requestRepository.save(requests);
		*/
		requestRepository.deleteAll();
		allocatedFoodItemRepository.deleteAllInBatch();
		allocationRepository.deleteAllInBatch();
		//System.out.println("requests deleted");
		//allocationRepository.deleteAll();
	}

	@Override
	public void generateEmails() throws Exception {
		// TODO Auto-generated method stub
		List<User> beneficiaries = userRepository.findUsersByUsertype("beneficiary");
		for(User beneficiary : beneficiaries) {
			String emailAddress = beneficiary.getEmail();
			new AutomatedEmailer(emailAddress, EmailMessages.WINDOW_OPENING_SUBJECT, EmailMessages.WINDOW_OPENING_MESSAGE);
		}
	}

	@Override
	@Scheduled(fixedRate = 86400000, initialDelay = 10000)
	public void generateDailyPassword() {
		// TODO Auto-generated method stub
		AdminSettings adminSettings = adminRepository.findById(idKey);
		int length = 8;
		boolean useLetters = false;
		boolean useNumbers = true;
		String dailyPassword = RandomStringUtils.random(length, useLetters, useNumbers);
		adminSettings.setDailyPassword(dailyPassword);
		User dbUser = userRepository.findByUsernameIgnoreCase("volunteer");
		dbUser = dbUser == null ? new User("volunteer", null, "volunteer", "volunteer", "volunteer-fb@gmail.com") : dbUser;
		dbUser.setPassword(BCrypt.hashpw(dailyPassword, BCrypt.gensalt()));
		dbUser.setRole(roleRepository.findOne(Role.VOLUNTEER));
		userRepository.save(dbUser);
		adminRepository.save(adminSettings);
	}

	@Override
	public void resetPassword(UserDTO user) throws Exception {
		// TODO Auto-generated method stub
		User dbUser = userRepository.findByUsernameIgnoreCase(user.getUsername());
		if(dbUser == null) {
			throw new UserException(ErrorMessages.NO_SUCH_USER);
		}
		int length = 8;
		boolean useLetters = true;
		boolean useNumbers = true;
		String newPassword = RandomStringUtils.random(length, useLetters, useNumbers);
		// System.out.println("The new password is : " + newPassword);
		new AutomatedEmailer(dbUser.getEmail(), EmailMessages.RESET_PASSWORD_SUBJECT, EmailMessages.RESET_PASSWORD_MESSAGE1 + newPassword + EmailMessages.RESET_PASSWORD_MESSAGE2);
		dbUser.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
		userRepository.save(dbUser);
	}

	@PostConstruct
	private void initAdmin() {
		User dbUser = userRepository.findByUsernameIgnoreCase("admin");
		if(dbUser == null) {
			dbUser = new User("admin", BCrypt.hashpw("password1", BCrypt.gensalt()), "admin", "admin", "bryan.lau.2015@sis.smu.edu.sg");
			dbUser.setRole(roleRepository.findOne(Role.ADMIN_USER));
			userRepository.save(dbUser);
		}
	}

}
