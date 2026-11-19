User Stories

Links in stories are to the front end. They require that the containers are running and that you are logged in as a chef or customer respectively.

Chef stories
Chef notification

http://localhost:5173/chef/notifications

As a chef, the deadline for ordering a seat at my listing expires. I get a notification with how many seats have been booked for my dinner.

Acceptance criteria:

The chef sees how many unread notifications they have on their homepage
If a new notification comes in, the website refreshes, and alerts the chef of new notification.
A chef can mark a notification as read to remove it from the list.
Get notifications about the order belonging to a purchase

As a chef, upon receiving a purchase I want to get a notification containing information letting me know that a purchase went through, with information about what listing the purchase is related to.

Acceptance criteria:

A notification is asynchronously delivered through Kafka to the chef.
The notification contains the purchase Id (
#456456).
The notification contains the status of the purchase (Purchase has been made).
The notification contains information about which listing and what quantity.
The notification contains a sum-total with the total price of the purchase.
A chef can edit their contact information

http://localhost:5173/chef/profile

As a chef, I can update my name, email address, and phone number on my profile page.

Acceptance criteria:

The chef receives visual feedback that their contact information has been updated
The chef must enter an 8 digit phone number
The chef must use a valid email format
The chef must fill in all fields
If a field is missing, meaningful user feedback is provided for the user to try again
Chef can create a new address object

http://localhost:5173/chef/profile

As a chef, I can create a new address to serve food from. I fill in information like street, postal code, city, as well as the social elements of my home, with a description, the genre of music I play, if I have pets, if it's outdoors, and if it is family friendly.

Acceptance criteria:

A chef receives visual feedback that a new address has been created
The chef must fill in all fields
The chef receives meaningful visual feedback if a field is missing
A chef can edit an existing address

http://localhost:5173/chef/profile

As a chef I want to be able to make changes to an existing address. If I change my mind about the vibe, this should be easy to edit.

Acceptance criteria:

The chef clicks on a button belonging to the address they want to edit to bring up a modal for editing
The chef receives visual feedback that the address has been updated.
The chef must fill in all fields
The chef receives meaningful visual feedback if fields are missing
A chef can mark an existing address as inactive

http://localhost:5173/chef/profile

As a chef, I stopped serving food at a particular address. I click "Mark as inactive" to remove the address from my page so I am not stuck with lots of old addresses on my profile.

Acceptance criteria:

A chef gets prompted if they are sure before removing an address
A chef gets a visual confirmation that their address has been removed upon removal
A chef can create a listing

From arbeidskrav priority user story: 1. Post dish

As a chef, I want to create a listing so that people can attend my dinners. I'm logged in as a chef. I go to the left sidebar, click "Listings", then "+ Create Listing", and pick the recipe I want to make, as well as the address where I plan to serve it. I decide number of portions/seats, and how much I will be selling them for. I get information about what my maximum revenue could be. I set the deadline for customers to purchase by, as well as when I expect dinner to be served.

Acceptance criteria:

The chef gets visual feedback when they have created a new listing
The chef gets a dropdown menu with all their active recipes
The chef gets a dropdown menu with all their active addresses
The purchase deadline must be set to a future date and time; a deadline in the past is rejected
There is meaningful visual feedback if a field is filled in wrong
The chef gets a visual confirmation, and the listing immediately becomes visible in the Browse section (left sidebar) after a listing is created.
A chef cannot delete a listing with purchases

http://localhost:5173/chef/listings

Description If I have published a listing, and a customer has purchased at least one seat at my table, I cannot revoke the lisiting.

Acceptance criteria:

A chef gets meaningful visual feedback when trying to delete a listing with purchases
A chef can add new recipes

http://localhost:5173/chef/recipe

Description As a chef I click the "+ Add Recipe" button to create new recipe. I find a URL to the food I am making, and see what that will look like on the customer's browse page. I give the recipe a name and a description. I pick a category from a predetermined list, and mark which allergens the recipe has.

Acceptance criteria:

A chef picks which allergens are in the recipe they are making.
The chef picks the category food from a enumerated list of options
The chef receives visual feedback when a recipe is created
A chef gets meaningful visual feedback when they fill in a field wrong.
A chef can edit their recipe

http://localhost:5173/chef/recipe

As a chef, I decide I want to make changes to an existing recipe. I make the changes I desire, and receive the updated recipe straight away on my recipes page.

Acceptance criteria:

The chef gets visual feedback when a recipe gets successfully updated
A chef gets meaningful visual feedback when they fill in a field wrong.
The old recipe is still connected to previous listings so that we as microservice owners can trace which allergens have been present at the time the dish has been made
A chef can mark recipes as active/inactive

http://localhost:5173/chef/recipe

As a chef, I want to be able to mark recipes I do not make any more as inactive. This makes creating listings easier. If some recipes I make are seasonal, I might want to reactivate them again.

Acceptance criteria:

A chef should easily be change the active/inactive status of a recipe
A chef must be able to view both active and inactive recipes.
Only active recipes show up in listings
View historical listings

http://localhost:5173/chef/history

As a chef I want to see all the dinner events I have hosted. I want to see how much money I earned at each event. I want to know when I hosted the events, and at which address.

Acceptance criteria:

The chef can see the name of the recipe they made
The chef can see how many portions were made, how many were sold, and how much money they made on the listing
The chef can see a timestamp for when the dinner was served, as well as the address they served it at.
Receive order

From arbeidskrav priority user story: 4. Receive order

Now that you have placed an order as a customer, you can see it from the chef's side. Log out and log in as the chef who owns the listing you ordered from. The test data also contains existing orders for Gordon Ramsay, so this works without placing an order first.

As a chef, I want to be notified when customers order from my listings, so that I know how many portions to prepare and for which customers. The mail icon in the left sidebar shows how many unread notifications I have. When I click it, I see a notification for each order, showing the dish, the number of seats, which customer ordered and when. I can mark each notification as read.

Acceptance criteria:

The chef receives a notification when a customer orders from one of their listings.
The notification shows the dish, the number of seats and which customer ordered.
The mail icon shows the number of unread notifications.
The chef can mark a notification as read.
Customer stories
A customer can edit their contact information

http://localhost:5173/customer/profile

As a customer, I wish to update my name or my email. I click edit contact information and easily change my information in the modal.

Acceptance criteria:

I get visual feedback when changes have been made to my contact information
I get meaningful visual feedback when I fill in the form with incorrect information
Browse dishes

From arbeidskrav priority user story: 2. Browse dishes

I am logged in as a customer and want to find a dinner to attend. I go to the left sidebar and click "Browse", which shows all active listings. For each listing I can see the name of the dish, which chef is serving it, the price per portion, how many seats are left, the chef's address, when the food is ready and a short description. To learn more, I click "More info & Order", which opens a detailed view where I can also see the food category, which allergens the dish contains, the chef's home vibe and the deadline for ordering.

Acceptance criteria:

Only listings that are still within the order-by deadline are displayed.
The name of the dish is prominently displayed as a title.
The chef's name, address, price per portion, seats left, ready-by time and description are visible directly in the Browse list.
The food category, allergens, home vibe and order-by deadline are displayed when "More info & Order" is clicked.
Make a purchase

From arbeidskrav priority user story: 3. Make purchase

I am logged in as a customer and have found a dinner I want to attend in "Browse". In the detailed view ("More info & Order"), I choose how many seats I want for me and my friends with the + and - buttons, where each seat includes one portion. I click "Add to order", which places the order immediately. I get a visual confirmation that the order went through, as well as a notification in the notification section (the mail icon in the left sidebar) showing what my order costs.

Acceptance criteria:

I can choose the number of seats with the + and - buttons before ordering.
The order is placed immediately when I click "Add to order".
When I order, I get a visual confirmation that the order went through, as well as a notification showing the cost.
See upcoming dinners

http://localhost:5173/customer/upcoming

As a customer I want to know which dinners I am attending. I want to know how much it cost, how many seats I have reserved, when and where my dinner is being served. I also want to know who the chef is. I want to know which allergens are in the food. I want to be able to see what kind of vibe the chef has at their home.

Acceptance criteria:

Individual upcoming dinners are displayed in separate elements.
The name of the recipe is easily visible.
The customer can see how many seats I reserved.
The customer can see how much they paid for this dinner.
The customer can see when and where they are attending the dinner
The vibe is easily visible when clicking more information
I can see which allergens the food has
View past dinners

http://localhost:5173/customer/history

As a customer I want to see the past dinners I have attended. I want to know what I ate, where, when, with which chef and how many guests I brought. I want to see a description of the dinner, allergens, as well as what the vibe was at the home.

Acceptance criteria:

The name of the recipe is easily visible.
The name of the chef is easily visible
The address is easily visible
It is possible to see when the dinner was served
The number of guests and price of the meal is visible
Clicking more info brings up a description of the food as well as the vibe of the chef's place
Get notifications about the order belonging to a purchase

http://localhost:5173/customer/notifications

As a customer, upon completing a purchase I want to receive a notification containing information letting me know if the purchase went through, what I bought for what price and the sum total of the purchase.

Acceptance criteria:

A notification is asynchronously delivered through Kafka to the customer
The notification contains the purchase Id (
#456456)
The notification contains the status of the purchase (Purchase went through)
The notification contains information about the prices linked to each recipe name of the product within the purchase.
The notification contains a sum-total with the total price of the purchase.