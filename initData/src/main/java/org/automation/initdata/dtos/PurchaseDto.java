package org.automation.initdata.dtos;

import java.util.List;

public record PurchaseDto(
        int customerId,
        List<PurchaseEventDto> purchaseEvents
) { }
