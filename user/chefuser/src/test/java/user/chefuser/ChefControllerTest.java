package user.chefuser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import user.chefuser.chef.*;
import user.chefuser.chef.dtos.*;
import user.chefuser.chef.exception.badrequest.BadRequestException;
import user.chefuser.chef.exception.NotFoundException.AddressNotFoundException;
import user.chefuser.chef.exception.NotFoundException.ChefNotFoundException;
import user.chefuser.chef.mappers.DtoMapper;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChefController.class)
public class ChefControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ChefService chefService;

    @MockitoBean
    private DtoMapper dtoMapper;

    @MockitoBean
    private ChefOrchestrationService chefOrchestrationService;

    /* CHEF */

    @Test
    void saveChef_shouldReturnCreatedChef() throws Exception {
        CreateChefDto dto = new CreateChefDto("Gordon Ramsay", "gordon@mail.com", "66616661");
        Chef chef = new Chef();
        ChefResponseDto response = new ChefResponseDto(1L, "Gordon Ramsay", "gordon@mail.com", "66616661", List.of());

        when(chefService.createChef(any(CreateChefDto.class))).thenReturn(chef);
        when(dtoMapper.toChefResponseDto(chef)).thenReturn(response);

        mockMvc.perform(post("/api/chef")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Gordon Ramsay"))
                .andExpect(jsonPath("$.email").value("gordon@mail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("66616661"));
    }

    @Test
    void saveChef_shouldReturnBadRequestWhenNameIsMissing() throws Exception {
        CreateChefDto dto = new CreateChefDto("", "gordon@mail.com", "66616661");
        when(chefService.createChef(any(CreateChefDto.class)))
                .thenThrow(new BadRequestException("All fields must contain a value", Map.of()));

        mockMvc.perform(post("/api/chef")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveChef_shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
        CreateChefDto dto = new CreateChefDto("Gordon Ramsay", "notanemail", "66616661");
        when(chefService.createChef(any(CreateChefDto.class)))
                .thenThrow(new BadRequestException("Invalid email format", Map.of()));

        mockMvc.perform(post("/api/chef")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getChefById_shouldReturnChef() throws Exception {
        Chef chef = new Chef();
        ChefResponseDto response = new ChefResponseDto(1L, "Bobbie Browne", "bobbie@mail.com", "50506060", List.of());

        when(chefService.getChefById(1L)).thenReturn(chef);
        when(dtoMapper.toChefResponseDto(chef)).thenReturn(response);

        mockMvc.perform(get("/api/chef/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Bobbie Browne"))
                .andExpect(jsonPath("$.email").value("bobbie@mail.com"));
    }

    @Test
    void getChefById_shouldReturnNotFoundWhenChefDoesNotExist() throws Exception {
        when(chefService.getChefById(999L))
                .thenThrow(new ChefNotFoundException("Chef with id: 999 not found", Map.of()));

        mockMvc.perform(get("/api/chef/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllChefs_shouldReturnListOfChefs() throws Exception {
        List<Chef> chefs = List.of(new Chef(), new Chef());
        List<ChefResponseDto> response = List.of(
                new ChefResponseDto(1L, "Chef One", "one@mail.com", "11111111", List.of()),
                new ChefResponseDto(2L, "Chef Two", "two@mail.com", "22222222", List.of()));

        when(chefService.getAllChefs()).thenReturn(chefs);
        when(dtoMapper.toChefResponseDtoList(chefs)).thenReturn(response);

        mockMvc.perform(get("/api/chef"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Chef One"))
                .andExpect(jsonPath("$[1].name").value("Chef Two"));
    }

    @Test
    void editChefById_shouldReturnUpdatedChef() throws Exception {
        UpdateChefDto dto = new UpdateChefDto("Robbie Row", "robbie@mail.com", "60605050");
        Chef chef = new Chef();
        ChefResponseDto response = new ChefResponseDto(1L, "Robbie Row", "robbie@mail.com", "60605050", List.of());

        when(chefService.editChefInfo(eq(1L), any(UpdateChefDto.class))).thenReturn(chef);
        when(dtoMapper.toChefResponseDto(chef)).thenReturn(response);

        mockMvc.perform(patch("/api/chef/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Robbie Row"))
                .andExpect(jsonPath("$.email").value("robbie@mail.com"));
    }

    @Test
    void editChefById_shouldReturnBadRequestWhenChefIsInactive() throws Exception {
        UpdateChefDto dto = new UpdateChefDto("Robbie Row", "robbie@mail.com", "60605050");
        when(chefService.editChefInfo(eq(1L), any(UpdateChefDto.class)))
                .thenThrow(new BadRequestException("Chef with id: 1 is inactive", Map.of()));

        mockMvc.perform(patch("/api/chef/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deactivateChef_shouldReturnDeactivatedChef() throws Exception {
        Chef chef = new Chef();
        ChefResponseDto response = new ChefResponseDto(1L, null, null, null, List.of());

        when(chefOrchestrationService.deactivateChef(1L)).thenReturn(chef);
        when(dtoMapper.toChefResponseDto(chef)).thenReturn(response);

        mockMvc.perform(patch("/api/chef/1/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").doesNotExist());
    }

    @Test
    void deactivateChef_shouldReturnBadRequestWhenAlreadyInactive() throws Exception {
        when(chefOrchestrationService.deactivateChef(1L))
                .thenThrow(new BadRequestException("Chef with id: 1 is already inactive", Map.of()));

        mockMvc.perform(patch("/api/chef/1/deactivate"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deactivateChef_shouldReturnNotFoundWhenChefDoesNotExist() throws Exception {
        when(chefOrchestrationService.deactivateChef(999L))
                .thenThrow(new ChefNotFoundException("Chef with id: 999 not found", Map.of()));

        mockMvc.perform(patch("/api/chef/999/deactivate"))
                .andExpect(status().isNotFound());
    }

    /* ADDRESS */

    @Test
    void getAddressById_shouldReturnAddress() throws Exception {
        Chef chef = new Chef();
        chef.setId(1L);
        Address address = new Address();
        address.setChef(chef);
        HomeVibeDto vibe = new HomeVibeDto("Cosy flat with a view", Genre.JAZZ, true, false, true);
        AddressResponseDto response = new AddressResponseDto(5L, "Storgata 1", "0150", "Oslo", 1L, vibe);

        when(chefOrchestrationService.getAddressById(5L)).thenReturn(address);
        when(dtoMapper.toAddressResponseDto(address, 1L)).thenReturn(response);

        mockMvc.perform(get("/api/chef/address/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.street").value("Storgata 1"))
                .andExpect(jsonPath("$.postalCode").value("0150"))
                .andExpect(jsonPath("$.city").value("Oslo"))
                .andExpect(jsonPath("$.chefId").value(1))
                .andExpect(jsonPath("$.homeVibe.description").value("Cosy flat with a view"))
                .andExpect(jsonPath("$.homeVibe.music").value("JAZZ"));
    }

    @Test
    void getAddressById_shouldReturnNotFoundWhenAddressDoesNotExist() throws Exception {
        when(chefOrchestrationService.getAddressById(999L))
                .thenThrow(new AddressNotFoundException("Address with id: 999 not found", Map.of()));

        mockMvc.perform(get("/api/chef/address/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getActiveAddressByChefId_shouldReturnActiveAddresses() throws Exception {
        List<Address> addresses = List.of(new Address(), new Address());
        HomeVibeDto vibe = new HomeVibeDto("Cosy flat with a view", Genre.JAZZ, true, false, true);
        HomeVibeDto notAVibe = new HomeVibeDto("Beer cans everywhere", Genre.NONE, false, false, false);
        List<ActiveAddressDto> response = List.of(
                new ActiveAddressDto(1L, "Storgata 1", "0150", "Oslo", true, 1L, vibe),
                new ActiveAddressDto(2L, "Lillegata 2", "5003", "Bergen", true, 1L, notAVibe));
        when(chefOrchestrationService.getActiveAddresses(1L)).thenReturn(addresses);
        when(dtoMapper.toActiveAddressDtoList(addresses, 1L)).thenReturn(response);

        mockMvc.perform(get("/api/chef/address/active/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Oslo"))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[1].city").value("Bergen"))
                .andExpect(jsonPath("$[0].homeVibe.music").value("JAZZ"))
                .andExpect(jsonPath("$[1].homeVibe.music").value("NONE"));
    }

    @Test
    void saveChefAddress_shouldReturnAddressList() throws Exception {
        HomeVibeDto vibe = new HomeVibeDto("Cosy flat with a view", Genre.JAZZ, true, false, true);
        SaveAddressDto dto = new SaveAddressDto("Storgata 1", "0150", "Oslo", vibe);
        List<Address> addresses = List.of(new Address());
        List<AddressResponseDto> response = List.of(
                new AddressResponseDto(1L, "Storgata 1", "0150", "Oslo", 1L, vibe));

        when(chefOrchestrationService.saveAddressToChef(1L, dto)).thenReturn(addresses);
        when(dtoMapper.toAddressResponseDtoList(addresses, 1L)).thenReturn(response);

        mockMvc.perform(post("/api/chef/address/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].street").value("Storgata 1"))
                .andExpect(jsonPath("$[0].chefId").value(1))
                .andExpect(jsonPath("$[0].homeVibe.music").value("JAZZ"))
                .andExpect(jsonPath("$[0].homeVibe.familyFriendly").value(true));
    }
    @Test
    void saveChefAddress_shouldReturnBadRequestWhenPostalCodeIsInvalid() throws Exception {
        SaveAddressDto dto = new SaveAddressDto("Storgata 1", "01", "Oslo", null);
        when(chefOrchestrationService.saveAddressToChef(eq(1L), any(SaveAddressDto.class)))
                .thenThrow(new BadRequestException("Your postal code must contain 4 digits", Map.of()));

        mockMvc.perform(post("/api/chef/address/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void editChefAddress_shouldReturnUpdatedAddress() throws Exception {
        HomeVibeDto vibe = new HomeVibeDto("Cosy flat with a view", Genre.JAZZ, true, false, true);
        SaveAddressDto dto = new SaveAddressDto("Lillegata 2", "5003", "Bergen", vibe);
        Address address = new Address();
        AddressResponseDto response = new AddressResponseDto(5L, "Lillegata 2", "5003", "Bergen", 1L, vibe);

        when(chefOrchestrationService.editChefAddress(1L, 5L, dto)).thenReturn(address);
        when(dtoMapper.toAddressResponseDto(address, 1L)).thenReturn(response);

        mockMvc.perform(patch("/api/chef/address/1/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("Lillegata 2"))
                .andExpect(jsonPath("$.city").value("Bergen"))
                .andExpect(jsonPath("$.homeVibe.description").value("Cosy flat with a view"))
                .andExpect(jsonPath("$.homeVibe.music").value("JAZZ"))
                .andExpect(jsonPath("$.homeVibe.pets").value(true))
                .andExpect(jsonPath("$.homeVibe.outdoor").value(false))
                .andExpect(jsonPath("$.homeVibe.familyFriendly").value(true));
    }
    @Test
    void editChefAddress_shouldReturnNotFoundWhenAddressDoesNotExist() throws Exception {
        SaveAddressDto dto = new SaveAddressDto("Lillegata 2", "5003", "Bergen", null);
        when(chefOrchestrationService.editChefAddress(eq(1L), eq(999L), any(SaveAddressDto.class)))
                .thenThrow(new AddressNotFoundException("Address with id: 999 not found for chef with id: 1", Map.of()));

        mockMvc.perform(patch("/api/chef/address/1/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void editChefAddress_shouldReturnBadRequestWhenAddressIsInactive() throws Exception {
        SaveAddressDto dto = new SaveAddressDto("Lillegata 2", "5003", "Bergen", null);
        when(chefOrchestrationService.editChefAddress(eq(1L), eq(5L), any(SaveAddressDto.class)))
                .thenThrow(new BadRequestException("Address with id: 5 is already inactive", Map.of()));

        mockMvc.perform(patch("/api/chef/address/1/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deactivateAddress_shouldReturnDeactivatedAddress() throws Exception {
        Address address = new Address();
        HomeVibeDto anonymisedVibe = new HomeVibeDto(null, Genre.JAZZ, true, false, true);
        AddressResponseDto response = new AddressResponseDto(5L, null, null, null, 1L, anonymisedVibe);

        when(chefOrchestrationService.deactivateAddress(1L, 5L)).thenReturn(address);
        when(dtoMapper.toAddressResponseDto(address, 1L)).thenReturn(response);

        mockMvc.perform(patch("/api/chef/address/1/5/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.street").doesNotExist())
                .andExpect(jsonPath("$.homeVibe.description").doesNotExist())
                .andExpect(jsonPath("$.homeVibe.music").value("JAZZ"));
    }

    @Test
    void deactivateAddress_shouldReturnBadRequestWhenAlreadyInactive() throws Exception {
        when(chefOrchestrationService.deactivateAddress(1L, 5L))
                .thenThrow(new BadRequestException("Address with id: 5 is already inactive", Map.of()));

        mockMvc.perform(patch("/api/chef/address/1/5/deactivate"))
                .andExpect(status().isBadRequest());
    }
}
