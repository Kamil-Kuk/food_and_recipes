package com.kamilkuk.food_and_recipes.deserialization;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamilkuk.food_and_recipes.deserialization.edamam.*;
import com.kamilkuk.food_and_recipes.deserialization.mealDB.ProductListMealDB;
import com.kamilkuk.food_and_recipes.deserialization.mealDB.ProductMealDB;
import com.kamilkuk.food_and_recipes.model.Product;
import com.kamilkuk.food_and_recipes.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductDeserializer implements CommandLineRunner {

    private final ProductService productService;
    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @Value("${com.kamilkuk.food_and_recipe.the_meal_db.url}")
    private String urlMealDB;

    @Value("${com.kamilkuk.food_and_recipe.edamam.product.app_id}")
    private String appIdEdamam;

    @Value("${com.kamilkuk.food_and_recipe.edamam.product.app_key}")
    private String appKeyEdamam;

    private HttpResponse<String> getResponse(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    private List<ProductMealDB> getProductListMealDB() throws IOException, InterruptedException {
        HttpResponse<String> response = getResponse(urlMealDB + "/list.php?i=list");
        return objectMapper.readValue(response.body(), ProductListMealDB.class).getMeals();
    }

    private String getProductEdamam(String query) throws IOException, InterruptedException {
        String uri = String.format("https://api.edamam.com/api/food-database/v2/parser?ingr=%s&app_id=%s&app_key=%s", query, appIdEdamam, appKeyEdamam);
        HttpResponse<String> response = getResponse(uri);
        return response.body();
    }

    private Nutrients getNutrientsEdamam(String json) throws IOException, InterruptedException {
        if (!objectMapper.readValue(json, ProductEdamam.class).getParsed().isEmpty()) {
            return objectMapper.readValue(json, ProductEdamam.class).getParsed().get(0).getFood().getNutrients();
        } else if (!objectMapper.readValue(json, ProductEdamam.class).getHints().isEmpty()) {
            return objectMapper.readValue(json, ProductEdamam.class).getHints().get(0).getFood().getNutrients();
        } else {
            return null;
        }
    }

    private Map<String, Double> getMeasuresEdamam(String json) throws IOException, InterruptedException {
        Map<String, Double> result = new LinkedHashMap<>();
        List<Measure> measures = objectMapper.readValue(json, ProductEdamam.class).getHints().get(0).getMeasures();
        for (Measure measure : measures) {
            if (measure.getQualified() != null) {
                for (Qualified qualified : measure.getQualified()) {
                    if (qualified.getQualifiers().get(0).getLabel() != null && qualified.getWeight() != null) {
                        String label = measure.getLabel() + " " + qualified.getQualifiers().get(0).getLabel();
                        result.put(label, qualified.getWeight());
                    }
                }
            }
            if (measure.getWeight() != null) {
                result.put(measure.getLabel(), measure.getWeight());
            }
        }
        return result;
    }

    @Override
    public void run(String... args) throws Exception {
//        List<ProductMealDB> products = getProductListMealDB();
//        for (int i = 0; i <= 572; i++) {
//            if (i == 49 || i == 165 || i == 337 || i == 493 || i == 566) { //skip on 'Challots', 'Hotsauce', 'Pilchards', 'Doubanjiang', 'Mulukhiyah'
//                continue;
//            }
//            ProductMealDB productMealDB = products.get(i);
//            String productDetails = getProductEdamam(productMealDB.getStrIngredient().replace(" ", "-"));
//            Optional<Nutrients> nutrients = Optional.ofNullable(getNutrientsEdamam(productDetails));
//            Map<String, Double> measures = getMeasuresEdamam(productDetails);
//
//            for (Map.Entry<String, Double> entry : measures.entrySet()) {
//                Product product = new Product();
//                product.setName(productMealDB.getStrIngredient());
//                product.setWeightUnit(entry.getKey());
//                product.setWeight(entry.getValue());
//                if (nutrients.isPresent()) {
//                    double multiplier = product.getWeight() / 100;
//                    product.setKcal(nutrients.get().getEnercKcal() * multiplier);
//                    product.setProteins(nutrients.get().getProcnt() * multiplier);
//                    product.setFat(nutrients.get().getFat() * multiplier);
//                    product.setCarbs(nutrients.get().getChocdf() * multiplier);
//                    product.setFiber(nutrients.get().getFibtg() * multiplier);
//                }
//                productService.save(product);
//            }
//
//
//        List<String> edamanNameList = List.of("shallot", "sardines", "Hot-sauce");
//        List<String> mealDBNameList = List.of("Challots", "Pilchards", "Hotsauce");
//
//        for (int i = 0; i < edamanNameList.size(); i++) {
//            String productDetails2 = getProductEdamam(edamanNameList.get(i));
//            Optional<Nutrients> nutrients2 = Optional.ofNullable(getNutrientsEdamam(productDetails2));
//            Map<String, Double> measures2 = getMeasuresEdamam(productDetails2);
//
//            for (Map.Entry<String, Double> entry : measures2.entrySet()) {
//                Product product = new Product();
//                product.setName(mealDBNameList.get(i));
//                product.setWeightUnit(entry.getKey());
//                product.setWeight(entry.getValue());
//                if (nutrients2.isPresent()) {
//                    double multiplier = product.getWeight() / 100;
//                    product.setKcal(nutrients2.get().getEnercKcal() * multiplier);
//                    product.setProteins(nutrients2.get().getProcnt() * multiplier);
//                    product.setFat(nutrients2.get().getFat() * multiplier);
//                    product.setCarbs(nutrients2.get().getChocdf() * multiplier);
//                    product.setFiber(nutrients2.get().getFibtg() * multiplier);
//                }
//                productService.save(product);
//            }
//        }
    }


}
