package com.kamilkuk.food_and_recipes.repository;

import com.kamilkuk.food_and_recipes.model.CusineCategory;
import com.kamilkuk.food_and_recipes.model.CusineRegion;
import com.kamilkuk.food_and_recipes.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByTitleContaining(String keyword);
    List<Recipe> findByCategory(CusineCategory category);
    List<Recipe> findByRegion(CusineRegion region);
}
