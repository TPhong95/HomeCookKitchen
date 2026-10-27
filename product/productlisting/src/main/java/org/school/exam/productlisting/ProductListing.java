package org.school.exam.productlisting;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductListing {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_listing_gen")
    @SequenceGenerator(name = "prod_listing_gen", sequenceName = "prod_listing_seq", allocationSize = 1)
    @Column(name = "pl_id")
    private Long productListingId;

    private int recipeId;
    private int chefId;
    private Boolean active;
    private int quantity;
    private float unitPrice;
    private LocalDateTime createdAtTime;
    private LocalDateTime purchaseDeadline;
    private LocalDateTime readyBy;


    public ProductListing(
            int recipeId, int chefId, boolean active, int quantity, float unitPrice,
            LocalDateTime purchaseDeadline, LocalDateTime readyBy)
    {
        this.recipeId = recipeId;
        this.chefId = chefId;
        this.active = true;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.purchaseDeadline = purchaseDeadline;
        this.readyBy = readyBy;

        this.createdAtTime = LocalDateTime.now();
    }
}
