package com.magicFridgeAi.MagicFridgeAi.controller;

import com.magicFridgeAi.MagicFridgeAi.enums.Meal;
import com.magicFridgeAi.MagicFridgeAi.model.FoodItem;
import com.magicFridgeAi.MagicFridgeAi.service.FoodItemService;
import com.magicFridgeAi.MagicFridgeAi.service.GeminiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/ui/recipe")
public class RecipeUiController {

    private final FoodItemService foodItemService;
    private final GeminiService geminiService;

    public RecipeUiController(FoodItemService foodItemService, GeminiService geminiService) {
        this.foodItemService = foodItemService;
        this.geminiService = geminiService;
    }

    @GetMapping
    public String showForm() {
        return "recipe-form";
    }

    @PostMapping
    public String generate(@RequestParam Meal meal, Model model) {
        List<FoodItem> foodItems = foodItemService.listar();
        String recipe = geminiService.generateRecipe(foodItems, meal).block();
        model.addAttribute("recipe", recipe);
        return "recipe-result";
    }
}

