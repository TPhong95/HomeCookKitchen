package org.automation.tasksscheduler.kafka;

import org.automation.tasksscheduler.dto.ChefNotificationCreationDto;
import org.automation.tasksscheduler.dto.CustomerNotificationCreationDto;
import org.automation.tasksscheduler.dto.NotificationDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static org.automation.tasksscheduler.kafka.KafkaTopicConfig.CHEF_NOTIFICATION_TOPIC;
import static org.automation.tasksscheduler.kafka.KafkaTopicConfig.CUSTOMER_NOTIFICATION_TOPIC;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, NotificationDto> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, NotificationDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendChefNotification(ChefNotificationCreationDto dto) {
        kafkaTemplate.send(CHEF_NOTIFICATION_TOPIC, dto.chefId().toString(), dto);
    }

    public void sendCustomerNotification(CustomerNotificationCreationDto dto) {
        kafkaTemplate.send(CUSTOMER_NOTIFICATION_TOPIC, dto.customerId().toString(), dto);
    }
}
