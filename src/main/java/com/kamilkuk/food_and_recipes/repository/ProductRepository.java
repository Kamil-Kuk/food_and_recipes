package com.kamilkuk.food_and_recipes.repository;

import com.kamilkuk.food_and_recipes.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByNameStartingWithIgnoreCase(String name);
    List<Product> findByNameStartingWithIgnoreCaseAndWeightUnit(String name, String weightUnit);

}
