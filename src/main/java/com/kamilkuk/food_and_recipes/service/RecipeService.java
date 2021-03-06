package com.kamilkuk.food_and_recipes.service;

import com.kamilkuk.food_and_recipes.mapper.RecipeDto;
import com.kamilkuk.food_and_recipes.mapper.RecipeMapper;
import com.kamilkuk.food_and_recipes.model.CusineCategory;
import com.kamilkuk.food_and_recipes.model.CusineRegion;
import com.kamilkuk.food_and_recipes.model.Product;
import com.kamilkuk.food_and_recipes.model.Recipe;
import com.kamilkuk.food_and_recipes.repository.ProductRepository;
import com.kamilkuk.food_and_recipes.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;
    private RecipeMapper mapper = new RecipeMapper();

    public Recipe get(Long id){
        return recipeRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException());
    }

    public Recipe save(RecipeDto dto){
        Recipe recipe = mapper.mapDtoToRecipe(dto, productRepository);
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getAll(){
        return recipeRepository.findAll();
    }

    public Recipe update(RecipeDto dto){
        Recipe recipe = mapper.mapDtoToRecipe(dto,productRepository);
        return recipeRepository.save(recipe);
    }

    public boolean remove(Long id){
        recipeRepository.delete(get(id));
        try{
            get(id);
            return false;
        }catch (NoSuchElementException e){
            return true;
        }
    }

    private boolean categoryEnumCheck(String s){
        for(CusineCategory category: CusineCategory.values()){
            if(category.name().equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }

    private boolean regionEnumCheck(String s){
        for(CusineRegion region: CusineRegion.values()){
            if(region.name().equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }

    public Set<Recipe> getByKeyword(String keyWord) {
        Set<Recipe> recipes = new HashSet<>();
        recipes.addAll(recipeRepository.findByTitleContaining(keyWord));
        if(categoryEnumCheck(keyWord)){
            recipes.addAll(recipeRepository.findByCategory(CusineCategory.valueOf(keyWord.toUpperCase())));
        }
        if(regionEnumCheck(keyWord)){
            recipes.addAll(recipeRepository.findByRegion(CusineRegion.valueOf(keyWord.toUpperCase())));
        }
        return recipes;
    }

    public List<Recipe> getByTitle(String title) {
        return recipeRepository.findByTitleContaining(title);
    }

    public List<Recipe> getByCategory(String category) {
        return recipeRepository.findByCategory(CusineCategory.valueOf(category.toUpperCase()));
    }

    public List<Recipe> getByRegion(String region) {
        return recipeRepository.findByRegion(CusineRegion.valueOf(region.toUpperCase()));
    }

    public List<Recipe> getByKcalBetween(Double kcalMin, Double kcalMax) {
        return recipeRepository.findByKcalBetween(kcalMin,kcalMax);
    }

    public List<Recipe> getByKcalLessThan(Double kcalMax) {
        return recipeRepository.findByKcalLessThanEqual(kcalMax);
    }

    public List<Recipe> getByKcalGreaterThan(Double kcalMin) {
        return recipeRepository.findByKcalGreaterThanEqual(kcalMin);
    }

    public List<String> getShoppingList(Long recipeId){
        Recipe recipe = get(recipeId);
        List<String> shoppingList = new ArrayList<>();
        for(Map.Entry<Product,Double> entry: recipe.getIngredients().entrySet()){
            shoppingList.add(entry.getKey().getName() + ": " + entry.getValue() + " " + entry.getKey().getWeightUnit() + "(s)");
        }
        return shoppingList;
    }

    public Double getTotalWeight(Recipe recipe){
        double weight = 0.0;
        for(Map.Entry<Product,Double> entry: recipe.getIngredients().entrySet()){
            weight += entry.getKey().getWeight() * entry.getValue();
        }
        return weight;
    }
}
