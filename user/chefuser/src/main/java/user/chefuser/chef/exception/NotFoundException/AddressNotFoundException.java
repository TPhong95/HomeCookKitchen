package user.chefuser.chef.exception.NotFoundException;



import java.util.Map;

public class AddressNotFoundException extends NotFoundException {
    public AddressNotFoundException(String message, Map<String, Object> fields) {
        super(message, fields);
    }
}
