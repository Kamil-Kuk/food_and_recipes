package com.kamilkuk.food_and_recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

//    @ManyToMany
//    private Set<Allergen> allergens;

//    @ElementCollection(targetClass = Allergen.class)
//    @CollectionTable(name = "recipe_allergen",
//            joinColumns = @JoinColumn(name = "recipe_id"))
//    @Enumerated(EnumType.STRING)
//    @Column(name = "allergen_name")
//    private Set<Allergen> allergens;


}
