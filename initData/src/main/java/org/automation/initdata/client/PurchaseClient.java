package org.automation.initdata.client;

import org.automation.initdata.dtos.CreateRecipeDTO;
import org.automation.initdata.dtos.PurchaseDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PurchaseClient {
    private final String url = "http://gateway:8100/api/purchase";
    private final RestClient restClient;

    public PurchaseClient() {
        this.restClient = RestClient.create();
    }

    public void createPurchase(PurchaseDto purchaseDto) {
        restClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(purchaseDto)
                .retrieve()
                .toBodilessEntity();
    }


}
