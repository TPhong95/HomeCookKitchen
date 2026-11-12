package org.school.exam.recipe.exception.NotFoundException;

import java.util.Map;

public class RecipeNotFoundException extends NotFoundException {
    public RecipeNotFoundException(String message, Map<String, Object> fields) {
        super(message, fields);
    }
}