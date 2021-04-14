package com.kamilkuk.food_and_recipes.service;

import com.kamilkuk.food_and_recipes.model.Product;
import com.kamilkuk.food_and_recipes.mapper.ProductDto;
import com.kamilkuk.food_and_recipes.mapper.ProductMapper;
import com.kamilkuk.food_and_recipes.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    public Product save(ProductDto productDto){
        Product product = mapper.dtoToProduct(productDto);
        return productRepository.save(product);
    }

    public Product get(Long id){
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    public List<Product> getByName(String productName){
        return productRepository.findByNameStartingWithIgnoreCase(productName);
    }

    public List<Product> getByNameAndWeightUnit(String productName, String weightUnit){
        return productRepository.findByNameStartingWithIgnoreCaseAndWeightUnit(productName, weightUnit);
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Product update(ProductDto productDto){
        Product product = mapper.dtoToProduct(productDto);
        return productRepository.save(product);
    }

    public boolean remove(Long id){
        productRepository.delete(get(id));
        try{
            get(id);
            return false;
        }catch (NoSuchElementException e){
            return true;
        }
    }
}
