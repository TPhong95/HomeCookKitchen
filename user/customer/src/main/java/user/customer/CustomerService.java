package user.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import user.customer.exception.BadRequestException;
import user.customer.exception.CustomerNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;

    public List<Customer> findAllCustomers() {
        var customers = customerRepo.findAll();
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("No customers found");
        }
        return customers;
    }

    public Customer findCustomerById(int id) {
        Customer customer = customerRepo.findById(id).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException("Could not find customer with id: " + id);
        }
        return customer;
    }

    public Customer saveCustomer(CustomerDTO DTO) {
        if (DTO.name() == null || DTO.name().isEmpty()) {
            throw new BadRequestException("Name is needed");
        }
        if (DTO.email() == null || DTO.email().isEmpty()) {
            throw new BadRequestException("Email is needed");
        }

        Customer newCustomer = new Customer(DTO.name(), DTO.email());
        return customerRepo.save(newCustomer);
    }

    public Customer updateCustomer(int id, CustomerDTO DTO) {
        Customer customerToUpdate = findCustomerById(id);
        if (DTO.name() != null && !DTO.name().isEmpty()) {
            customerToUpdate.setName(DTO.name());
        }
        if (DTO.email() != null && !DTO.email().isEmpty()) {
            customerToUpdate.setName(DTO.email());
        }
        if ((DTO.name() == null || DTO.name().isEmpty()) || (DTO.email() == null || DTO.email().isEmpty())) {
            throw new BadRequestException("No information to update customer was provided");
        }
        return customerRepo.save(customerToUpdate);
    }
}
