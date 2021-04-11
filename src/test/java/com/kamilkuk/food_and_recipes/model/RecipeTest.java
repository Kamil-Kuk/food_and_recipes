package com.kamilkuk.food_and_recipes.model;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    void should_set_valid_nutrients() {
        //given
        Product prod1 = new Product();
        prod1.setKcal(100);
        prod1.setFat(100);
        prod1.setFiber(100);
        prod1.setProteins(100);
        prod1.setCarbs(100);

        Product prod2 = new Product();
        prod2.setKcal(10);
        prod2.setFat(10);
        prod2.setFiber(10);
        prod2.setProteins(10);
        prod2.setCarbs(10);

        Recipe recipe = new Recipe();
        recipe.setIngredients(Map.of(prod1,5.0,prod2,2.0));

        //when
        recipe.setNutrients();

        //then
        assertAll(
                () -> assertEquals(520, recipe.getKcal()),
                () -> assertEquals(520, recipe.getFat()),
                () -> assertEquals(520, recipe.getFiber()),
                () -> assertEquals(520, recipe.getProteins()),
                () -> assertEquals(520, recipe.getCarbs())
        );
    }


}