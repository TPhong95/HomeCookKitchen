package org.automation.tasksscheduler.dto;

import java.time.LocalDateTime;

public record GetListingDto(
        Long id,
        Boolean activeOrder,
        LocalDateTime deadline
) {
}
