package user.chefuser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.chefuser.chef.Address;
import user.chefuser.chef.Genre;
import user.chefuser.chef.HomeVibe;
import user.chefuser.chef.HomeVibeRepo;
import user.chefuser.chef.HomeVibeService;
import user.chefuser.chef.dtos.HomeVibeDto;
import user.chefuser.chef.exception.NotFoundException.HomeVibeNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HomeVibeServiceTest {
    @Mock
    HomeVibeRepo homeVibeRepo;

    @InjectMocks
    HomeVibeService homeVibeService;

    /*
    1. Create home vibe happy path
    2. Links both sides of the relationship
     */
    @Test
    void createHomeVibe_shouldCreateHomeVibe() {
        Address address = new Address();
        address.setId(5L);
        HomeVibeDto dto = new HomeVibeDto("Cosy flat with a view", Genre.JAZZ, true, false, true);

        when(homeVibeRepo.save(any(HomeVibe.class))).thenAnswer(i -> i.getArgument(0));

        HomeVibe res = homeVibeService.createHomeVibe(address, dto);
        assertNotNull(res);
        assertEquals("Cosy flat with a view", res.getDescription());
        assertEquals(Genre.JAZZ, res.getMusic());
        assertEquals(true, res.getPets());
        assertEquals(false, res.getOutdoor());
        assertEquals(true, res.getFamilyFriendly());
        verify(homeVibeRepo).save(res);
    }

    @Test
    void createHomeVibe_shouldLinkBothSides() {
        Address address = new Address();
        address.setId(5L);
        HomeVibeDto dto = new HomeVibeDto("Cosy flat with a view", Genre.JAZZ, true, false, true);

        when(homeVibeRepo.save(any(HomeVibe.class))).thenAnswer(i -> i.getArgument(0));

        HomeVibe res = homeVibeService.createHomeVibe(address, dto);
        assertSame(address, res.getAddress());
        assertSame(res, address.getHomeVibe());
    }

    /*
    1. Update home vibe happy path
    2. Address has no home vibe
     */
    @Test
    void updateHomeVibe_shouldUpdateHomeVibe() {
        Address address = new Address();
        address.setId(5L);

        HomeVibe existing = new HomeVibe();
        existing.setDescription("Cosy flat with a view");
        existing.setMusic(Genre.JAZZ);
        existing.setPets(true);
        existing.setOutdoor(false);
        existing.setFamilyFriendly(true);
        existing.setAddress(address);

        when(homeVibeRepo.findByAddressId(5L)).thenReturn(Optional.of(existing));
        when(homeVibeRepo.save(any(HomeVibe.class))).thenAnswer(i -> i.getArgument(0));

        HomeVibeDto dto = new HomeVibeDto("Loud and proud", Genre.ROCK, false, true, false);

        HomeVibe res = homeVibeService.updateHomeVibe(address, dto);
        assertNotNull(res);
        assertEquals("Loud and proud", res.getDescription());
        assertEquals(Genre.ROCK, res.getMusic());
        assertEquals(false, res.getPets());
        assertEquals(true, res.getOutdoor());
        assertEquals(false, res.getFamilyFriendly());
        assertSame(address, res.getAddress());
        verify(homeVibeRepo).save(res);
    }

    @Test
    void updateHomeVibe_shouldThrowWhenAddressHasNoHomeVibe() {
        Address address = new Address();
        address.setId(5L);
        when(homeVibeRepo.findByAddressId(5L)).thenReturn(Optional.empty());
        HomeVibeDto dto = new HomeVibeDto("Loud and proud", Genre.ROCK, false, true, false);

        HomeVibeNotFoundException e = assertThrows(HomeVibeNotFoundException.class,
                () -> homeVibeService.updateHomeVibe(address, dto));
        assertEquals("Home vibe for address with id: 5 not found", e.getMessage());
        verify(homeVibeRepo, never()).save(any(HomeVibe.class));
    }
    /*
    1. Anonymise home vibe happy path
        - Description is removed, the rest is kept
    2. Address has no home vibe
     */
    @Test
    void anonymiseHomeVibe_shouldRemoveDescription() {
        Address address = new Address();
        address.setId(5L);

        HomeVibe existing = new HomeVibe();
        existing.setDescription("Cosy flat above my mum's bakery");
        existing.setMusic(Genre.JAZZ);
        existing.setPets(true);
        existing.setOutdoor(false);
        existing.setFamilyFriendly(true);

        when(homeVibeRepo.findByAddressId(5L)).thenReturn(Optional.of(existing));

        homeVibeService.anonymiseHomeVibe(address);
        assertNull(existing.getDescription());
        assertEquals(Genre.JAZZ, existing.getMusic());
        assertEquals(true, existing.getPets());
        assertEquals(false, existing.getOutdoor());
        assertEquals(true, existing.getFamilyFriendly());
    }

    @Test
    void anonymiseHomeVibe_shouldDoNothingWhenAddressHasNoHomeVibe() {
        Address address = new Address();
        address.setId(5L);
        when(homeVibeRepo.findByAddressId(5L)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> homeVibeService.anonymiseHomeVibe(address));
        verify(homeVibeRepo, never()).save(any(HomeVibe.class));
    }
}