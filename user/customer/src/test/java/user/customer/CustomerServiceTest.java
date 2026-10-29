package user.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.customer.exception.BadRequestException;
import user.customer.exception.CustomerNotFoundException;
import user.customer.exception.DuplicateEmailException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    CustomerRepo customerRepo;

    @InjectMocks
    CustomerService customerService;

    @Test
    void findCustomerById_shouldThrowCustomerNotFoundException() {
        when(customerRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerService.findCustomerById(999));
    }

    @Test
    void patchCustomerName_shouldUpdateCorrectColumns() {
        CustomerDTO dto = new CustomerDTO("New Name", "newemail@mail.com");
        Customer existingCustomer = new Customer();
        existingCustomer.setName("Old Name");
        existingCustomer.setEmail("oldemail@mail.com");

        when(customerRepo.findById(1)).thenReturn(Optional.of(existingCustomer));
        when(customerRepo.existsByEmail("newemail@mail.com")).thenReturn(false);
        when(customerRepo.save(any(Customer.class))).thenAnswer(i -> i.getArgument(0));

        Customer updatedCustomer = customerService.updateCustomer(1, dto);

        assertEquals("New Name", updatedCustomer.getName(), "Name should match update");
        assertEquals("newemail@mail.com", updatedCustomer.getEmail(), "Email should match update");
    }

    @Test
    void findAllCustomers_shouldReturnList() {
        List<Customer> customers = List.of(new Customer("Gene", "parmesan@mail.com"));
        when(customerRepo.findAll()).thenReturn(customers);

        List<Customer> result = customerService.findAllCustomers();

        assertEquals(1, result.size());
        assertEquals("Gene", result.get(0).getName());
    }

    @Test
    void findAllCustomers_shouldThrowExceptionWhenEmpty() {
        when(customerRepo.findAll()).thenReturn(List.of());

        assertThrows(CustomerNotFoundException.class, () -> customerService.findAllCustomers());
    }

    @Test
    void createCustomer_shouldThrowExceptionWhenNameIsNull() {
        CustomerDTO dto = new CustomerDTO("", "test@mail.com");
        assertThrows(BadRequestException.class, () -> customerService.createCustomer(dto));
    }

    @Test
    void createCustomer_shouldThrowExceptionWhenEmailExists() {
        CustomerDTO dto = new CustomerDTO("Test Name", "existing@mail.com");
        when(customerRepo.existsByEmail("existing@mail.com")).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> customerService.createCustomer(dto));
    }

    @Test
    void createCustomer_shouldSaveAndReturnCustomer() {
        CustomerDTO dto = new CustomerDTO("Test Name", "test@mail.com");
        Customer savedCustomer = new Customer("Test Name", "test@mail.com");

        when(customerRepo.existsByEmail("test@mail.com")).thenReturn(false);
        when(customerRepo.save(any(Customer.class))).thenReturn(savedCustomer);

        Customer result = customerService.createCustomer(dto);

        assertEquals("Test Name", result.getName());
        assertEquals("test@mail.com", result.getEmail());
    }

    @Test
    void updateCustomer_shouldThrowExceptionWhenCustomerNotFound() {
        CustomerDTO dto = new CustomerDTO("Name", "email@mail.com");
        when(customerRepo.findById(999)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(999, dto));
    }

    @Test
    void updateCustomer_shouldThrowBadRequestWhenInfoIsMissing() {
        CustomerDTO dto = new CustomerDTO("", "email@mail.com"); // Empty name
        Customer existingCustomer = new Customer("Old", "old@mail.com");

        when(customerRepo.findById(1)).thenReturn(Optional.of(existingCustomer));

        assertThrows(BadRequestException.class, () -> customerService.updateCustomer(1, dto));
    }
}
