package com.kamilkuk.food_and_recipes.repository;

import com.kamilkuk.food_and_recipes.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
