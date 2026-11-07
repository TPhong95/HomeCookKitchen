package user.chefuser.chef.datainit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import user.chefuser.chef.Address;
import user.chefuser.chef.Chef;
import user.chefuser.chef.ChefRepo;
import user.chefuser.chef.ChefService;
import user.chefuser.chef.dtos.CreateChefDto;

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

            CreateChefDto chef1 = new CreateChefDto("Gordon Ramsay", "gordonram@gmail.com", "66616661");
            Chef savedChef1 = chefService.createChef(chef1);
            Address c1a1 = new Address();
            c1a1.setStreet("10 Baker Street");
            c1a1.setPostalCode("1234");
            c1a1.setCity("Trondheim");
            chefService.saveAddressToChef(savedChef1, c1a1);
            Address c1a2 = new Address();
            c1a2.setStreet("12 Eggs Way");
            c1a2.setPostalCode("8128");
            c1a2.setCity("Oslo");
            chefService.saveAddressToChef(savedChef1, c1a2);

            CreateChefDto chef2 = new CreateChefDto("Eyvind Hellstrøm", "eyvind@hellstrom.se", "55443322");
            Chef savedChef2 = chefService.createChef(chef2);
            Address c2a1 = new Address();
            c2a1.setStreet("Søndre gate 1");
            c2a1.setPostalCode("5544");
            c2a1.setCity("Oslo");
            chefService.saveAddressToChef(savedChef2, c2a1);

            CreateChefDto chef3 = new CreateChefDto("Jamie Oliver", "ollie@jamie.com", "20192020");
            Chef savedChef3 = chefService.createChef(chef3);
            Address c3a1 = new Address();
            c3a1.setStreet("15 Whitchurch Road");
            c3a1.setPostalCode("0123");
            c3a1.setCity("Sandvika");
            chefService.saveAddressToChef(savedChef3, c3a1);
            Address c3a2 = new Address();
            c3a2.setStreet("16 Churchwit Drive");
            c3a2.setPostalCode("1234");
            c3a2.setCity("Vikersund");
            chefService.saveAddressToChef(savedChef3, c3a2);

            CreateChefDto chef4 = new CreateChefDto("Julia Child", "julia@child.com", "55440000");
            Chef savedChef4 = chefService.createChef(chef4);
            Address c4a1 = new Address();
            c4a1.setStreet("103 Irving Street");
            c4a1.setPostalCode("4040");
            c4a1.setCity("Oslo");
            chefService.saveAddressToChef(savedChef4,c4a1);

            CreateChefDto chef5 = new CreateChefDto("Marco Pierre White", "marco@white.com", "99887766");
            Chef savedChef5 = chefService.createChef(chef5);
            Address c5a1 = new Address();
            c5a1.setStreet("3 Exeter Street");
            c5a1.setPostalCode("1203");
            c5a1.setCity("Oslo");
            chefService.saveAddressToChef(savedChef5, c5a1);

            CreateChefDto chef6 = new CreateChefDto("Markus Nilsen", "markus@nilsen.no", "32112332");
            chefService.createChef(chef6);

            System.out.println(">>> " + chefRepo.count() + " chefs were added to the chef database! <<<");
        }
    }
}
