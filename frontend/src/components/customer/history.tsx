import {useEffect, useRef, useState} from "react";
import styles from './history.module.css';
import {Banner} from "../general/banner.tsx";
import {type RecipeStruct, allergensList, DishCategory} from "../shared/recipeinterface.ts";
import {type ListingStruct} from "../shared/listinginterface.ts";
import {type ChefAddress, GENRE_LABELS} from "../shared/chefinterface.ts";
import {type OrderItem, type PurchasedListingQuantityDto} from "../shared/purchaseinterface.ts";
import {useUserContext} from "../context/useUserContext.tsx";
import toast from "react-hot-toast";

type DetailsStatus = "loading" | "loaded" | "error";

export function CustomerHistory() {
  const {user} = useUserContext();
  const customerId = user?.role === "customer" ? user.id : null;

  const [historyItems, setHistoryItems] = useState<OrderItem[]>([]);
  const [selectedItem, setSelectedItem] = useState<OrderItem | null>(null);
  const [recipe, setRecipe] = useState<RecipeStruct | null>(null);
  const [address, setAddress] = useState<ChefAddress | null>(null);
  const [detailsStatus, setDetailsStatus] = useState<DetailsStatus>("loading");

  const historyItemDialogRef = useRef<HTMLDialogElement>(null);
  const latestDetailsRequestRef = useRef<number | null>(null);

  useEffect(() => {
    if (customerId === null) return;

    async function loadHistory(id: number) {
      try {
        const res = await fetch(`http://localhost:8100/api/purchase/customer/${id}`);
        if (!res.ok) {
          console.log(`Purchase request failed: ${res.status}`);
          return;
        }
        const purchased: PurchasedListingQuantityDto[] = await res.json();

        // Fetch every purchased listing in parallel. allSettled, so one missing listing
        // (e.g. deleted by the chef) doesn't hide all the others
        const results = await Promise.allSettled(purchased.map(async (p): Promise<OrderItem> => {
          const listingRes = await fetch(`http://localhost:8100/api/productListing/${p.productListingId}`);
          if (!listingRes.ok) throw new Error(`Listing ${p.productListingId} failed: ${listingRes.status}`);
          const listing: ListingStruct = await listingRes.json();
          return {listing, quantity: p.quantity};
        }));

        results
          .filter(r => r.status === "rejected")
          .forEach(r => console.log(r.reason));

        const items = results
          .filter((r): r is PromiseFulfilledResult<OrderItem> => r.status === "fulfilled")
          .map(r => r.value);

        const now = Date.now();
        const pastItems = items
          .filter(item => new Date(item.listing.readyBy).getTime() < now)
          .sort((a, b) => new Date(b.listing.readyBy).getTime() - new Date(a.listing.readyBy).getTime());

        setHistoryItems(pastItems);
      } catch (error) {
        // Only reached on network errors, e.g. the gateway is down
        console.log(error);
      }
    }

    loadHistory(customerId);
  }, [customerId]);

  const handleOpenItemModal = async (item: OrderItem) => {
    const listing = item.listing;

    // Clear the previous item's details before opening, so nothing stale is shown while loading
    setSelectedItem(item);
    setRecipe(null);
    setAddress(null);
    setDetailsStatus("loading");
    latestDetailsRequestRef.current = listing.productListingId;
    historyItemDialogRef.current?.showModal();

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

  const allergensInRecipe = recipe
    ? allergensList.filter(allergen => recipe[allergen.id])
    : [];

  return (
    <div className={styles["history-layout"]}>
      <Banner/>
      <div className={styles["history-wrapper"]}>
        <div className={styles["history-top-banner"]}>
          <h1>Order History</h1>
        </div>

        <div className={styles["all-history-items-container"]}>
          {customerId === null ? (
            <p>Not logged in as a customer. Pick a customer on the front page.</p>
          ) : (
            <>
              {historyItems.length === 0 && <p>No past orders yet.</p>}
              {historyItems.map((item) => (
                <div
                  className={styles["history-item-container"]}
                  key={item.listing.productListingId}
                  onClick={() => handleOpenItemModal(item)}
                >
                  <div className={styles["history-picture-container"]}>
                    <img src={item.listing.recipeUrl} alt={item.listing.recipeName}/>
                  </div>
                  <div className={styles["history-info-main"]}>
                    <div className={styles["history-item-title"]}>{item.listing.recipeName}</div>
                    <div className={styles["history-item-chef"]}>
                      Chef: <span className={styles["chef-name"]}>{item.listing.chefName}</span>
                    </div>
                    <div className={styles["order-detail-text"]}>
                      Chef's address: <span className={styles["order-highlight"]}>{item.listing.compositeAddress}</span>
                    </div>
                    <div className={styles["history-ready-by"]}>Eaten at: {item.listing.readyBy}</div>
                  </div>
                  <div className={styles["history-info-side"]}>
                    <div className={styles["history-qty-text"]}>Number of guests: {item.quantity}</div>
                    <div className={styles["history-price-text"]}>
                      {(item.listing.unitPrice * item.quantity).toFixed(2)} NOK
                    </div>
                  </div>
                </div>
              ))}
            </>
          )}
        </div>

        <dialog
          className={styles["see-item-dialog"]}
          ref={historyItemDialogRef}
        >
          {selectedItem && (
            <>
              <div className={styles["modal-pic-name-close-container"]}>
                <div className={styles["history-picture-container"]}>
                  <img src={selectedItem.listing.recipeUrl} alt={selectedItem.listing.recipeName}/>
                </div>
                <div>
                  <h1>{selectedItem.listing.recipeName}</h1>
                  {recipe && (
                    <div className={styles["food-category-container"]}>
                      {DishCategory[recipe.foodCategory]}
                    </div>
                  )}
                </div>
                <div
                  className={styles["close-btn"]}
                  onClick={() => historyItemDialogRef.current?.close()}
                >
                  ❌
                </div>
              </div>

              <div>Chef: {selectedItem.listing.chefName}</div>
              <div className={styles["order-detail-text"]}>
                Address: <span className={styles["order-highlight"]}>{selectedItem.listing.compositeAddress}</span>
              </div>
              <div className={styles["modal-description"]}>{selectedItem.listing.recipeDescription}</div>

              {detailsStatus === "loading" && <p>Loading allergens and home vibe…</p>}
              {detailsStatus === "error" && <p>Could not load allergens and home vibe.</p>}
              {detailsStatus === "loaded" && address && (
                <>
                  <div><strong>Allergens:</strong></div>
                  <div className={styles["allergens-modal-list"]}>
                    {allergensInRecipe.length === 0
                      ? <div>None</div>
                      : allergensInRecipe.map(allergen => (
                        <div key={allergen.id}>{allergen.label}</div>
                      ))}
                  </div>

                  <div><strong>Home vibe:</strong></div>
                  <div className={styles["modal-description"]}>
                    <p>{address.homeVibe.description}</p>
                    <div>Music: {GENRE_LABELS[address.homeVibe.music]}</div>
                    <div>Pets: {address.homeVibe.pets ? "Yes" : "No"}</div>
                    <div>Outdoor: {address.homeVibe.outdoor ? "Yes" : "No"}</div>
                    <div>Family friendly: {address.homeVibe.familyFriendly ? "Yes" : "No"}</div>
                  </div>
                </>
              )}

              <div>
                <strong>Eaten at:</strong> {selectedItem.listing.readyBy} · <strong>Guests:</strong> {selectedItem.quantity}
                · <strong>Paid:</strong> {(selectedItem.listing.unitPrice * selectedItem.quantity).toFixed(2)} NOK
              </div>
            </>
          )}
        </dialog>
      </div>
    </div>
  );
}