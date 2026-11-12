package user.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.customer.dto.CustomerNotificationCreationDto;
import user.customer.exception.BadRequestException.BadRequestException;
import user.customer.exception.InternalServerError.InternalServerError;

import java.util.List;
import java.util.Map;

@Service
public class CustomerOrchestrationService {
    private final CustomerService customerService;
    private final CustomerNotificationService customerNotificationService;

    public CustomerOrchestrationService(CustomerService customerService, CustomerNotificationService customerNotificationService) {
        this.customerService = customerService;
        this.customerNotificationService = customerNotificationService;
    }

    // Customer Notification //

    @Transactional
    public CustomerNotification createCustomerNotification(CustomerNotificationCreationDto inputDto) {
        if (inputDto.customerId() == null) {
            throw new BadRequestException("You need a customer id ", Map.of());
        }
        customerService.findCustomerById(inputDto.customerId());
        CustomerNotification newNotification = new CustomerNotification(
                inputDto.customerId(),
                inputDto.message()
        );
        CustomerNotification savedNotification = customerNotificationService.createNotification(newNotification);
        validateCustomerNotification(savedNotification);
        return savedNotification;
    }

    public List<CustomerNotification> getAllNotificationsByCustomerId(Long customerId) {
        if (customerId == null || customerId == 0L) {throw new BadRequestException("You need a customer id", Map.of());}
        customerService.findCustomerById(customerId);
        List<CustomerNotification> result = customerNotificationService.getAllNotificationsByCustomerId(customerId);
        result.forEach(this::validateCustomerNotification);
        return result;
    }

    public CustomerNotification getNotificationById(Long notificationId) {
        if (notificationId == null || notificationId == 0L) {throw new BadRequestException("You need a notification id", Map.of());}
        CustomerNotification result = customerNotificationService.getNotificationById(notificationId);
        validateCustomerNotification(result);
        return result;
    }

    @Transactional
    public CustomerNotification updateNotificationReadStatus(Long id) {
        CustomerNotification notification = getNotificationById(id);
        if (!notification.getIsRead()){
            notification.setIsRead(true);
        } else {
            notification.setIsRead(false);
        }
        CustomerNotification updatedNotification = customerNotificationService.updateNotification(notification);
        return updatedNotification;
    }

    private void validateCustomerNotification(CustomerNotification notification) {
        if (notification.getId() == null || notification.getId() == 0L) {
            throw new InternalServerError("Notification with customer id: " + notification.getCustomerId() + " has no id", Map.of());
        }
        if (notification.getMessage() == null || notification.getMessage().isEmpty()) {
            throw new InternalServerError("Notification with id: " + notification.getId() + " has no message", Map.of());
        }
        if (notification.getCustomerId() == null || notification.getCustomerId() == 0L) {
            throw new InternalServerError("Notification with id: " + notification.getId() + " has no customer id", Map.of());
        }
    }
}
