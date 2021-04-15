package com.kamilkuk.food_and_recipes.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Component;

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
    private Map<Long, Double> ingredients;
    private String region;
    private String category;
    private double kcal;
    private double proteins;
    private double fat;
    private double carbs;
    private double fiber;
}
