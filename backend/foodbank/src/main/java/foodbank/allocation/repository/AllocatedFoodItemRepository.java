package foodbank.allocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.allocation.entity.AllocatedFoodItem;

public interface AllocatedFoodItemRepository extends JpaRepository<AllocatedFoodItem, Long> {

}
