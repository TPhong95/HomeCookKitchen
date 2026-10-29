package org.school.exam.productlisting.dto;

public record GetRecipeDTO(
        Long recipeId,
        Long cookId,
        String recipeName,
        boolean active
) {}
