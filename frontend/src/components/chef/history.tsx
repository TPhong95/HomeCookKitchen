import {Banner} from "../general/banner.tsx";
import styles from "./history.module.css";
import {type ListingStruct} from "../shared/listinginterface.ts";
import {useState} from "react";

const testHistoryListings: ListingStruct[] = [
  {
    id: 101,
    image: "../../../testdata/falafelbaguette.jpg",
    recipeName: "Falafel Baguette",
    quantity: 10,
    quantityPurchased: 8,
    unitPrice: 100,
    readyBy: "2026-05-15T14:30",
    createdAt: "2026-05-10T11:00",
    purchaseDeadline: "2026-05-14T12:00",
  },
  {
    id: 102,
    image: "../../../testdata/lasagne.jpg",
    recipeName: "Lasagne",
    quantity: 8,
    quantityPurchased: 8,
    unitPrice: 180,
    readyBy: "2026-05-20T17:00",
    createdAt: "2026-05-15T11:00",
    purchaseDeadline: "2026-05-19T15:00",
  },
  {
    id: 103,
    image: "../../../testdata/falafelbaguette.jpg",
    recipeName: "Falafel Baguette",
    quantity: 5,
    quantityPurchased: 5,
    unitPrice: 100,
    readyBy: "2026-05-01T12:00",
    createdAt: "2026-04-28T10:00",
    purchaseDeadline: "2026-04-30T12:00",
  }
];

export function History() {
  const name = "Geoffrey";
  const [historyListings] = useState<ListingStruct[]>(testHistoryListings);

  const sortedHistory = [...historyListings].sort(
    (a, b) => new Date(a.readyBy).getTime() - new Date(b.readyBy).getTime()
  );

  return (
    <div className={styles["listings-layout"]}>
      <Banner/>
      <div className={styles["listings-wrapper"]}>
        <div className={styles["listings-top-banner"]}>
          <h1>{name}'s Listing History</h1>
        </div>

        <div className={styles["all-active-listings-container"]}>
          {sortedHistory.map((listing) => {
            const revenue = (listing.quantityPurchased ?? 0) * (listing.unitPrice ?? 0);
            return (
              <div
                className={styles["active-listing-item-container"]}
                key={listing.id}
              >
                <div className={styles["listing-picture-container"]}>
                  <img src={listing.image} alt={listing.recipeName} />
                </div>
                <div className={styles["listing-info-container"]}>
                  <div className={styles["name-and-edit-container"]}>
                    <div className={styles["listing-title"]}>{listing.recipeName}</div>
                  </div>

                  <div className={styles["listing-quantity-text"]}>
                    Total Revenue: <span className={styles["listing-quantity-numbers"]}>{revenue.toFixed(2)} NOK</span>
                  </div>
                  <div className={styles["listing-quantity-text"]}>
                    Sold: <span className={styles["listing-quantity-numbers"]}>{listing.quantityPurchased} / {listing.quantity} portions</span>
                  </div>
                  <div className={styles["listing-price-container"]}>Hosted at: {listing.readyBy}</div>
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}