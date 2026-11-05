package user.chefuser.chef;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.chefuser.chef.dtos.SaveAddressDto;
import user.chefuser.chef.exception.badrequest.BadRequestException;
import user.chefuser.chef.exception.notfound.AddressNotFoundException;
import user.chefuser.chef.mappers.DtoMapper;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepo addressRepo;
    private final DtoMapper dtoMapper;

    public AddressService(AddressRepo addressRepo, DtoMapper dtoMapper) {
        this.addressRepo = addressRepo;
        this.dtoMapper = dtoMapper;
    }

    public Address createAddressObject(Chef chef, SaveAddressDto dto) {
        return dtoMapper.toAddressEntity(dto, chef);
    }

    @Transactional
    public Address updateAddress(Long chefId, Long addressId, SaveAddressDto dto) {
        Address address = addressRepo.findByIdAndChefId(addressId, chefId)
                        .orElseThrow(() -> new AddressNotFoundException(
                                String.format("Address with id: %d not found for chef with id: %d", addressId, chefId)
                        ));
        if (Boolean.FALSE.equals(address.getActive())) {
            throw new BadRequestException(
                    String.format("Address with id: %d is already inactive", addressId));
        }
        address.setStreet(dto.street());
        address.setPostalCode(dto.postalCode());
        address.setCity(dto.city());
        return addressRepo.save(address);
    }

    @Transactional
    public Address deactivateAddress(Long chefId, Long addressId) {
        Address address = addressRepo.findByIdAndChefId(addressId, chefId)
                .orElseThrow(() -> new AddressNotFoundException(
                        String.format("Address with id: %d not found for chef with id: %d", addressId, chefId)));

        if (Boolean.FALSE.equals(address.getActive())) {
            throw new BadRequestException(
                    String.format("Address with id: %d is already inactive", addressId));
        }
        address.setActive(false);
        address.setStreet(null);
        address.setPostalCode(null);
        address.setCity(null);
        return addressRepo.save(address);
    }

    public Address getAddressById(Long addressId) {
        return addressRepo.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(
                        String.format("Address with id: %d not found", addressId)
                ));
    }

    public List<Address> getActiveAddressesByChef(Long chefId) {
        return addressRepo.findActiveByChefId(chefId);
    }

    /* VALIDATION */
    /*
        If this is ever in actual production,
        we can validate addresses against Kartverket,
        but we are not risking external dependencies
        on the exam
       */
}
