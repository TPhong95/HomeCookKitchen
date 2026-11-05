package user.chefuser.chef.exception.NotFoundException;



import java.util.Map;

public class ChefNotFoundException extends NotFoundException {
    public ChefNotFoundException(String message, Map<String, Object> fields) {
        super(message, fields);
    }
}
