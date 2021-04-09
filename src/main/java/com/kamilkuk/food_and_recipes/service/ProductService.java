package com.kamilkuk.food_and_recipes.service;

import com.kamilkuk.food_and_recipes.model.Product;
import com.kamilkuk.food_and_recipes.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(Product product){
        return productRepository.save(product);
    }
}
