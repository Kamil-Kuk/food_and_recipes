package com.kamilkuk.food_and_recipes.repository;

import com.kamilkuk.food_and_recipes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
