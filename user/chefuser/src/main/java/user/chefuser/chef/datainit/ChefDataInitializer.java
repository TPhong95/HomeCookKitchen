/*
package user.chefuser.chef.datainit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import user.chefuser.chef.*;
import user.chefuser.chef.dtos.CreateChefDto;
import user.chefuser.chef.dtos.HomeVibeDto;
import user.chefuser.chef.dtos.SaveAddressDto;

@Component
public class ChefDataInitializer implements CommandLineRunner {

    private final ChefRepo chefRepo;
    private final ChefService chefService;
    private final ChefOrchestrationService chefOrchestrationService;

    public ChefDataInitializer(ChefRepo chefRepo, ChefService chefService, ChefOrchestrationService chefOrchestrationService) {
        this.chefRepo = chefRepo;
        this.chefService = chefService;
        this.chefOrchestrationService = chefOrchestrationService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (chefRepo.count() == 0) {

            /* KEY: c = chef, a = address */ /*

            CreateChefDto chef1 = new CreateChefDto("Gordon Ramsay", "gordonram@gmail.com", "66616661");
            Chef savedChef1 = chefService.createChef(chef1);
            HomeVibeDto c1a1vibe = new HomeVibeDto("I will swear at you", Genre.NONE, false, false, false);
            SaveAddressDto c1a1 = new SaveAddressDto("10 Baker Street", "1234", "Trondheim", c1a1vibe);
            chefOrchestrationService.saveAddressToChef(savedChef1.getId(), c1a1);
            HomeVibeDto c1a2vibe = new HomeVibeDto("I also swear when I'm in Oslo", Genre.NONE, false, false, false);
            SaveAddressDto c1a2 = new SaveAddressDto("12 Eggs Way", "8128", "Oslo", c1a2vibe);
            chefOrchestrationService.saveAddressToChef(savedChef1.getId(), c1a2);

            CreateChefDto chef2 = new CreateChefDto("Eyvind Hellstrøm", "eyvind@hellstrom.se", "55443322");
            Chef savedChef2 = chefService.createChef(chef2);
            HomeVibeDto c2a1vibe = new HomeVibeDto("Koselig sted", Genre.CLASSICAL, false, true, true);
            SaveAddressDto c2a1 = new SaveAddressDto("Søndre gate 1", "5544", "Oslo", c2a1vibe);
            chefOrchestrationService.saveAddressToChef(savedChef2.getId(), c2a1);

            CreateChefDto chef3 = new CreateChefDto("Jamie Oliver", "ollie@jamie.com", "20192020");
            Chef savedChef3 = chefService.createChef(chef3);
            String makingFun = "I will spend a lot of time talking to you about how kids do not eat enough vegetables. I also smile.";
            HomeVibeDto c3a1vibe = new HomeVibeDto(makingFun, Genre.POP, true, false, true);
            SaveAddressDto c3a1 = new SaveAddressDto("15 Whitchurch Road","0123","Sandvika", c3a1vibe);
            chefOrchestrationService.saveAddressToChef(savedChef3.getId(), c3a1);
            SaveAddressDto c3a2 = new SaveAddressDto("16 Churchwit Drive", "1234", "Vikersund", c3a1vibe);
            chefOrchestrationService.saveAddressToChef(savedChef3.getId(), c3a2);

            CreateChefDto chef4 = new CreateChefDto("Julia Child", "julia@child.com", "55440000");
            Chef savedChef4 = chefService.createChef(chef4);
            HomeVibeDto c4a1vibe = new HomeVibeDto("I enjoy a good conversation about food", Genre.POP, true, true, true);
            SaveAddressDto c4a1 = new SaveAddressDto("103 Irving Street", "4040", "Oslo", c4a1vibe);
            chefOrchestrationService.saveAddressToChef(savedChef4.getId(), c4a1);

            CreateChefDto chef5 = new CreateChefDto("Marco Pierre White", "marco@white.com", "99887766");
            Chef savedChef5 = chefService.createChef(chef5);
            HomeVibeDto c5a1vibe = new HomeVibeDto("Yes, my surname is also my favourite sauce.", Genre.JAZZ, false, false, false);
            SaveAddressDto c5a1 = new SaveAddressDto("3 Exeter Street", "1203", "Oslo", c5a1vibe);
            chefOrchestrationService.saveAddressToChef(savedChef5.getId(), c5a1);

            CreateChefDto chef6 = new CreateChefDto("Markus Nilsen", "markus@nilsen.no", "32112332");
            chefService.createChef(chef6);

            System.out.println(">>> " + chefRepo.count() + " chefs were added to the chef database! <<<");
        }
    }
}
*/