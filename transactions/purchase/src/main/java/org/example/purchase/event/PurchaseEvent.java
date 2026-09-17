package org.example.purchase.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.purchase.productlisting.ProductListingDto;
import org.example.purchase.purchase.Purchase;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PurchaseEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_event_seq")
    @SequenceGenerator(name = "purchase_event_seq", sequenceName = "purchase_event_seq", allocationSize = 1)
    private int id;

    private ProductListingDto productListing;

    @ManyToOne()
    @JoinColumn(name = "purchase_id")
    @JsonIgnore
    private Purchase purchase;

    private int quantity;
}
