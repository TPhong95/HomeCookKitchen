package user.chefuser.chef;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.chefuser.chef.dtos.HomeVibeDto;
import user.chefuser.chef.exception.NotFoundException.HomeVibeNotFoundException;

import java.util.Map;

@Service
public class HomeVibeService {
    private final HomeVibeRepo homeVibeRepo;

    public HomeVibeService(HomeVibeRepo homeVibeRepo) {
        this.homeVibeRepo = homeVibeRepo;
    }

    @Transactional
    public HomeVibe createHomeVibe(Address address, HomeVibeDto dto) {
        HomeVibe vibe = new HomeVibe();
        applyDto(vibe, dto);

        vibe.setAddress(address);
        address.setHomeVibe(vibe);

        return homeVibeRepo.save(vibe);
    }

    @Transactional
    public HomeVibe updateHomeVibe(Address address, HomeVibeDto dto) {
        HomeVibe vibe = homeVibeRepo.findByAddressId(address.getId())
                .orElseThrow(() -> new HomeVibeNotFoundException(
                        String.format("Home vibe for address with id: %d not found", address.getId()), Map.of()));
        applyDto(vibe, dto);
        return homeVibeRepo.save(vibe);
    }

    @Transactional
    public void anonymiseHomeVibe(Address address) {
        homeVibeRepo.findByAddressId(address.getId())
                .ifPresent(vibe -> vibe.setDescription(null));
    }

    private void applyDto(HomeVibe vibe, HomeVibeDto dto) {
        vibe.setDescription(dto.description());
        vibe.setMusic(dto.music());
        vibe.setPets(dto.pets());
        vibe.setOutdoor(dto.outdoor());
        vibe.setFamilyFriendly(dto.familyFriendly());
    }
}