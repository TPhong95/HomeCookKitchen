package org.automation.initdata.dtos;

import org.automation.initdata.enums.Genre;

public record HomeVibeDto(
        String description,
        Genre music,
        Boolean pets,
        Boolean outdoor,
        Boolean familyFriendly
) {
}