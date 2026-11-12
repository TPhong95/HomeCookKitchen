package user.chefuser.chef;

import org.springframework.http.HttpStatus;
import user.chefuser.chef.dtos.*;
import user.chefuser.chef.mappers.DtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<ChefResponseDto> saveChef(@RequestBody CreateChefDto chef) {
        Chef savedChef = chefService.createChef(chef);

        ChefResponseDto response = dtoMapper.toChefResponseDto(savedChef);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
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

    @PatchMapping("/{id}")
    public ResponseEntity<ChefResponseDto> editChefById(@PathVariable Long id, @RequestBody UpdateChefDto dto) {
        Chef updated = chefService.editChefInfo(id, dto);
        return ResponseEntity.ok(dtoMapper.toChefResponseDto(updated));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ChefResponseDto> deactivateChef(@PathVariable Long id) {
        Chef chef = chefOrchestrationService.deactivateChef(id);
        return ResponseEntity.ok(dtoMapper.toChefResponseDto(chef));
    }

    // Chef Notification //

    @PostMapping("/notification")
    public ResponseEntity<ChefNotificationResponseDto> createNotification(@RequestBody ChefNotificationCreationDto notificationInputDTO) {
        ChefNotification savedNotification = chefOrchestrationService.createChefNotification(notificationInputDTO);
        ChefNotificationResponseDto responseDto = dtoMapper.toChefNotificationResponseDto(savedNotification);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/notification/{id}")
    public ResponseEntity<ChefNotificationResponseDto> getNotificationById(@PathVariable Long id) {
        ChefNotification result = chefOrchestrationService.getNotificationById(id);
        ChefNotificationResponseDto DtoMappedResult = dtoMapper.toChefNotificationResponseDto(result);
        return ResponseEntity.ok(DtoMappedResult);
    }

    @GetMapping("/notification/chefId/{id}")
    public ResponseEntity<List<ChefNotificationResponseDto>> getAllNotificationsByChefId(@PathVariable Long id) {
        List<ChefNotification> result = chefOrchestrationService.getAllNotificationsByChefId(id);
        List<ChefNotificationResponseDto> DTOMappedResult = new ArrayList<ChefNotificationResponseDto>();
        result.forEach(notification -> {
            ChefNotificationResponseDto DtoMappedNotification = dtoMapper.toChefNotificationResponseDto(notification);
            DTOMappedResult.add(DtoMappedNotification);
        });
        return ResponseEntity.ok(DTOMappedResult);
    }

    @PatchMapping("/notification/{id}")
    public ResponseEntity<ChefNotificationResponseDto> updateNotificationReadStatusByChefId(@PathVariable Long id) {
        ChefNotification updatedNotification = chefOrchestrationService.updateNotificationReadStatus(id);
        return ResponseEntity.ok(dtoMapper.toChefNotificationResponseDto(updatedNotification));
    }

    // ADDRESS //
    @GetMapping("/address/{addressId}")
    public ResponseEntity<AddressResponseDto> getAddressById (@PathVariable Long addressId) {
        Address address = chefOrchestrationService.getAddressById(addressId);
        return ResponseEntity.ok(dtoMapper.toAddressResponseDto(address, address.getChef().getId()));
    }

    @GetMapping("/address/active/{chefId}")
    public ResponseEntity<List<ActiveAddressDto>> getActiveAddressByChefId(@PathVariable Long chefId) {
        List<Address> activeAddresses = chefOrchestrationService.getActiveAddresses(chefId);
        return ResponseEntity.ok(dtoMapper.toActiveAddressDtoList(activeAddresses, chefId));
    }

    @PostMapping("/address/{chefId}")
    public ResponseEntity<List<AddressResponseDto>> saveChefAddress(@RequestBody SaveAddressDto dto, @PathVariable Long chefId) {
        List<Address> addresses = chefOrchestrationService.saveAddressToChef(chefId, dto);
        List<AddressResponseDto> response = dtoMapper.toAddressResponseDtoList(addresses, chefId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/address/{chefId}/{addressId}")
    public ResponseEntity<AddressResponseDto> editChefAddress(@PathVariable Long chefId,
                                                              @PathVariable Long addressId,
                                                              @RequestBody SaveAddressDto dto) {
        Address updated = chefOrchestrationService.editChefAddress(chefId, addressId, dto);
        return ResponseEntity.ok(dtoMapper.toAddressResponseDto(updated, chefId));
    }

    @PatchMapping("/address/{chefId}/{addressId}/deactivate")
    public ResponseEntity<AddressResponseDto> deactivateAddress(@PathVariable Long chefId,
                                                                @PathVariable Long addressId) {
        Address address = chefOrchestrationService.deactivateAddress(chefId, addressId);
        return ResponseEntity.ok(dtoMapper.toAddressResponseDto(address, chefId));
    }

}

