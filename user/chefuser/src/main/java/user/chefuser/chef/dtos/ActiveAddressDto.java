package user.chefuser.chef.dtos;

public record ActiveAddressDto(
        Long id,
        String street,
        String postalCode,
        String city,
        Boolean active,
        Long chefId,
        HomeVibeDto homeVibe
) {
}
