package org.example.users.chef.dtos;

public record ChefResponseDto(
        Long id,
        String name,
        String email,
        String phoneNumber
) {
}
