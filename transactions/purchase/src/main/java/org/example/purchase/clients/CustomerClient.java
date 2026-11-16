package org.example.purchase.clients;


import org.example.purchase.dtos.CustomerDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CustomerClient {
    private final String url;
    private final RestClient restClient;

    public CustomerClient(
            //@Value("${deck.service.url}") String url
    ) {
        this.url = "http://gateway:8100/api/";

        this.restClient = RestClient.create();
        //this.url = url;
    }

    public CustomerDto getCustomerInfoById(Long id) {
        String callUrl = this.url + "customer/" + id;
        CustomerDto customerDto = restClient
                .get()
                .uri(callUrl)
                .retrieve()
                .body(CustomerDto.class);
        return customerDto;
    }
}
