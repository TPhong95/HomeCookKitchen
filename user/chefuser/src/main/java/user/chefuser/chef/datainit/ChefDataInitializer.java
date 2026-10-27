package user.chefuser.chef.datainit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import user.chefuser.chef.Chef;
import user.chefuser.chef.ChefRepo;
import user.chefuser.chef.ChefService;
import user.chefuser.chef.dtos.SaveAddressDto;

@Component
public class ChefDataInitializer implements CommandLineRunner {

    private final ChefRepo chefRepo;
    private final ChefService chefService;

    public ChefDataInitializer(ChefRepo chefRepo, ChefService chefService) {
        this.chefRepo = chefRepo;
        this.chefService = chefService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (chefRepo.count() == 0) {

            Chef chef1 = new Chef();
            chef1.setName("Gordon Ramsay");
            chef1.setEmail("gordonram@gmail.com");
            chef1.setPhoneNumber("666 666 666");
            Chef savedChef1 = chefService.save(chef1);
            chefService.saveAddressToChef(savedChef1.getId(), new SaveAddressDto("10 Baker Street, London", "12345678", "gordon@ramsay.com"));

            Chef chef2 = new Chef();
            chef2.setName("Eyvind Hellstrøm");
            Chef savedChef2 = chefService.save(chef2);
            chefService.saveAddressToChef(savedChef2.getId(), new SaveAddressDto("Søndre gate 1, Oslo", "87654321", "eyvind@hellstrom.no"));

            Chef chef3 = new Chef();
            chef3.setName("Jamie Oliver");
            Chef savedChef3 = chefService.save(chef3);
            chefService.saveAddressToChef(savedChef3.getId(), new SaveAddressDto("15 Whitchurch Road, London", "11223344", "jamie@oliver.com"));

            Chef chef4 = new Chef();
            chef4.setName("Julia Child");
            Chef savedChef4 = chefService.save(chef4);
            chefService.saveAddressToChef(savedChef4.getId(), new SaveAddressDto("103 Irving Street, Cambridge", "55667788", "julia@child.com"));

            Chef chef5 = new Chef();
            chef5.setName("Marco Pierre White");
            Chef savedChef5 = chefService.save(chef5);
            chefService.saveAddressToChef(savedChef5.getId(), new SaveAddressDto("Exeter Street, London", "99887766", "marco@white.com"));

            Chef chef6 = new Chef();
            chef6.setName("Markus Nilsen");
            chefService.save(chef6);

            System.out.println(">>> " + chefRepo.count() + " chefs were added to the chef database! <<<");
        }
    }
}
