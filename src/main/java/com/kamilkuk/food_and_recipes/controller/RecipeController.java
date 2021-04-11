package com.kamilkuk.food_and_recipes.controller;

import com.kamilkuk.food_and_recipes.model.Recipe;
import com.kamilkuk.food_and_recipes.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<Recipe>> getAll(){
        return ResponseEntity.ok(recipeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> get(@PathVariable Long id){
        return ResponseEntity.ok(recipeService.get(id));
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<Recipe>> findByTitle(@RequestParam String keyword){
        return ResponseEntity.ok(recipeService.getByKeyword(keyword));
    }

    @GetMapping("/search/category")
    public ResponseEntity<List<Recipe>> findByCategory(@RequestParam String category){
        return ResponseEntity.ok(recipeService.getByCategory(category));
    }

    @GetMapping("/search/region")
    public ResponseEntity<List<Recipe>> findByRegion(@RequestParam String region){
        return ResponseEntity.ok(recipeService.getByRegion(region));
    }

    @PostMapping
    public ResponseEntity<Recipe> save(@RequestBody Recipe recipe){
        return ResponseEntity.ok(recipeService.save(recipe));
    }

    @PatchMapping
    public ResponseEntity<Recipe> update(@RequestBody Recipe recipe){
        return ResponseEntity.ok(recipeService.update(recipe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.ok(recipeService.remove(id));
    }
}
