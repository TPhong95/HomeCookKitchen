package org.example.recipeproduct;

import org.springframework.boot.SpringApplication;

public class TestRecipeProductApplication {

    public static void main(String[] args) {
        SpringApplication.from(RecipeProductApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
