package user.customer.dto;

import java.time.LocalDateTime;

public record CustomerNotificationResponseDto(
        Long id,
        Long customerId,
        String message,
        LocalDateTime time,
        Boolean isRead
) {
}
