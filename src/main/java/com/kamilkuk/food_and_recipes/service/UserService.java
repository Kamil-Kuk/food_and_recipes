package com.kamilkuk.food_and_recipes.service;

import com.kamilkuk.food_and_recipes.model.Recipe;
import com.kamilkuk.food_and_recipes.model.User;
import com.kamilkuk.food_and_recipes.model.UserRole;
import com.kamilkuk.food_and_recipes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found in database!"));
    }

    public User save(UserDetails user){
        return userRepository.save((User) user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User get(Long id){
        return userRepository.findById(id).orElseThrow(()->new NoSuchElementException());
    }

    public void remove(Long id){
        userRepository.delete(get(id));
    }

    public User getUserByName(String username){
        return userRepository.findUserByUsername(username).orElseThrow(()->new NoSuchElementException());
    }

    public void removeFavouriteRecipe(User user, Recipe recipe){
        Set<Recipe> favs = user.getFavourites();
        favs.remove(recipe);
        user.setFavourites(favs);
        userRepository.save(user);
    }

    public void addFavouriteRecipe(User user, Recipe recipe){
        Set<Recipe> favs = user.getFavourites();
        favs.add(recipe);
        user.setFavourites(favs);
        userRepository.save(user);
    }
}
