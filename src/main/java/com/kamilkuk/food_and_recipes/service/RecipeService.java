package com.kamilkuk.food_and_recipes.service;

import com.kamilkuk.food_and_recipes.model.Recipe;
import com.kamilkuk.food_and_recipes.repository.ProductRepository;
import com.kamilkuk.food_and_recipes.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;

    public Recipe get(Long id){
        return recipeRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException());
    }

    public Recipe save(Recipe recipe){
        return recipeRepository.save(recipe);
    }

}
