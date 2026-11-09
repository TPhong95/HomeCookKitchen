package user.customer.datainit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import user.customer.Customer;
import user.customer.CustomerDTO;
import user.customer.CustomerRepo;
import user.customer.CustomerService;

@Component
public class CustomerDataInitializer implements CommandLineRunner {

    private final CustomerRepo repo;
    private final CustomerService service;

    public CustomerDataInitializer(CustomerRepo repo, CustomerService service) {
        this.repo = repo;
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repo.count() == 0) {

            CustomerDTO customer1 = new CustomerDTO("John", "John@gmail.com");
            Customer savedCustomer1 = service.createCustomer(customer1);

            CustomerDTO customer2 = new CustomerDTO("Bob", "Bob@gmail.com");
            Customer savedCustomer2 = service.createCustomer(customer2);

            CustomerDTO customer3 = new CustomerDTO("Mannen", "Mannen@gmail.com");
            Customer savedCustomer3 = service.createCustomer(customer3);

            CustomerDTO customer4 = new CustomerDTO("The Greatest Customer", "TheBest@gmail.com");
            Customer savedCustomer4 = service.createCustomer(customer4);

            CustomerDTO customer5 = new CustomerDTO("Gramps", "Gramps@gmail.com");
            Customer savedCustomer5 = service.createCustomer(customer5);

            System.out.println(">>> " + repo.count() + " customer were added to the customer database! <<<");
        }
    }
}
