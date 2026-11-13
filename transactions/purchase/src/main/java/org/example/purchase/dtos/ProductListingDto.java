package org.example.purchase.dtos;


import java.time.LocalDateTime;

public record ProductListingDto(
        Long productListingId,
        Long recipeId,
        Long chefId,
        Long addressId,
        Long quantityOffered,
        float unitPrice,
        LocalDateTime purchaseDeadline,
        LocalDateTime readyBy
) {}
