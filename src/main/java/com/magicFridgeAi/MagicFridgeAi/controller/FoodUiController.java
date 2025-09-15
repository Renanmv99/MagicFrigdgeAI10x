package com.magicFridgeAi.MagicFridgeAi.controller;

import com.magicFridgeAi.MagicFridgeAi.model.FoodItem;
import com.magicFridgeAi.MagicFridgeAi.service.FoodItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui/food")
public class FoodUiController {

    private final FoodItemService foodItemService;

    public FoodUiController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("foods", foodItemService.listar());
        return "food-list"; // Thymeleaf vai procurar em templates/food-list.html
    }

    @GetMapping("/new")
    public String newFoodForm(Model model) {
        model.addAttribute("food", new FoodItem());
        return "food-form";
    }

    @PostMapping
    public String saveFood(@ModelAttribute FoodItem foodItem) {
        foodItemService.salvar(foodItem);
        return "redirect:/ui/food";
    }
}

