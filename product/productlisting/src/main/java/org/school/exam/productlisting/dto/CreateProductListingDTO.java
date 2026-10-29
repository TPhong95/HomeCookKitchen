package org.school.exam.productlisting.dto;

import java.time.LocalDateTime;

public record CreateProductListingDTO(
        Long recipeId,
        Long chefId,
        boolean active,
        int quantity,
        float unitPrice,
        LocalDateTime purchaseDeadline,
        LocalDateTime readyBy
) {
}
