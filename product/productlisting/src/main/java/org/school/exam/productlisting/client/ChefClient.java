package org.school.exam.productlisting.client;

import org.school.exam.productlisting.dto.ChefResponseDTO;
import org.school.exam.productlisting.exception.BadRequestException.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class ChefClient {
    private final String url = "http://gateway:8100/api/chef";
    private final RestClient restClient;

    public ChefClient() {
        this.restClient = RestClient.create();
    }

    public ChefResponseDTO getChefById(Long chefId) {
        String callUri = url + "/" + chefId;
        ChefResponseDTO DTO = restClient
                .get()
                .uri(callUri)
                .retrieve()
                .onStatus(status -> status.value() == 404, (request, response) -> {
                    throw new BadRequestException("Chef with id " + chefId + " not found", Map.of());
                })
                .body(ChefResponseDTO.class);
        return DTO;
    }
}
