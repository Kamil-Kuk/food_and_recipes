package com.kamilkuk.food_and_recipes.repository;

import com.kamilkuk.food_and_recipes.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByNameAndWeightUnit(String name, String weightUnit);
    List<Product> findByNameContainsIgnoreCase(String name);
    List<Product> findByNameLikeIgnoreCase(String name);
}
