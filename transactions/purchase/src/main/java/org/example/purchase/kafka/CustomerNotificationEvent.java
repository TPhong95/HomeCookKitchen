package org.example.purchase.kafka;

public record CustomerNotificationEvent(
        Long customerId,
        String message
) {
}
