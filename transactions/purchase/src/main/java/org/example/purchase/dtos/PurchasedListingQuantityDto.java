package org.example.purchase.dtos;

public record PurchasedListingQuantityDto(
        Long productListingId,
        Long quantity
) {
}