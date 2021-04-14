package com.kamilkuk.food_and_recipes.controller;

import com.kamilkuk.food_and_recipes.model.Product;
import com.kamilkuk.food_and_recipes.mapper.ProductDto;
import com.kamilkuk.food_and_recipes.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
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

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<Product> save(@RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.save(productDto));
    }

    @Secured({"ROLE_ADMIN"})
    @PatchMapping
    public ResponseEntity<Product> update(@RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.update(productDto));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return ResponseEntity.ok(productService.remove(id));
    }

}
