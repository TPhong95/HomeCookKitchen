package org.example.users.chef.dtos;

public record AddressResponseDto(
        Long id,
        String street,
        String city,
        String country,
        Long chefId
) {
}
