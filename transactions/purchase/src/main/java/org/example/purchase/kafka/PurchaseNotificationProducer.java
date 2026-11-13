package org.example.purchase.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static org.example.purchase.kafka.KafkaTopicConfig.CHEF_NOTIFICATION_TOPIC;
import static org.example.purchase.kafka.KafkaTopicConfig.CUSTOMER_NOTIFICATION_TOPIC;

@Service
public class PurchaseNotificationProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PurchaseNotificationProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void notifyCustomer(Long customerId, String message) {
        kafkaTemplate.send(CUSTOMER_NOTIFICATION_TOPIC, String.valueOf(customerId),
                            new CustomerNotificationEvent(customerId, message));
    }

    public void notifyChef(Long chefId, String message) {
        kafkaTemplate.send(CHEF_NOTIFICATION_TOPIC, String.valueOf(chefId),
                            new ChefNotificationEvent(chefId, message));
    }
}
