package org.example.users.chef.dtos;

import java.util.List;

public record ChefResponseDto(
        Long id,
        String name,
        String email,
        String phoneNumber,
        List<AddressResponseDto> addresses
) {
}
