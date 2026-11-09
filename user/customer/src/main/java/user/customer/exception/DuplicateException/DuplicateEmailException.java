package user.customer.exception.DuplicateException;

import lombok.Getter;

import java.util.Map;

@Getter
public class DuplicateEmailException extends RuntimeException {

    private final Map<String, Object> fields;

    public DuplicateEmailException(String message, Map<String, Object> fields) {
        super(message);
        this.fields = Map.copyOf(fields);
    }
}
