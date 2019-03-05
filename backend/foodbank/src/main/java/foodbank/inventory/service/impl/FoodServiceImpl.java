package foodbank.inventory.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import foodbank.donor.entity.DonatedNPFoodItem;
import foodbank.donor.entity.Donor;
import foodbank.donor.repository.DonorRepository;
import foodbank.inventory.dto.BarcodeResponseDTO;
import foodbank.inventory.dto.FoodItemDTO;
import foodbank.inventory.entity.Barcode;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.BarcodeRepository;
import foodbank.inventory.repository.FoodRepository;
import foodbank.inventory.service.FoodService;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.InvalidFoodException;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private BarcodeRepository barcodeRepository;
	
	@Autowired
	private DonorRepository donorRepository;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Override
	public List<FoodItem> retrieveAllFoodItems() {
		// TODO Auto-generated method stub
		return foodRepository.findAll();
	}

	@Override
	public List<FoodItem> retrieveAllFoodItemsInCategory(String categoryName) {
		// TODO Auto-generated method stub
		return foodRepository.findByCategory(categoryName);
	}

	@Override
	public List<FoodItem> retrieveFoodItemsByCategoryAndClassification(String categoryName, String classificationName) {
		// TODO Auto-generated method stub
		return foodRepository.findByCategoryAndClassification(categoryName, classificationName);
	}

	@Override
	public void createFoodItem(FoodItemDTO foodItem) {
		// TODO Auto-generated method stub
		String category = foodItem.getCategory();
		String classification = foodItem.getClassification();
		String description = foodItem.getDescription();
		Integer quantity = foodItem.getQuantity();
		Double value = foodItem.getValue();
		FoodItem dbFoodItem = foodRepository.findByCategoryAndClassificationAndDescription(category, classification, description);
		if(dbFoodItem != null) {
			throw new InvalidFoodException(ErrorMessages.DUPLICATE_ITEM);
		}
		foodRepository.save(new FoodItem(category, classification, description, quantity, value));
	}

	@Override
	public void overwriteFoodItemQuantity(FoodItemDTO foodItem) {
		// TODO Auto-generated method stub
		// This method overwrites the existing quantity (e.g. 3 -> 0)
		String category = foodItem.getCategory();
		String classification = foodItem.getClassification();
		String description = foodItem.getDescription();
		FoodItem dbFoodItem = foodRepository.findByCategoryAndClassificationAndDescription(category, classification, description);
		if(dbFoodItem == null) {
			throw new InvalidFoodException(ErrorMessages.NO_SUCH_ITEM);
		}
		dbFoodItem.setQuantity(foodItem.getQuantity());
		dbFoodItem.setValue(foodItem.getValue());
		foodRepository.save(dbFoodItem);
	}

	@Override
	public void modifyFoodItemQuantity(FoodItemDTO foodItem) {
		// TODO Auto-generated method stub
		// This method adds/subtracts the existing quantity (e.g. 3 -> 4)
		// This method is used in stocktaking, but may entail creation of new item due to weight differences
		FoodItem dbFoodItem = null;
		String category = foodItem.getCategory();
		String classification = foodItem.getClassification();
		String description = foodItem.getDescription();
		String barcode = foodItem.getBarcode();
		Integer quantity = foodItem.getQuantity();
		if(barcode != null && !barcode.isEmpty()) {
			Barcode dbBarcode = barcodeRepository.findByBarcode(barcode);
			if(dbBarcode != null) {
				dbFoodItem = dbBarcode.getScannedItem();
				dbFoodItem.setQuantity(dbFoodItem.getQuantity() + quantity);
				foodRepository.save(dbFoodItem);
			} else {
				dbFoodItem = foodRepository.findByCategoryAndClassificationAndDescription(category, classification, description);
				if(dbFoodItem == null) {
					dbFoodItem = new FoodItem(category, classification, description, Integer.valueOf(0), Double.valueOf(0));
					this.template.convertAndSend("/client/notifications", Boolean.TRUE);
				}
				dbFoodItem.setQuantity(dbFoodItem.getQuantity() + quantity);
				Barcode newBarcode = new Barcode(barcode, dbFoodItem);
				barcodeRepository.save(newBarcode);
			}
		} else {
			dbFoodItem = foodRepository.findByCategoryAndClassificationAndDescription(category, classification, description);
			if(dbFoodItem == null) {
				dbFoodItem = new FoodItem(category, classification, description, Integer.valueOf(0), Double.valueOf(0));
			}
			dbFoodItem.setQuantity(dbFoodItem.getQuantity() + quantity);
			foodRepository.save(dbFoodItem);
		}
		if(foodItem.getDonorName() != null && !foodItem.getDonorName().isEmpty()) {
			Donor dbDonor = donorRepository.findByName(foodItem.getDonorName());
			if(dbDonor == null) {
				dbDonor = new Donor(foodItem.getDonorName());
			}
			List<DonatedNPFoodItem> npDonations = dbDonor.getNonperishableDonations();
			Boolean foundPreviouslyDonatedItem = Boolean.FALSE;
			for(DonatedNPFoodItem donation : npDonations) {
				FoodItem donatedItem = donation.getDonatedItem();
				String donatedItemCategory = donatedItem.getCategory();
				String donatedItemClassification = donatedItem.getClassification();
				String donatedItemDescription = donatedItem.getDescription();
				if(donatedItemCategory.equals(category) && donatedItemClassification.equals(classification)
						&& donatedItemDescription.equals(description)) {
					donation.setDonatedQuantity(donation.getDonatedQuantity() + quantity);
					foundPreviouslyDonatedItem = Boolean.TRUE;
					break;
				}
			}
			if(!foundPreviouslyDonatedItem) {
				DonatedNPFoodItem newDonation = new DonatedNPFoodItem(dbFoodItem, quantity, new Date());
				newDonation.setDonor(dbDonor);
				npDonations.add(newDonation);
			}
			donorRepository.save(dbDonor);
		}
	}

	@Override
	public void resetInventoryQuantity() {
		// TODO Auto-generated method stub
		List<FoodItem> dbFoodItems = foodRepository.findAll();
		for(FoodItem dbFoodItem : dbFoodItems) {
			dbFoodItem.setQuantity(0);
		}
		foodRepository.save(dbFoodItems);
	}

	@Override
	public BarcodeResponseDTO readBarcode(String barcode) {
		// TODO Auto-generated method stub
		FoodItem dbFoodItem = barcodeRepository.findByBarcode(barcode).getScannedItem();
		if(dbFoodItem != null) {
			String category = dbFoodItem.getCategory();
			String classification = dbFoodItem.getClassification();
			String description = dbFoodItem.getDescription();
			return new BarcodeResponseDTO(category, classification, description);
		}
		return null;
	}

}
