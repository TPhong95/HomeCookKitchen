package org.example.users.chef;

import org.example.users.chef.address.Address;
import org.example.users.chef.dtos.AddressResponseDto;
import org.example.users.chef.dtos.ChefResponseDto;
import org.example.users.chef.dtos.SaveAddressDto;
import org.example.users.chef.mappers.DtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/chef")
public class ChefController {
    private final ChefService chefService;
    private final DtoMapper dtoMapper;

    public ChefController(ChefService chefService, DtoMapper dtoMapper) {
        this.chefService = chefService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping()
    public ResponseEntity<ChefResponseDto> saveChef(@RequestBody Chef chef) {
        Chef savedChef = chefService.save(chef);

        ChefResponseDto response = dtoMapper.toChefResponseDto(savedChef);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChefResponseDto> getChefById(@PathVariable Long id){
        Chef result = chefService.getChefById(id);

        ChefResponseDto response = dtoMapper.toChefResponseDto(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<ChefResponseDto>> getAllChefs() {
        List<Chef> result = chefService.getAllChefs();
            if (result.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            List<ChefResponseDto> response = dtoMapper.toChefResponseDtoList(result);

            return ResponseEntity.ok(response);
        }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChefById(@PathVariable Long id) {
        chefService.deleteChefById(id);
        return ResponseEntity.ok("Chef with id: " + id + " deleted");
    }

    @PostMapping("/address/{chefId}")
    public ResponseEntity<List<AddressResponseDto>> saveChefAddress(@RequestBody SaveAddressDto dto, @PathVariable Long chefId) {
        List<Address> addresses = chefService.saveAddressToChef(chefId, dto);
        List<AddressResponseDto> response = dtoMapper.toAddressResponseDtoList(addresses, chefId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/address/{chefId}/{addressId}")
    public ResponseEntity<String> deleteChefAddress(@PathVariable Long chefId, @PathVariable Long addressId) {
        chefService.deleteAddressFromChef(chefId, addressId);
        return ResponseEntity.ok("Address with id: " + addressId + " deleted");
    }
}

