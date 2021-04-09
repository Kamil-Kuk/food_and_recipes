package com.kamilkuk.food_and_recipes.service;

import com.kamilkuk.food_and_recipes.model.Recipe;
import com.kamilkuk.food_and_recipes.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public Recipe save(Recipe recipe){
        return recipeRepository.save(recipe);
    }

}
