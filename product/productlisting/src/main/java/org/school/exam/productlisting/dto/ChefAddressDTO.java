package org.school.exam.productlisting.dto;

public record ChefAddressDTO(
        Long id,
        String street,
        String postalCode,
        String city
) {
}
