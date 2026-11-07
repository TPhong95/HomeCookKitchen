package user.chefuser.chef.dtos;

import user.chefuser.chef.Genre;

public record HomeVibeDto(
        String description,
        Genre music,
        Boolean pets,
        Boolean outdoor,
        Boolean familyFriendly
) {
}