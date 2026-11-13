import {useEffect, useRef, useState} from "react";
import styles from './browse.module.css';
import {Banner} from "../general/banner.tsx";
import {type RecipeStruct, allergensList, DishCategory} from "../shared/recipeinterface.ts";
import {type ListingStruct} from "../shared/listinginterface.ts";
import {type ChefAddress, GENRE_LABELS} from "../shared/chefinterface.ts";
import {useUserContext} from "../context/useUserContext.tsx";
import {sendJson} from "../util/api.ts";
import toast from "react-hot-toast";

type DetailsStatus = "loading" | "loaded" | "error";

export function BrowseListing() {
  const {user} = useUserContext();
  // Anyone can browse, only customers can order
  const customerId = user?.role === "customer" ? user.id : null;

  const [listings, setListings] = useState<ListingStruct[]>([]);
  const [selectedListing, setSelectedListing] = useState<ListingStruct | null>(null);
  const [recipe, setRecipe] = useState<RecipeStruct | null>(null);
  const [address, setAddress] = useState<ChefAddress | null>(null);
  const [detailsStatus, setDetailsStatus] = useState<DetailsStatus>("loading");
  const [quantity, setQuantity] = useState(1);

  const listingDialogRef = useRef<HTMLDialogElement>(null);
  const latestDetailsRequestRef = useRef<number | null>(null);

  useEffect(() => {
    async function loadListings() {
      try {
        const res = await fetch("http://localhost:8100/api/productListing/active");
        if (!res.ok) {
          console.log(`Listings request failed: ${res.status}`);
          return;
        }
        const data: ListingStruct[] = await res.json();
        setListings(data);
      } catch (error) {
        console.log(error);
      }
    }

    loadListings();
  }, []);

  const seatsLeft = (listing: ListingStruct) => listing.quantityOffered - listing.quantityPurchased;

  const handleMoreInfo = async (listing: ListingStruct) => {
    setSelectedListing(listing);
    setRecipe(null);
    setAddress(null);
    setDetailsStatus("loading");
    setQuantity(1);
    latestDetailsRequestRef.current = listing.productListingId;
    listingDialogRef.current?.showModal();

    try {
      const [recipeRes, addressRes] = await Promise.all([
        fetch(`http://localhost:8100/api/recipe/${listing.recipeId}`),
        fetch(`http://localhost:8100/api/chef/address/${listing.addressId}`),
      ]);
      if (!recipeRes.ok) throw new Error(`Recipe request failed: ${recipeRes.status}`);
      if (!addressRes.ok) throw new Error(`Address request failed: ${addressRes.status}`);

      const recipeData: RecipeStruct = await recipeRes.json();
      const addressData: ChefAddress = await addressRes.json();

      if (latestDetailsRequestRef.current !== listing.productListingId) return;
      setRecipe(recipeData);
      setAddress(addressData);
      setDetailsStatus("loaded");
    } catch (error) {
      if (latestDetailsRequestRef.current !== listing.productListingId) return;
      setDetailsStatus("error");
      toast.error(error instanceof Error ? error.message : "Something went wrong");
    }
  };

  const handleAddToOrder = async () => {
    if (!selectedListing || customerId === null) return;

    const productListingId = selectedListing.productListingId;

    try {
      await sendJson<unknown>("POST", "http://localhost:8100/api/purchase", {
        customerId,
        purchaseEvents: [{productListingId, quantity}],
      });

      setListings(prev => prev.map(l =>
        l.productListingId === productListingId
          ? {...l, quantityPurchased: l.quantityPurchased + quantity}
          : l
      ));
      toast.success(`Ordered ${quantity} portion(s) of ${selectedListing.recipeName}`);
      listingDialogRef.current?.close();
    } catch (error) {
      toast.error(error instanceof Error ? error.message : "Something went wrong");
    }
  };

  const allergensInRecipe = recipe
    ? allergensList.filter(allergen => recipe[allergen.id])
    : [];

  return (
    <div className={styles["browse-layout"]}>
      <Banner/>
      <div className={styles["browse-wrapper"]}>
        <div className={styles["all-browse-items-container"]}>
          {listings.length === 0 && <p>No listings available right now.</p>}
          {listings.map((listing: ListingStruct) => (
            <div
              className={styles["browse-item-container"]}
              key={listing.productListingId}
            >
              <div className={styles["browse-item-picture-container"]}>
                <img src={listing.recipeUrl} alt={listing.recipeName}/>
              </div>
              <div className={styles["browse-item-info-container"]}>
                <div className={styles["name-and-edit-container"]}>
                  <div className={styles["browse-item-title"]}>{listing.recipeName}</div>
                  <button
                    className={styles["more-info-btn"]}
                    onClick={() => handleMoreInfo(listing)}
                  >
                    More info & Order
                  </button>
                </div>
                <div>Chef: {listing.chefName}</div>
                <div className={styles["browse-item-quantity-text"]}>
                  Price per portion: <span
                  className={styles["browse-item-quantity-numbers"]}>{listing.unitPrice} NOK</span>
                </div>
                <div className={styles["browse-item-quantity-text"]}>
                  Seats left: <span
                  className={styles["browse-item-quantity-numbers"]}>{seatsLeft(listing)} / {listing.quantityOffered}</span>
                </div>
                <div className={styles["order-detail-text"]}>
                  Chef's address: <span className={styles["order-highlight"]}>{listing.compositeAddress}</span>
                </div>
                <div className={styles["browse-item-ready-by"]}>Ready by: {listing.readyBy}</div>
                <div className={styles["browse-item-description"]}>{listing.recipeDescription}</div>
              </div>
            </div>
          ))}
        </div>

        <dialog
          className={styles["see-item-dialog"]}
          ref={listingDialogRef}
        >
          {selectedListing && (
            <>
              <div className={styles["modal-pic-name-close-container"]}>
                <div className={styles["browse-item-picture-container"]}>
                  <img src={selectedListing.recipeUrl} alt={selectedListing.recipeName}/>
                </div>
                <div>
                  <h1>{selectedListing.recipeName}</h1>
                  {recipe && (
                    <div className={styles["food-category-container"]}>
                      {DishCategory[recipe.foodCategory]}
                    </div>
                  )}
                </div>
                <div
                  className={styles["close-btn"]}
                  onClick={() => listingDialogRef.current?.close()}
                >
                  ❌
                </div>
              </div>

              <div>Chef: {selectedListing.chefName}</div>
              <div className={styles["order-detail-text"]}>
                Address: <span className={styles["order-highlight"]}>{selectedListing.compositeAddress}</span>
              </div>
              <div className={styles["modal-description"]}>{selectedListing.recipeDescription}</div>

              {detailsStatus === "loading" && <p>Loading allergens and home vibe…</p>}
              {detailsStatus === "error" && <p>Could not load allergens and home vibe.</p>}
              {detailsStatus === "loaded" && address && (
                <>
                  <div className={styles["modal-date-descriptions"]}>Allergens:</div>
                  <div className={styles["allergens-modal-list"]}>
                    {allergensInRecipe.length === 0
                      ? <div>None</div>
                      : allergensInRecipe.map(allergen => (
                        <div key={allergen.id}>{allergen.label}</div>
                      ))}
                  </div>

                  <div className={styles["modal-date-descriptions"]}>Home vibe:</div>
                  <div className={styles["modal-description"]}>
                    <p>{address.homeVibe.description}</p>
                    <div>Music: {GENRE_LABELS[address.homeVibe.music]}</div>
                    <div>Pets: {address.homeVibe.pets ? "Yes" : "No"}</div>
                    <div>Outdoor: {address.homeVibe.outdoor ? "Yes" : "No"}</div>
                    <div>Family friendly: {address.homeVibe.familyFriendly ? "Yes" : "No"}</div>
                  </div>
                </>
              )}

              <div className={styles["both-dates-container"]}>
                <div className={styles["date-container"]}>
                  <div className={styles["modal-date-descriptions"]}>Ready by:</div>
                  <div className={styles["modal-date-text"]}>{selectedListing.readyBy}</div>
                </div>
                <div>
                  <div className={styles["modal-date-descriptions"]}>Order by:</div>
                  <div className={styles["modal-date-text"]}>{selectedListing.purchaseDeadline}</div>
                </div>
              </div>

              {customerId === null ? (
                <p>Log in as a customer on the front page to order.</p>
              ) : (
                <>
                  <div className={styles["order-quantity-box"]}>
                    <button
                      className={styles["order-quantity-btn"]}
                      onClick={() => setQuantity(q => Math.min(seatsLeft(selectedListing), q + 1))}
                    >
                      +
                    </button>
                    <button
                      className={styles["order-quantity-btn"]}
                      onClick={() => setQuantity(q => Math.max(1, q - 1))}
                    >
                      -
                    </button>
                    <div>{quantity}</div>
                  </div>
                  <button
                    className={styles["add-to-order-btn"]}
                    onClick={handleAddToOrder}
                    disabled={seatsLeft(selectedListing) <= 0}
                  >
                    {seatsLeft(selectedListing) <= 0 ? "Sold out" : "Add to order"}
                  </button>
                </>
              )}
            </>
          )}
        </dialog>
      </div>
    </div>
  )
}