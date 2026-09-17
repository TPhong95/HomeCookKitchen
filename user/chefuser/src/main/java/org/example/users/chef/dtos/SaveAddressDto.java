package org.example.users.chef.dtos;

public record SaveAddressDto(
        String street,
        String city,
        String country
) {
}
