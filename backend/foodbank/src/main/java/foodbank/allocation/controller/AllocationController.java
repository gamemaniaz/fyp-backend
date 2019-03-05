package foodbank.allocation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import foodbank.allocation.dto.AllocatedFoodItemDTO;
import foodbank.allocation.dto.AllocationResponseDTO;
import foodbank.allocation.dto.AllocationUpdateDTO;
import foodbank.allocation.service.AllocationService;
import foodbank.util.MessageConstants;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.ResponseDTO;

@RestController
@CrossOrigin
@RequestMapping("/rest/allocation")
public class AllocationController {
	
	@Autowired
	private AllocationService allocationService;
	
	@GetMapping("/display-all")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO retrieveAllocations() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ALLOCATION_GET_SUCCESS);
		try {
			List<AllocationResponseDTO> result = allocationService.retrieveAllAllocations();
			responseDTO.setResult(result);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.ALLOCATION_GET_FAIL);
		}
		return responseDTO;
	}
	
	@GetMapping("/display-allocations")
	public ResponseDTO retrieveFoodItemsAllocatedToBeneficiary(@RequestParam(value = "beneficiary", required = true) String beneficiary) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ALLOCATION_GET_SUCCESS);
		try {
			List<AllocatedFoodItemDTO> results = allocationService.retrieveAllocationByBeneficiary(beneficiary);
			responseDTO.setResult(results);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.ALLOCATION_GET_FAIL);
		}
		return responseDTO;
	}
	
	@PostMapping("/generate-allocations")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO generateAllocations() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ALLOCATION_GENERATE_SUCCESS);
		try {
			allocationService.generateAllocationList();
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update-allocation")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO updateAllocation(@RequestBody AllocationUpdateDTO allocation) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ALLOCATION_UPDATE_SUCCESS);
		try {
			allocationService.updateAllocation(allocation);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/approve-allocations")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO approveAllocations() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ALLOCATION_APPROVE_SUCCESS);
		try {
			allocationService.approveAllocations();
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@GetMapping("/approval-status")
	public ResponseDTO getApproveStatus() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ADMIN_GET_SUCCESS);
		try {
			Boolean result = allocationService.checkApproveStatus();
			responseDTO.setResult(result);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.ADMIN_GET_FAIL);
		}
		return responseDTO;
	}
	
	/*
	@Transactional
	@DeleteMapping("/delete")
	public @ResponseBody String deleteAllocations() {
		allocationRepository.deleteAll();
		return "Deleted";
	}
	*/
	
}