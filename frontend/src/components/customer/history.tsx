import { useRef, useState } from "react";
import styles from './history.module.css';
import { Banner } from "../general/banner.tsx";
import { type BrowseItem } from "../shared/browseinterface.ts";
import { DishCategory, DishPreference, Allergen } from "../shared/recipeinterface.ts";
import * as React from "react";

export interface HistoryItem extends BrowseItem {
  orderQuantity: number;
}

const testHistoryItems: HistoryItem[] = [
  {
    listingId: 101,
    chefId: 1,
    chefName: "George",
    chefStreet: "Ingenmannsland 3",
    chefPostalCode: "0123",
    chefCity: "Oslo",
    recipeName: "Falafel Baguette",
    description: "It tastes like falafel and baguette at the same time",
    image: "../../../testdata/falafelbaguette.jpg",
    category: DishCategory.CURRIES_AND_STEWS,
    preference: DishPreference.VEGAN,
    allergens: {
      [Allergen.GLUTEN]: true,
    },
    unitPrice: 50,
    quantity: 4,
    quantityPurchased: 4,
    readyBy: "2026-05-20T14:30",
    purchaseDeadline: "2026-05-20T12:00",
    orderQuantity: 2,
  },
  {
    listingId: 102,
    chefId: 2,
    chefName: "Joanne",
    chefStreet: "Hvavar Gata 7",
    chefPostalCode: "0549",
    chefCity: "Oslo",
    recipeName: "Lasagne",
    description: "It tastes like a cross between gravel and dead leaves.",
    image: "../../../testdata/lasagne.jpg",
    category: DishCategory.PASTA,
    preference: DishPreference.NONE,
    allergens: {
      [Allergen.MILK]: true,
      [Allergen.CELERY]: true,
      [Allergen.GLUTEN]: true,
    },
    unitPrice: 180,
    quantity: 6,
    quantityPurchased: 6,
    readyBy: "2026-05-22T17:00",
    purchaseDeadline: "2026-05-22T15:00",
    orderQuantity: 1,
  },
];

export function CustomerHistory() {
  const [historyItems] = useState<HistoryItem[]>(testHistoryItems);
  const [selectedHistoryItem, setSelectedHistoryItem] = useState<HistoryItem | null>(null);

  const seeHistoryItemDialogRef = useRef<HTMLDialogElement>(null);

  const handleOpenItemModal = (item: HistoryItem) => {
    setSelectedHistoryItem(item);
    seeHistoryItemDialogRef.current?.showModal();
  };

  return (
    <div className={styles["history-layout"]}>
      <Banner />
      <div className={styles["history-wrapper"]}>
        <div className={styles["history-top-banner"]}>
          <h1>Order History</h1>
        </div>

        <div className={styles["all-history-items-container"]}>
          {historyItems.map((item) => (
            <div
              className={styles["history-item-container"]}
              key={item.listingId}
              onClick={() => handleOpenItemModal(item)}
            >
              <div className={styles["history-picture-container"]}>
                <img src={item.image} alt={item.recipeName} />
              </div>
              <div className={styles["history-info-main"]}>
                <div className={styles["history-item-title"]}>{item.recipeName}</div>
                <div className={styles["history-item-chef"]}>
                  Chef: <span className={styles["chef-name"]}>{item.chefName}</span>
                </div>
                <div className={styles["order-detail-text"]}>
                  Chef's address: <span
                  className={styles["order-highlight"]}>{item.chefStreet}, {item.chefPostalCode} {item.chefCity}</span>
                </div>
                <div className={styles["history-ready-by"]}>Eaten at: {item.readyBy}</div>
              </div>
              <div className={styles["history-info-side"]}>
                <div className={styles["history-qty-text"]}>Number of guests: {item.orderQuantity}</div>
                <div className={styles["history-price-text"]}>
                  {(item.unitPrice * item.orderQuantity).toFixed(2)} NOK
                </div>
              </div>
            </div>
          ))}
        </div>

        <dialog
          className={styles["see-item-dialog"]}
          ref={seeHistoryItemDialogRef}
        >
          {selectedHistoryItem && (
            <>
              <div className={styles["modal-pic-name-close-container"]}>
                <div className={styles["history-picture-container"]}>
                  <img src={selectedHistoryItem.image} alt={selectedHistoryItem.recipeName} />
                </div>
                <div>
                  <h1>{selectedHistoryItem.recipeName}</h1>
                  <div className={styles["food-category-container"]}>{selectedHistoryItem.category}</div>
                </div>
                <div
                  className={styles["close-btn"]}
                  onClick={() => seeHistoryItemDialogRef.current?.close()}
                >
                  ❌
                </div>
              </div>
              <div className={styles["allergens-modal-list"]}>
                {Object.entries(selectedHistoryItem.allergens)
                  .filter(([_, hasAllergen]) => hasAllergen)
                  .map(([allergenKey]) => (
                    <div key={allergenKey}>{allergenKey}</div>
                  ))}
              </div>
              <div className={styles["modal-description"]}>{selectedHistoryItem.description}</div>
            </>
          )}
        </dialog>
      </div>
    </div>
  );
}