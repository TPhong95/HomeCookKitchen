package org.school.exam.recipe.dto;

import org.school.exam.recipe.FoodCategory;

public record CreateRecipeDTO(
        Long cookId,
        String name,
        boolean active,
        Long pictureId,
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
