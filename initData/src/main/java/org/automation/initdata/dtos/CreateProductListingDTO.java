package org.automation.initdata.dtos;

import java.time.LocalDateTime;

public record CreateProductListingDTO(
        Long recipeId,
        Long chefId,
        Long addressId,
        int quantityOffered,
        float unitPrice,
        LocalDateTime purchaseDeadline,
        LocalDateTime readyBy
) {
}
