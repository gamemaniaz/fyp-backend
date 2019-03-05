package foodbank.inventory.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import foodbank.inventory.dto.BarcodeResponseDTO;
import foodbank.inventory.dto.FoodItemDTO;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.service.FoodService;
import foodbank.util.MessageConstants;
import foodbank.util.ResponseDTO;
import foodbank.util.MessageConstants.ErrorMessages;

@RestController
@CrossOrigin
@RequestMapping("/rest/inventory")
public class FoodController {
	
	@Autowired
	private FoodService foodService;
	
	@GetMapping("/display-all")
	public ResponseDTO getAllFoodItems() {
		List<FoodItem> results = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, results, MessageConstants.ITEM_RETRIEVE_SUCCESS);
		try {
			results = foodService.retrieveAllFoodItems();
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		responseDTO.setResult(results);
		return responseDTO;
	}
	
	@GetMapping("/display-by-category")
	public ResponseDTO getAllFoodItemsInCategory(@RequestParam(value = "category", required = true) String category) {
		List<FoodItem> results = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, results, MessageConstants.ITEM_RETRIEVE_SUCCESS);
		try {
			results = foodService.retrieveAllFoodItemsInCategory(category);
		} catch (Exception e) { 
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		responseDTO.setResult(results);
		return responseDTO;
	}
	
	@GetMapping("/display-by-category-classification")
	public ResponseDTO getAllFoodItemsInClassification(@RequestParam(value = "category", required = true) String category, @RequestParam(value = "classification", required = true) String classification,
			HttpServletRequest request) {
		Object result = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, result, MessageConstants.ITEM_RETRIEVE_SUCCESS);
		try {
			result = foodService.retrieveFoodItemsByCategoryAndClassification(category, classification);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		responseDTO.setResult(result);
		return responseDTO;
	}
	
	@PostMapping("/update-item")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO updateFoodItem(@RequestBody FoodItemDTO foodItem) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ITEM_OVERWRITE_SUCCESS);
		try {
			foodService.overwriteFoodItemQuantity(foodItem);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update-item-quantity")
	public ResponseDTO updateFoodItemQuantity(@RequestBody FoodItemDTO foodItem) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ITEM_UPDATE_SUCCESS);
		try {
			foodService.modifyFoodItemQuantity(foodItem);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PutMapping("/create-item")
	public ResponseDTO createFoodItem(@RequestBody FoodItemDTO foodItem) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ITEM_CREATION_SUCCESS);
		try {
			foodService.createFoodItem(foodItem);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@GetMapping("/scanner")
	public ResponseDTO getBarcodeDetails(@RequestParam(value = "barcode", required = true) String barcode) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.ITEM_RETRIEVE_SUCCESS);
		try {
			BarcodeResponseDTO foodDetails = foodService.readBarcode(barcode);
			responseDTO.setResult(foodDetails);
			if (foodDetails == null) {
				responseDTO.setStatus(ResponseDTO.Status.FAIL);
				responseDTO.setMessage(ErrorMessages.NO_SUCH_ITEM);
			}
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;		
	}
	
	@PostMapping("/reset-all")
    public ResponseDTO resetFoodQuantity() {
	    ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.INVENTORY_RESET_SUCCESS);
	    try {
	        foodService.resetInventoryQuantity();
	    } catch (Exception e) {
	        responseDTO.setStatus(ResponseDTO.Status.FAIL);
	        responseDTO.setMessage(e.getMessage());
	    }
	    return responseDTO;
	}

}
