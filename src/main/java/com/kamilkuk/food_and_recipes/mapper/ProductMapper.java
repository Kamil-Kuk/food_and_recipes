package com.kamilkuk.food_and_recipes.mapper;

import com.kamilkuk.food_and_recipes.model.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductDto productToDto(Product product);
    Product dtoToProduct(ProductDto dto);
}
