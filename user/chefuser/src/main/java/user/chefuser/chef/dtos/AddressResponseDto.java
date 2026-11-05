package user.chefuser.chef.dtos;

public record AddressResponseDto(
        Long id,
        String street,
        String postalCode,
        String city,
        Long chefId
) {
}
