package com.kamilkuk.food_and_recipes.deserialization;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamilkuk.food_and_recipes.deserialization.mealDB.ProductListMealDB;
import com.kamilkuk.food_and_recipes.deserialization.mealDB.ProductMealDB;
import com.kamilkuk.food_and_recipes.deserialization.recipes.Meal;
import com.kamilkuk.food_and_recipes.deserialization.recipes.RecipeMealDB;
import com.kamilkuk.food_and_recipes.model.CusineCategory;
import com.kamilkuk.food_and_recipes.model.CusineRegion;
import com.kamilkuk.food_and_recipes.model.Product;
import com.kamilkuk.food_and_recipes.model.Recipe;
import com.kamilkuk.food_and_recipes.repository.ProductRepository;
import com.kamilkuk.food_and_recipes.service.ProductService;
import com.kamilkuk.food_and_recipes.service.RecipeService;
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
public class RecipeDeserializer implements CommandLineRunner {

    private final RecipeService recipeService;
    private final ProductService productService;

    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Value("${com.kamilkuk.food_and_recipe.the_meal_db.url}")
    private String urlMealDB;

    private HttpResponse<String> getResponse(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    private List<Meal> getRecipeListMealDB(char letter) throws IOException, InterruptedException {
        HttpResponse<String> response = getResponse(urlMealDB + "/search.php?f=" + letter);
        return objectMapper.readValue(response.body(), RecipeMealDB.class).getMeals();
    }
//
//    List<String> parseStrMeasureMealDB(String strMeasure){
//        Map<Character,Double> measureDictionary = Map.of('½',0.5,'¾',0.75,'¼',0.25, '⅓',0.3);
//
//    }

    private Map<Product, Double> getProductByNameAndWeightUnit(String name, String weightUnit) {
        List<Product> products = productService.getByName(name);
        if (!products.isEmpty()) {
            return Map.of(products.get(0), 1.0);
//            List<String> weightUnitData = parseStrMeasureMealDB(weightUnit);
//            for(Product product: products){
//
//            }
        } else {
            return null;
        }
    }


    @Override
    public void run(String... args) throws Exception {
        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            List<Meal> recipes = getRecipeListMealDB(alphabet);
            if (recipes == null) {
                continue;
            }
            for (Meal recipeMealDB : recipes) {
                if (recipeMealDB.getStrMeal().equals("Ma Po Tofu") || recipeMealDB.getStrMeal().equals("Mulukhiyah")) {
                    continue;
                }
                recipeMealDB.getStrInstructions().length();
                Recipe recipe = new Recipe();
                recipe.setTitle(recipeMealDB.getStrMeal());
                recipe.setCategory(CusineCategory.valueOf(recipeMealDB.getStrCategory().toUpperCase()));
                recipe.setRegion(CusineRegion.valueOf(recipeMealDB.getStrArea().toUpperCase()));
                if (recipeMealDB.getStrInstructions().length() > 5000) {
                    throw new IllegalArgumentException("String length " + recipeMealDB.getStrInstructions().length() + " is larger then max value");
                }
                recipe.setInstructions(recipeMealDB.getStrInstructions());
                recipe.setImageURL(recipeMealDB.getStrMealThumb());
                recipe.setYouTubeURL(recipeMealDB.getStrYoutube());

                Map<Product, Double> ingredients = new HashMap<>();
                for (String productName : recipeMealDB.getAllIngredients()) {
                    switch (productName.toLowerCase()) {
                        case "butter, softened":
                            productName = "Butter";
                            break;
                        case "blackberrys":
                            productName = "Blackberries";
                            break;
                        case "green chili":
                            productName = "Green Chilli";
                            break;
                        case "red chili powder":
                            productName = "Red Chilli Powder";
                            break;
                        case "all spice":
                            productName = "Allspice";
                            break;
                        case "gruyere cheese":
                            productName = "Cheddar Cheese";
                            break;
                        case "red chili":
                            productName = "Red Chilli";
                            break;
                        case "self raising flour":
                            productName = "Self-raising Flour";
                            break;
                    }
                    List<Product> products = productService.getByName(productName);
                    if (products != null && !products.isEmpty()) {
                        ingredients.put(products.get(0), 1.0);
                    } else {
                        throw new NoSuchElementException("Couldn't find product named " + productName);
//                    String[] productNameArray = productName.split(" ");
//                    for(String s: productNameArray){
//                        s=s.replaceAll("\\W","");
//                        products = productService.getByName(s);
//                        if(products!=null && !products.isEmpty()) {
//                            ingredients.put(products.get(0), 1.0);
//                        }
//                    }
                    }
                }
                recipe.setIngredients(ingredients);
                RecipeService.setKcal(recipe, ingredients);
                recipeService.save(recipe);
            }
        }
    }
}
