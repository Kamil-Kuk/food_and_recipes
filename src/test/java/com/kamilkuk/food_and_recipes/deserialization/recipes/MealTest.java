package com.kamilkuk.food_and_recipes.deserialization.recipes;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class MealTest {

    @Test
    void should_return_non_null_non_empty_ingredient_list() {
        //given
        Meal meal = new Meal();
        meal.setStrIngredient1("ingr1");
        meal.setStrIngredient3("ingr2");
        meal.setStrIngredient6("ingr3");
        meal.setStrIngredient7("ingr4");
        meal.setStrIngredient8("");
        meal.setStrIngredient9("  ");

        //when
        List<String> result = meal.getAllIngredients();

        //then
        Assertions.assertThat(result.size()).isEqualTo(4);
    }

    @Test
    void should_return_non_null_non_empty_measurement_list() {
        //given
        Meal meal = new Meal();
        meal.setStrMeasure1("measure1");
        meal.setStrMeasure3("measure2");
        meal.setStrMeasure5("measure3");
        meal.setStrMeasure8("");
        meal.setStrMeasure19(" ");
        meal.setStrMeasure20("  ");

        //when
        List<String> result = meal.getAllMeasures();

        //then
        Assertions.assertThat(result.size()).isEqualTo(3);
    }
}