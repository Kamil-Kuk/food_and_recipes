package com.kamilkuk.food_and_recipes.controller;

import com.kamilkuk.food_and_recipes.mapper.RecipeDto;
import com.kamilkuk.food_and_recipes.model.Recipe;
import com.kamilkuk.food_and_recipes.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @Transactional
    @GetMapping
    public ResponseEntity<List<Recipe>> getAll(){
        return ResponseEntity.ok(recipeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> get(@PathVariable Long id){
        return ResponseEntity.ok(recipeService.get(id));
    }

//    @GetMapping("/{id}/shoppingList")
//    public ResponseEntity<Map<Product,Double>> getShoppingList(@PathVariable Long id){
//        return ResponseEntity.ok(recipeService.get(id).getIngredients());
//    }

    @GetMapping("/{id}/shoppingList")
    public ResponseEntity<List<String>> getShoppingList(@PathVariable Long id){
        return ResponseEntity.ok(recipeService.getShoppingList(id));
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<Recipe>> findByTitle(@RequestParam String title){
        return ResponseEntity.ok(recipeService.getByTitle(title));
    }

    @GetMapping("/search/keyword")
    public ResponseEntity<Set<Recipe>> findByKeyword(@RequestParam String keyword){
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

    @GetMapping("/search/kcal")
    public ResponseEntity<List<Recipe>> findByKcalRange(@RequestParam(required = false) Double min, @RequestParam(required = false) Double max){
        if(min!=null && max!=null){
            return ResponseEntity.ok(recipeService.getByKcalBetween(min, max));
        } else if(max!=null){
            return ResponseEntity.ok(recipeService.getByKcalLessThan(max));
        } else if(min!=null){
            return ResponseEntity.ok(recipeService.getByKcalGreaterThan(min));
        } else{
            return ResponseEntity.status(400).build();
        }
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<Recipe> save(@RequestBody RecipeDto dto){
        return ResponseEntity.ok(recipeService.save(dto));
    }

    @Secured({"ROLE_ADMIN"})
    @PatchMapping
    public ResponseEntity<Recipe> update(@RequestBody RecipeDto dto){
        return ResponseEntity.ok(recipeService.update(dto));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.ok(recipeService.remove(id));
    }
}
