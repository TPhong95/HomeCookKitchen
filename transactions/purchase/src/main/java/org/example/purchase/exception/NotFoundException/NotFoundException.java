package org.example.purchase.exception.NotFoundException;

import lombok.Getter;

import java.util.Map;

@Getter
public class NotFoundException extends RuntimeException {
    private final Map<String, Object> fields;

    public NotFoundException(String message, Map<String, Object> fields) {
        super(message);
        this.fields = Map.copyOf(fields);
    }
}
