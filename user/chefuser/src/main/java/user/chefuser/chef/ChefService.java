package user.chefuser.chef;

import user.chefuser.chef.dtos.CreateChefDto;
import user.chefuser.chef.dtos.UpdateChefDto;
import user.chefuser.chef.exception.badrequest.BadRequestException;
import user.chefuser.chef.exception.notfound.ChefNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class ChefService {
    private final ChefRepo chefRepo;
    /*
        Everyone loves a good regex
        https://www.baeldung.com/java-email-validation-regex
        Pattern.compile is moved as a constant to avoid compiling on every call
    */
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{8}");
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

    public ChefService(ChefRepo chefRepo) {
        this.chefRepo = chefRepo;
    }

    /* CHEF */
    @Transactional
    public Chef createChef(CreateChefDto dto) {
        validateChefInput(dto.name(), dto.email(), dto.phoneNumber());

        Chef chef = new Chef();
        chef.setName(dto.name());
        chef.setEmail(dto.email());
        chef.setPhoneNumber(dto.phoneNumber());

        return chefRepo.save(chef);
    }

    public Chef getChefById(Long id) {
        return chefRepo.findById(id).orElseThrow(() -> new ChefNotFoundException("Chef with id: " + id + " not found"));

    }

    public List<Chef> getAllChefs() {
        return chefRepo.findByActiveTrue();
    }

    @Transactional
    public Chef editChefInfo(Long id, UpdateChefDto dto) {
        Chef chef = getChefById(id);

        if (Boolean.FALSE.equals(chef.getActive())) {
            throw new BadRequestException(String.format("Chef with id: %d is inactive", id));
        }

        validateChefInput(dto.name(), dto.email(), dto.phoneNumber());

        chef.setName(dto.name());
        chef.setEmail(dto.email());
        chef.setPhoneNumber(dto.phoneNumber());

        return chefRepo.save(chef);
    }

    @Transactional
    public Chef deactivateChef(Long id) {
        Chef chef = getChefById(id);
        if (Boolean.FALSE.equals(chef.getActive())) {
            throw new BadRequestException("Chef with id: " + id + " is already inactive");
        }
        chef.setActive(false);
        chef.setName(null);
        chef.setEmail(null);
        chef.setPhoneNumber(null);
        chef.getAddresses().forEach(address -> address.setActive(false));

        return chefRepo.save(chef);
    }


    /* ADDRESS */
    @Transactional
    public List<Address> saveAddressToChef(Chef chef, Address address) {
        chef.addAddress(address);
        Chef savedChef = chefRepo.save(chef);
        return savedChef.getAddresses();
    }

    /* VALIDATION */
    private void validateChefInput(String name, String email, String phone) {
        if (name == null || name.isBlank()
                || email == null || email.isBlank()
                || phone == null || phone.isBlank()
        ) throw new BadRequestException("All fields must contain a value");

        if(checkValidEmail(email) != true) throw new BadRequestException("Invalid email format");
        if(checkValidPhone(phone) != true) throw new BadRequestException("Must be Norwegian 8 digit phone number");
    }

    private boolean checkValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean checkValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

}
