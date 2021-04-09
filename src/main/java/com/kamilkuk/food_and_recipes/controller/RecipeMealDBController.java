package com.kamilkuk.food_and_recipes.controller;

import com.kamilkuk.food_and_recipes.deserialization.mealDB.RecipeListMealDB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class RecipeMealDBController {
    RestTemplate restTemplate;

    @Value("${com.kamilkuk.food_and_recipe.the_meal_db.url}")
    String url;

    public RecipeMealDBController(RestTemplateBuilder restTemplateBuilder) {
        restTemplateBuilder.build();
    }

    public RecipeListMealDB get() {
        RecipeListMealDB recipeList = null;
        try {
            URI uri = new URI(url);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            Map<String,String> params = new HashMap<>();
            params.put("f","b");

            HttpEntity<String> request = new HttpEntity<>(headers);
            String recipeUrl = url + "?f={f}";
            ResponseEntity<RecipeListMealDB> totalEntity = restTemplate.exchange(recipeUrl,
                    HttpMethod.GET,
                    request,
                    RecipeListMealDB.class,
                    params);
            recipeList = totalEntity.getBody();
        } catch(URISyntaxException e){
            e.printStackTrace();
        }
        return recipeList;
    }
}
