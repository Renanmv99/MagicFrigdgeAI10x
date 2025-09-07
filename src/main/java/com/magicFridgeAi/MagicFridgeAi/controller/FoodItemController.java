package com.magicFridgeAi.MagicFridgeAi.controller;

import com.magicFridgeAi.MagicFridgeAi.model.FoodItem;
import com.magicFridgeAi.MagicFridgeAi.service.FoodItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/food")
public class FoodItemController {
    private final FoodItemService foodItemService;

    public FoodItemController(FoodItemService foodItemService){
        this.foodItemService = foodItemService;
    }

    @PostMapping
    public ResponseEntity<FoodItem > criar(@RequestBody FoodItem foodItem){
        FoodItem salvo = foodItemService.salvar(foodItem);
        return ResponseEntity.ok(foodItem);
    }

    @GetMapping
    public ResponseEntity<List<FoodItem>> listarTodos(){
        return ResponseEntity.ok().body(foodItemService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id){
        Optional<FoodItem> item = foodItemService.listarPorId(id);
        return ResponseEntity.ok().body(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> atualizar(@PathVariable Long id, @RequestBody FoodItem foodItem) {
        return foodItemService.listarPorId(id)
                .map(itemExistente -> {
                    foodItem.setId(itemExistente.getId());
                    FoodItem atualizado = foodItemService.update(foodItem);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        foodItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
