package user.customer.dto;

public record CustomerNotificationCreationDto(
        Long customerId,
        String message
) {
}
