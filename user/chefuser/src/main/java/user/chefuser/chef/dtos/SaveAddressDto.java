package user.chefuser.chef.dtos;

public record SaveAddressDto(
        String street,
        String postalCode,
        String city
) {
}
