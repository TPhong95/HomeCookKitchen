package org.example.purchase.kafka;

public record ChefNotificationEvent(
        Long chefId,
        String message
) {
}
