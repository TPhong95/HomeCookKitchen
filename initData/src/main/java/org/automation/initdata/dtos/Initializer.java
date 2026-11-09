package org.automation.initdata.dtos;

import org.automation.initdata.client.*;
import org.automation.initdata.enums.Genre;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.automation.initdata.enums.FoodCategory.*;

@Component
public class Initializer implements CommandLineRunner {

    private final ChefClient chefClient;
    private final CustomerClient customerClient;
    private final RecipeClient recipeClient;
    private final ListingClient listingClient;
    private final PurchaseClient purchaseClient;

    public Initializer(ChefClient chefClient, CustomerClient customerClient, RecipeClient recipeClient, ListingClient listingClient, PurchaseClient purchaseClient) {
        this.chefClient = chefClient;
        this.customerClient = customerClient;
        this.recipeClient = recipeClient;
        this.listingClient = listingClient;
        this.purchaseClient = purchaseClient;
    }

    @Override
    public void run(String... args) {

        //CHEF
        CreateChefDto chef1 = new CreateChefDto("Gordon Ramsay", "gordonram@gmail.com", "66616661");
        chefClient.createChef(chef1);
        HomeVibeDto c1a1vibe = new HomeVibeDto("I will swear at you", Genre.NONE, false, false, false);
        SaveAddressDto c1a1 = new SaveAddressDto("10 Baker Street", "1234", "Trondheim", c1a1vibe);
        chefClient.createChefAddress(c1a1, 1L);
        HomeVibeDto c1a2vibe = new HomeVibeDto("I also swear when I'm in Oslo", Genre.NONE, false, false, false);
        SaveAddressDto c1a2 = new SaveAddressDto("12 Eggs Way", "8128", "Oslo", c1a2vibe);
        chefClient.createChefAddress(c1a2, 1L);

        CreateChefDto chef2 = new CreateChefDto("Eyvind Hellstrøm", "eyvind@hellstrom.se", "55443322");
        chefClient.createChef(chef2);
        HomeVibeDto c2a1vibe = new HomeVibeDto("Koselig sted", Genre.CLASSICAL, false, true, true);
        SaveAddressDto c2a1 = new SaveAddressDto("Søndre gate 1", "5544", "Oslo", c2a1vibe);
        chefClient.createChefAddress(c2a1, 2L);

        CreateChefDto chef3 = new CreateChefDto("Jamie Oliver", "ollie@jamie.com", "20192020");
        chefClient.createChef(chef3);
        String makingFun = "I will spend a lot of time talking to you about how kids do not eat enough vegetables. I also smile.";
        HomeVibeDto c3a1vibe = new HomeVibeDto(makingFun, Genre.POP, true, false, true);
        SaveAddressDto c3a1 = new SaveAddressDto("15 Whitchurch Road","0123","Sandvika", c3a1vibe);
        chefClient.createChefAddress(c3a1, 3L);
        SaveAddressDto c3a2 = new SaveAddressDto("16 Churchwit Drive", "1234", "Vikersund", c3a1vibe);
        chefClient.createChefAddress(c3a2, 3L);

        CreateChefDto chef4 = new CreateChefDto("Julia Child", "julia@child.com", "55440000");
        chefClient.createChef(chef4);
        HomeVibeDto c4a1vibe = new HomeVibeDto("I enjoy a good conversation about food", Genre.POP, true, true, true);
        SaveAddressDto c4a1 = new SaveAddressDto("103 Irving Street", "4040", "Oslo", c4a1vibe);
        chefClient.createChefAddress(c4a1, 4L);

        CreateChefDto chef5 = new CreateChefDto("Marco Pierre White", "marco@white.com", "99887766");
        chefClient.createChef(chef5);
        HomeVibeDto c5a1vibe = new HomeVibeDto("Yes, my surname is also my favourite sauce.", Genre.JAZZ, false, false, false);
        SaveAddressDto c5a1 = new SaveAddressDto("3 Exeter Street", "1203", "Oslo", c5a1vibe);
        chefClient.createChefAddress(c5a1, 5L);

        CreateChefDto chef6 = new CreateChefDto("Markus Nilsen", "markus@nilsen.no", "32112332");
        chefClient.createChef(chef6);

        //CUSTOMER

        CustomerDTO customer1 = new CustomerDTO("John", "John@gmail.com");
        customerClient.createCustomer(customer1);

        CustomerDTO customer2 = new CustomerDTO("Bob", "Bob@gmail.com");
        customerClient.createCustomer(customer2);

        CustomerDTO customer3 = new CustomerDTO("Mannen", "Mannen@gmail.com");
        customerClient.createCustomer(customer3);

        CustomerDTO customer4 = new CustomerDTO("The Greatest Customer", "TheBest@gmail.com");
        customerClient.createCustomer(customer4);

        CustomerDTO customer5 = new CustomerDTO("Gramps", "Gramps@gmail.com");
        customerClient.createCustomer(customer5);

        //RECIPE

        CreateRecipeDTO recipe1 = new CreateRecipeDTO(1L, "Soup", true, 0L, 0L, ASIAN,
                false, true, false, true, false, true, false, false, false, false, false, false, false, true);
        recipeClient.createRecipe(recipe1);

        CreateRecipeDTO recipe2 = new CreateRecipeDTO(2L, "Burger", true, 0L, 0L, BURGER,
                true, true, false, false, false, false, false, false, false, false, false, false, false, false);
        recipeClient.createRecipe(recipe2);

        CreateRecipeDTO recipe3 = new CreateRecipeDTO(3L, "Noodles", true, 0L, 0L, UNDEFINED,
                false, true, false, true, false, true, false, false, false, false, false, false, false, true);
        recipeClient.createRecipe(recipe3);

        CreateRecipeDTO recipe4 = new CreateRecipeDTO(4L, "Spaghetti", true, 0L, 0L, UNDEFINED,
                false, true, false, true, false, true, false, false, false, false, false, false, false, true);
        recipeClient.createRecipe(recipe4);

        CreateRecipeDTO recipe5 = new CreateRecipeDTO(1L, "Salad", true, 0L, 0L, UNDEFINED,
                false, true, false, true, false, true, false, false, false, false, false, false, false, true);
        recipeClient.createRecipe(recipe5);

        //LISTING

        CreateProductListingDTO productListing1 = new CreateProductListingDTO(1L, 1L, true, 20, 14.99f, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        listingClient.createListing(productListing1);

        CreateProductListingDTO productListing2 = new CreateProductListingDTO(2L, 2L, true, 30, 20.00f, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        listingClient.createListing(productListing2);

        CreateProductListingDTO productListing3 = new CreateProductListingDTO(3L, 3L, true, 15, 4.99f, LocalDateTime.now().plusMinutes(4), LocalDateTime.now().plusHours(2));
        listingClient.createListing(productListing3);

        CreateProductListingDTO productListing4 = new CreateProductListingDTO(4L, 4L, true, 22, 24.99f, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        listingClient.createListing(productListing4);

        CreateProductListingDTO productListing5 = new CreateProductListingDTO(5L, 1L, true, 33, 12.99f, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        listingClient.createListing(productListing5);

        // PURCHASE

        List<PurchaseEventDto> purchaseList1 = new ArrayList<>();
        purchaseList1.add(new PurchaseEventDto(1L, 3));
        purchaseList1.add(new PurchaseEventDto(2L, 4));
        PurchaseDto purchase1 = new PurchaseDto(1, purchaseList1);
        purchaseClient.createPurchase(purchase1);


        List<PurchaseEventDto> purchaseList2 = new ArrayList<>();
        purchaseList2.add(new PurchaseEventDto(3L, 2));
        purchaseList2.add(new PurchaseEventDto(4L, 1));
        PurchaseDto purchase2 = new PurchaseDto(2, purchaseList2);
        purchaseClient.createPurchase(purchase2);

        List<PurchaseEventDto> purchaseList3 = new ArrayList<>();
        purchaseList3.add(new PurchaseEventDto(5L, 1));
        purchaseList3.add(new PurchaseEventDto(1L, 2));
        PurchaseDto purchase3 = new PurchaseDto(3, purchaseList3);
        purchaseClient.createPurchase(purchase3);


    }


}
