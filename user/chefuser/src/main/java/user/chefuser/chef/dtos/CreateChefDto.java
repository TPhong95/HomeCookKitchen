package user.chefuser.chef.dtos;

public record CreateChefDto(
        String name,
        String email,
        String phoneNumber
) {
}
