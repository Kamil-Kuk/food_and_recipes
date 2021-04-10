package com.kamilkuk.food_and_recipes.service;

import com.kamilkuk.food_and_recipes.model.Product;
import com.kamilkuk.food_and_recipes.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product save(Product product){
        return productRepository.save(product);
    }

    public Product get(Long id){
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    public List<Product> getByName(String productName){
        return productRepository.findByNameContainsIgnoreCase(productName);
    }

}
