package com.kamilkuk.food_and_recipes.mvc;
import com.kamilkuk.food_and_recipes.model.User;
import com.kamilkuk.food_and_recipes.model.UserRole;
import com.kamilkuk.food_and_recipes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

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

}
