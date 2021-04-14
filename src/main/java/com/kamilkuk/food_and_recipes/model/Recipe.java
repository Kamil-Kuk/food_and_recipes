package com.kamilkuk.food_and_recipes.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String imageURL;
    private String youTubeURL;

    @Size(max = 5000)
    private String instructions;

    @ElementCollection
    @CollectionTable(name = "recipe_product_mapping",
            joinColumns = {@JoinColumn(name = "recipe_id", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Double> ingredients;

    @Enumerated(EnumType.STRING)
    private CusineRegion region;

    @Enumerated(EnumType.STRING)
    private CusineCategory category;
    private double kcal;
    private double proteins;
    private double fat;
    private double carbs;
    private double fiber;

    public void setNutrients(){
        double kcal = 0;
        double fat = 0;
        double fiber = 0;
        double proteins = 0;
        double carbs = 0;
        for(Map.Entry<Product,Double> entry: ingredients.entrySet()){
            Product product = entry.getKey();
            kcal += product.getKcal() * entry.getValue();
            fat += product.getFat() * entry.getValue();
            fiber += product.getFiber() * entry.getValue();
            proteins += product.getProteins() * entry.getValue();
            carbs += product.getCarbs() * entry.getValue();
        }
        this.setKcal(kcal);
        this.setFat(fat);
        this.setFiber(fiber);
        this.setProteins(proteins);
        this.setCarbs(carbs);
    }
}

