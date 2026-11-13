package org.example.purchase.purchase;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.purchase.event.PurchaseEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_seq")
    @SequenceGenerator(name = "purchase_seq", sequenceName = "purchase_seq", allocationSize = 1)
    private Long id;
    private Long customerId;
    private float totalPrice;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseEvent> purchaseEvent = new ArrayList<>();

    private LocalDateTime timestamp;

    public Purchase() {
        this.timestamp = LocalDateTime.now();
    }

}
