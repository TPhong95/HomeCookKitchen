package org.school.exam.recipe.dto;

import org.school.exam.recipe.FoodCategory;

public record CreateRecipeDTO(
        int cookId,
        String name,
        boolean active,
        int pictureId,
        int instanceOf,
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
