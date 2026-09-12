package org.example.purchase.purchase;

import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class Purchase {
    @Id
    private int id;
    private int customerId;
    private Long totalPrice;
    private int totalProducts;
    private LocalDateTime timestamp;


}
