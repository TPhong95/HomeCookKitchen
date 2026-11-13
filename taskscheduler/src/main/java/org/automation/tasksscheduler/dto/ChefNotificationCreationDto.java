package org.automation.tasksscheduler.dto;

public record ChefNotificationCreationDto(
        Long chefId,
        String message
) implements NotificationDto {
}
