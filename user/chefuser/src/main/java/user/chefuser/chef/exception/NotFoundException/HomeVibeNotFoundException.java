package user.chefuser.chef.exception.NotFoundException;

import java.util.Map;

public class HomeVibeNotFoundException extends NotFoundException {
    public HomeVibeNotFoundException(String message, Map<String, Object> fields) {
        super(message, fields);
    }
}