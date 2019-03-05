package foodbank.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.inventory.entity.FoodItem;

public interface FoodRepository extends JpaRepository<FoodItem, Long> {

	List<FoodItem> findByCategory(String category);
	
	List<FoodItem> findByCategoryAndClassification(String category, String classification);
	
	FoodItem findByCategoryAndClassificationAndDescription(String category, String classification, String description);
	
	List<FoodItem> findByValueEquals(Double value);
	
}
