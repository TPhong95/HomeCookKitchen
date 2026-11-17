import {useEffect, useRef, useState} from "react";
import styles from './purchases.module.css';
import {Banner} from "../general/banner.tsx";
import {type RecipeStruct, allergensList, DishCategory} from "../shared/recipeinterface.ts";
import {type ChefAddress, GENRE_LABELS} from "../shared/chefinterface.ts";
import {type OrderItem} from "../shared/purchaseinterface.ts";
import {useUserContext} from "../context/useUserContext.tsx";
import {loadCustomerOrders, readyByTime} from "../util/purchases.ts";
import {formatDateTime} from "../util/time.ts";
import toast from "react-hot-toast";

type DetailsStatus = "loading" | "loaded" | "error";

export function Purchases() {
  const {user} = useUserContext();
  const customerId = user?.role === "customer" ? user.id : null;

  const [orders, setOrders] = useState<OrderItem[]>([]);
  const [selectedOrder, setSelectedOrder] = useState<OrderItem | null>(null);
  const [recipe, setRecipe] = useState<RecipeStruct | null>(null);
  const [address, setAddress] = useState<ChefAddress | null>(null);
  const [detailsStatus, setDetailsStatus] = useState<DetailsStatus>("loading");

  const orderDialogRef = useRef<HTMLDialogElement>(null);
  const latestDetailsRequestRef = useRef<number | null>(null);

  useEffect(() => {
    if (customerId === null) return;

    async function loadOrders(id: number) {
      try {
        const allOrders = await loadCustomerOrders(id);
        const now = Date.now();
        setOrders(allOrders
          .filter(order => readyByTime(order) >= now)
          .sort((a, b) => readyByTime(a) - readyByTime(b)));
      } catch (error) {
        toast.error(error instanceof Error ? error.message : "Something went wrong");
      }
    }

    loadOrders(customerId);
  }, [customerId]);

  const handleOpenOrderModal = async (order: OrderItem) => {
    const listing = order.listing;
    setSelectedOrder(order);
    setRecipe(null);
    setAddress(null);
    setDetailsStatus("loading");
    latestDetailsRequestRef.current = listing.productListingId;
    orderDialogRef.current?.showModal();

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
    <div className={styles["orders-layout"]}>
      <Banner/>
      <div className={styles["orders-wrapper"]}>
        <div className={styles["orders-top-banner"]}>
          <h1>Upcoming Dinners</h1>
        </div>

        <div className={styles["all-orders-container"]}>
          {customerId === null ? (
            <p>Not logged in as a customer. Pick a customer on the front page.</p>
          ) : (
            <>
              {orders.length === 0 && <p>No upcoming dinners. Browse to find a dinner to attend!</p>}
              {orders.map((order) => (
                <div
                  className={styles["order-item-container"]}
                  key={order.listing.productListingId}
                >
                  <div className={styles["order-picture-container"]}>
                    <img src={order.listing.recipeUrl} alt={order.listing.recipeName}/>
                  </div>
                  <div className={styles["order-info-container"]}>
                    <div className={styles["name-and-info-container"]}>
                      <div className={styles["order-title"]}>{order.listing.recipeName}</div>
                      <button
                        className={styles["more-info-btn"]}
                        onClick={() => handleOpenOrderModal(order)}
                      >
                        More info
                      </button>
                    </div>
                    <div className={styles["order-detail-text"]}>
                      Chef: <span className={styles["order-highlight"]}>{order.listing.chefName}</span>
                    </div>
                    <div className={styles["order-detail-text"]}>
                      You've reserved: <span className={styles["order-highlight"]}>
                        {order.quantity} {order.quantity === 1 ? "seat" : "seats"}
                      </span>
                    </div>
                    <div className={styles["order-detail-text"]}>
                      Total Price: <span className={styles["order-highlight"]}>
                        {(order.listing.unitPrice * order.quantity).toFixed(2)} NOK
                      </span>
                    </div>
                    <div className={styles["order-detail-text"]}>
                      Food is served at: <span className={styles["order-highlight"]}>{formatDateTime(order.listing.readyBy)}</span>
                    </div>
                    <div className={styles["order-detail-text"]}>
                      Chef's address: <span className={styles["order-highlight"]}>{order.listing.compositeAddress}</span>
                    </div>
                  </div>
                </div>
              ))}
            </>
          )}
        </div>

        <dialog
          className={styles["see-item-dialog"]}
          ref={orderDialogRef}
        >
          {selectedOrder && (
            <>
              <div className={styles["modal-pic-name-close-container"]}>
                <div className={styles["order-picture-container"]}>
                  <img src={selectedOrder.listing.recipeUrl} alt={selectedOrder.listing.recipeName}/>
                </div>
                <div>
                  <h1>{selectedOrder.listing.recipeName}</h1>
                  {recipe && (
                    <div className={styles["food-category-container"]}>
                      {DishCategory[recipe.foodCategory]}
                    </div>
                  )}
                </div>
                <div
                  className={styles["close-btn"]}
                  onClick={() => orderDialogRef.current?.close()}
                >
                  ❌
                </div>
              </div>

              <div>Chef: {selectedOrder.listing.chefName}</div>
              <div className={styles["order-detail-text"]}>
                Address: <span className={styles["order-highlight"]}>{selectedOrder.listing.compositeAddress}</span>
              </div>
              <div className={styles["modal-description"]}>{selectedOrder.listing.recipeDescription}</div>

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
                <strong>Served at:</strong> {formatDateTime(selectedOrder.listing.readyBy)} · <strong>Seats:</strong> {selectedOrder.quantity}
                · <strong>Total:</strong> {(selectedOrder.listing.unitPrice * selectedOrder.quantity).toFixed(2)} NOK
              </div>
            </>
          )}
        </dialog>
      </div>
    </div>
  );
}
