package org.example.purchase.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseEventRepo extends JpaRepository<PurchaseEvent,Integer> {
}
