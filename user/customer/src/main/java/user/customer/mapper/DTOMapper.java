package user.customer.mapper;

import org.springframework.stereotype.Component;
import user.customer.CustomerNotification;
import user.customer.dto.CustomerNotificationResponseDto;

@Component
public class DTOMapper {

    // Customer Notification Response DTO //
    public CustomerNotificationResponseDto toCustomerNotificationResponseDto(CustomerNotification notification) {
        return new CustomerNotificationResponseDto(
                notification.getId(),
                notification.getCustomerId(),
                notification.getMessage(),
                notification.getTime(),
                notification.getIsRead()
        );
    }
}
