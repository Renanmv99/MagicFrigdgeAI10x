package com.magicFridgeAi.MagicFridgeAi.repository;

import com.magicFridgeAi.MagicFridgeAi.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
}
