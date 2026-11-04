package user.chefuser.chef;

import user.chefuser.chef.address.Address;
import user.chefuser.chef.dtos.AddressResponseDto;
import user.chefuser.chef.dtos.ChefResponseDto;
import user.chefuser.chef.dtos.SaveAddressDto;
import user.chefuser.chef.dtos.UpdateChefDto;
import user.chefuser.chef.mappers.DtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/chef")
public class ChefController {
    private final ChefService chefService;
    private final DtoMapper dtoMapper;
    private final ChefOrchestrationService chefOrchestrationService;

    public ChefController(ChefService chefService, DtoMapper dtoMapper, ChefOrchestrationService chefOrchestrationService) {
        this.chefService = chefService;
        this.dtoMapper = dtoMapper;
        this.chefOrchestrationService = chefOrchestrationService;
    }

    // CHEF //
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
        List<ChefResponseDto> response = dtoMapper.toChefResponseDtoList(result);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChefResponseDto> editChefById(@PathVariable Long id, @RequestBody UpdateChefDto dto) {
        Chef updated = chefService.editChefInfo(id, dto);
        return ResponseEntity.ok(dtoMapper.toChefResponseDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChefById(@PathVariable Long id) {
        chefService.deleteChefById(id);
        return ResponseEntity.ok("Chef with id: " + id + " deleted");
    }

    // ADDRESS //
    @PostMapping("/address/{chefId}")
    public ResponseEntity<List<AddressResponseDto>> saveChefAddress(@RequestBody SaveAddressDto dto, @PathVariable Long chefId) {
        List<Address> addresses = chefOrchestrationService.saveAddressToChef(chefId, dto);
        List<AddressResponseDto> response = dtoMapper.toAddressResponseDtoList(addresses, chefId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/address/{chefId}/{addressId}")
    public ResponseEntity<String> deleteChefAddress(@PathVariable Long chefId, @PathVariable Long addressId) {
        chefService.deleteAddressFromChef(chefId, addressId);
        return ResponseEntity.ok("Address with id: " + addressId + " deleted");
    }
}

