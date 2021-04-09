package com.kamilkuk.food_and_recipes.repository;

import com.kamilkuk.food_and_recipes.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
