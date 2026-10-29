package user.chefuser.chef;

import org.springframework.stereotype.Service;
import user.chefuser.chef.address.Address;
import user.chefuser.chef.dtos.SaveAddressDto;
import user.chefuser.chef.mappers.DtoMapper;

@Service
public class AddressService {
    private final ChefRepo chefRepo;
    private final DtoMapper dtoMapper;

    public AddressService(ChefRepo chefRepo, DtoMapper dtoMapper) {
        this.chefRepo = chefRepo;
        this.dtoMapper = dtoMapper;
    }

    public Address createAddressObject(Chef chef, SaveAddressDto dto) {
        Address address = dtoMapper.toAddressEntity(dto, chef);
        return address;
    }
}
