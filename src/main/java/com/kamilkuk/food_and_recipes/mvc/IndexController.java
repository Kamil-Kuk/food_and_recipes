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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

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
    private void addUser(@RequestParam Map<String,String> body){
        User user = new User();
        user.setUsername(body.get("username"));
//        user.setPassword(passwordEncoder.encode(body.get("password")));
        user.setPassword(body.get("password"));
        user.setRole(UserRole.ROLE_USER);
        userService.save(user);
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

    @GetMapping("/categories")
    public String showCategories() {
        return "categories";
    }

    @GetMapping("/search")
    public String showSearchResults(Model model) {
        model.addAttribute("recipes", recipeService.getByCategory(String.valueOf(CusineCategory.BEEF)));
        return "search";
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
