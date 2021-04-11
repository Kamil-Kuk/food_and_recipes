package com.kamilkuk.food_and_recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
