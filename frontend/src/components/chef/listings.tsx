import {Banner} from "../general/banner.tsx";
import "./listings.css";
import {type ListingStruct} from "../shared/listinginterface.ts";
import {useRef, useState} from "react";
import {Allergen, DishCategory, DishPreference, type RecipeStruct} from "../shared/recipeinterface.ts";

const initialAllergens: Record<Allergen, boolean> = {
  [Allergen.GLUTEN]: false,
  [Allergen.CRUSTACEANS]: false,
  [Allergen.EGGS]: false,
  [Allergen.FISH]: false,
  [Allergen.PEANUTS]: false,
  [Allergen.SOYBEANS]: false,
  [Allergen.MILK]: false,
  [Allergen.NUTS]: false,
  [Allergen.CELERY]: false,
  [Allergen.MUSTARD]: false,
  [Allergen.SESAME]: false,
  [Allergen.SULFITES]: false,
  [Allergen.LUPIN]: false,
  [Allergen.MOLLUSCS]: false,
};

const testRecipes: RecipeStruct[] = [
  {
    id: 1,
    recipeName: "Falafel Baguette",
    description: "I put falafel in a baguette, it tastes alright",
    active: true,
    category: DishCategory.PASTA,
    preference: DishPreference.VEGAN,
    image: "../../../testdata/falafelbaguette.jpg",
    allergens: {
      ...initialAllergens,
      [Allergen.GLUTEN]: true,
    }
  },
  {
    id: 2,
    recipeName: "Lasagne",
    description: "My grandpa's shoe tastes better",
    active: false,
    category: DishCategory.PASTA,
    preference: DishPreference.NONE,
    image: "../../../testdata/lasagne.jpg",
    allergens: {
      ...initialAllergens,
      [Allergen.MILK]: true,
      [Allergen.CELERY]: true,
      [Allergen.GLUTEN]: true,
    }
  }
]

const testListings: ListingStruct[] = [
  {
    id: 1,
    image: "../../../testdata/falafelbaguette.jpg",
    recipeName: "Falafel Baguette",
    quantity: 4,
    quantityPurchased: 2,
    unitPrice: 100,
    readyBy: "2026-06-06T14:30",
    createdAt: "2026-06-06T11:00",
    purchaseDeadline: "2026-06-06T12:00",
  },
  {
    id: 2,
    image: "../../../testdata/lasagne.jpg",
    recipeName: "Lasagne",
    quantity: 6,
    quantityPurchased: 3,
    unitPrice: 180,
    readyBy: "2026-06-07T17:00",
    createdAt: "2026-06-07T11:00",
    purchaseDeadline: "2026-06-07T15:00",
  }
];

export function Listings() {
  const name = "Geoffrey";

  const [listings, setListings] = useState<ListingStruct[]>(testListings);
  const [recipes /*, setRecipes */] = useState<RecipeStruct[]>(testRecipes); // FIX: Backend call actually sets recipes

  const [modalStep, setModalStep] = useState<1 | 2>(1);
  const [selectedRecipeId, setSelectedRecipeId] = useState<number | "">("");

  const [tempRecipeName, setTempRecipeName] = useState("");
  const [tempPortions, setTempPortions] = useState("0");
  const [tempPricePerPortion, setTempPricePerPortion] = useState("0");
  const [tempBuyTo, setTempBuyTo] = useState("");
  const [tempReadyTime, setTempReadyTime] = useState("");
  const [tempRecipePicture, setTempRecipePicture] = useState("");

  const portionsNum = Number(tempPortions) || 0;
  const priceNum = Number(tempPricePerPortion) || 0;
  const calculatedTotal = portionsNum * priceNum;

  const createListingDialogRef = useRef<HTMLDialogElement>(null);

  const handleOpenCreateListingModal = () => {
    setModalStep(1);
    setSelectedRecipeId("");
    setTempPortions("0");
    setTempPricePerPortion("0");
    setTempBuyTo("");
    setTempReadyTime("");
    setTempRecipeName("");
    setTempRecipePicture("");
    createListingDialogRef.current?.showModal();
  }

  const handleEditListingModal = (listing: ListingStruct) => {
    setModalStep(2);
    setTempRecipeName(listing.recipeName);
    setTempRecipePicture(listing.image || "");
    setTempPortions(String(listing.quantity));
    setTempPricePerPortion(String(listing.unitPrice));
    setTempBuyTo(listing.purchaseDeadline);
    setTempReadyTime(listing.readyBy);
    createListingDialogRef.current?.showModal();
  };

  const handleNextStep = () => {
    if (selectedRecipeId === "") return;
    const foundRecipe = recipes.find(r => r.id === Number(selectedRecipeId));
    if (foundRecipe) {
      setTempRecipeName(foundRecipe.recipeName);
      if (foundRecipe.image) setTempRecipePicture(foundRecipe.image);
      setModalStep(2);
    }
  }

  const handleCreateListing = () => {
    if (!tempRecipeName.trim()) return;

    const newListing: ListingStruct = {
      id: Date.now(), // FIX: Temporary mock ID
      image: tempRecipePicture,
      recipeName: tempRecipeName,
      quantity: Number(tempPortions) || 0,
      quantityPurchased: Number(tempPricePerPortion) || 0,
      unitPrice: Number(tempPricePerPortion) || 0,
      purchaseDeadline: tempBuyTo,
      readyBy: tempReadyTime,
      createdAt: new Date().toISOString(),
    };

    setListings((prevListings) => [...prevListings, newListing]);
    createListingDialogRef.current?.close();
  };
  return (
    <div className={"listings-layout"}>
      <Banner/>
      <div className={"listings-wrapper"}>
        <div className={"listings-top-banner"}>
          <h1>{name}'s Listings</h1>
          <button
            className={"new-listing-btn"}
            onClick={handleOpenCreateListingModal}
          >
            + Create Listing
          </button>
        </div>

        <div className={"all-active-listings-container"}>
          {listings.map((listing) => (
            <div
              className={"active-listing-item-container"}
              key={listing.id}
            >
              <div className={"listing-picture-container"}>
                <img src={listing.image} alt={listing.recipeName} />
              </div>
              <div className={"listing-info-container"}>
                <div className={"name-and-edit-container"}>
                  <div className={"listing-title"}>{listing.recipeName}</div>
                  <button
                    className={"edit-recipe-btn"}
                    onClick={() => handleEditListingModal(listing)}
                  >
                    Edit listing
                  </button>
                </div>

                <div className={"listing-quantity-text"}>
                  Price per portion: <span className={"listing-quantity-numbers"}>{listing.unitPrice} NOK</span>
                </div>
                <div className={"listing-quantity-text"}>
                  Seats left: <span className={"listing-quantity-numbers"}>{listing.quantityPurchased} / {listing.quantity}</span>
                </div>
                <div className={"listing-price-container"}>Ready by: {listing.readyBy}</div>
                <div className={"listing-purchase-deadline"}>Purchase deadline: {listing.purchaseDeadline}</div>
              </div>
            </div>
          ))}
        </div>

        <dialog
          className={"create-listing-dialog"}
          ref={createListingDialogRef}
        >
          {modalStep === 1 ? (
            <div>
              <h2>Select a Recipe</h2>
              <div>
                <label htmlFor={"recipe-select"}>Choose recipe:</label>
                <select
                  id={"recipe-select"}
                  value={selectedRecipeId}
                  onChange={(e) => setSelectedRecipeId(e.target.value ? Number(e.target.value) : "")}
                >
                  <option value={""}>-- Select a recipe --</option>
                  {recipes.map((recipe) => (
                    <option key={recipe.id} value={recipe.id}>
                      {recipe.recipeName}
                    </option>
                  ))}
                </select>
              </div>
              <div className={"save-recipe-box"}>
                <button
                  className={"yes-btn"}
                  onClick={handleNextStep}
                  disabled={selectedRecipeId === ""}
                >
                  Next
                </button>
                <button
                  className={"no-btn"}
                  onClick={() => createListingDialogRef.current?.close()}
                >
                  Cancel
                </button>
              </div>
            </div>
          ) : (
            <div>
              <div className={"create-listing-header-container"}>
                <div className={"listing-recipe-image-container"}>
                  <img src={tempRecipePicture} alt={tempRecipeName}/>
                </div>
                <h1>{tempRecipeName}</h1>
              </div>
              <div className={"portion-control-container"}>
                <label htmlFor={"number-of-portions"}>Number of portions:</label>
                <input
                  id={"number-of-portions"}
                  type={"text"}
                  value={tempPortions}
                  onChange={(e) => setTempPortions(e.target.value)}
                />
                <label htmlFor={"price-per-portion"}>Price per portion:</label>
                <input
                  id={"price-per-portion"}
                  type={"text"}
                  value={tempPricePerPortion}
                  onChange={(e) => setTempPricePerPortion(e.target.value)}
                />
                <div className={"listing-total-preview"}>
                  Total potential revenue: <strong>{calculatedTotal.toFixed(2)}NOK</strong>
                </div>
              </div>
              <div className={"buy-window-container"}>
                <div>
                  <label htmlFor={"buy-to-window"}>Purchase Deadline</label>
                  <input
                    id={"buy-to-window"}
                    type={"datetime-local"}
                    value={tempBuyTo}
                    onChange={(e) => setTempBuyTo(e.target.value)}
                  />
                </div>
              </div>
              <div className={"ready-by-container"}>
                <label htmlFor={"ready-by"}>Expected time of completion:</label>
                <input
                  id={"ready-by"}
                  type={"datetime-local"}
                  value={tempReadyTime}
                  onChange={(e) => setTempReadyTime(e.target.value)}
                />
              </div>
              <p className={"create-listing-question"}>Create listing?</p>
              <div className={"save-recipe-box"}>
                <button
                  className={"yes-btn"}
                  onClick={handleCreateListing}
                >
                  Yes
                </button>
                <button
                  className={"no-btn"}
                  onClick={() => setModalStep(1)}
                >
                  Back
                </button>
              </div>
            </div>
          )}
        </dialog>
      </div>
    </div>
  )
}