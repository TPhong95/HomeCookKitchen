package org.example.purchase.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.purchase.dtos.ListingInfoDto;
import org.example.purchase.purchase.Purchase;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PurchaseEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_event_seq")
    @SequenceGenerator(name = "purchase_event_seq", sequenceName = "purchase_event_seq", allocationSize = 1)
    private Long id;

    @Embedded
    private ListingInfoDto listingInfoDto;

    @ManyToOne()
    @JoinColumn(name = "purchase_id")
    @JsonIgnore
    private Purchase purchase;

    private Long quantity;
}
