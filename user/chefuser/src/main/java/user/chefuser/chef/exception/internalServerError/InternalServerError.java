package user.chefuser.chef.exception.internalServerError;

import lombok.Getter;

import java.util.Map;

@Getter
public class InternalServerError extends RuntimeException {
    private final Map<String, Object> fields;

    public InternalServerError(String message, Map<String, Object> fields) {
        super(message);
        this.fields = fields;
    }
}
