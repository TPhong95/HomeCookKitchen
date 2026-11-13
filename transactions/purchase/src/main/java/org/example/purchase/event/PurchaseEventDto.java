package org.example.purchase.event;

public record PurchaseEventDto(
        Long productListingId,
        Long quantity

) {
}
