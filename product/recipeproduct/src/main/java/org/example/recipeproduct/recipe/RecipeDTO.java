package org.example.recipeproduct.recipe;

public record RecipeDTO(
        Long cookId,
        String name,
        boolean active
) {}
