package user.chefuser.chef;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.chefuser.chef.dtos.HomeVibeDto;
import user.chefuser.chef.dtos.SaveAddressDto;
import user.chefuser.chef.exception.badrequest.BadRequestException;

import java.util.List;
import java.util.Map;

@Service
public class ChefOrchestrationService {
    private final ChefService chefService;
    private final AddressService addressService;
    private final HomeVibeService homeVibeService;

    public ChefOrchestrationService(ChefService chefService, AddressService addressService, HomeVibeService homeVibeService) {
        this.chefService = chefService;
        this.addressService = addressService;
        this.homeVibeService = homeVibeService;
    }

    public Address getAddressById(Long addressId) {
        return addressService.getAddressById(addressId);
    }

    public List<Address> getActiveAddresses(Long chefId) {
        return addressService.getActiveAddressesByChef(chefId);
    }

    @Transactional
    public List<Address> saveAddressToChef(Long chefId, SaveAddressDto dto) {
        Chef chef = chefService.getChefById(chefId);
        if (Boolean.FALSE.equals(chef.getActive())) {
            throw new BadRequestException(String.format("Chef with id: %d inactive", chefId), Map.of());
        }
        validateAddressDTO(dto);
        Address address = addressService.createAddress(chef, dto);
        homeVibeService.createHomeVibe(address, dto.homeVibe());
        return addressService.getActiveAddressesByChef(chefId);
    }

    @Transactional
    public Address editChefAddress(Long chefId, Long addressId, SaveAddressDto dto) {
        chefService.getChefById(chefId);
        validateAddressDTO(dto);
        Address address = addressService.updateAddress(chefId, addressId, dto);
        homeVibeService.updateHomeVibe(address, dto.homeVibe());
        return address;
    }

    @Transactional
    public Address deactivateAddress(Long chefId, Long addressId) {
        chefService.getChefById(chefId);
        Address address = addressService.deactivateAddress(chefId, addressId);
        homeVibeService.anonymiseHomeVibe(address);
        return address;
    }

    @Transactional
    public Chef deactivateChef(Long chefId) {
        Chef chef = chefService.deactivateChef(chefId);
        chef.getAddresses().forEach(address -> {
            addressService.anonymiseAddress(address);
            homeVibeService.anonymiseHomeVibe(address);
        });
        return chef;
    }

    private void validateAddressDTO(SaveAddressDto dto) {
        if (isBlank(dto.city())) throw new BadRequestException("You need a city", Map.of());
        if (isBlank(dto.street())) throw new BadRequestException("You need an address", Map.of());
        if (isBlank(dto.postalCode())) throw new BadRequestException("You need a postal code", Map.of());
        if (dto.postalCode().length() != 4) throw new BadRequestException("Your postal code must contain 4 digits", Map.of());
        try {
            Long.parseLong(dto.postalCode());
        } catch (NumberFormatException e) {
            throw new BadRequestException("Your postal code must contain digits", Map.of());
        }

        validateHomeVibeDto(dto.homeVibe());
    }

    private void validateHomeVibeDto(HomeVibeDto dto) {
        if (dto == null) throw new BadRequestException("You need to describe the home vibe", Map.of());
        if (isBlank(dto.description())) throw new BadRequestException("You need a description", Map.of());
        if (dto.description().length() > 500)
            throw new BadRequestException("Description cannot exceed 500 characters", Map.of());
        if (dto.music() == null) throw new BadRequestException("You need to choose a music genre", Map.of());
        if (dto.pets() == null || dto.outdoor() == null || dto.familyFriendly() == null)
            throw new BadRequestException("Pets, outdoor and family friendly must all be answered", Map.of());
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

}
