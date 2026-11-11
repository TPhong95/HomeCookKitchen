package org.example.purchase.dtos;


import java.time.LocalDateTime;

public record ProductListingDto(
        Long productListingId,
        int recipeId,
        Long chefId,
        Long addressId,
        int quantityOffered,
        float unitPrice,
        LocalDateTime purchaseDeadline,
        LocalDateTime readyBy
) {}
