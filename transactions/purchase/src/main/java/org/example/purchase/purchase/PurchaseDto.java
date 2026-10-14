package org.example.purchase.purchase;

import org.example.purchase.event.PurchaseEventDto;

import java.util.List;

public record PurchaseDto(
        int customerId,
        List<PurchaseEventDto> purchaseEvents
) { }
