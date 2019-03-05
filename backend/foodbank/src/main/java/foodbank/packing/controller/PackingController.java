package foodbank.packing.controller;

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

import foodbank.packing.dto.PackingListDTO;
import foodbank.packing.entity.PackingList;
import foodbank.packing.service.PackingService;
import foodbank.util.MessageConstants;
import foodbank.util.ResponseDTO;

@RestController
@CrossOrigin
@RequestMapping("/rest/packing")
public class PackingController {
	
	@Autowired
	private PackingService packingService;
	
	/*
	@Autowired
	private ReportService reportService;
	*/
	
	@GetMapping("/display-all")
	public ResponseDTO getAllPackingLists() {
		List<PackingListDTO> results = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, results, MessageConstants.PACKING_LIST_RETRIEVE_SUCCESS);
		try {
			results = packingService.retrieveAllPackingLists();
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		responseDTO.setResult(results);
		return responseDTO;
	}
	
	@GetMapping("/display-by")
	public ResponseDTO getBeneficiaryPackingList(@RequestParam(value = "beneficiary", required = true) String beneficiary) {
		PackingListDTO result = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, result, MessageConstants.PACKING_LIST_RETRIEVE_SUCCESS);
		try {
			result = packingService.findByBeneficiary(beneficiary);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		responseDTO.setResult(result);
		return responseDTO;
	}
	
	@GetMapping("/display/in-window")
	public ResponseDTO getAllPackingListsInWindow() {
		List<PackingListDTO> results = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, results, MessageConstants.PACKING_LIST_RETRIEVE_SUCCESS);
		try {
			results = packingService.retrieveAllPackingListsInWindow();
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		responseDTO.setResult(results);
		return responseDTO;
	}
	
	@PostMapping("/generate-list")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO generatePackingList() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.PACKING_LIST_GENERATE_SUCCESS);
		try {
			packingService.generatePackingList();
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@PostMapping("/update-list")
	public ResponseDTO updateList(@RequestBody PackingListDTO packingList) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.PACKING_LIST_UPDATE_SUCCESS);
		try {
			packingService.updatePackedQuantities(packingList);
			PackingList dbPackingList = packingService.findDbListById(packingList.getId());
			packingService.generateDbInvoices(dbPackingList);
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	@GetMapping("/review-statuses")
	public ResponseDTO viewAllPackingStatus() {
		Boolean result = null;
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, result, MessageConstants.ALL_PACKING_STATUS_RETRIEVE_SUCCESS);
		try {
			result = packingService.reviewAllPackingStatus();
		} catch (Exception e) {
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
	
	/*
	@Autowired
	private PackingRepository packingRepository;
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
	@Autowired
	private FoodRepository foodRepository;
	
	@GetMapping("/display-all")
	public List<PackingListDTO> getAllPackingLists() {
		List<PackingList> dbPackingLists = packingRepository.findAll();
		List<PackingListDTO> response = new ArrayList<PackingListDTO>();
		for(PackingList dbPackingList : dbPackingLists) {
			Beneficiary dbBeneficiary = dbPackingList.getBeneficiary();
			User dbUser = dbBeneficiary.getUser();
			BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO(dbUser.getUsername(), 
					dbUser.getName(), dbUser.getEmail(), dbBeneficiary.getNumBeneficiary(), 
					dbBeneficiary.getAddress(), dbBeneficiary.getScore(), 
					dbBeneficiary.getContactPerson(), dbBeneficiary.getContactNumber(), 
					dbBeneficiary.getMemberType(), dbBeneficiary.isHasTransport());
			List<PackedFoodItem> dbPackedItems = dbPackingList.getPackedItems();
			List<PackedItemDTO> packedItemDTOList = new ArrayList<PackedItemDTO>();
			for(PackedFoodItem packedItem : dbPackedItems) {
				FoodItem dbFoodItem = packedItem.getPackedFoodItem();
				PackedItemDTO packedItemDTO = new PackedItemDTO(dbFoodItem.getCategory(), dbFoodItem.getClassification(), dbFoodItem.getDescription(), dbFoodItem.getQuantity(), packedItem.getPackedQuantity());
				packedItemDTOList.add(packedItemDTO);
			}
			PackingListDTO packingListDTO = new PackingListDTO(dbPackingList.getId(), beneficiaryDTO, packedItemDTOList, dbPackingList.getPackingStatus());
			response.add(packingListDTO);
		}
		return response;
	}
	
	@PostMapping("/generate-lists")
	public @ResponseBody String generateLists() {
		boolean successfulEntry = true;
		// PackingList1
		try {
			Beneficiary dbBeneficiary = beneficiaryRepository.findByUserUsername("dan");
			List<PackedFoodItem> packedItems = new ArrayList<PackedFoodItem>();
			FoodItem dbItem1 = foodRepository.findByCategoryAndClassificationAndDescription("Snack", "Chocolate", "Chocolate-550g");
			FoodItem dbItem2 = foodRepository.findByCategoryAndClassificationAndDescription("Snack", "Chocolate", "Chocolate-650g");
			FoodItem dbItem3 = foodRepository.findByCategoryAndClassificationAndDescription("Snack", "Chocolate", "Sweets-550g");
			FoodItem dbItem4 = foodRepository.findByCategoryAndClassificationAndDescription("Snack", "Chocolate", "Chocolate-750g");
			PackedFoodItem packedItem1 = new PackedFoodItem(dbItem1, 10);
			PackedFoodItem packedItem2 = new PackedFoodItem(dbItem2, 20);
			PackedFoodItem packedItem3 = new PackedFoodItem(dbItem3, 30);
			PackedFoodItem packedItem4 = new PackedFoodItem(dbItem4, 40);
			packedItems.addAll(Arrays.asList(new PackedFoodItem[]{packedItem1, packedItem2, packedItem3, packedItem4}));
			PackingList packingList = new PackingList(dbBeneficiary, packedItems);
			for(PackedFoodItem packedItem : packedItems) {
				packedItem.setPackingList(packingList);
			}
			packingRepository.save(packingList);
		} catch (Exception e) {
			successfulEntry = false;
		}
		// PackingList2
		try {
			Beneficiary dbBeneficiary = beneficiaryRepository.findByUserUsername("cat");
			List<PackedFoodItem> packedItems = new ArrayList<PackedFoodItem>();
			FoodItem dbItem1 = foodRepository.findByCategoryAndClassificationAndDescription("Snack", "Chocolate", "Chocolate-550g");
			FoodItem dbItem2 = foodRepository.findByCategoryAndClassificationAndDescription("Snack", "Chocolate", "Chocolate-750g");
			FoodItem dbItem3 = foodRepository.findByCategoryAndClassificationAndDescription("Snack", "Chocolate", "Sweets-550g");
			FoodItem dbItem4 = foodRepository.findByCategoryAndClassificationAndDescription("Snack", "Chocolate", "Sweets-650g");
			PackedFoodItem packedItem1 = new PackedFoodItem(dbItem1, 10);
			PackedFoodItem packedItem2 = new PackedFoodItem(dbItem2, 20);
			PackedFoodItem packedItem3 = new PackedFoodItem(dbItem3, 30);
			PackedFoodItem packedItem4 = new PackedFoodItem(dbItem4, 40);
			packedItems.addAll(Arrays.asList(new PackedFoodItem[]{packedItem1, packedItem2, packedItem3, packedItem4}));
			PackingList packingList = new PackingList(dbBeneficiary, packedItems);
			for(PackedFoodItem packedItem : packedItems) {
				packedItem.setPackingList(packingList);
			}
			packingRepository.save(packingList);
		} catch (Exception e) {
			successfulEntry = false;
		}
		return successfulEntry ? "Saved" : "Failed";
	}
	*/

}
