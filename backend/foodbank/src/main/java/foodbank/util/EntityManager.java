package foodbank.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import foodbank.allocation.dto.AllocatedFoodItemDTO;
import foodbank.allocation.dto.AllocationResponseDTO;
import foodbank.allocation.entity.AllocatedFoodItem;
import foodbank.allocation.entity.Allocation;
import foodbank.beneficiary.dto.BeneficiaryDTO;
import foodbank.beneficiary.entity.Beneficiary;
import foodbank.history.dto.PastRequestDTO;
import foodbank.history.dto.RequestHistoryDTO;
import foodbank.history.entity.PastRequest;
import foodbank.history.entity.RequestHistory;
import foodbank.inventory.entity.FoodItem;
import foodbank.packing.dto.PackedItemDTO;
import foodbank.packing.dto.PackingListDTO;
import foodbank.packing.entity.PackedFoodItem;
import foodbank.packing.entity.PackingList;
import foodbank.user.dto.UserDTO;
import foodbank.user.entity.User;

public class EntityManager {
	
	public enum DTOKey {
		BeneficiaryDTO, UserDTO, AllocationResponseDTO, PackingListDTO, RequestHistoryDTO
	}
	
	public static User transformUserDTO(UserDTO userDTO) {
		String username = userDTO.getUsername().toLowerCase();
		String password = userDTO.getPassword();
		String usertype = userDTO.getUsertype();
		String name = userDTO.getName();
		String email = userDTO.getEmail();
		return new User(username, password, usertype, name, email);
	}
	
	public static Beneficiary transformBeneficiaryDTO(BeneficiaryDTO beneficiaryDTO) {
		String username = beneficiaryDTO.getUsername().toLowerCase();
		String password = beneficiaryDTO.getPassword();
		String usertype = beneficiaryDTO.getUsertype();
		String name = beneficiaryDTO.getName();
		String email = beneficiaryDTO.getEmail();
		Integer numBeneficiaries = beneficiaryDTO.getNumBeneficiary();
		String address = beneficiaryDTO.getAddress();
		Double score = beneficiaryDTO.getScore();
		Boolean hasTransport = beneficiaryDTO.getHasTransport();
		String memberType = beneficiaryDTO.getMemberType();
		String contactPerson = beneficiaryDTO.getContactPerson();
		String contactNumber = beneficiaryDTO.getContactNumber();
		Beneficiary newBeneficiary = new Beneficiary(numBeneficiaries, 
				address, score, contactPerson, contactNumber, memberType, 
				hasTransport);
		newBeneficiary.setUser(new User(username, password, usertype, name, email));
		return newBeneficiary;
	}
	
	public static Object convertToDTO(DTOKey dtoKey, Object object) {
		Object result = null;
		if(dtoKey == DTOKey.BeneficiaryDTO) {
			Beneficiary beneficiary = (Beneficiary)object;
			User user = beneficiary.getUser();
			result = new BeneficiaryDTO(user.getUsername(), user.getName(), 
					user.getEmail(), beneficiary.getNumBeneficiary(), 
					beneficiary.getAddress(), beneficiary.getScore(), 
					beneficiary.getContactPerson(), 
					beneficiary.getContactNumber(), 
					beneficiary.getMemberType(), 
					beneficiary.getTransportationStatus());
		} else if (dtoKey == DTOKey.AllocationResponseDTO) {
			Allocation allocation = (Allocation)object;
			Beneficiary beneficiary = allocation.getBeneficiary();
			List<AllocatedFoodItem> allocatedItems = allocation.getAllocatedItems();
			result = new AllocationResponseDTO(
					(BeneficiaryDTO)convertToDTO(DTOKey.BeneficiaryDTO, beneficiary), 
					convertAllocatedFoodItemListToDTOList(allocatedItems), 
					allocation.getApprovalStatus());
		} else if (dtoKey == DTOKey.PackingListDTO) {
			PackingList packingList = (PackingList)object;
			Beneficiary beneficiary = packingList.getBeneficiary();
			List<PackedFoodItem> packedItems = packingList.getPackedItems();
			result = new PackingListDTO(packingList.getId(),
					(BeneficiaryDTO)convertToDTO(DTOKey.BeneficiaryDTO, beneficiary),
					convertPackedItemListToDTOList(packedItems),
					packingList.getPackingStatus());
		} else if (dtoKey == DTOKey.RequestHistoryDTO) {
			RequestHistory requestHistory = (RequestHistory)object;
			Beneficiary beneficiary = requestHistory.getBeneficiary();
			List<PastRequest> pastRequests = requestHistory.getPastRequests();
			result = new RequestHistoryDTO(
					(BeneficiaryDTO)convertToDTO(DTOKey.BeneficiaryDTO, beneficiary),
					convertPastRequestListToDTOList(pastRequests));
		}
		return result;
	}
	
	public static List<AllocatedFoodItemDTO> convertAllocatedFoodItemListToDTOList(List<AllocatedFoodItem> allocatedItems) {
		List<AllocatedFoodItemDTO> result = new ArrayList<AllocatedFoodItemDTO>();
		for(AllocatedFoodItem allocatedItem : allocatedItems) {
			FoodItem foodItem = allocatedItem.getFoodItem();
			String category = foodItem.getCategory();
			String classification = foodItem.getClassification();
			String description = foodItem.getDescription();
			Integer inventoryQuantity = foodItem.getQuantity();
			Integer requestedQuantity = allocatedItem.getRequestedQuantity();
			Integer allocatedQuantity = allocatedItem.getAllocatedQuantity();
			result.add(new AllocatedFoodItemDTO(category, classification, description, inventoryQuantity, requestedQuantity, allocatedQuantity));
		}
		return result;
	}
	
	public static List<PackedItemDTO> convertPackedItemListToDTOList(List<PackedFoodItem> packedItems) {
		List<PackedItemDTO> results = new ArrayList<PackedItemDTO>();
		for(PackedFoodItem packedItem : packedItems) {
			FoodItem foodItem = packedItem.getPackedFoodItem();
			String category = foodItem.getCategory();
			String classification = foodItem.getClassification();
			String description = foodItem.getDescription();
			Integer inventoryQuantity = foodItem.getQuantity();
			Integer allocatedQuantity = packedItem.getAllocatedQuantity();
			Integer packedQuantity = packedItem.getPackedQuantity();
			results.add(new PackedItemDTO(category, classification, description, inventoryQuantity, allocatedQuantity, packedQuantity));
		}
		return results;
	}
	
	public static List<PastRequestDTO> convertPastRequestListToDTOList(List<PastRequest> pastRequests) {
		List<PastRequestDTO> results = new ArrayList<PastRequestDTO>();
		for(PastRequest pastRequest : pastRequests) {
			FoodItem foodItem = pastRequest.getPreviouslyRequestedItem();
			String category = foodItem.getCategory();
			String classification = foodItem.getClassification();
			String description = foodItem.getDescription();
			Integer requestedQuantity = pastRequest.getRequestedQuantity();
			Integer allocatedQuantity = pastRequest.getAllocatedQuantity();
			Date requestCreationDate = pastRequest.getRequestCreationDate();
			results.add(new PastRequestDTO(category, classification, description, requestedQuantity, allocatedQuantity, requestCreationDate));
		}
		return results;
	}
	
	public static Boolean checkForUpdateIntent(Object object) {
		return object == null ? Boolean.FALSE : Boolean.TRUE;
	}

}
