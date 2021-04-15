package com.kamilkuk.food_and_recipes.mapper;

import com.kamilkuk.food_and_recipes.model.CusineCategory;
import com.kamilkuk.food_and_recipes.model.CusineRegion;
import com.kamilkuk.food_and_recipes.model.Product;
import com.kamilkuk.food_and_recipes.model.Recipe;
import com.kamilkuk.food_and_recipes.repository.ProductRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class RecipeMapper {

    public Recipe mapDtoToRecipe(RecipeDto dto, ProductRepository productRepository){
        Recipe recipe = new Recipe();
        if(dto.getTitle()!=null){
            recipe.setTitle(dto.getTitle());
        }
        if(dto.getImageURL()!=null){
            recipe.setImageURL(dto.getImageURL());
        }
        if(dto.getYouTubeURL()!=null){
            recipe.setYouTubeURL(dto.getYouTubeURL());
        }
        if(dto.getInstructions()!=null){
            recipe.setInstructions(dto.getInstructions());
        }
        if(dto.getRegion()!=null){
            recipe.setRegion(CusineRegion.valueOf(dto.getRegion()));
        }
        if(dto.getCategory()!=null){
            recipe.setCategory(CusineCategory.valueOf(dto.getCategory()));
        }
        recipe.setKcal(dto.getKcal());
        recipe.setProteins(dto.getProteins());
        recipe.setFat(dto.getFat());
        recipe.setCarbs(dto.getCarbs());
        recipe.setFiber(dto.getFiber());
        if(dto.getIngredients()!=null&&dto.getIngredients().size()>0){
            Product product;
            Map<Product,Double> ingredients = new HashMap<>();
            for(Map.Entry<Long,Double> entry: dto.getIngredients().entrySet()){
                product = productRepository.findById(entry.getKey()).orElseThrow(() -> new NoSuchElementException());
                ingredients.put(product,entry.getValue());
            }
            recipe.setIngredients(ingredients);
        }
        return recipe;
    }

}
