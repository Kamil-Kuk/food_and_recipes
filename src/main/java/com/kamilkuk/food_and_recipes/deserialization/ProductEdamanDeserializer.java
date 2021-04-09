package com.kamilkuk.food_and_recipes.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.kamilkuk.food_and_recipes.model.Product;

import java.io.IOException;

public class ProductEdamanDeserializer extends StdDeserializer<Product> {



    public ProductEdamanDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Product deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return null;
    }
}
