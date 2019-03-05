package foodbank.request.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import foodbank.request.dto.RequestDTO;
import foodbank.request.entity.Request;
import foodbank.request.service.RequestService;
import foodbank.util.MessageConstants;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.ResponseDTO;

@RestController
@CrossOrigin
@RequestMapping("/rest/request")
public class RequestController {

	@Autowired
	private RequestService requestService;
	
	@GetMapping("/display-all")
	public ResponseDTO getAllRequests() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.REQUEST_GET_SUCCESS);
		try {
			List<Request> result = requestService.getAllRequests();
			responseDTO.setResult(result);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.REQUEST_GET_FAIL);
		}
		return responseDTO;
	}
	
	@GetMapping("/display")
	public ResponseDTO getAllRequestsFromBeneficiary(@RequestParam(value = "beneficiary", required = true) String beneficiary) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.REQUEST_GET_SUCCESS);
		try {
			List<Request> result = requestService.getAllRequestsByBeneficiary(beneficiary);
			responseDTO.setResult(result);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(ErrorMessages.REQUEST_GET_FAIL);
		}
		return responseDTO;
	}
	
	@PostMapping("/create-request")
	public ResponseDTO createRequest(@RequestBody RequestDTO request) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.REQUEST_CREATE_SUCCESS);
		try {
			requestService.createAndUpdateRequest(request);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@DeleteMapping("/delete-request")
	public ResponseDTO deleteRequest(@RequestParam (value = "id", required = true) String id) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.REQUEST_DELETE_SUCCESS);
		try {
			requestService.deleteRequest(id);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
}
