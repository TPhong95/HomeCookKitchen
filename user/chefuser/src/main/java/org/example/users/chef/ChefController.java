package org.example.users.chef;

import org.example.users.chef.address.Address;
import org.example.users.chef.dtos.AddressResponseDto;
import org.example.users.chef.dtos.ChefResponseDto;
import org.example.users.chef.dtos.SaveAddressDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/chef")
public class ChefController {
    private final ChefService chefService;

    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    @PostMapping()
    public ResponseEntity<ChefResponseDto> saveChef(@RequestBody Chef chef) {
        Chef savedChef = chefService.save(chef);

        //Dto mapping skal flyttes til en egen klasse senere
        ChefResponseDto response = new ChefResponseDto(
                savedChef.getId(),
                savedChef.getName(),
                savedChef.getEmail(),
                savedChef.getPhoneNumber()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chef> getChefById(@PathVariable Long id){
        Chef result = chefService.getChefById(id);
        return ResponseEntity.ok(result);
    }
    @GetMapping()
    public ResponseEntity<List<Chef>> getAllChefs() {
        List<Chef> result = chefService.getAllChefs();
            if (result.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChefById(@PathVariable Long id) {
        chefService.deleteChefById(id);
        return ResponseEntity.ok("Chef with id: " + id + " deleted");
    }

    @PostMapping("/address/{chefId}")
    public ResponseEntity<List<AddressResponseDto>> saveChefAddress(@RequestBody SaveAddressDto dto, @PathVariable Long chefId) {
        List<Address> addresses = chefService.saveAddressToChef(chefId, dto);

        //Skal skille ut denne mappinga i den DTOmapper klasse senere
        List<AddressResponseDto> response = addresses.stream()
                .map(address -> new AddressResponseDto(
                        address.getId(),
                        address.getStreet(),
                        address.getCity(),
                        address.getCountry(),
                        chefId
                ))
                .toList();

        return ResponseEntity.ok(response);

    }
}

