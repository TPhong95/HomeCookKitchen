package org.example.purchase.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductListingDto(
        Long productListingId,
        @JsonProperty("recipeId") RecipeDto recipe,
        float unitPrice
) {
    public Long recipeId() {
        return recipe.recipeId();
    }
}
