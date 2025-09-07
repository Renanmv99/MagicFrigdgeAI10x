package com.magicFridgeAi.MagicFridgeAi.service;

import com.magicFridgeAi.MagicFridgeAi.model.FoodItem;
import com.magicFridgeAi.MagicFridgeAi.repository.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;

    public FoodItemService(FoodItemRepository foodItemRepository){
        this.foodItemRepository = foodItemRepository;
    }

    public FoodItem salvar(FoodItem foodItem){
        return foodItemRepository.save(foodItem);
    }

    public List<FoodItem> listar(){
        return foodItemRepository.findAll();
    }

    public Optional<FoodItem> listarPorId(Long id){
        return foodItemRepository.findById(id);
    }

    public FoodItem update(FoodItem item){
           return foodItemRepository.save(item);
    }

    public void delete(Long id){
        foodItemRepository.deleteById(id);
    }
}
