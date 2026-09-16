package org.example.purchase.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class purchaseEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_event_seq")
    @SequenceGenerator(name = "purchase_event_seq", sequenceName = "purchase_event_seq", allocationSize = 1)
    private Long id;
    private Long productListId;
    private Long purchaseId;
    private int quantity;




}
