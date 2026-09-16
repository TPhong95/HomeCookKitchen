package org.example.users.chef;

import org.springframework.stereotype.Service;


@Service
public class ChefService {
    private final ChefRepo chefRepo;

    public ChefService(ChefRepo chefRepo) {
        this.chefRepo = chefRepo;
    }

    public Chef save(Chef chef) {
        Chef savedChef = chefRepo.save(chef);
        return savedChef;
    }


}
