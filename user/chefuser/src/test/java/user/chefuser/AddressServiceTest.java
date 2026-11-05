package user.chefuser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.chefuser.chef.Address;
import user.chefuser.chef.AddressRepo;
import user.chefuser.chef.AddressService;
import user.chefuser.chef.Chef;
import user.chefuser.chef.dtos.SaveAddressDto;
import user.chefuser.chef.exception.badrequest.BadRequestException;
import user.chefuser.chef.exception.notfound.AddressNotFoundException;
import user.chefuser.chef.mappers.DtoMapper;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    AddressRepo addressRepo;

    @Mock
    DtoMapper dtoMapper;

    @InjectMocks
    AddressService addressService;

    /*
    1. Create address object happy path
     */
    @Test
    void createAddressObject_shouldCreateAddress() {
        Chef chef = new Chef();
        chef.setId(1L);
        SaveAddressDto dto = new SaveAddressDto("Storgata 1", "0150", "Oslo");

        Address mapped = new Address();
        mapped.setStreet("Storgata 1");
        mapped.setPostalCode("0150");
        mapped.setCity("Oslo");
        mapped.setChef(chef);
        when(dtoMapper.toAddressEntity(dto, chef)).thenReturn(mapped);

        Address res = addressService.createAddressObject(chef, dto);
        assertNotNull(res);
        assertEquals("Storgata 1", res.getStreet());
        assertEquals("0150", res.getPostalCode());
        assertEquals("Oslo", res.getCity());
        assertEquals(1, res.getChef().getId());
    }

    /*
    1. Update address happy path
    2. Address not found
    3. Cannot update inactive address
     */
    @Test
    void updateAddress_shouldUpdateAddress() {
        Long chefId = 1L;
        Long addressId = 5L;
        Address address = new Address();
        address.setId(addressId);
        address.setStreet("Storgata 1");
        address.setPostalCode("0150");
        address.setCity("Oslo");

        when(addressRepo.findByIdAndChefId(addressId, chefId)).thenReturn(Optional.of(address));
        when(addressRepo.save(any(Address.class))).thenAnswer(i -> i.getArgument(0));

        SaveAddressDto dto = new SaveAddressDto("Lillegata 2", "5003", "Bergen");

        Address res = addressService.updateAddress(chefId, addressId, dto);
        assertNotNull(res);
        assertEquals("Lillegata 2", res.getStreet());
        assertEquals("5003", res.getPostalCode());
        assertEquals("Bergen", res.getCity());
        verify(addressRepo).save(res);
    }

    @Test
    void updateAddress_shouldThrowNotFound() {
        when(addressRepo.findByIdAndChefId(100L, 1L)).thenReturn(Optional.empty());
        SaveAddressDto dto = new SaveAddressDto("Lillegata 2", "5003", "Bergen");

        AddressNotFoundException e = assertThrows(AddressNotFoundException.class,
                () -> addressService.updateAddress(1L, 100L, dto));
        assertEquals("Address with id: 100 not found for chef with id: 1", e.getMessage());
    }

    @Test
    void updateAddress_forbidsUpdateInactiveAddress() {
        Address address = new Address();
        address.setId(5L);
        address.setActive(false);
        when(addressRepo.findByIdAndChefId(5L, 1L)).thenReturn(Optional.of(address));
        SaveAddressDto dto = new SaveAddressDto("Lillegata 2", "5003", "Bergen");

        BadRequestException e = assertThrows(BadRequestException.class,
                () -> addressService.updateAddress(1L, 5L, dto));
        assertEquals("Address with id: 5 is already inactive", e.getMessage());
    }

    /*
    1. Deactivate address happy path
    2. Address not found
    3. Inactive address cannot be deactivated
     */
    @Test
    void deactivateAddress_shouldDeactivateAddress() {
        Long chefId = 1L;
        Long addressId = 5L;
        Address address = new Address();
        address.setId(addressId);
        address.setStreet("Storgata 1");
        address.setPostalCode("0150");
        address.setCity("Oslo");
        assertEquals(true, address.getActive());

        when(addressRepo.findByIdAndChefId(addressId, chefId)).thenReturn(Optional.of(address));
        when(addressRepo.save(any(Address.class))).thenAnswer(i -> i.getArgument(0));

        Address res = addressService.deactivateAddress(chefId, addressId);
        assertNotNull(res);
        assertEquals(false, res.getActive());
        verify(addressRepo).save(res);
    }

    @Test
    void deactivateAddress_shouldAnonymizeData() {
        Long chefId = 1L;
        Long addressId = 5L;
        Address address = new Address();
        address.setId(addressId);
        address.setStreet("Storgata 1");
        address.setPostalCode("0150");
        address.setCity("Oslo");
        assertEquals(true, address.getActive());

        when(addressRepo.findByIdAndChefId(addressId, chefId)).thenReturn(Optional.of(address));
        when(addressRepo.save(any(Address.class))).thenAnswer(i -> i.getArgument(0));

        Address res = addressService.deactivateAddress(chefId, addressId);
        assertNotNull(res);
        assertEquals(false, res.getActive());
        assertEquals(null, res.getStreet());
        assertEquals(null, res.getStreet());
        assertEquals(null, res.getPostalCode());
        verify(addressRepo).save(res);
    }

    @Test
    void deactivateAddress_shouldThrowNotFound() {
        when(addressRepo.findByIdAndChefId(100L, 1L)).thenReturn(Optional.empty());

        AddressNotFoundException e = assertThrows(AddressNotFoundException.class,
                () -> addressService.deactivateAddress(1L, 100L));
        assertEquals("Address with id: 100 not found for chef with id: 1", e.getMessage());
    }

    @Test
    void deactivateAddress_forbidsDeactivateInactiveAddress() {
        Address address = new Address();
        address.setId(5L);
        address.setActive(false);
        when(addressRepo.findByIdAndChefId(5L, 1L)).thenReturn(Optional.of(address));

        BadRequestException e = assertThrows(BadRequestException.class,
                () -> addressService.deactivateAddress(1L, 5L));
        assertEquals("Address with id: 5 is already inactive", e.getMessage());
    }

    /*
    1. Get address by id happy path
    2. Address not found
     */
    @Test
    void getAddressById_shouldReturnAddress() {
        Address address = new Address();
        address.setId(5L);
        address.setStreet("Storgata 1");
        when(addressRepo.findById(5L)).thenReturn(Optional.of(address));

        Address res = addressService.getAddressById(5L);
        assertNotNull(res);
        assertEquals(5, res.getId());
        assertEquals("Storgata 1", res.getStreet());
    }

    @Test
    void getAddressById_shouldThrowNotFound() {
        when(addressRepo.findById(100L)).thenReturn(Optional.empty());

        AddressNotFoundException e = assertThrows(AddressNotFoundException.class,
                () -> addressService.getAddressById(100L));
        assertEquals("Address with id: 100 not found", e.getMessage());
    }

    /*
    1. Get active addresses happy path
    2. Does not return inactive addresses
    3. Returns empty list when chef has no active addresses
     */
    @Test
    void getActiveAddressesByChef_shouldReturnAddresses() {
        Address address1 = new Address();
        address1.setId(1L);
        Address address2 = new Address();
        address2.setId(2L);
        when(addressRepo.findActiveByChefId(1L)).thenReturn(Arrays.asList(address1, address2));

        var res = addressService.getActiveAddressesByChef(1L);
        assertEquals(2, res.size());
    }

    @Test
    void getActiveAddressesByChef_doesNotReturnInactive() {
        Address active = new Address();
        active.setId(1L);
        Address inactive = new Address();
        inactive.setId(2L);
        inactive.setActive(false);
        when(addressRepo.findActiveByChefId(1L)).thenReturn(Arrays.asList(active));

        var res = addressService.getActiveAddressesByChef(1L);
        assertEquals(1, res.size());
        assertEquals(1, res.get(0).getId());
    }

    @Test
    void getActiveAddressesByChef_shouldReturnEmptyList() {
        when(addressRepo.findActiveByChefId(1L)).thenReturn(List.of());

        var res = addressService.getActiveAddressesByChef(1L);
        assertTrue(res.isEmpty());
    }
}