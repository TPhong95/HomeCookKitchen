package org.automation.initdata.dtos;

public record CustomerNotificationCreationDto(
        Long customerId,
        String message
) {
}
