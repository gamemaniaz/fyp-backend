package foodbank.admin.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foodbank.admin.dto.AdminSettingsDTO;
import foodbank.admin.dto.WindowDataDTO;
import foodbank.admin.service.AdminService;
import foodbank.allocation.service.AllocationService;
import foodbank.user.dto.UserDTO;
import foodbank.util.MessageConstants;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.ResponseDTO;

@RestController
@CrossOrigin
@RequestMapping("/rest/admin-settings")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AllocationService allocationService;
	
	@GetMapping("/display-all")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO getAdminSettings() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ADMIN_GET_SUCCESS);
		try {
			WindowDataDTO result = adminService.retrieveWindowData();
			responseDTO.setResult(result);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.ADMIN_GET_FAIL);
		}
		return responseDTO;
	}
	
	@GetMapping("/display/window-status")
	public ResponseDTO getWindowStatus() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ADMIN_GET_SUCCESS);
		try {
			Map<String, Boolean> map = Collections.singletonMap("windowStatus", adminService.getWindowStatus());
			responseDTO.setResult(map);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.ADMIN_GET_FAIL);
		}
		return responseDTO;
	}

	@PostMapping("/update/window-status")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO toggleWindowStatus(@RequestBody AdminSettingsDTO adminSettings) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, null);
		try {
			String message = adminService.toggleWindow(adminSettings);
			if(message.equals(MessageConstants.WINDOW_CLOSE_SUCCESS)) {
				allocationService.generateAllocationList();
			} else if (message.equals(MessageConstants.WINDOW_OPEN_SUCCESS)) {
				adminService.generateEmails();
				adminService.insertPastRequests();
				adminService.clearWindowData();
			}
			responseDTO.setMessage(message);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update/decay-rate")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO updateDecayRate(@RequestBody AdminSettingsDTO adminSettings) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.DECAY_RATE_UPDATE_SUCCESS);
		try {
			adminService.modifyDecayRate(adminSettings);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update/multiplier-rate")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO updateMultiplierRate(@RequestBody AdminSettingsDTO adminSettings) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.MULTIPLIER_RATE_UPDATE_SUCCESS);
		try {
			adminService.modifyMultiplierRate(adminSettings);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update/closing-date")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO updateClosingDate(@RequestBody AdminSettingsDTO adminSettings) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.MULTIPLIER_RATE_UPDATE_SUCCESS);
		try {
			adminService.modifyClosingDate(adminSettings);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/reset-password")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO resetPassword(@RequestBody UserDTO user) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.RESET_PASSWORD_SUCCESS);
		try {
			adminService.resetPassword(user);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
}
