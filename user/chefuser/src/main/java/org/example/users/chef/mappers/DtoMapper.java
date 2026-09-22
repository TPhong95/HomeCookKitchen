package org.example.users.chef.mappers;

import org.example.users.chef.Chef;
import org.example.users.chef.address.Address;
import org.example.users.chef.dtos.AddressResponseDto;
import org.example.users.chef.dtos.ChefResponseDto;
import org.example.users.chef.dtos.SaveAddressDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoMapper {

    // CHEF MAPPINGS //

    public ChefResponseDto toChefResponseDto(Chef chef) {
        return new ChefResponseDto(
                chef.getId(),
                chef.getName(),
                chef.getEmail(),
                chef.getPhoneNumber()
        );
    }

    // ADDRESS MAPPINGS //

    public Address toAddressEntity(SaveAddressDto dto, Chef chef) {
        Address address = new Address();
        address.setStreet(dto.street());
        address.setCity(dto.city());
        address.setCountry(dto.country());
        address.setChef(chef);
        return address;
    }

    public AddressResponseDto toAddressResponseDto(Address address, Long chefId) {
        return new AddressResponseDto(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                chefId
        );
    }

    public List<AddressResponseDto> toAddressResponseDtoList(List<Address> addresses, Long chefId) {
        return addresses.stream()
                .map(address -> toAddressResponseDto(address, chefId))
                .toList();
    }
}
