package com.kamilkuk.food_and_recipes.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kamilkuk.food_and_recipes.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {

    private Long id;
    private String title;
    private String imageURL;
    private String youTubeURL;
    private String instructions;
    @JsonIgnore
    private Map<ProductDto, Double> ingredients;
    private String region;
    private String category;
    private double kcal;
    private double proteins;
    private double fat;
    private double carbs;
    private double fiber;
}
