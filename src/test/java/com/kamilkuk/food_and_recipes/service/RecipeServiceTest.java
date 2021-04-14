package com.kamilkuk.food_and_recipes.service;

import com.kamilkuk.food_and_recipes.model.Recipe;
import com.kamilkuk.food_and_recipes.repository.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository repositoryMock;

    private static Recipe recipe1;
    private static Recipe recipe2;
    private static Recipe recipe3;

    @BeforeAll
    static void setUp(){
        recipe1 = new Recipe();
        recipe1.setId(1001L);
        recipe2 = new Recipe();
        recipe2.setId(1002L);
        recipe3 = new Recipe();
        recipe3.setId(1003L);
    }

    @Test
    void should_save_recipe(){
//        //given
//        RecipeService service = new RecipeService(repositoryMock);
//
//        //when
//        service.save(recipe1);
//
//        //then
//        Mockito.verify(repositoryMock).save(recipe1);
    }

    @Test
    void should_return_true_on_successful_delete(){
        //given
        RecipeService service = new RecipeService(repositoryMock);
        Mockito.when(repositoryMock.findById(1001L)).thenReturn(Optional.of(recipe1));

        //when
        boolean result = service.remove(1001L);

        //then
        Assertions.assertTrue(result);
    }

}