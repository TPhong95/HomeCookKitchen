package user.chefuser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.chefuser.chef.Chef;
import user.chefuser.chef.ChefRepo;
import user.chefuser.chef.ChefService;
import user.chefuser.chef.dtos.CreateChefDto;
import user.chefuser.chef.dtos.UpdateChefDto;
import user.chefuser.chef.exception.badrequest.BadRequestException;
import user.chefuser.chef.exception.NotFoundException.ChefNotFoundException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ChefServiceTest {
    @Mock
    ChefRepo chefRepo;

    @InjectMocks
    ChefService chefService;

    /*
    1. Create chef happy path
    2. Missing name
    3. Missing email
    4. Missing phone
     */
    @Test
    void createChef_shouldCreateChef() {
        when(chefRepo.save(any(Chef.class))).thenAnswer(i -> i.getArgument(0));
        CreateChefDto dto = new CreateChefDto("Chef 1", "at@at.at", "12345678");
        Chef res = chefService.createChef(dto);
        assertNotNull(res);
        assertEquals("Chef 1", res.getName());
        assertEquals("at@at.at", res.getEmail());
        assertEquals("12345678", res.getPhoneNumber());
        verify(chefRepo).save(res);
    }

    @Test
    void createChef_shouldRequireName() {
        CreateChefDto dto = new CreateChefDto("", "at@at.at", "12345678");
        BadRequestException e = assertThrows(BadRequestException.class, () -> chefService.createChef(dto));
        assertEquals("All fields must contain a value", e.getMessage());
    }

    @Test
    void createChef_shouldRequireEmail() {
        CreateChefDto dto = new CreateChefDto("Cheffyboii Kewl", "", "12345678");
        BadRequestException e = assertThrows(BadRequestException.class, () -> chefService.createChef(dto));
        assertEquals("All fields must contain a value", e.getMessage());
    }

    @Test
    void createChef_shouldRequirePhone() {
        CreateChefDto dto = new CreateChefDto("Cheffy Jeffy", "cheffy@jeffy.no", "");
        BadRequestException e = assertThrows(BadRequestException.class, () -> chefService.createChef(dto));
        assertEquals("All fields must contain a value", e.getMessage());
    }

    /*
    1. Get chef happy path
    2. Chef not found
    3. Get inactive chef
     */
    @Test
    void findChefById_shouldReturnCustomer() {
        Chef chef = new Chef();
        chef.setId(1L);
        chefRepo.save(chef);
        when(chefRepo.findById(1L)).thenReturn(Optional.of(chef));
        var res = chefService.getChefById(1L);
        assert res != null;
        assertEquals(1, res.getId());
    }

    @Test
    void findChefById_shouldThrowNotFound() {
        when(chefRepo.findById(100L)).thenReturn(Optional.empty());
        ChefNotFoundException e = assertThrows(ChefNotFoundException.class, () -> chefService.getChefById(100L));
        assertEquals("Chef with id: 100 not found", e.getMessage());
    }

    @Test
    void findChefById_shouldReturnInactive() {
        Chef chef = new Chef();
        chef.setId(1L);
        chef.setActive(false);
        chefRepo.save(chef);
        when(chefRepo.findById(1L)).thenReturn(Optional.of(chef));
        var res = chefService.getChefById(1L);
        assert res != null;
        assertEquals(1, res.getId());
    }

    /*
    1. Find all chefs
    2. Does not return inactive chefs
     */
    @Test
    void findAllChefs_shouldReturnChefs() {
        Chef chef1 = new Chef();
        chef1.setId(1L);
        Chef chef2 = new Chef();
        chef2.setId(2L);
        when(chefRepo.findByActiveTrue()).thenReturn(Arrays.asList(chef1, chef2));
        var res = chefService.getAllChefs();
        assertEquals(2, res.size());
    }

    @Test
    void findAllChefs_doesNotReturnInactive() {
        Chef active = new Chef();
        active.setId(1L);
        Chef inactive = new Chef();
        inactive.setId(2L);
        inactive.setActive(false);
        when(chefRepo.findByActiveTrue()).thenReturn(Arrays.asList(active));
        var res = chefService.getAllChefs();
        assertEquals(1, res.size());
    }

    /*
    1. Edit chef info happy path
    2. Cannot edit inactive chef
    3. Missing name
    4. Missing email
    5. Missing phone
     */
    @Test
    void editChef_allowsChefToBeEdited() {
        Long chefId = 10L;
        Chef chef = new Chef();
        chef.setId(chefId);
        chef.setName("Bobbie Browne");
        chef.setEmail("brown@bobby.co.uk");
        chef.setPhoneNumber("50506060");

        when(chefRepo.findById(chefId)).thenReturn(Optional.of(chef));
        when(chefRepo.save(any(Chef.class))).thenAnswer(i -> i.getArgument(0));

        Chef preChange = chefService.getChefById(chefId);
        assertEquals("Bobbie Browne", preChange.getName());
        assertEquals("brown@bobby.co.uk", preChange.getEmail());
        assertEquals("50506060", preChange.getPhoneNumber());

        UpdateChefDto dto = new UpdateChefDto("Robbie Row", "row@robbie.com", "60605050");

        Chef postChange = chefService.editChefInfo(chefId, dto);
        assertNotNull(postChange);
        verify(chefRepo).save(postChange);

        Chef res = chefService.getChefById(chefId);
        assertEquals("Robbie Row", res.getName());
        assertEquals("row@robbie.com", res.getEmail());
        assertEquals("60605050", res.getPhoneNumber());

    }

    @Test
    void editChef_forbidsEditInactiveChef() {
        Chef chef = new Chef();
        chef.setId(1L);
        chef.setActive(false);
        when(chefRepo.findById(1L)).thenReturn(Optional.of(chef));
        UpdateChefDto dto = new UpdateChefDto("Millie Billie", "millie@billie.com", "12345678");

        BadRequestException e = assertThrows(BadRequestException.class, () -> chefService.editChefInfo(1L, dto));
        assertEquals("Chef with id: 1 is inactive", e.getMessage());
    }

    @Test
    void editChef_requiresName() {
        Chef chef = new Chef();
        chef.setId(1L);
        when(chefRepo.findById(1L)).thenReturn(Optional.of(chef));
        UpdateChefDto dto = new UpdateChefDto("", "blank@mail.com", "12022020");
        BadRequestException e = assertThrows(BadRequestException.class, () -> chefService.editChefInfo(1L, dto));
        assertEquals("All fields must contain a value", e.getMessage());
    }

    @Test
    void editChef_requiresEmail() {
        Chef chef = new Chef();
        chef.setId(1L);
        when(chefRepo.findById(1L)).thenReturn(Optional.of(chef));
        UpdateChefDto dto = new UpdateChefDto("Blank Expression", "", "12022020");
        BadRequestException e = assertThrows(BadRequestException.class, () -> chefService.editChefInfo(1L, dto));
        assertEquals("All fields must contain a value", e.getMessage());
    }

    @Test
    void editChef_requiresPhone() {
        Chef chef = new Chef();
        chef.setId(1L);
        when(chefRepo.findById(1L)).thenReturn(Optional.of(chef));
        UpdateChefDto dto = new UpdateChefDto("Blank Expression", "blank@mail.com", "");
        BadRequestException e = assertThrows(BadRequestException.class, () -> chefService.editChefInfo(1L, dto));
        assertEquals("All fields must contain a value", e.getMessage());
    }

    /*
    1. Active chef gets inactivated
        - Gets anonymised
    2. Inactive chef cannot be inactivated
     */
    @Test
    void deactivateChef_shouldAnonymizeData() {
        when(chefRepo.save(any(Chef.class))).thenAnswer(i -> i.getArgument(0));
        CreateChefDto dto = new CreateChefDto("Shadow People", "vanish@forever.com", "12345678");
        Chef res = chefService.createChef(dto);
        assertNotNull(res);
        res.setId(1L);
        assertEquals("Shadow People", res.getName());
        assertEquals("vanish@forever.com", res.getEmail());
        assertEquals("12345678", res.getPhoneNumber());
        assertEquals(true, res.getActive());
        verify(chefRepo).save(res);

        when(chefRepo.findById(1L)).thenReturn(Optional.of(res));

        Chef deactivated = chefService.deactivateChef(res.getId());
        assertEquals(null, deactivated.getName());
        assertEquals(null, deactivated.getEmail());
        assertEquals(null, deactivated.getPhoneNumber());
        assertEquals(false, deactivated.getActive());
    }
}
