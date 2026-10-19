import { useRef, useState } from "react";
import styles from './cart.module.css';
import { Banner } from "../general/banner.tsx";
import { type BrowseItem } from "../shared/browseinterface.ts";
import { useNavigate } from "react-router-dom";
import {DishCategory, DishPreference, Allergen} from "../shared/recipeinterface.ts";
import * as React from "react";

export interface CartItem extends BrowseItem {
  orderQuantity: number;
}

const testCartItems: CartItem[] = [
  {
    listingId: 1,
    chefId: 1,
    chefName: "George",
    chefStreet: "Igokk Etsted 560123",
    chefPostalCode: "0999",
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
    quantityPurchased: 3,
    readyBy: "2026-06-06T14:30",
    purchaseDeadline: "2026-06-06T12:00",
    orderQuantity: 2,
  },
  {
    listingId: 2,
    chefId: 2,
    chefName: "Joanne",
    chefStreet: "Jada gata 55",
    chefPostalCode: "0865",
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
    quantityPurchased: 4,
    readyBy: "2026-06-07T17:00",
    purchaseDeadline: "2026-06-07T15:00",
    orderQuantity: 1,
  },
];

export function Cart() {
  const navigate = useNavigate();
  const [cartItems, setCartItems] = useState<CartItem[]>(testCartItems);
  const [selectedCartItem, setSelectedCartItem] = useState<CartItem | null>(null);
  const [tempOrderQuantity, setTempOrderQuantity] = useState<number>(1);

  const seeCartItemDialogRef = useRef<HTMLDialogElement>(null);
  const checkoutDialogRef = useRef<HTMLDialogElement>(null);

  const handleOpenItemModal = (item: CartItem) => {
    setSelectedCartItem(item);
    setTempOrderQuantity(item.orderQuantity);
    seeCartItemDialogRef.current?.showModal();
  };

  const handleSaveItemQuantity = () => {
    if (!selectedCartItem) return;
    setCartItems(prev =>
      prev.map(item =>
        item.listingId === selectedCartItem.listingId
          ? { ...item, orderQuantity: tempOrderQuantity }
          : item
      )
    );
    seeCartItemDialogRef.current?.close();
  };

  const calculateTotal = () => {
    return cartItems.reduce((sum, item) => sum + item.unitPrice * item.orderQuantity, 0);
  };

  const handleCheckoutClick = () => {
    checkoutDialogRef.current?.showModal();
  };

  const handleConfirmOrder = () => {
    // Mock backend call to write order to database
    console.log("Writing order to database...", cartItems);
    checkoutDialogRef.current?.close();
    navigate("/user/customer/orders");
  };

  return (
    <div className={styles["cart-layout"]}>
      <Banner />
      <div className={styles["cart-wrapper"]}>
        <div className={styles["cart-top-banner"]}>
          <h1>Shopping Cart</h1>
        </div>

        <div className={styles["all-cart-items-container"]}>
          {cartItems.map((item) => (
            <div
              className={styles["cart-item-container"]}
              key={item.listingId}
              onClick={() => handleOpenItemModal(item)}
            >
              <div className={styles["cart-picture-container"]}>
                <img src={item.image} alt={item.recipeName} />
              </div>
              <div className={styles["cart-info-main"]}>
                <div className={styles["cart-item-title"]}>{item.recipeName}</div>
                <div className={styles["cart-item-chef"]}>Chef: <span className={styles["chef-name"]}>{item.chefName}</span></div>
                <div className={styles["order-detail-text"]}>
                  Chef's address: <span
                  className={styles["order-highlight"]}>{item.chefStreet}, {item.chefPostalCode} {item.chefCity}</span>
                </div>
                <div className={styles["cart-ready-by"]}>Ready by: {item.readyBy}</div>
              </div>
              <div className={styles["cart-info-side"]}>
                <div className={styles["cart-qty-text"]}>Number of guests: {item.orderQuantity}</div>
                <div className={styles["cart-price-text"]}>
                  {(item.unitPrice * item.orderQuantity).toFixed(2)} NOK
                </div>
              </div>
            </div>
          ))}
        </div>

        <div className={styles["cart-footer"]}>
          <div className={styles["cart-total-display"]}>
            Total: <span>{calculateTotal().toFixed(2)} NOK</span>
          </div>
          <button
            className={styles["checkout-btn"]}
            onClick={handleCheckoutClick}
          >
            Checkout
          </button>
        </div>

        {/* Item Details & Edit Modal */}
        <dialog
          className={styles["see-item-dialog"]}
          ref={seeCartItemDialogRef}
        >
          {selectedCartItem && (
            <>
              <div className={styles["modal-pic-name-close-container"]}>
                <div className={styles["cart-picture-container"]}>
                  <img src={selectedCartItem.image} alt={selectedCartItem.recipeName} />
                </div>
                <div>
                  <h1>{selectedCartItem.recipeName}</h1>
                  <div className={styles["food-category-container"]}>{selectedCartItem.category}</div>
                </div>
                <div
                  className={styles["close-btn"]}
                  onClick={() => seeCartItemDialogRef.current?.close()}
                >
                  ❌
                </div>
              </div>
              <div className={styles["allergens-modal-list"]}>
                {Object.entries(selectedCartItem.allergens)
                  .filter(([_, hasAllergen]) => hasAllergen)
                  .map(([allergenKey]) => (
                    <div key={allergenKey}>{allergenKey}</div>
                  ))}
              </div>
              <div className={styles["modal-description"]}>{selectedCartItem.description}</div>
              <div className={styles["both-dates-container"]}>
                <div className={styles["date-container"]}>
                  <div className={styles["modal-date-descriptions"]}>Ready by:</div>
                  <div className={styles["modal-date-text"]}>{selectedCartItem.readyBy}</div>
                </div>
                <div>
                  <div className={styles["modal-date-descriptions"]}>Order by:</div>
                  <div className={styles["modal-date-text"]}>{selectedCartItem.purchaseDeadline}</div>
                </div>
              </div>
              <div className={styles["order-quantity-box"]}>
                <button
                  className={styles["order-quantity-btn"]}
                  onClick={() => setTempOrderQuantity(prev => Math.max(1, prev + 1))}
                >
                  +
                </button>
                <button
                  className={styles["order-quantity-btn"]}
                  onClick={() => setTempOrderQuantity(prev => Math.max(1, prev - 1))}
                >
                  -
                </button>
                <div>{tempOrderQuantity}</div>
              </div>
              <button
                className={styles["add-to-order-btn"]}
                onClick={handleSaveItemQuantity}
              >
                Update Cart Quantity
              </button>
            </>
          )}
        </dialog>

        {/* Checkout Confirmation Modal */}
        <dialog
          className={styles["checkout-dialog"]}
          ref={checkoutDialogRef}
        >
          <div className={styles["checkout-question"]}>Are you sure you want to place this order?</div>
          <div className={styles["checkout-btn-box"]}>
            <button
              className={styles["yes-btn"]}
              onClick={handleConfirmOrder}
            >
              Yes
            </button>
            <button
              className={styles["no-btn"]}
              onClick={() => checkoutDialogRef.current?.close()}
            >
              No
            </button>
          </div>
        </dialog>
      </div>
    </div>
  );
}