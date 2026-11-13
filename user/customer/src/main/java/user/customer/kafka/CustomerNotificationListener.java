package user.customer.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import user.customer.CustomerOrchestrationService;
import user.customer.dto.CustomerNotificationCreationDto;

@Slf4j
@Component
public class CustomerNotificationListener {
    private final CustomerOrchestrationService customerOrchestrationService;

    public CustomerNotificationListener(CustomerOrchestrationService customerOrchestrationService) {
        this.customerOrchestrationService = customerOrchestrationService;
    }

    @KafkaListener(topics = "customer-notification")
    public void onCustomerNotification(CustomerNotificationCreationDto dto) {
        log.info("Received customer notification for customerId {}", dto.customerId());
        customerOrchestrationService.createCustomerNotification(dto);
    }
}
