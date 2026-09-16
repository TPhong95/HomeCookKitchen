package user.customer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDTO customer) {
        return ResponseEntity.ok(customerService.saveCustomer(customer));
    }
}