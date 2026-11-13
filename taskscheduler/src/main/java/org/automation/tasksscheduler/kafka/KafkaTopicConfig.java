package org.automation.tasksscheduler.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    public static final String CUSTOMER_NOTIFICATION_TOPIC = "customer-notification";
    public static final String CHEF_NOTIFICATION_TOPIC = "chef-notification";

    @Bean
    public NewTopic customerNotificationTopic() {
        return TopicBuilder.name(CUSTOMER_NOTIFICATION_TOPIC).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic chefNotificationTopic() {
        return TopicBuilder.name(CHEF_NOTIFICATION_TOPIC).partitions(1).replicas(1).build();
    }
}
