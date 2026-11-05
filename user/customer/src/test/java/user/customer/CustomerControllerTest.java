package user.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import user.customer.exception.BadRequestException.BadRequestException;
import user.customer.exception.NotFoundException.CustomerNotFoundException;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CustomerService customerService;

    @Test
    void getAllCustomers_shouldReturnListOfCustomers() throws Exception {
        Customer customer = new Customer("Alice", "alice@mail.com");
        when(customerService.findAllCustomers()).thenReturn(List.of(customer));

        mockMvc.perform(get("/api/customer/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[0].email").value("alice@mail.com"));
    }

    @Test
    void getCustomerById_shouldReturnCustomer() throws Exception {
        Customer customer = new Customer("Bob", "bob@mail.com");
        when(customerService.findCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(get("/api/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bob"))
                .andExpect(jsonPath("$.email").value("bob@mail.com"));
    }

    @Test
    void createCustomer_shouldReturnCreatedCustomer() throws Exception {
        CustomerDTO dto = new CustomerDTO("Charlie", "charlie@mail.com");
        Customer customer = new Customer("Charlie", "charlie@mail.com");

        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(customer);

        mockMvc.perform(post("/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Charlie"))
                .andExpect(jsonPath("$.email").value("charlie@mail.com"));
    }

    @Test
    void updateCustomer_shouldReturnUpdatedCustomer() throws Exception {
        CustomerDTO dto = new CustomerDTO("Updated Name", "updated@mail.com");
        Customer customer = new Customer("Updated Name", "updated@mail.com");

        when(customerService.updateCustomer(eq(1L), any(CustomerDTO.class))).thenReturn(customer);

        mockMvc.perform(patch("/api/customer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("updated@mail.com"));
    }

    @Test
    void getCustomerById_shouldReturnNotFoundWhenCustomerDoesNotExist() throws Exception {
        when(customerService.findCustomerById(999L)).thenThrow(new CustomerNotFoundException("Could not find customer with id: 999", Map.of()));
        mockMvc.perform(get("/api/customer/999"))
                .andExpect(status().isNotFound()); // Adjust based on your GlobalExceptionHandler mapping
    }

    @Test
    void createCustomer_shouldReturnBadRequestWhenNameIsMissing() throws Exception {
        CustomerDTO dto = new CustomerDTO("", "test@mail.com");
        when(customerService.createCustomer(any(CustomerDTO.class)))
                .thenThrow(new BadRequestException("Name is needed", Map.of()));
        mockMvc.perform(post("/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}