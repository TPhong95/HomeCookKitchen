package org.school.exam.productlisting.exception.ConflictException;

import lombok.Getter;

import java.util.Map;

@Getter
public class ConflictException extends RuntimeException {

    private final Map<String, Object> fields;

    public ConflictException(String message, Map<String, Object> fields) {
        super(message);
        this.fields = Map.copyOf(fields);
    }

}
