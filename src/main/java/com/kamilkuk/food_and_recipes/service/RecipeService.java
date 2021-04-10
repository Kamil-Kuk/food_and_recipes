package com.kamilkuk.food_and_recipes.service;

import com.kamilkuk.food_and_recipes.model.Product;
import com.kamilkuk.food_and_recipes.model.Recipe;
import com.kamilkuk.food_and_recipes.repository.ProductRepository;
import com.kamilkuk.food_and_recipes.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;

    public Recipe save(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public static void setKcal(Recipe recipe, Map<Product,Double> ingredients){
        double kcal = 0;
        for(Map.Entry<Product,Double> entry: ingredients.entrySet()){
            kcal += entry.getKey().getKcal() * entry.getValue();
        }
        recipe.setKcal(kcal);
    }

//    private void addIngredient(Recipe recipe, Product product){
//        recipe.setIngredients(Map);
//    }


}
