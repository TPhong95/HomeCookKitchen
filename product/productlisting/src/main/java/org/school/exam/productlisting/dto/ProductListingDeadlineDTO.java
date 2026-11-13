package org.school.exam.productlisting.dto;

import java.time.LocalDateTime;

public record ProductListingDeadlineDTO(
        Long id,
        Boolean active,
        LocalDateTime purchaseDeadline,
        String recipeName,
        String compositeAddress,
        Long chefId,
        String chefName,
        LocalDateTime readyBy,
        Long quantityOffered,
        Long quantityPurchased
) {
}
