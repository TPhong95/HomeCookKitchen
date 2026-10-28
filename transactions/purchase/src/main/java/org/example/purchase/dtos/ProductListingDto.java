package org.example.purchase.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductListingDto(
        Long productListingId,
        int recipeId,
        Long chefId,
        float unitPrice
) {}
