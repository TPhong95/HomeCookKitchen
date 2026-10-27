package org.school.exam.productlisting.dto;

public record GetRecipeDTO(
        int recipeId,
        int cookId,
        String recipeName,
        boolean active
) {}
