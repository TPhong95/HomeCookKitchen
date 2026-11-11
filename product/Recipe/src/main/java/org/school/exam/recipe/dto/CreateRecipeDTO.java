package org.school.exam.recipe.dto;

import org.school.exam.recipe.FoodCategory;

public record CreateRecipeDTO(
        Long cookId,
        String name,
        boolean invalidated,
        boolean active,
        String pictureUrl,
        String description,
        Long instanceOf,
        FoodCategory foodCategory,
        boolean gluten,
        boolean crustaceans,
        boolean egg,
        boolean fish,
        boolean peanuts,
        boolean soybeans,
        boolean milk,
        boolean nuts,
        boolean celery,
        boolean mustard,
        boolean sesame,
        boolean sulfites,
        boolean lupin,
        boolean molluscs
) {}
