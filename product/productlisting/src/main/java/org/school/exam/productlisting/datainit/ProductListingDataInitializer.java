package org.school.exam.productlisting.datainit;

import org.school.exam.productlisting.ProductListing;
import org.school.exam.productlisting.ProductListingOrchestrationService;
import org.school.exam.productlisting.ProductListingRepo;
import org.school.exam.productlisting.ProductListingService;
import org.school.exam.productlisting.dto.CreateProductListingDTO;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductListingDataInitializer implements CommandLineRunner {

    private final ProductListingRepo repo;
    private final ProductListingOrchestrationService orchestrationService;

    public ProductListingDataInitializer(ProductListingRepo repo, ProductListingService service, ProductListingOrchestrationService orchestrationService) {
        this.repo = repo;
        this.orchestrationService = orchestrationService;
    }


    @Override
    public void run(String... args) throws Exception {
        if (repo.count() == 0) {

            CreateProductListingDTO productListing1 = new CreateProductListingDTO(1L, 1L, true, 20, 14.99f, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
            ProductListing savedProductListing1 = orchestrationService.createProductListing(productListing1);

            CreateProductListingDTO productListing2 = new CreateProductListingDTO(2L, 2L, true, 30, 20.00f, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
            ProductListing savedProductListing2 = orchestrationService.createProductListing(productListing2);

            CreateProductListingDTO productListing3 = new CreateProductListingDTO(3L, 3L, true, 15, 4.99f, LocalDateTime.now().plusMinutes(1), LocalDateTime.now().plusHours(2));
            ProductListing savedProductListing3 = orchestrationService.createProductListing(productListing3);

            CreateProductListingDTO productListing4 = new CreateProductListingDTO(4L, 4L, true, 22, 24.99f, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
            ProductListing savedProductListing4 = orchestrationService.createProductListing(productListing4);

            CreateProductListingDTO productListing5 = new CreateProductListingDTO(5L, 1L, true, 33, 12.99f, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
            ProductListing savedProductListing5 = orchestrationService.createProductListing(productListing5);

            System.out.println(">>> " + repo.count() + " productlisting were added to the productlisting database! <<<");
        }
    }
}
