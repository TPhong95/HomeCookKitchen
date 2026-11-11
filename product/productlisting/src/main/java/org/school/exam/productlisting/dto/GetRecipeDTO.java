package org.school.exam.productlisting.dto;

public record GetRecipeDTO(
        Long recipeId,
        Long cookId,
        String recipeName,
        String pictureUrl,
        String description,
        boolean active
) {}
