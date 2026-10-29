package org.automation.tasksscheduler.dto;

import java.time.LocalDateTime;

public record ListingDto(
        Long id,
        Boolean active,
        LocalDateTime purchaseDeadline
) {
}
