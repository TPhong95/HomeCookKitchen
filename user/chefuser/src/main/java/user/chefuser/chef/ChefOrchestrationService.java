package user.chefuser.chef;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import user.chefuser.chef.address.Address;
import user.chefuser.chef.dtos.SaveAddressDto;
import user.chefuser.chef.exception.badrequest.BadRequestException;

import java.util.List;

@Service
@Getter
@Setter
public class ChefOrchestrationService {
    private ChefService chefService;
    private AddressService addressService;

    public ChefOrchestrationService(ChefService chefService, AddressService addressService) {
        this.chefService = chefService;
        this.addressService = addressService;
    }

    public List<Address> saveAddressToChef(Long chefId, SaveAddressDto dto) {
        Chef chef = chefService.getChefById(chefId);
        validateCreateAddressDTO(dto);
        Address address = addressService.createAddressObject(chef, dto);
        List<Address> addressList = chefService.saveAddressToChef(chef, address);
        return addressList;
    }

    private void validateCreateAddressDTO(SaveAddressDto dto) {
        if(dto.city() == null) throw new BadRequestException("You need a city");
        if(dto.street() == null) throw new BadRequestException("You need an address");
        if(dto.postalCode() == null) throw new BadRequestException("You need a postal code");
        if(dto.postalCode().length() != 4) throw new BadRequestException("Your postal code must contain 4 digits");
        try {
            Long.parseLong(dto.postalCode());
        } catch (NumberFormatException e) {
            throw new BadRequestException("Your postal code must contain digits");
        }

    }


}
