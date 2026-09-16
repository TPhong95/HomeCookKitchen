package user.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;

    public List<Customer> findAllCustomers() {
        var customers = customerRepo.findAll();
        /*
        if(customers.isEmpty()) {
            // CREATE CUSTOM EXCEPTION
        }
         */
        return customers;
    }

    public Customer saveCustomer(CustomerDTO DTO) {
        Customer newCustomer = new Customer(DTO.name(), DTO.email());
        return customerRepo.save(newCustomer);
    }
}
