package com.kamilkuk.food_and_recipes.mapper;

import com.kamilkuk.food_and_recipes.model.Recipe;
import org.mapstruct.Mapper;

@Mapper
public interface RecipeMapper {

    RecipeDto recipeToDto(Recipe recipe);
    Recipe dtoToRecipe(RecipeDto dto);
}
