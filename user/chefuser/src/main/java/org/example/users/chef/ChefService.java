package org.example.users.chef;

import org.example.users.chef.address.Address;
import org.example.users.chef.dtos.SaveAddressDto;
import org.example.users.chef.exception.notfound.ChefNotFoundException;
import org.example.users.chef.mappers.DtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChefService {
    private final ChefRepo chefRepo;
    private final DtoMapper dtoMapper;

    public ChefService(ChefRepo chefRepo, DtoMapper dtoMapper) {
        this.chefRepo = chefRepo;
        this.dtoMapper = dtoMapper;
    }

    @Transactional
    public Chef save(Chef chef) {
        if (chef.getAddresses() != null){
            chef.getAddresses().forEach(address -> address.setChef(chef));
        }
        return chefRepo.save(chef);
    }

    public Chef getChefById(Long id){
        return chefRepo.findById(id).orElseThrow(()->{
            return new ChefNotFoundException("Chef with id: " + id + " not found");
        });
    }

    public List<Chef> getAllChefs(){
        return chefRepo.findAll();
    }

    @Transactional
    public void deleteChefById(Long id){
        Chef chef = getChefById(id);
        chefRepo.delete(chef);
    }

    @Transactional
    public List<Address> saveAddressToChef(Long chefId, SaveAddressDto dto) {
        Chef chef = getChefById(chefId);

        Address address = dtoMapper.toAddressEntity(dto, chef);

        chef.addAddress(address);

        Chef savedChef = chefRepo.save(chef);
        return savedChef.getAddresses();
    }

    @Transactional
    public void deleteAddressFromChef(Long chefId, Long addressId) {
        Chef chef = getChefById(chefId);
        Address addressToDelete = chef.getAddresses().stream()
                .filter(a -> a.getId().equals(addressId))
                .findFirst()
                //husk å fikse expection på addresse
                .orElseThrow(() -> new ChefNotFoundException("Address with id: " + addressId + " not found"));

        chef.removeAddress(addressToDelete);
        chefRepo.save(chef);
    }


}
