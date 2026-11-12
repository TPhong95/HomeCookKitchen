import {Banner} from "../general/banner.tsx";
import styles from "./listings.module.css";
import {type ListingStruct} from "../shared/listinginterface.ts";
import {useEffect, useRef, useState} from "react";
import {type RecipeStruct} from "../shared/recipeinterface.ts";
import {useUserContext} from "../context/useUserContext.tsx";
import toast from "react-hot-toast";
import type {ChefAddress, ChefResponse} from "../shared/chefinterface.ts";
import {sendJson} from "../util/api.ts";



export function Listings() {
  const {user} = useUserContext();
  const chefId = user?.role === "chef" ? user.id : null;
  const [addresses, setAddresses] = useState<ChefAddress[]>([]);
  const [name, setName] = useState("");

  const [listings, setListings] = useState<ListingStruct[]>([]);
  const [recipes, setRecipes] = useState<RecipeStruct[]>([]);

  const [modalStep, setModalStep] = useState<1 | 2>(1);
  const [selectedRecipeId, setSelectedRecipeId] = useState<number | "">("");
  const [selectedAddressId, setSelectedAddressId] = useState<number | "">("");

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

  useEffect(() => {
    if (chefId === null) return;

    async function loadChef(chefId: number) {
      try {
        const res = await fetch(`http://localhost:8100/api/chef/${chefId}`);
        if (!res.ok) throw new Error(`Chef request failed: ${res.status}`);
        const chef: ChefResponse = await res.json();
        setName(chef.name);
        setAddresses(chef.addresses);
      } catch (error) {
        toast.error(error instanceof Error ? error.message : "Something went wrong");
      }
    }

    async function loadListings(chefId: number) {
      try {
        const res = await fetch(`http://localhost:8100/api/productListing/chef/${chefId}/active`);
        if(!res.ok) throw new Error("Failed to fetch listings");

        const data: ListingStruct[] = await res.json();
        setListings(data);
      } catch (error) {
        toast.error(error instanceof Error ? error.message : "Something went wrong");
      }
    }

    async function loadRecipes(chefId: number) {
      try {
        const res = await fetch(`http://localhost:8100/api/recipe/chef/${chefId}`);
        if(!res.ok) throw new Error("Failed to fetch recipes");
        const data: RecipeStruct[] = await res.json();
        setRecipes(data);
      } catch (error) {
        toast.error(error instanceof Error ? error.message : "Something went wrong");
      }
    }
    loadChef(chefId);
    loadListings(chefId);
    loadRecipes(chefId);

  }, [chefId]);

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

  const handleNextStep = () => {
    if (selectedRecipeId === "") return;
    const foundRecipe = recipes.find(r => r.recipeId === Number(selectedRecipeId));
    if (foundRecipe) {
      setTempRecipeName(foundRecipe.recipeName);
      if (foundRecipe.pictureUrl) setTempRecipePicture(foundRecipe.pictureUrl);
      setModalStep(2);
    }
  }

  const handleCreateListing = async () => {
    if (chefId === null || selectedRecipeId === "" || selectedAddressId === ""
      || !tempBuyTo || !tempReadyTime) {
      toast.error("Please fill in all fields");
      return;
    }

    try {
      const created = await sendJson<ListingStruct>(
        "POST",
        "http://localhost:8100/api/productListing",
        {
          recipeId: selectedRecipeId,
          chefId: chefId,
          addressId: selectedAddressId,
          quantityOffered: Number(tempPortions) || 0,
          unitPrice: Number(tempPricePerPortion) || 0,
          purchaseDeadline: tempBuyTo,
          readyBy: tempReadyTime,
        }
      );
      setListings(prev => [...prev, created]);
      toast.success("Listing created");
      createListingDialogRef.current?.close();
    } catch (error) {
      toast.error(error instanceof Error ? error.message : "Something went wrong");
    }
  };

  async function handleDeleteListing(productListingId: number) {
    try {
      await sendJson<void>(
        "DELETE",
        `http://localhost:8100/api/productListing/${productListingId}`
      );
      setListings(prev => prev.filter(l => l.productListingId !== productListingId));
      toast.success("Successfully deleted listing");
    } catch (error) {
      toast.error(error instanceof Error ? error.message : "Something went wrong");
    }
  }
  return (
    <div className={styles["listings-layout"]}>
      <Banner/>
      <div className={styles["listings-wrapper"]}>
        <div className={styles["listings-top-banner"]}>
          {name ? (<h1>{name}'s Listings</h1>) : (<h1>Listings</h1>)}
          {chefId && (<button
            className={styles["new-listing-btn"]}
            onClick={handleOpenCreateListingModal}
          >
            + Create Listing
          </button>)}
        </div>

        {chefId === null ? (
          <div className={styles["contact-info"]}>
            <p>Not logged in as a chef. Pick a chef on the front page.</p>
          </div>
        ) : (
        <div className={styles["all-active-listings-container"]}>
          {listings.map((listing) => (
            <div
              className={styles["active-listing-item-container"]}
              key={listing.productListingId}
            >
              <div className={styles["listing-picture-container"]}>
                <img src={listing.recipeUrl} alt={listing.recipeName} />
              </div>
              <div className={styles["listing-info-container"]}>
                <div className={styles["name-and-edit-container"]}>
                  <div className={styles["listing-title"]}>{listing.recipeName}</div>
                  <button
                    className={styles["edit-recipe-btn"]}
                    onClick={() => handleDeleteListing(listing.productListingId)}
                  >
                    Delete listing
                  </button>
                </div>

                <div className={styles["listing-quantity-text"]}>
                  Price per portion: <span className={styles["listing-quantity-numbers"]}>{listing.unitPrice} NOK</span>
                </div>
                <div className={styles["listing-quantity-text"]}>
                  Seats left: <span className={styles["listing-quantity-numbers"]}>{listing.quantityOffered - listing.quantityPurchased} / {listing.quantityOffered}</span>
                </div>
                <div className={styles["listing-price-container"]}>Ready by: {listing.readyBy}</div>
                <div className={styles["listing-purchase-deadline"]}>Purchase deadline: {listing.purchaseDeadline}</div>
                <div className={styles["listing-price-container"]}>Served at: {listing.compositeAddress}</div>
              </div>
            </div>
          ))}
        </div>)}

        <dialog
          className={styles["create-listing-dialog"]}
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
                    <option key={recipe.recipeId} value={recipe.recipeId}>
                      {recipe.recipeName}
                    </option>
                  ))}
                </select>
              </div>
              <div>
                <label htmlFor={"address-select"}>Choose address:</label>
                <select
                  id={"address-select"}
                  value={selectedAddressId}
                  onChange={(e) => setSelectedAddressId(e.target.value ? Number(e.target.value) : "")}
                >
                  <option value={""}>-- Select an address --</option>
                  {addresses.map((address) => (
                    <option key={address.id} value={address.id}>
                      {address.street}, {address.city}
                    </option>
                  ))}
                </select>
              </div>
              <div className={styles["save-recipe-box"]}>
                <button
                  className={styles["yes-btn"]}
                  onClick={handleNextStep}
                  disabled={selectedRecipeId === "" || selectedAddressId === ""}
                >
                  Next
                </button>
                <button
                  className={styles["no-btn"]}
                  onClick={() => createListingDialogRef.current?.close()}
                >
                  Cancel
                </button>
              </div>
            </div>
          ) : (
            <div>
              <div className={styles["create-listing-header-container"]}>
                <div className={styles["listing-recipe-image-container"]}>
                  <img src={tempRecipePicture} alt={tempRecipeName}/>
                </div>
                <h1>{tempRecipeName}</h1>
              </div>
              <div className={styles["portion-control-container"]}>
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
                <div className={styles["listing-total-preview"]}>
                  Total potential revenue: <strong>{calculatedTotal.toFixed(2)}NOK</strong>
                </div>
              </div>
              <div className={styles["buy-window-container"]}>
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
              <div className={styles["ready-by-container"]}>
                <label htmlFor={"ready-by"}>Expected time of completion:</label>
                <input
                  id={"ready-by"}
                  type={"datetime-local"}
                  value={tempReadyTime}
                  onChange={(e) => setTempReadyTime(e.target.value)}
                />
              </div>

              <p className={styles["create-listing-question"]}>Create listing?</p>
              <div className={styles["save-recipe-box"]}>
                <button
                  className={styles["yes-btn"]}
                  onClick={handleCreateListing}
                >
                  Yes
                </button>
                <button
                  className={styles["no-btn"]}
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