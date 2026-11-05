package user.customer.exception.NotFoundException;

import java.util.Map;

public class CustomerNotFoundException extends NotFoundException {
    public CustomerNotFoundException(String message, Map<String, Object> fields) {
        super(message, fields);
    }
}
