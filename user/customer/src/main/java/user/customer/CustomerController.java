package user.customer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.customer.dto.CustomerNotificationCreationDto;
import user.customer.dto.CustomerNotificationResponseDto;
import user.customer.mapper.DTOMapper;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final DTOMapper dtoMapper;
    private final CustomerOrchestrationService customerOrchestrationService;

    public CustomerController(CustomerService customerService, DTOMapper dtoMapper, CustomerOrchestrationService customerOrchestrationService) {
        this.customerService = customerService;
        this.dtoMapper = dtoMapper;
        this.customerOrchestrationService = customerOrchestrationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }

    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDTO customer) {
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO DTO) {
        return ResponseEntity.ok(customerService.updateCustomer(id, DTO));
    }

    // Customer Notification //

    @PostMapping("/notification")
    public ResponseEntity<CustomerNotificationResponseDto> createNotification(@RequestBody CustomerNotificationCreationDto inputDTO) {
        CustomerNotification savedNotification = customerOrchestrationService.createCustomerNotification(inputDTO);
        CustomerNotificationResponseDto responseDto = dtoMapper.toCustomerNotificationResponseDto(savedNotification);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/notification/{id}")
    public ResponseEntity<CustomerNotificationResponseDto> getNotificationById(@PathVariable Long id) {
        CustomerNotification result = customerOrchestrationService.getNotificationById(id);
        CustomerNotificationResponseDto DTOMappedResult = dtoMapper.toCustomerNotificationResponseDto(result);
        return ResponseEntity.ok(DTOMappedResult);
    }

    @GetMapping("/notification/customerId/{id}")
    public ResponseEntity<List<CustomerNotificationResponseDto>> getAllNotificationsByCustomerId(@PathVariable Long id) {
        List<CustomerNotification> result = customerOrchestrationService.getAllNotificationsByCustomerId(id);
        List<CustomerNotificationResponseDto> DTOMappedResult = new ArrayList<CustomerNotificationResponseDto>();
        result.forEach(customerNotification -> {
            DTOMappedResult.add(dtoMapper.toCustomerNotificationResponseDto(customerNotification));
        });
        return ResponseEntity.ok(DTOMappedResult);
    }

    @PatchMapping("/notification/{id}")
    public ResponseEntity<CustomerNotificationResponseDto> updateNotificationReadStatusByCustomerId(@PathVariable Long id) {
        CustomerNotification updatedNotification = customerOrchestrationService.updateNotificationReadStatus(id);
        return ResponseEntity.ok(dtoMapper.toCustomerNotificationResponseDto(updatedNotification));
    }
}