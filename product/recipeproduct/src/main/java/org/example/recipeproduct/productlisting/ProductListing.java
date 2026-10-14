package org.example.recipeproduct.productlisting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.recipeproduct.recipe.Recipe;

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

    @ManyToOne
    @JsonIgnoreProperties("productListing")
    @JoinColumn(name = "r_id")
    private Recipe recipeId;

    private int quantity;
    private float unitPrice;

    public ProductListing(Recipe recipe, int quantity, float unitPrice) {
        this.recipeId = recipe;
        this.quantity = quantity;
        this.unitPrice = (int) unitPrice;
    }
}
