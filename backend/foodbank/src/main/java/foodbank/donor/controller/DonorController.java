package foodbank.donor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import foodbank.donor.dto.DonationDTO;
import foodbank.donor.dto.DonorDTO;
import foodbank.donor.entity.Donor;
import foodbank.donor.service.DonorService;
import foodbank.util.MessageConstants;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.ResponseDTO;

@RestController
@CrossOrigin
@RequestMapping("/rest/donor")
public class DonorController {

	@Autowired
	private DonorService donorService;
	
	@GetMapping("/display-all")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO getAllDonors() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.DONOR_GET_SUCCESS);
		try {
			List<Donor> results = donorService.getAllDonors();
			responseDTO.setResult(results);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.DONOR_GET_FAIL);
		}
		return responseDTO;
	}
	
	@GetMapping("/display-donors")
	public ResponseDTO getDonorNames() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.DONOR_GET_SUCCESS);
		try {
			List<String> results = donorService.getDonorNames();
			responseDTO.setResult(results);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.DONOR_GET_FAIL);
		}
		return responseDTO;
	}
	
	@GetMapping("/retrieve-np-donations")
	public ResponseDTO retrieveNonperishableDonationsByDonor(@RequestParam(value = "donor", required = true) String donor) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.DONOR_NP_RETRIEVE_SUCCESS);
		try {
			DonationDTO result = donorService.retrieveNonperishableDonations(donor);
			responseDTO.setResult(result);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PutMapping("/create-donor")
	public ResponseDTO createDonor(@RequestBody DonorDTO donor) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.DONOR_ADD_SUCCESS);
		try {
			donorService.createDonor(donor);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@DeleteMapping("/delete-donor")
	public ResponseDTO deleteDonor(@RequestParam (value = "id", required = true) String id) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.DONOR_DELETE_SUCCESS);
		try {
			donorService.deleteDonor(id);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
}
