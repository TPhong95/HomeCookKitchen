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
/*
public class Chef {
private Long id;
private String name;
private String email;
private String phoneNumber;
List<Address> addresses = new ArrayList<>();


public record ChefResponseDto(
        Long id,
        String name,
        String email,
        String phoneNumber
) {
}
 */