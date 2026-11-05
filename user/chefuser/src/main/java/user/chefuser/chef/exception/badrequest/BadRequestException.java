package user.chefuser.chef.exception.badrequest;

import lombok.Getter;
import java.util.Map;

@Getter
public class BadRequestException extends RuntimeException {
    private final Map<String, Object> fields;

    public BadRequestException(String message, Map<String, Object> fields) {
        super(message);
        this.fields = Map.copyOf(fields);
    }
}
