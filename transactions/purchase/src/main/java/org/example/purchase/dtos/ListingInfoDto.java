package org.example.purchase.dtos;

import jakarta.persistence.Embeddable;

@Embeddable
public record ListingInfoDto(
        Long cookId,
        String chefName,
        Long recipeListingId,
        String recipeName,
        float unitPrice
) {
}

