package org.automation.initdata.client;

import org.automation.initdata.dtos.CustomerDTO;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CustomerClient {
    private final String url = "http://gateway:8100/api/customer";
    private final RestClient restClient;

    public CustomerClient() {
        this.restClient = RestClient.create();
    }

    public void createCustomer(CustomerDTO customerDto) {
        restClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(customerDto)
                .retrieve()
                .toBodilessEntity();
    }


}
