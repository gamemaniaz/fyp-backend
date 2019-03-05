package foodbank.inventory.service;

import java.util.List;

import foodbank.inventory.dto.BarcodeResponseDTO;
import foodbank.inventory.dto.FoodItemDTO;
import foodbank.inventory.entity.FoodItem;

public interface FoodService {
	
	List<FoodItem> retrieveAllFoodItems();
	
	List<FoodItem> retrieveAllFoodItemsInCategory(final String categoryName);
	
	List<FoodItem> retrieveFoodItemsByCategoryAndClassification(final String categoryName, final String classificationName);
	
	void createFoodItem(final FoodItemDTO foodItem);
	
	void overwriteFoodItemQuantity(final FoodItemDTO foodItem);
	
	void modifyFoodItemQuantity(final FoodItemDTO foodItem);
	
	void resetInventoryQuantity();
	
	BarcodeResponseDTO readBarcode(final String barcode);

}
