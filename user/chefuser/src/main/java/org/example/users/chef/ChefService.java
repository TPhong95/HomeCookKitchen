package org.example.users.chef;

import org.example.users.chef.address.Address;
import org.example.users.chef.dtos.SaveAddressDto;
import org.example.users.chef.exception.notfound.ChefNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChefService {
    private final ChefRepo chefRepo;

    public ChefService(ChefRepo chefRepo) {
        this.chefRepo = chefRepo;
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

        //Skal lage Dto-mapper klasse senere
        Address address = new Address();
        address.setStreet(dto.street());
        address.setCity(dto.city());
        address.setCountry(dto.country());
        address.setChef(chef);

        chef.getAddresses().add(address);
        Chef savedChef = chefRepo.save(chef);

        return savedChef.getAddresses();
    }


}
