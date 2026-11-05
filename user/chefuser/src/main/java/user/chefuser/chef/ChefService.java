package user.chefuser.chef;

import user.chefuser.chef.dtos.CreateChefDto;
import user.chefuser.chef.dtos.UpdateChefDto;
import user.chefuser.chef.exception.badrequest.BadRequestException;
import user.chefuser.chef.exception.notfound.ChefNotFoundException;
import user.chefuser.chef.mappers.DtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChefService {
    private final ChefRepo chefRepo;
    private final DtoMapper dtoMapper;

    public ChefService(ChefRepo chefRepo, DtoMapper dtoMapper) {
        this.chefRepo = chefRepo;
        this.dtoMapper = dtoMapper;
    }

    /* CHEF */
    @Transactional
    public Chef createChef(CreateChefDto dto) {
        if (    dto.name() == null || dto.name().isBlank()
                || dto.email() == null || dto.email().isBlank()
                || dto.phoneNumber() == null || dto.phoneNumber().isBlank()
        ) throw new BadRequestException("All fields must contain a value");

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

        if (    dto.name() == null || dto.name().isBlank()
                || dto.email() == null || dto.email().isBlank()
                || dto.phoneNumber() == null || dto.phoneNumber().isBlank()
        ) throw new BadRequestException("All fields must contain a value");

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

}
