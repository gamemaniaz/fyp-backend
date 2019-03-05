package foodbank.beneficiary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import foodbank.beneficiary.dto.BeneficiaryDTO;
import foodbank.beneficiary.service.BeneficiaryService;
import foodbank.util.MessageConstants;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.ResponseDTO;

@RestController
@CrossOrigin
@RequestMapping("/rest/beneficiary")
public class BeneficiaryController {
	
	@Autowired
	private BeneficiaryService beneficiaryService;
	
	@GetMapping("/display-all")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO getAllBeneficiaries() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.BENEFICIARY_GET_SUCCESS);
		try {
			List<BeneficiaryDTO> result = beneficiaryService.getAllBeneficiaries();
			responseDTO.setResult(result);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.BENEFICIARY_GET_FAIL);
		}
		return responseDTO;
	}
	
	@GetMapping("/get-details")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO getBeneficiaryDetails(@RequestParam(value = "beneficiary", required = true) String beneficiary) {
		BeneficiaryDTO result = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.BENEFICIARY_RETRIEVE_SUCCESS);
		try {
			result = beneficiaryService.getBeneficiaryDetails(beneficiary);
			responseDTO.setResult(result);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PutMapping("/insert-beneficiary")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO insertBeneficiary(@RequestBody BeneficiaryDTO beneficiary) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.BENEFICIARY_ADD_SUCCESS);
		try {
			beneficiaryService.createBeneficiary(beneficiary);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update-beneficiary")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO updateBeneficiary(@RequestBody BeneficiaryDTO beneficiary) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.BENEFICIARY_UPDATE_SUCCESS);
		try {
			beneficiaryService.updateBeneficiary(beneficiary);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
}
