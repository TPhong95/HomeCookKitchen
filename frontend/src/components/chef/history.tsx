import {Banner} from "../general/banner.tsx";
import styles from "./history.module.css";
import {type ListingStruct} from "../shared/listinginterface.ts";
import {type ChefResponse} from "../shared/chefinterface.ts";
import {useEffect, useState} from "react";
import {useUserContext} from "../context/useUserContext.tsx";
import {formatDateTime} from "../util/time.ts";
import toast from "react-hot-toast";

export function History() {
  const {user} = useUserContext();
  const chefId = user?.role === "chef" ? user.id : null;

  const [name, setName] = useState("");
  const [historyListings, setHistoryListings] = useState<ListingStruct[]>([]);

  useEffect(() => {
    if (chefId === null) return;

    async function loadChef(id: number) {
      try {
        const res = await fetch(`http://localhost:8100/api/chef/${id}`);
        if (!res.ok) throw new Error(`Chef request failed: ${res.status}`);
        const chef: ChefResponse = await res.json();
        setName(chef.name);
      } catch (error) {
        toast.error(error instanceof Error ? error.message : "Something went wrong");
      }
    }

    async function loadHistory(id: number) {
      try {
        const res = await fetch(`http://localhost:8100/api/productListing/chef/${id}/history`);
        if (!res.ok) {
          // The backend may answer with an error when there is no history yet, so show an empty page
          console.log(`History request failed: ${res.status}`);
          return;
        }
        // Already ordered by readyBy in the backend
        const data: ListingStruct[] = await res.json();
        setHistoryListings(data);
      } catch (error) {
        // Only reached on network errors, e.g. the gateway is down
        console.log(error);
      }
    }

    loadChef(chefId);
    loadHistory(chefId);
  }, [chefId]);

  return (
    <div className={styles["listings-layout"]}>
      <Banner/>
      <div className={styles["listings-wrapper"]}>
        <div className={styles["listings-top-banner"]}>
          {name ? <h1>{name}'s Listing History</h1> : <h1>Listing History</h1>}
        </div>

        <div className={styles["all-active-listings-container"]}>
          {chefId === null ? (
            <p>Not logged in as a chef. Pick a chef on the front page.</p>
          ) : (
            <>
              {historyListings.length === 0 && <p>No past listings yet.</p>}
              {historyListings.map((listing) => {
                const revenue = listing.quantityPurchased * listing.unitPrice;
                return (
                  <div
                    className={styles["active-listing-item-container"]}
                    key={listing.productListingId}
                  >
                    <div className={styles["listing-picture-container"]}>
                      <img src={listing.recipeUrl} alt={listing.recipeName}/>
                    </div>
                    <div className={styles["listing-info-container"]}>
                      <div className={styles["name-and-edit-container"]}>
                        <div className={styles["listing-title"]}>{listing.recipeName}</div>
                      </div>

                      <div className={styles["listing-quantity-text"]}>
                        Total Revenue: <span
                        className={styles["listing-quantity-numbers"]}>{revenue.toFixed(2)} NOK</span>
                      </div>
                      <div className={styles["listing-quantity-text"]}>
                        Sold: <span
                        className={styles["listing-quantity-numbers"]}>{listing.quantityPurchased} / {listing.quantityOffered} portions</span>
                      </div>
                      <div className={styles["listing-price-container"]}>
                        Hosted: {formatDateTime(listing.readyBy)} at {listing.compositeAddress}
                      </div>
                    </div>
                  </div>
                );
              })}
            </>
          )}
        </div>
      </div>
    </div>
  );
}
