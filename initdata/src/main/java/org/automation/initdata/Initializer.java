package org.automation.initdata;

import org.automation.initdata.client.*;
import org.automation.initdata.dtos.*;
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

        if (!chefClient.getAllChef().isEmpty()) {return;}

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
        String pictureUrl1 = "https://www.thespruceeats.com/thmb/XRUmww4wwE88XXKd4-6HnFYm6ME=/750x0/filters:no_upscale():max_bytes(150000):strip_icc():focal(3119x0:3121x2):format(webp)/easy-and-hearty-vegetable-soup-99538-hero-01-1d3b936ff03144af95ddca7640259c11.jpg";
        CreateRecipeDTO recipe1 = new CreateRecipeDTO(1L, "Soup", true, pictureUrl1, "This is canned soup",0L, SKANDINAVIAN,
                false, true, false, true, false, true, false, false, false, false, false, false, false, true);
        recipeClient.createRecipe(recipe1);

        String pictureUrl2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT80AylpKjDlZPtVgeSuEIhNYdrSbi501NoLft80xV-aK5BRCfcqNSIDtxI1zpc-o-RVsafSwDV8GpEweFrzYjz87vXOM8hYt4XtlPMouf4wQ&s=10";
        CreateRecipeDTO recipe2 = new CreateRecipeDTO(2L, "Burger", true, pictureUrl2, "This is without cheese", 0L, BURGER,
                true, true, false, false, false, false, false, false, false, false, false, false, false, false);
        recipeClient.createRecipe(recipe2);

        String pictureUrl3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSAt49Jy6aKOME_xegYQErf2wqevYOPbzBit3VNYISfUw2Lb_GbCWMyg0vLhloR_QiNxAz9FHSxOku2iNJmETRiBqisrNYmA18aLPXNw9ldEw&s=10";
        CreateRecipeDTO recipe3 = new CreateRecipeDTO(3L, "Noodles", true, pictureUrl3, "Cup noodles", 0L, ASIAN,
                false, true, false, true, false, true, false, false, false, false, false, false, false, true);
        recipeClient.createRecipe(recipe3);

        String pictureUrl4 = "https://www.allrecipes.com/thmb/DZLFEwAZT2vRyo9-XjzUoALJFyI=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/19356-spaghetti-italian-VAT-Beauty-4x3-af05b7565e014c16b1c5f86d45a18738.jpg";
        CreateRecipeDTO recipe4 = new CreateRecipeDTO(4L, "Spaghetti", true, pictureUrl4, "Looks like coding", 0L, PASTA,
                false, true, false, true, false, true, false, false, false, false, false, false, false, true);
        recipeClient.createRecipe(recipe4);

        String pictureUrl5 = "https://cdn.loveandlemons.com/wp-content/uploads/2024/12/caesar-salad-500x500.jpg";
        CreateRecipeDTO recipe5 = new CreateRecipeDTO(1L, "Salad", true, pictureUrl5, "Healthy kind", 0L, VEGETARIAN,
                false, true, false, true, false, true, false, false, false, false, false, false, false, true);
        recipeClient.createRecipe(recipe5);

        //LISTING UNACTIVE

        //DEADLINE LOCALDATE TIME
        LocalDateTime listingTime1 = LocalDateTime.parse("2026-10-01T18:00:00");
        CreateProductListingDTO oldProductListing1 = new CreateProductListingDTO(1L, 1L, chefClient.getFirstAddressByChefId(1L), 20L, 14.99f, listingTime1, listingTime1.plusHours(2));
        listingClient.createUnactiveListing(oldProductListing1);

        LocalDateTime listingTime2 = LocalDateTime.parse("2026-11-01T18:00:00");
        CreateProductListingDTO oldProductListing2 = new CreateProductListingDTO(1L, 1L, chefClient.getFirstAddressByChefId(1L), 30L, 20.00f, listingTime2, listingTime2.plusHours(2));
        listingClient.createUnactiveListing(oldProductListing2);

        LocalDateTime listingTime3 = LocalDateTime.parse("2026-11-03T12:00:00");
        CreateProductListingDTO oldProductListing3 = new CreateProductListingDTO(5L, 1L, chefClient.getFirstAddressByChefId(1L), 15L, 4.99f, listingTime3, listingTime3.plusHours(2));
        listingClient.createUnactiveListing(oldProductListing3);

        //LISTING ACTIVE

        CreateProductListingDTO productListing1 = new CreateProductListingDTO(1L, 1L, chefClient.getFirstAddressByChefId(1L), 20L, 14.99f, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        listingClient.createListing(productListing1);

        CreateProductListingDTO productListing2 = new CreateProductListingDTO(2L, 2L, chefClient.getFirstAddressByChefId(2L), 30L, 20.00f, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        listingClient.createListing(productListing2);

        CreateProductListingDTO productListing3 = new CreateProductListingDTO(3L, 3L, chefClient.getFirstAddressByChefId(3L), 15L, 4.99f, LocalDateTime.now().plusMinutes(4), LocalDateTime.now().plusHours(2));
        listingClient.createListing(productListing3);

        CreateProductListingDTO productListing4 = new CreateProductListingDTO(4L, 4L, chefClient.getFirstAddressByChefId(4L), 22L, 24.99f, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        listingClient.createListing(productListing4);

        CreateProductListingDTO productListing5 = new CreateProductListingDTO(5L, 1L, chefClient.getFirstAddressByChefId(1L), 33L, 12.99f, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
        listingClient.createListing(productListing5);

        CreateProductListingDTO productListing6 = new CreateProductListingDTO(1L, 1L, chefClient.getFirstAddressByChefId(1L), 33L, 12.99f, LocalDateTime.now().plusMinutes(4), LocalDateTime.now().plusHours(2));
        listingClient.createListing(productListing6);

        try{
            Thread.sleep(10000);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        // PURCHASE OLD UNACTIVE

        List<PurchaseEventDto> oldPurchaseList1 = new ArrayList<>();
        oldPurchaseList1.add(new PurchaseEventDto(1L, 3L));
        PurchaseDto oldPurchase1 = new PurchaseDto(1L, oldPurchaseList1);
        purchaseClient.createOldPurchase(oldPurchase1);

        List<PurchaseEventDto> oldPurchaseList2 = new ArrayList<>();
        oldPurchaseList2.add(new PurchaseEventDto(2L, 2L));
        PurchaseDto oldPurchase2 = new PurchaseDto(1L, oldPurchaseList2);
        purchaseClient.createOldPurchase(oldPurchase2);

        List<PurchaseEventDto> oldPurchaseList3 = new ArrayList<>();
        oldPurchaseList3.add(new PurchaseEventDto(1L, 1L));
        PurchaseDto oldPurchase3 = new PurchaseDto(2L, oldPurchaseList3);
        purchaseClient.createOldPurchase(oldPurchase3);

        // PURCHASE NEW ACTIVE

        List<PurchaseEventDto> purchaseList1 = new ArrayList<>();
        purchaseList1.add(new PurchaseEventDto(4L, 3L));
        purchaseList1.add(new PurchaseEventDto(5L, 4L));
        PurchaseDto purchase1 = new PurchaseDto(1L, purchaseList1);
        purchaseClient.createPurchase(purchase1);

        List<PurchaseEventDto> purchaseList2 = new ArrayList<>();
        purchaseList2.add(new PurchaseEventDto(6L, 2L));
        purchaseList2.add(new PurchaseEventDto(7L, 1L));
        PurchaseDto purchase2 = new PurchaseDto(2L, purchaseList2);
        purchaseClient.createPurchase(purchase2);

        List<PurchaseEventDto> purchaseList3 = new ArrayList<>();
        purchaseList3.add(new PurchaseEventDto(8L, 1L));
        purchaseList3.add(new PurchaseEventDto(4L, 2L));
        PurchaseDto purchase3 = new PurchaseDto(3L, purchaseList3);
        purchaseClient.createPurchase(purchase3);

    }


}
