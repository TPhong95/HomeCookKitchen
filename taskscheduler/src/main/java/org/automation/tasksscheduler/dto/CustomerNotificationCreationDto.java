package org.automation.tasksscheduler.dto;

public record CustomerNotificationCreationDto(
        Long customerId,
        String message
) implements NotificationDto {
}
