package org.automation.initdata.client;

import org.automation.initdata.dtos.AddressIdDto;
import org.automation.initdata.dtos.ChefNotificationCreationDto;
import org.automation.initdata.dtos.CreateChefDto;
import org.automation.initdata.dtos.SaveAddressDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;

@Service
public class ChefClient {
    private final String url = "http://gateway:8100/api/chef";
    private final RestClient restClient;

    public ChefClient() {
        this.restClient = RestClient.create();
    }

    public void createChef(CreateChefDto createChefDto) {
        restClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createChefDto)
                .retrieve()
                .toBodilessEntity();
    }

    public void createChefAddress(SaveAddressDto saveAddressDto, Long chefId) {
        restClient.post()
                .uri(url + "/address/" + chefId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(saveAddressDto)
                .retrieve()
                .toBodilessEntity();
    }

    public void createChefNotification(ChefNotificationCreationDto notificationDto) {
        restClient.post()
                .uri(url + "/notification")
                .contentType(MediaType.APPLICATION_JSON)
                .body(notificationDto)
                .retrieve()
                .toBodilessEntity();
    }

    public Long getFirstAddressByChefId(Long chefId) {
        String callUri = url + "/address/active/" + chefId;
        List<AddressIdDto> addresses = restClient
                .get()
                .uri(callUri)
                .retrieve()
                .body(new ParameterizedTypeReference<List<AddressIdDto>>() {
                });

        if (addresses == null || addresses.isEmpty()) {
            return null;
        }
        return addresses.getFirst().id();
    }
}
