package com.magicFridgeAi.MagicFridgeAi.controller;

import com.magicFridgeAi.MagicFridgeAi.enums.Meal;
import com.magicFridgeAi.MagicFridgeAi.model.FoodItem;
import com.magicFridgeAi.MagicFridgeAi.service.GeminiService;
import com.magicFridgeAi.MagicFridgeAi.service.FoodItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class RecipeController {

    private FoodItemService foodItemService;
    private GeminiService geminiService;

    public RecipeController(FoodItemService foodItemService, GeminiService geminiService){
        this.foodItemService = foodItemService;
        this.geminiService = geminiService;
    }

    @GetMapping("/generate")
    public Mono<ResponseEntity<String>> generateRecipe(@RequestParam Meal meal){
        List<FoodItem> foodItems = foodItemService.listar();
        return geminiService.generateRecipe(foodItems, meal)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
}
