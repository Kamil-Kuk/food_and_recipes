package com.kamilkuk.food_and_recipes.deserialization;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeDeserializerTest {


    @Test
    void should_parse_quantity_from_string() {
        //given
        String str1 = "50g/1¾oz";
        String str2 = "2 free-range";
        String str3 = "grated, to taste";
        String str4 = "1 - 2tbsp";
        String str5 = "1½ cup ";
        String str6 = "4ml";
        String str7 = "2 x 400g tins";
        String str8 = "2 wyz 5";
        String str9 = "1/2 cup";
        String str10 = "2 (460g)";
        String str11 = "1/2 cups";
        String str12 = "1 ½ tsp";
        String str13 = "1 cut into 1/2-inch cubes";
        String str14 = "8-ounce slice";
        String str15 = "1 – 14-ounce can";

        List<String> stringList = List.of(str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14, str15);
        List<Double> result = new ArrayList<>();

        //when
        for (String str : stringList) {
            result.addAll(RecipeDeserializer.getQuantity(str));
        }

        //then
        assertAll(
                () -> assertEquals(50, result.get(0)),
                () -> assertEquals(1.75, result.get(1)),
                () -> assertEquals(2, result.get(2)),
                () -> assertEquals(1, result.get(3)),
                () -> assertEquals(1.5, result.get(4)),
                () -> assertEquals(1.5, result.get(5)),
                () -> assertEquals(4, result.get(6)),
                () -> assertEquals(800, result.get(7)),
                () -> assertEquals(2, result.get(8)),
                () -> assertEquals(5, result.get(9)),
                () -> assertEquals(0.5, result.get(10)),
                () -> assertEquals(920, result.get(11)),
                () -> assertEquals(0.5, result.get(12)), //TODO - fix parsing /d-/d\/d
                () -> assertEquals(1.5, result.get(13)),
                () -> assertEquals(1, result.get(14)),
                () -> assertEquals(8, result.get(16)),
                () -> assertEquals(14, result.get(17))
        );
    }

    @Test
    void should_parse_weight_units_from_string() {
        //given
        String str1 = "25g/1oz";
        String str4 = "1-2tbsp";
        String str5 = "1½ cup ";
        String str6 = "4ml";
        String str10 = "2 (460g)";
        String str11 = "6 cloves";

        List<String> stringList = List.of(str1, str4, str5, str6, str10, str11);
        List<String> result = new ArrayList<>();

        //when
        for (String str : stringList) {
            result.addAll(RecipeDeserializer.getWeightUnit(str));
        }

        //then
        assertAll(
                () -> assertEquals("Gram", result.get(0)),
                () -> assertEquals("Ounce", result.get(1)),
                () -> assertEquals("Tablespoon", result.get(2)),
                () -> assertEquals("cup", result.get(3)),
                () -> assertEquals("Mililiter", result.get(4)),
                () -> assertEquals("Gram", result.get(5)),
                () -> assertEquals("clove", result.get(6))
        );
    }
}