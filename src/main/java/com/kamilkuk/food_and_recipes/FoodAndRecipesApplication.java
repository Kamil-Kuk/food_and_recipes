package com.kamilkuk.food_and_recipes;

import com.kamilkuk.food_and_recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodAndRecipesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodAndRecipesApplication.class, args);
    }
}
