package user.chefuser.chef.mappers;

import user.chefuser.chef.Chef;
import user.chefuser.chef.Address;
import user.chefuser.chef.ChefNotification;
import user.chefuser.chef.HomeVibe;
import user.chefuser.chef.dtos.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DtoMapper {

    // CHEF MAPPINGS //

    public ChefResponseDto toChefResponseDto(Chef chef) {
        List<AddressResponseDto> addressDtos;

        if (chef.getAddresses() != null) {
            List<Address> activeAddresses = chef.getAddresses().stream()
                    .filter(a -> Boolean.TRUE.equals(a.getActive()))
                    .toList();
            addressDtos = toAddressResponseDtoList(activeAddresses, chef.getId());
        } else {
            addressDtos = List.of();
        }

        return new ChefResponseDto(
                chef.getId(),
                chef.getName(),
                chef.getEmail(),
                chef.getPhoneNumber(),
                addressDtos
        );
    }

    public List<ChefResponseDto> toChefResponseDtoList(List<Chef> chefs) {
        return chefs.stream()
                .map(this::toChefResponseDto)
                .toList();
    }

    // ADDRESS MAPPINGS //
    public ActiveAddressDto toActiveAddressDto(Address address, Long chefId) {
        return new ActiveAddressDto(address.getId(), address.getStreet(), address.getPostalCode(),
                address.getCity(), address.getActive(), chefId, toHomeVibeDto(address.getHomeVibe()));
    }

    public List<ActiveAddressDto> toActiveAddressDtoList(List<Address> addresses, Long chefId) {
        return addresses.stream().map(a -> toActiveAddressDto(a, chefId)).toList();
    }

    public Address toAddressEntity(SaveAddressDto dto, Chef chef) {
        Address address = new Address();
        address.setStreet(dto.street());
        address.setPostalCode(dto.postalCode());
        address.setCity(dto.city());
        address.setChef(chef);
        return address;
    }

    public AddressResponseDto toAddressResponseDto(Address address, Long chefId) {
        return new AddressResponseDto(
                address.getId(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity(),
                chefId,
                toHomeVibeDto(address.getHomeVibe())
        );
    }

    public List<AddressResponseDto> toAddressResponseDtoList(List<Address> addresses, Long chefId) {
        return addresses.stream()
                .map(address -> toAddressResponseDto(address, chefId))
                .toList();
    }

    public HomeVibeDto toHomeVibeDto(HomeVibe vibe) {
        if (vibe == null) return null;
        return new HomeVibeDto(vibe.getDescription(), vibe.getMusic(),
                vibe.getPets(), vibe.getOutdoor(), vibe.getFamilyFriendly());
    }

    // Chef Notification Response DTO //
    public ChefNotificationResponseDto toChefNotificationResponseDto(ChefNotification notification) {
        return new ChefNotificationResponseDto(
                notification.getId(),
                notification.getChefId(),
                notification.getMessage(),
                notification.getTime(),
                notification.getIsRead()
        );
    }
}
