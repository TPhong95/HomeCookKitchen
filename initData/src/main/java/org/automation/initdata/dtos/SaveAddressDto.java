package org.automation.initdata.dtos;

public record SaveAddressDto(
        String street,
        String postalCode,
        String city,
        HomeVibeDto homeVibe
) {
}
