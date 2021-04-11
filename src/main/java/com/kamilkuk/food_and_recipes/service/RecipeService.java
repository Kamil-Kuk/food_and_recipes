package com.kamilkuk.food_and_recipes.service;

import com.kamilkuk.food_and_recipes.model.CusineCategory;
import com.kamilkuk.food_and_recipes.model.CusineRegion;
import com.kamilkuk.food_and_recipes.model.Recipe;
import com.kamilkuk.food_and_recipes.repository.ProductRepository;
import com.kamilkuk.food_and_recipes.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public Recipe get(Long id){
        return recipeRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException());
    }

    public Recipe save(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getAll(){
        return recipeRepository.findAll();
    }

    public Recipe update(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public boolean remove(Long id){
        recipeRepository.delete(get(id));
        try{
            get(id);
            return false;
        }catch (NoSuchElementException e){
            return true;
        }
    }

    public List<Recipe> getByKeyword(String keyWord) {
        return recipeRepository.findByTitleContaining(keyWord);
    }

    public List<Recipe> getByCategory(String category) {
        return recipeRepository.findByCategory(CusineCategory.valueOf(category.toUpperCase()));
    }

    public List<Recipe> getByRegion(String region) {
        return recipeRepository.findByRegion(CusineRegion.valueOf(region.toUpperCase()));
    }
}
