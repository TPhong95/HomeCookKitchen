package user.chefuser.chef;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.chefuser.chef.address.Address;
import user.chefuser.chef.dtos.SaveAddressDto;
import user.chefuser.chef.exception.notfound.AddressNotFoundException;
import user.chefuser.chef.mappers.DtoMapper;

@Service
public class AddressService {
    private final AddressRepo addressRepo;
    private final DtoMapper dtoMapper;

    public AddressService(AddressRepo addressRepo, DtoMapper dtoMapper) {
        this.addressRepo = addressRepo;
        this.dtoMapper = dtoMapper;
    }

    public Address createAddressObject(Chef chef, SaveAddressDto dto) {
        Address address = dtoMapper.toAddressEntity(dto, chef);
        return address;
    }

    @Transactional
    public Address updateAddress(Long chefId, Long addressId, SaveAddressDto dto) {
        Address address = addressRepo.findByIdAndChefId(chefId, addressId)
                        .orElseThrow(() -> new AddressNotFoundException(
                                String.format("Address with id: %d not found for chef with id: %d", addressId, chefId)
                        ));
        address.setStreet(dto.street());
        address.setPostalCode(dto.postalCode());
        address.setCity(dto.city());
        return addressRepo.save(address);
    }
}
