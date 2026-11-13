package org.automation.tasksscheduler.dto;

import java.time.LocalDateTime;

public record ListingDto(
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
