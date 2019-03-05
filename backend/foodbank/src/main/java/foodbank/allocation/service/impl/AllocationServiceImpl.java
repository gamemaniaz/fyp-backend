package foodbank.allocation.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.allocation.dto.AllocatedFoodItemDTO;
import foodbank.allocation.dto.AllocationResponseDTO;
import foodbank.allocation.dto.AllocationUpdateDTO;
import foodbank.allocation.entity.AllocatedFoodItem;
import foodbank.allocation.entity.Allocation;
import foodbank.allocation.repository.AllocationRepository;
import foodbank.allocation.service.AllocationService;
import foodbank.beneficiary.entity.Beneficiary;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;
import foodbank.util.EntityManager;
import foodbank.util.EntityManager.DTOKey;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.InvalidAllocationException;

@Service
public class AllocationServiceImpl implements AllocationService {

	@Autowired
	private AllocationRepository allocationRepository;
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Override
	public List<AllocationResponseDTO> retrieveAllAllocations() {
		// TODO Auto-generated method stub
		List<Allocation> allocations = allocationRepository.findAll();
		List<AllocationResponseDTO> response = new ArrayList<AllocationResponseDTO>();
		for(Allocation allocation : allocations) {
			response.add((AllocationResponseDTO)EntityManager.convertToDTO(DTOKey.AllocationResponseDTO, allocation));
		}
		return response;
	}

	@Override
	public List<AllocatedFoodItemDTO> retrieveAllocationByBeneficiary(String beneficiary) {
		// TODO Auto-generated method stub
		Allocation allocation = allocationRepository.findByBeneficiaryUserUsername(beneficiary);
		List<AllocatedFoodItem> allocatedItems = allocation.getAllocatedItems();
		return EntityManager.convertAllocatedFoodItemListToDTOList(allocatedItems);
	}

	@Override
	public void generateAllocationList() {
		// TODO Auto-generated method stub
		List<Request> requests = requestRepository.findAll();
		List<Beneficiary> beneficiaries = beneficiaryRepository.findAll();
		Map<String, Double> beneficiariesScoreTable = generateBeneficiaryScoreTable(beneficiaries);
		Map<String, Allocation> allocationMap = generateAllocationMapping(generateMapping(requests), beneficiariesScoreTable);
		allocationRepository.save(allocationMap.values());
	}
	
	private Map<String, Double> generateBeneficiaryScoreTable(List<Beneficiary> beneficiaries) {
		Map<String, Double> beneficiariesScoreTable = new HashMap<String, Double>();
		for(Beneficiary beneficiary : beneficiaries) {
			beneficiariesScoreTable.put(beneficiary.getUser().getUsername(), beneficiary.getScore());
		}
		return beneficiariesScoreTable;
	}

	private HashMap<String, List<Request>> generateMapping(List<Request> requests) {
		HashMap<String, List<Request>> requestsByFoodItems = new HashMap<String, List<Request>>();
		for(Request request : requests) {
			String category = request.getFoodItem().getCategory();
			String classification = request.getFoodItem().getClassification();
			String description = request.getFoodItem().getDescription();
			String key = category + "," + classification + "," + description;
			List<Request> requestsContainingFoodItem = requestsByFoodItems.get(key);
			if(requestsContainingFoodItem != null) {
				requestsContainingFoodItem.add(request);
				requestsByFoodItems.replace(key, requestsContainingFoodItem);
			} else {
				requestsContainingFoodItem = new ArrayList<Request>();
				requestsContainingFoodItem.add(request);
				requestsByFoodItems.put(key, requestsContainingFoodItem);
			}
		}
		return requestsByFoodItems;
	}
	
	private Map<String, Allocation> generateAllocationMapping(Map<String, List<Request>> requestsByFoodItems, Map<String, Double> beneficiariesScoreTable) {
		// The key for this map will be the username of the beneficiaries
		// The value for this map will be allocation object of each beneficiary
		Map<String, Allocation> allocationMap = new HashMap<String, Allocation>();
		List<FoodItem> foodItemsAllocated = new ArrayList<>();		//this list will the store food items which have been been allocated out, only food items which have 0 allocation are given as 2nd option to similar items
		List<Request> zeroAllocatedRequests = new ArrayList<>();		//this will store all requests which have zero allocation
		for(Map.Entry<String, List<Request>> entry : requestsByFoodItems.entrySet()) {
			// If the value of the entry is non-null, we know that this item has been requested by some beneficiaries
			List<Request> requestsForFoodItem = entry.getValue();
			Request request = requestsForFoodItem.size() > 0 ? requestsForFoodItem.get(0) : null;
			if(request != null) {
				FoodItem dbFoodItem = request.getFoodItem();
				Integer inventoryQuantity = dbFoodItem.getQuantity();
				foodItemsAllocated.add(dbFoodItem);
				Integer numberOfBeneficiariesAllocated = requestsForFoodItem.size();
				Double totalScore = Double.valueOf(0);
				numberOfBeneficiariesAllocated = evaluateOptimalBeneficiaryAllocationCount(numberOfBeneficiariesAllocated, inventoryQuantity);
				for(int i = 0; i < numberOfBeneficiariesAllocated; i++) {
					totalScore += requestsForFoodItem.get(i).getBeneficiary().getScore();
				}
				requestsForFoodItem.sort((Request r1, Request r2)->(int)(beneficiariesScoreTable.get(r1.getBeneficiary().getUser().getUsername()) - beneficiariesScoreTable.get(r2.getBeneficiary().getUser().getUsername())));
				Collections.reverse(requestsForFoodItem);
				LinkedHashMap<String, AllocatedFoodItem> unfulfilledRequests = new LinkedHashMap<>();
				for(int i = 0; i < requestsForFoodItem.size(); i++) {
					Request currentRequest = requestsForFoodItem.get(i);
					beneficiariesScoreTable.get(currentRequest.getBeneficiary().getUser().getUsername());
					Double beneficiaryScore = currentRequest.getBeneficiary().getScore();
					Double allocationRatio = beneficiaryScore/totalScore;
					Integer maxAllocatedQuantity = (int)Math.ceil(allocationRatio * inventoryQuantity);
					Integer allocatedQuantity = Integer.valueOf(0);
					Integer requestedQuantity = currentRequest.getRequestedQuantity();
					// Inventory quantity will not fall below 0
					// If inventory quantity reaches 0, all beneficiaries will be allocated 0 for the item
					if(inventoryQuantity > 0) {
						// When inventory is sufficient to allocate to this beneficiary
						if(maxAllocatedQuantity <= inventoryQuantity) {
							// Conditional for when beneficiary request for more than what they can get
							// Written using a ternary operator
							allocatedQuantity = requestedQuantity > maxAllocatedQuantity ? maxAllocatedQuantity : requestedQuantity;
						} else {
							// Enter this block if inventory is insufficient to meet maxAllocatedQuantity
							maxAllocatedQuantity = inventoryQuantity;
							allocatedQuantity = requestedQuantity > maxAllocatedQuantity ? maxAllocatedQuantity : requestedQuantity;
						}
					}
					inventoryQuantity -= allocatedQuantity;
					String beneficiaryUsername = currentRequest.getBeneficiary().getUser().getUsername();
					Allocation allocation = allocationMap.get(beneficiaryUsername);
					FoodItem foodItem = currentRequest.getFoodItem();
					// Modifying the score of each beneficiary after allocation of this food item
					Double oldScore = beneficiariesScoreTable.get(beneficiaryUsername);
					beneficiariesScoreTable.put(beneficiaryUsername, (double)(oldScore - allocatedQuantity));
					AllocatedFoodItem allocationByAlgorithm = new AllocatedFoodItem(foodItem, requestedQuantity, allocatedQuantity);
					// Check if the beneficiary already has other allocated food items
					if(allocation != null) {
						allocationByAlgorithm.setAllocation(allocation);
						allocation.getAllocatedItems().add(allocationByAlgorithm);
						allocationMap.replace(beneficiaryUsername, allocation);
					} else {
						// This block will only be entered if the beneficiary currently has no other allocation of food items
						ArrayList<AllocatedFoodItem> foodItems = new ArrayList<AllocatedFoodItem>();
						foodItems.add(allocationByAlgorithm);
						allocation = new Allocation(currentRequest.getBeneficiary(), foodItems, Boolean.FALSE);
						for(AllocatedFoodItem allocationListItem : foodItems) {
							allocationListItem.setAllocation(allocation);
						}
						allocationMap.put(beneficiaryUsername, allocation);
					}
					if(requestedQuantity > allocatedQuantity) {
						// Unfulfilled requests mapping
						unfulfilledRequests.put(beneficiaryUsername, allocationByAlgorithm);
						if(allocatedQuantity == 0) {
							zeroAllocatedRequests.add(request);
						}
					}
				}
				allocationMap = allocateLeftovers(unfulfilledRequests, allocationMap, inventoryQuantity);
			}
		}
		allocationMap = allocateSimilarFoodItems(allocationMap, foodItemsAllocated, zeroAllocatedRequests);
		return allocationMap;
	}
	
	private Map<String, Allocation> allocateLeftovers(LinkedHashMap<String, AllocatedFoodItem> unfulfilledRequests, Map<String, Allocation> allocationMap, Integer inventoryQuantity) {
		if(!unfulfilledRequests.keySet().isEmpty()) {
			while(inventoryQuantity > 0) {
				if(unfulfilledRequests.keySet().isEmpty()) {
					break;
				}
				String usernameKey = unfulfilledRequests.keySet().iterator().next();
				AllocatedFoodItem unfulfilledRequest = unfulfilledRequests.get(usernameKey);
				unfulfilledRequests.remove(usernameKey);
				Integer allocatedQuantity = unfulfilledRequest.getAllocatedQuantity();
				Integer requestedQuantity = unfulfilledRequest.getRequestedQuantity();
				Integer difference = requestedQuantity - allocatedQuantity;
				if(difference >= inventoryQuantity) {
					unfulfilledRequest.setAllocatedQuantity(unfulfilledRequest.getAllocatedQuantity() + inventoryQuantity);
					inventoryQuantity -= inventoryQuantity;
					break;
				} else {
					unfulfilledRequest.setAllocatedQuantity(unfulfilledRequest.getAllocatedQuantity() + difference);
					inventoryQuantity -= difference;
				}
				FoodItem unfulfilledRequestFoodItem = unfulfilledRequest.getFoodItem();
				String category = unfulfilledRequestFoodItem.getCategory();
				String classification = unfulfilledRequestFoodItem.getClassification();
				String description = unfulfilledRequestFoodItem.getDescription();
				Allocation allocation = allocationMap.get(usernameKey);
				// Iterator is generated here to remove previously added AllocatedFoodItem
				Iterator<AllocatedFoodItem> allocationListIterator = allocation.getAllocatedItems().iterator();
				while(allocationListIterator.hasNext()) {
					AllocatedFoodItem allocatedFoodItem = allocationListIterator.next();
					FoodItem allocatedDbFoodItem = allocatedFoodItem.getFoodItem();
					if(allocatedDbFoodItem.getCategory().equals(category) && 
							allocatedDbFoodItem.getClassification().equals(classification) &&
							allocatedDbFoodItem.getDescription().equals(description)) {
						allocationListIterator.remove();
						allocation.getAllocatedItems().add(unfulfilledRequest);
						unfulfilledRequest.setAllocation(allocation);
						allocationMap.replace(usernameKey, allocation);
					}
				}
			}
		}
		return allocationMap;
	}
	
	private Integer evaluateOptimalBeneficiaryAllocationCount(Integer numberOfBeneficiariesAllocated, Integer inventoryQuantity) {
		Integer maxNumberBeneficiariesPerFoodItem = 0;
		if(inventoryQuantity < 50) {
			maxNumberBeneficiariesPerFoodItem = 5;
		} else if (inventoryQuantity < 100) {
			maxNumberBeneficiariesPerFoodItem = 10;
		} else {
			maxNumberBeneficiariesPerFoodItem = 20;
		}
		if(numberOfBeneficiariesAllocated > maxNumberBeneficiariesPerFoodItem) {
			numberOfBeneficiariesAllocated = maxNumberBeneficiariesPerFoodItem;
		} 
		return numberOfBeneficiariesAllocated;
	}
	
	@Override
	public void updateAllocation(AllocationUpdateDTO allocation) {
		// TODO Auto-generated method stub
		Allocation dbAllocation = allocationRepository.findByBeneficiaryUserUsername(allocation.getBeneficiary());// Id(allocation.getId());
		if(dbAllocation == null) {
			throw new InvalidAllocationException(ErrorMessages.INVALID_ALLOCATION);
		}
		List<AllocatedFoodItem> dbAllocatedItems = dbAllocation.getAllocatedItems();
		Map<String, Integer> dbAllocationMap = new HashMap<String, Integer>();
		for(int i = 0; i < dbAllocatedItems.size(); i++) {
			AllocatedFoodItem allocatedFoodItem = dbAllocatedItems.get(i);
			String key = allocatedFoodItem.getFoodItem().getCategory() + "," + 
					allocatedFoodItem.getFoodItem().getClassification() + "," + 
					allocatedFoodItem.getFoodItem().getDescription();
			dbAllocationMap.put(key, i);
		}
		List<Map<String, Object>> updatedAllocatedItems = allocation.getAllocatedItems();
		for(Map<String, Object> updatedAllocatedItemMap : updatedAllocatedItems) {
			String category = (String)updatedAllocatedItemMap.get("category");
			String classification = (String)updatedAllocatedItemMap.get("classification");
			String description = (String)updatedAllocatedItemMap.get("description");
			Integer allocatedQuantity = (Integer)updatedAllocatedItemMap.get("allocatedQuantity");
			String key = category + "," + classification + "," + description;
			dbAllocatedItems.get(dbAllocationMap.get(key)).setAllocatedQuantity(allocatedQuantity);
		}
		dbAllocation.setAllocatedItems(dbAllocatedItems);
		allocationRepository.save(dbAllocation);
	}

	private Map<String, Allocation> allocateSimilarFoodItems(Map<String, Allocation> allocationMap, List<FoodItem> foodItemsAllocated, List<Request> zeroAllocatedRequests) {
		List<FoodItem> allFoodItems = foodRepository.findAll();
		Iterator<Request> zeroAllocatedRequestIterator = zeroAllocatedRequests.iterator();
		
		for(FoodItem dbFoodItem : allFoodItems) {
			if(!foodItemsAllocated.contains(dbFoodItem) && dbFoodItem.getQuantity() > 0) {
				//this block is entered only if the current dbFoodItem has not been allocated before at all (not found in foodItemsAllocated)
				String dbFoodItemCategory = dbFoodItem.getCategory();
				String dbFoodItemClassification = dbFoodItem.getClassification();
				String [] dbFoodItemDescription = dbFoodItem.getDescription().split("-");
				int dbFoodItemQuantity = dbFoodItem.getQuantity();
				
				while(zeroAllocatedRequestIterator.hasNext()) {
					Request unfulfilledRequest = zeroAllocatedRequestIterator.next();

					if(dbFoodItemQuantity == 0) {
						break;
					}
					FoodItem requestFoodItem = unfulfilledRequest.getFoodItem();
					String requestCategory = requestFoodItem.getCategory();
					String requestClassification = requestFoodItem.getClassification();
					String [] requestDescription = requestFoodItem.getDescription().split("-");
					int unfulfilledRequestQuantity = unfulfilledRequest.getRequestedQuantity();
					int allocatedQuantity = 0;
					String beneficiaryUsername = unfulfilledRequest.getBeneficiary().getUser().getUsername();
					
					if(requestCategory.equals(dbFoodItemCategory) && requestClassification.equals(dbFoodItemClassification)) {
						if(requestDescription.length == dbFoodItemDescription.length) {	//this is to check if halal food item is halal or not, because halal will have length of 2, non-halal will have length of 1
							if(requestDescription[0].equals(dbFoodItemDescription[0])) {
								//this block will be entered if the food item name is similar, just different weight
								double dbFoodItemWeight = normalizeWeight(dbFoodItemDescription);
								double requestFoodItemWeight = normalizeWeight(requestDescription);
								
								double requestTotalWeight = requestFoodItemWeight * unfulfilledRequestQuantity;
								double dbTotalWeight = dbFoodItemWeight *  dbFoodItemQuantity;
								
								if(dbTotalWeight >= requestTotalWeight) {
									//db's food item total weight is more of same to the request total weight
									//Example:
									//db food item = 250g * 10 = 2500g
									//requested food item = 100g * 11 = 1100
									allocatedQuantity = (int)(requestTotalWeight / dbFoodItemWeight);
									//allocatedQuantity = 1100 / 250 = 4
									dbFoodItemQuantity = dbFoodItemQuantity - allocatedQuantity;
									
									//add to allocationMap
									Allocation allocation = allocationMap.get(beneficiaryUsername);
									AllocatedFoodItem allocatedBySimilarity = new AllocatedFoodItem(dbFoodItem, 0, allocatedQuantity);	//0 represents the requested quantity which is 0
									allocatedBySimilarity.setAllocation(allocation);
									allocation.getAllocatedItems().add(allocatedBySimilarity);
									allocationMap.replace(beneficiaryUsername, allocation);
									
									zeroAllocatedRequestIterator.remove();
									
									if(dbTotalWeight == requestTotalWeight) {
										break;
									}
									
								} else {
									//db's food item total weight is more of same to the request total weight
									//Example:
									//db food item = 250g * 6 = 1500g
									//requested food item = 100g * 32 = 3200g
									allocatedQuantity = dbFoodItemQuantity;
									dbFoodItemQuantity = 0;
									
									//add to allocationMap
									Allocation allocation = allocationMap.get(beneficiaryUsername);
									AllocatedFoodItem allocatedBySimilarity = new AllocatedFoodItem(dbFoodItem, 0, allocatedQuantity);	//0 represents the requested quantity which is 0
									allocatedBySimilarity.setAllocation(allocation);
									allocation.getAllocatedItems().add(allocatedBySimilarity);
									allocationMap.replace(beneficiaryUsername, allocation);
									
									zeroAllocatedRequestIterator.remove();
									break;
								}
							}
						}
					}
					
				}
			}
		}
		return allocationMap;
	}
	
	private double normalizeWeight(String [] description) {
		String weightInString = description[1];
		Double weight = Double.valueOf(0);
		if(weightInString.contains("kg") || weightInString.contains("l")) {
			weightInString.replace("kg", "").replace("l", "");
			weight = Double.parseDouble(weightInString) * 1000;
		} else {
			weightInString.replace("g", "").replace("ml", "");
			weight = Double.parseDouble(weightInString);
		}
		return weight;
	}
	
	@Override
	public void approveAllocations() {
		// TODO Auto-generated method stub
		List<Allocation> allocations = allocationRepository.findAll();
		for(Allocation allocation : allocations) {
			allocation.setApprovalStatus(true);
		}
		allocationRepository.save(allocations);
	}

	@Override
	public Boolean checkApproveStatus() {
		// TODO Auto-generated method stub
		Boolean result = true;
		List<Allocation> allocations = allocationRepository.findAll();
		for(Allocation allocation : allocations) {
			if(!allocation.getApprovalStatus()) {
				result = false;
				break;
			}
		}
		return result;
	}

}
