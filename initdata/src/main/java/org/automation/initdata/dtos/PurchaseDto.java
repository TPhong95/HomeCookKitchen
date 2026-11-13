package org.automation.initdata.dtos;

import java.util.List;

public record PurchaseDto(
        Long customerId,
        List<PurchaseEventDto> purchaseEvents
) { }
