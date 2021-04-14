package com.kamilkuk.food_and_recipes.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String weightUnit;
    private double weight;
    private double kcal;
    private double proteins;
    private double fat;
    private double carbs;
    private double fiber;
}
