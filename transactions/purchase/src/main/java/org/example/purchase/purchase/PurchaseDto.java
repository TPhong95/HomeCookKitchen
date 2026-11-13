package org.example.purchase.purchase;

import org.example.purchase.event.PurchaseEventDto;

import java.util.List;

public record PurchaseDto(
        Long customerId,
        List<PurchaseEventDto> purchaseEvents
) { }
