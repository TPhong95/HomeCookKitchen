package user.chefuser.chef.mappers;

import user.chefuser.chef.Chef;
import user.chefuser.chef.address.Address;
import user.chefuser.chef.dtos.AddressResponseDto;
import user.chefuser.chef.dtos.ChefResponseDto;
import user.chefuser.chef.dtos.SaveAddressDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoMapper {

    // CHEF MAPPINGS //

    public ChefResponseDto toChefResponseDto(Chef chef) {
        List<AddressResponseDto> addressDtos;

        if (chef.getAddresses() != null) {
            addressDtos = toAddressResponseDtoList(chef.getAddresses(), chef.getId());
        } else {
            addressDtos = List.of();
        }

        return new ChefResponseDto(
                chef.getId(),
                chef.getName(),
                chef.getEmail(),
                chef.getPhoneNumber(),
                addressDtos
        );
    }

    public List<ChefResponseDto> toChefResponseDtoList(List<Chef> chefs) {
        return chefs.stream()
                .map(this::toChefResponseDto)
                .toList();
    }

    // ADDRESS MAPPINGS //

    public Address toAddressEntity(SaveAddressDto dto, Chef chef) {
        Address address = new Address();
        address.setStreet(dto.street());
        address.setPostalCode(dto.postalCode());
        address.setCity(dto.city());
        address.setChef(chef);
        return address;
    }

    public AddressResponseDto toAddressResponseDto(Address address, Long chefId) {
        return new AddressResponseDto(
                address.getId(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity(),
                chefId
        );
    }

    public List<AddressResponseDto> toAddressResponseDtoList(List<Address> addresses, Long chefId) {
        return addresses.stream()
                .map(address -> toAddressResponseDto(address, chefId))
                .toList();
    }
}
