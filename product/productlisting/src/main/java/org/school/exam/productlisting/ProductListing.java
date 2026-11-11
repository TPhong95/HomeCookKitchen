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

    private Long recipeId;
    private String recipeName;
    private String pictureUrl;
    private String recipeDescription;
    private Long addressId;
    private String compositeAddress;
    private Long chefId;
    private String chefName;
    private Boolean active;
    private int quantityOffered;
    private int quantityPurchased;
    private float unitPrice;
    private LocalDateTime createdAtTime;
    private LocalDateTime purchaseDeadline;
    private LocalDateTime readyBy;

    public ProductListing(Long productListingId, Long recipeId, String recipeName,
                          String pictureUrl, String recipeDescription, Long addressId,
                          String compositeAddress, Long chefId, String chefName,
                          Boolean active, int quantityOffered, int quantityPurchased,
                          float unitPrice, LocalDateTime createdAtTime, LocalDateTime purchaseDeadline,
                          LocalDateTime readyBy) {
        this.productListingId = productListingId;
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.pictureUrl = pictureUrl;
        this.recipeDescription = recipeDescription;
        this.addressId = addressId;
        this.compositeAddress = compositeAddress;
        this.chefId = chefId;
        this.chefName = chefName;
        this.active = active;
        this.quantityOffered = quantityOffered;
        this.quantityPurchased = quantityPurchased;
        this.unitPrice = unitPrice;
        this.createdAtTime = createdAtTime;
        this.purchaseDeadline = purchaseDeadline;
        this.readyBy = readyBy;
    }
}
