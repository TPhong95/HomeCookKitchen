package org.example.purchase.purchase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.purchase.event.PurchaseEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_seq")
    @SequenceGenerator(name = "purchase_seq", sequenceName = "purchase_seq", allocationSize = 1)
    private int id;
    private int customerId;
    private int totalPrice;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseEvent> purchaseEvent = new ArrayList<>();

    private LocalDateTime timestamp;

    public Purchase(int customerId, List<PurchaseEvent> purchaseEvent) {
        this.customerId = customerId;
        this.purchaseEvent = purchaseEvent;
        this.timestamp = LocalDateTime.now();
    }

}
