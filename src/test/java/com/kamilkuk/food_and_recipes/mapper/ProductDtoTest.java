package com.kamilkuk.food_and_recipes.mapper;

import com.kamilkuk.food_and_recipes.model.Product;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ProductDtoTest {

    @Test
    void should_map_Product_to_dto() {
        //given
        ProductMapper mapper = Mappers.getMapper(ProductMapper.class);
        Product product = new Product(1L, "Product", "g", 100.5, 12.5, 15.5, 10.8, 15.5, 0.5);

        //when
        ProductDto productDto = mapper.productToDto(product);

        //then
        assertAll(
                () -> assertNotNull(productDto),
                () -> assertEquals(product.getName(), productDto.getName()),
                () -> assertEquals(product.getId(), productDto.getId()),
                () -> assertEquals(product.getWeightUnit(), productDto.getWeightUnit()),
                () -> assertEquals(product.getWeight(), productDto.getWeight())
        );
    }

}
