package com.kamilkuk.food_and_recipes.mapper;

import com.kamilkuk.food_and_recipes.model.Product;
import com.kamilkuk.food_and_recipes.model.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RecipeDtoTest {

    @Test
    void should_map_dto_to_Recipe() {
//        //given
//        @Autowired
//        RecipeMapper mapper = new RecipeMapper();
//        Product product1 = new Product(1L, "Product 1", "g", 100.5, 12.5, 15.5, 10.8, 15.5, 0.5);
//        Product product2 = new Product(1L, "Product 2", "g", 100.5, 12.5, 15.5, 10.8, 15.5, 0.5);
//        RecipeDto dto = new RecipeDto();
//        dto.setTitle("Test recipe");
//        dto.setRegion("ITALIAN");
//        dto.setCategory("DESSERT");
////        dto.setIngredients(Map.of(product1,1.0,product2,5.5));
//
//        //when
//        Recipe recipe = mapper.mapDtoToRecipe(dto);
//
//        //then
//        Assertions.assertAll(
//                () -> assertNotNull(recipe),
//                () -> assertEquals(dto.getRegion(), recipe.getRegion().toString()),
//                () -> assertEquals(dto.getCategory(), recipe.getCategory().toString())
////                () -> assertEquals(dto.getIngredients(), recipe.getIngredients())
//        );
    }
}