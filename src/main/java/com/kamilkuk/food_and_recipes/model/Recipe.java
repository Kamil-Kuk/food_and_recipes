package com.kamilkuk.food_and_recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;
    private String imageURL;
    private String youTubeURL;
    private String category;
    private String cuisineRegion;
    private String instructions;

    @OneToMany
    private Set<Product> ingredients;

//    @Enumerated(EnumType.STRING)
//    private CousineRegion cuisineRegion;

//    @Enumerated(EnumType.STRING)
//    private CousineCategory cousineCategory;

//    @ManyToMany
//    private Set<Allergen> allergens;

//    @ElementCollection(targetClass = Allergen.class)
//    @CollectionTable(name = "recipe_allergen",
//            joinColumns = @JoinColumn(name = "recipe_id"))
//    @Enumerated(EnumType.STRING)
//    @Column(name = "allergen_name")
//    private Set<Allergen> allergens;


}
