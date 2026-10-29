package user.chefuser.chef;

import user.chefuser.chef.address.Address;
import user.chefuser.chef.dtos.SaveAddressDto;
import user.chefuser.chef.exception.notfound.AddressNotFoundException;
import user.chefuser.chef.exception.notfound.ChefNotFoundException;
import user.chefuser.chef.mappers.DtoMapper;
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
        return chefRepo.findById(id).orElseThrow(()-> new ChefNotFoundException("Chef with id: " + id + " not found"));

    }

    public List<Chef> getAllChefs(){
        return chefRepo.findAll();
    }

    @Transactional
    public void deleteChefById(Long id){
        if (!chefRepo.existsById(id)){
            throw new ChefNotFoundException("Chef with id: " + id + " not found");
        }
        Chef chef = getChefById(id);
        chefRepo.delete(chef);
    }

    @Transactional
    public List<Address> saveAddressToChef(Chef chef, Address address) {
        chef.addAddress(address);
        Chef savedChef = chefRepo.save(chef);
        return savedChef.getAddresses();
    }

    @Transactional
    public void deleteAddressFromChef(Long chefId, Long addressId) {
        if (!chefRepo.existsById(chefId)){
            throw new ChefNotFoundException("Chef with id: " + chefId + " not found");
        }
        Chef chef = getChefById(chefId);
        Address addressToDelete = chef.getAddresses().stream()
                .filter(a -> a.getId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new AddressNotFoundException("Address with id: " + addressId + " not found"));

        chef.removeAddress(addressToDelete);
        chefRepo.save(chef);
    }


}
