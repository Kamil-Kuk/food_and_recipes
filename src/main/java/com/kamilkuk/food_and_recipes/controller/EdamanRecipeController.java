package com.kamilkuk.food_and_recipes.controller;

import com.kamilkuk.food_and_recipes.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class EdamanRecipeController {
    private final RecipeService recipeService;

    @Value("${com.kamilkuk.food_and_recipe.edamam.recipe.app_id}")
    String appId;

    @Value("${com.kamilkuk.food_and_recipe.edamam.recipe.app_key}")
    String appKey;


    RestTemplate restTemplate;



    @GetMapping("https://api.edamam.com/search?app_id=0c532a02&app_key=17df71d2fcd35af285f7ae548d4103ac")
    public ResponseEntity<String> get(){
        return ResponseEntity.ok().body("hi");
    }

}
