package user.customer;

import org.springframework.stereotype.Service;
import user.customer.exception.NotFoundException.NotFoundException;

import java.util.List;
import java.util.Map;

@Service
public class CustomerNotificationService {
    private final CustomerNotificationRepo customerNotificationRepo;

    public CustomerNotificationService(CustomerNotificationRepo customerNotificationRepo) {
        this.customerNotificationRepo = customerNotificationRepo;
    }

    public CustomerNotification createNotification(CustomerNotification customerNotification) {
        return customerNotificationRepo.save(customerNotification);
    }

    public CustomerNotification getNotificationById(Long notificationId) {
        return customerNotificationRepo.findById(notificationId)
                .orElseThrow(() -> new NotFoundException("Notification with id " + notificationId + " not found", Map.of()));
    }

    public List<CustomerNotification> getAllNotificationsByCustomerId(Long customerId) {
        return customerNotificationRepo.getCustomerNotificationByCustomerId(customerId);
    }

    public CustomerNotification updateNotification(CustomerNotification notification) {
        return customerNotificationRepo.save(notification);
    }
}
