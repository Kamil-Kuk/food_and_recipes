package com.kamilkuk.food_and_recipes.mvc;
import com.kamilkuk.food_and_recipes.model.*;
import com.kamilkuk.food_and_recipes.service.RecipeService;
import com.kamilkuk.food_and_recipes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RecipeService recipeService;

    @GetMapping("/")
    public String showIndex(){
        return "index";
    }

    @GetMapping("/login")
    public String showLogin(HttpServletRequest request, HttpSession session){
        session.setAttribute("error",getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(){
        return "register";
    }

    @PostMapping(value = "/register",
                consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                produces = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    private String addUser(@RequestParam Map<String,String> body){
        User user = new User();
        user.setUsername(body.get("username"));
        user.setPassword(passwordEncoder.encode(body.get("password")));
        user.setRole(UserRole.ROLE_USER);
        userService.save(user);
        return "redirect:/login";
    }

    private String getErrorMessage(HttpServletRequest request, String key){
        Exception exception = (Exception) request.getSession().getAttribute(key);
        String error = "";
        if(exception instanceof BadCredentialsException){
            error = "Invalid username and password";
        } else if(exception instanceof LockedException){
            error = exception.getMessage();
        } else {
            error = "Invalid username and password";
        }
        return error;
    }

    @GetMapping("/account")
    public String showAccount(Principal principal, Model model,
                              @RequestParam(value = "recipe",required = false) Long recipeId) {
//        model.addAttribute("user",auth);
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user",user);
        if(recipeId!=null) {
            Recipe recipe = recipeService.get(recipeId);
            if (user.getFavourites().contains(recipe)) {
                userService.removeFavouriteRecipe(user,recipe);
            }
        }
        return "account";
    }



    @GetMapping("/categories")
    public String showCategories(Model model) {
        model.addAttribute("categories",CusineCategory.values());
        return "categories";
    }

    @GetMapping("/regions")
    public String showRegions(Model model) {
        model.addAttribute("regions",CusineRegion.values());
        return "regions";
    }

    @GetMapping("/search")
    public String showSearchResultsForCategoryOrRegion(Principal principal, Model model,
                                    @RequestParam(value = "query",required = false) String query,
                                    @RequestParam(value = "category",required = false) String category,
                                    @RequestParam(value = "region", required = false) String region,
                                    @RequestParam(value = "recipe", required = false) Long recipeId) {
        if(recipeId!=null) {
            User user = userService.getUserByName(principal.getName());
            Recipe recipe = recipeService.get(recipeId);
            userService.addFavouriteRecipe(user, recipe);
            return "redirect:/search";
        }else {
            Set<Recipe> resultRecipes = new HashSet<>();
            if (query != null) {
                if (query.equalsIgnoreCase("all")) {
                    resultRecipes.addAll(recipeService.getAll());
                } else {
                    resultRecipes.addAll(recipeService.getByKeyword(query));
                }
                model.addAttribute("query", query.toLowerCase());
            }
            if (category != null) {
                if (category.equalsIgnoreCase("dinner")) {
                    resultRecipes.addAll(recipeService.getByCategory("beef"));
                    resultRecipes.addAll(recipeService.getByCategory("chicken"));
                    resultRecipes.addAll(recipeService.getByCategory("goat"));
                    resultRecipes.addAll(recipeService.getByCategory("lamb"));
                    resultRecipes.addAll(recipeService.getByCategory("pasta"));
                    resultRecipes.addAll(recipeService.getByCategory("pork"));
                    resultRecipes.addAll(recipeService.getByCategory("seafood"));
                    resultRecipes.addAll(recipeService.getByCategory("vegan"));
                    resultRecipes.addAll(recipeService.getByCategory("vegetarian"));
                } else if (category.equalsIgnoreCase("side")) {
                    resultRecipes.addAll(recipeService.getByCategory(category));
                    resultRecipes.addAll(recipeService.getByCategory("starter"));
                } else if (category.equalsIgnoreCase("veganvegetarian")) {
                    resultRecipes.addAll(recipeService.getByCategory("vegan"));
                    resultRecipes.addAll(recipeService.getByCategory("vegetarian"));
                    category = "vegan vegetarian";
                } else {
                    resultRecipes.addAll(recipeService.getByCategory(category));
                }
                model.addAttribute("category", category.toLowerCase());
            }
            if (region != null) {
                resultRecipes.addAll(recipeService.getByRegion(region));
                model.addAttribute("region", region.toLowerCase());
            }
            model.addAttribute("recipes", resultRecipes);
            return "search";
        }
    }

    @PostMapping("/account/addrecipe")
    public String addFavourite(Recipe recipe,
                          Principal principal) {
        User user = userService.getUserByName(principal.getName());
        userService.addFavouriteRecipe(user, recipe);
        return "redirect:/search";
    }

    @GetMapping("/recipe/{id}")
    public String showRecipePage(Model model, @PathVariable Long id) {
        Recipe recipe = recipeService.get(id);
        Map<Product, String> ingredients = new HashMap<>();
        DecimalFormat df = new DecimalFormat("###.#");
        for(Map.Entry<Product,Double> entry: recipe.getIngredients().entrySet()){
            String quantity = df.format(entry.getValue());
            ingredients.put(entry.getKey(),quantity);
        }
        model.addAttribute("recipe", recipe);
        model.addAttribute("weight", String.format("%.0f",recipeService.getTotalWeight(recipe)));
        model.addAttribute("kcal", String.format("%.0f",recipe.getKcal()));
        model.addAttribute("protein", String.format("%.0f",recipe.getProteins()));
        model.addAttribute("fat", String.format("%.0f",recipe.getFat()));
        model.addAttribute("carbs", String.format("%.0f",recipe.getCarbs()));
        model.addAttribute("fiber", String.format("%.0f",recipe.getFiber()));
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("instructions", convertInstructionsString(recipe.getInstructions()));
        model.addAttribute("youTubeURL", parseYouTubeURL(recipe.getYouTubeURL()));
        return "recipe";
    }

    private String[] convertInstructionsString(String s){
        return s.split("\\. ");
    }

    private String parseYouTubeURL(String s){
        String result = "";
        result = s.replace("watch?v=","embed/");
        return result;
    }

}
