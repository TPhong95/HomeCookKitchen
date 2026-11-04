package org.example.purchase.exception.notfound;

import java.util.Map;

public class ExampleNotFoundException extends NotFoundException {
    public ExampleNotFoundException(String message, Map<String, Object> fields) {
        super(message, fields);
    }
}
