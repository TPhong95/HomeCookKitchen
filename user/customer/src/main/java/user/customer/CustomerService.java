package user.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import user.customer.exception.BadRequestException.BadRequestException;
import user.customer.exception.NotFoundException.CustomerNotFoundException;
import user.customer.exception.DuplicateEmailException;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;

    public List<Customer> findAllCustomers() {
        var customers = customerRepo.findAll();
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("No customers found", Map.of());
        }
        return customers;
    }

    public Customer findCustomerById(Long id) {
        Customer customer = customerRepo.findById(id).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException("Could not find customer: ", Map.of("entityType", "Customer", "entityId", id));
        }
        return customer;
    }

    public Customer createCustomer(CustomerDTO DTO) {
        if (DTO.name() == null || DTO.name().isEmpty()) {
            throw new BadRequestException("Name is needed", Map.of());
        }
        if (DTO.email() == null || DTO.email().isEmpty()) {
            throw new BadRequestException("Email is needed", Map.of());
        }
        validateUniqueEmail(DTO.email());

        Customer newCustomer = new Customer(DTO.name(), DTO.email());
        return customerRepo.save(newCustomer);
    }

    public Customer updateCustomer(Long id, CustomerDTO DTO) {
        Customer customerToUpdate = findCustomerById(id);
        if (DTO.name() != null && !DTO.name().isEmpty()) {
            customerToUpdate.setName(DTO.name());
        }
        if (DTO.email() != null && !DTO.email().isEmpty()) {
            customerToUpdate.setEmail(DTO.email());
        }
        validateUniqueEmail(DTO.email());
        if ((DTO.name() == null || DTO.name().isEmpty()) || (DTO.email() == null || DTO.email().isEmpty())) {
            throw new BadRequestException("No information to update customer was provided", Map.of());
        }
        return customerRepo.save(customerToUpdate);
    }

    public void validateUniqueEmail(String email) {
        if (customerRepo.existsByEmail(email)) {
            throw new DuplicateEmailException("This email is already in use");
        }
    }
}
