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
import lombok.NoArgsConstructor;
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
import java.util.regex.Pattern;

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

    public static List<String> getWeightUnit(String strMeasure) {
        String[] strArray = strMeasure.split("[^a-zA-Z]");
        List<String> result = new ArrayList<>();
        for (String str : strArray) {
            switch (str.toLowerCase()) {
                case "g":
                    str = "Gram";
                    break;
                case "kg":
                    str = "Kilogram";
                    break;
                case "tbs":
                case "tbsp":
                case "tblsp":
                    str = "Tablespoon";
                    break;
                case "tsp":
                    str = "Teaspoon";
                    break;
                case "ml":
                    str = "Mililiter";
                    break;
                case "l":
                    str = "Liter";
                    break;
                case "oz":
                    str = "Ounce";
                    break;
                case "fl":
                case "fl oz":
                    str = "Fluid ounce";
                    break;
                case "florets":
                    str = "Branch";
                    break;
            }
            if (!str.isBlank()) {
                if (str.endsWith("s")) {
                    str = str.substring(0, str.length() - 1);
                }
                result.add(str.trim());
            }
        }
        return result;
    }

    public static List<Double> getQuantity(String strMeasure) {
        Map<Character, Double> measureDictionary = Map.of('½', 0.5, '¾', 0.75, '¼', 0.25, '⅓', 0.3);
        for (Map.Entry<Character, Double> entry : measureDictionary.entrySet()) {
            strMeasure = strMeasure.replaceAll(entry.getKey().toString(), " " + entry.getValue().toString() + " ");
        }


        String[] strArray = strMeasure.split("[a-wyzA-Z,]");
        List<Double> result = new ArrayList<>();
        for (String str : strArray) {

            switch (str) {
                case "1 – 14-":
                    str = "14";
                    break;
            }

            if (Pattern.compile("\\d").matcher(str).find()) {


                if (Pattern.compile("\\d/\\d").matcher(str).find()) {
                    String[] subArray = str.split("/");
                    Double dividedStr = Double.parseDouble(subArray[0].replaceAll("[^0-9]", "")) / Double.parseDouble(subArray[1].replaceAll("[^0-9]", ""));
                    str = dividedStr.toString();
                }
                str = str.replaceAll("/", "");

                if (Pattern.compile("\\d ?\\(\\d").matcher(str).find()) {
                    String[] subArray = str.split("\\(");
                    Double multipliedStr = Double.parseDouble(subArray[0]) * Double.parseDouble(subArray[1]);
                    str = multipliedStr.toString();
                }
                str = str.replace("(", "");

                if (Pattern.compile("\\d ?x ?\\d").matcher(str).find()) {
                    String[] subArray = str.split(" ?x ?");
                    Double multipliedStr = Double.parseDouble(subArray[0]) * Double.parseDouble(subArray[1]);
                    str = multipliedStr.toString();
                }

                if (Pattern.compile("\\d ?- ?\\d").matcher(str).find()) {
                    String[] subArray = str.split(" ?- ?");
                    Double averageStr = (Double.parseDouble(subArray[0]) + Double.parseDouble(subArray[1])) / 2;
                    str = averageStr.toString();//if range is given as quantity, the average is taken in account
                }
                str = str.replace("-", "");

                if (Pattern.compile("\\d ? \\d").matcher(str).find()) {
                    String[] subArray = str.split(" ? ");
                    Double summedStr = Double.parseDouble(subArray[0]) + Double.parseDouble(subArray[1]);
                    str = summedStr.toString();
                }

                Double parsedDouble = Double.parseDouble(str);
                result.add(parsedDouble);
            }
        }
        if (result.isEmpty()) {
            result.add(1.0);
        }
        return result;
    }


    @Override
    public void run(String... args) throws Exception {
//        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
//            List<Meal> recipes = getRecipeListMealDB(alphabet);
//            if (recipes == null) {
//                continue;
//            }
//
//            for (Meal recipeMealDB : recipes) {
//                if (recipeMealDB.getStrMeal().equals("Ma Po Tofu") || recipeMealDB.getStrMeal().equals("Mulukhiyah")) { //skip recipes with unidentified ingredients
//                    continue;
//                }
//
//                Recipe recipe = new Recipe();
//                recipe.setTitle(recipeMealDB.getStrMeal());
//                recipe.setCategory(CusineCategory.valueOf(recipeMealDB.getStrCategory().toUpperCase()));
//                recipe.setRegion(CusineRegion.valueOf(recipeMealDB.getStrArea().toUpperCase()));
//
//                if (recipeMealDB.getStrInstructions().length() > 5000) {
//                    throw new IllegalArgumentException("String length " + recipeMealDB.getStrInstructions().length() + " is larger then max value");
//                }
//
//                recipe.setInstructions(recipeMealDB.getStrInstructions());
//                recipe.setImageURL(recipeMealDB.getStrMealThumb());
//                recipe.setYouTubeURL(recipeMealDB.getStrYoutube());
//
//                Map<Product, Double> ingredients = new HashMap<>();
//                List<String> productNames = recipeMealDB.getAllIngredients();
//                List<String> productMeasures = recipeMealDB.getAllMeasures();
//                int limit = Math.min(productNames.size(), productMeasures.size());
//
//                for (int i = 0; i < limit; i++) {
//                    String productName = productNames.get(i);
//                    String productMeasure = productMeasures.get(i);
//                    List<Double> quantities = getQuantity(productMeasure);
//                    List<String> weightUnits = getWeightUnit(productMeasure);
//
//                    switch (productName.toLowerCase()) { // rename misspelled ingredients
//                        case "butter, softened":
//                            productName = "Butter";
//                            break;
//                        case "blackberrys":
//                            productName = "Blackberries";
//                            break;
//                        case "green chili":
//                            productName = "Green Chilli";
//                            break;
//                        case "red chili powder":
//                            productName = "Red Chilli Powder";
//                            break;
//                        case "all spice":
//                            productName = "Allspice";
//                            break;
//                        case "gruyere cheese":
//                            productName = "Cheddar Cheese";
//                            break;
//                        case "red chili":
//                            productName = "Red Chilli";
//                            break;
//                        case "self raising flour":
//                            productName = "Self-raising Flour";
//                            break;
//                    }
//
//                    List<Product> products = new ArrayList<>();
//                    if (weightUnits.isEmpty()) {
//                        weightUnits.add("whole");
//                    }
//
//                    boolean firstUnitFound = false;
//                    if (products.addAll(productService.getByNameAndWeightUnit(productName, weightUnits.get(0)))) {
//                        firstUnitFound = true;
//                    }
//                    for (String weightUnit : weightUnits) {
//                        products.addAll(productService.getByNameAndWeightUnit(productName, weightUnit));
//                    }
//
//                    if (products.isEmpty()) {
//                        weightUnits.add("dash");
//                        weightUnits.add("serving");
//                        for (String weightUnit : weightUnits) {
//                            products.addAll(productService.getByNameAndWeightUnit(productName, weightUnit));
//                        }
//                    }
//
//                    if (products.isEmpty()) {
//                        products = productService.getByName(productName);//TODO - handle unrecognized weightUnits
//                    }
//
//                    int quantityIndex = 0;
//                    if (quantities.size() > 1 && !firstUnitFound) {
//                        quantityIndex = 1;
//                    }
//
//                    if (ingredients.containsKey(products.get(0))) {
//                        Double oldVal = ingredients.get(products.get(0));
//                        ingredients.put(products.get(0), oldVal + quantities.get(quantityIndex));
//                    } else {
//                        ingredients.put(products.get(0), quantities.get(quantityIndex));
//                    }
//                }
//                recipe.setIngredients(ingredients);
//                recipe.setNutrients();
//                recipeService.save(recipe);
//            }
//        }
    }
}
