package com.kamilkuk.food_and_recipes.controller;

import com.kamilkuk.food_and_recipes.model.Product;
import com.kamilkuk.food_and_recipes.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id){
        return ResponseEntity.ok(productService.get(id));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product){
        return ResponseEntity.ok(productService.save(product));
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping
    public ResponseEntity<Product> update(@RequestBody Product product){
        return ResponseEntity.ok(productService.update(product));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.ok(productService.remove(id));
    }

}
