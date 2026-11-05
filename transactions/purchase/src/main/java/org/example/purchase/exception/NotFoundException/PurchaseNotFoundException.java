package org.example.purchase.exception.NotFoundException;

import java.util.Map;

public class PurchaseNotFoundException extends NotFoundException {
    public PurchaseNotFoundException(String message, Map<String, Object> fields) {
        super(message, fields);
    }
}
