import {useState} from "react";
import styles from './orders.module.css';
import {Banner} from "../general/banner.tsx";
import {type ActiveOrder} from "../shared/purchaseinterface.ts";

const testOrders: ActiveOrder[] = [
  {
    listingId: 1,
    chefId: 1,
    chefName: "George",
    chefStreet: "Storgata 12",
    chefPostalCode: "0155",
    chefCity: "Oslo",
    recipeName: "Falafel Baguette",
    image: "../../../testdata/falafelbaguette.jpg",
    totalPrice: 200,
    numberOfGuests: 2,
    readyBy: "2026-06-06T14:30",
    purchaseDeadline: "2026-06-06T12:00",
  },
  {
    listingId: 2,
    chefId: 2,
    chefName: "Joanne",
    chefStreet: "Pilestredet 32",
    chefPostalCode: "0167",
    chefCity: "Oslo",
    recipeName: "Lasagne",
    image: "../../../testdata/lasagne.jpg",
    totalPrice: 180,
    numberOfGuests: 1,
    readyBy: "2026-06-07T17:00",
    purchaseDeadline: "2026-06-07T15:00",
  },
];

export function Purchases() {
  const [orders] = useState<ActiveOrder[]>(testOrders);

  return (
    <div className={styles["orders-layout"]}>
      <Banner/>
      <div className={styles["orders-wrapper"]}>
        <div className={styles["orders-top-banner"]}>
          <h1>Active Orders</h1>
        </div>

        <div className={styles["all-orders-container"]}>
          {orders.map((order) => (
            <div
              className={styles["order-item-container"]}
              key={order.listingId}
            >
              <div className={styles["order-picture-container"]}>
                <img src={order.image} alt={order.recipeName}/>
              </div>
              <div className={styles["order-info-container"]}>
                <div className={styles["order-title"]}>{order.recipeName}</div>
                <div className={styles["order-detail-text"]}>
                  Chef: <span className={styles["order-highlight"]}>{order.chefName}</span>
                </div>
                {/* sorry for the mess, grammar rules solved with spaghetti */}
                <div className={styles["order-detail-text"]}>
                  <span>You've reserved: </span>
                  <span className={styles["order-highlight"]}>
                     {order.numberOfGuests}
                  </span>
                  <span> seat</span>{order.numberOfGuests > 1 && (<span>s</span>)}
                </div>
                <div className={styles["order-detail-text"]}>
                  Total Price: <span className={styles["order-highlight"]}>{order.totalPrice} NOK</span>
                </div>
                <div className={styles["order-detail-text"]}>
                  Food is served at: <span className={styles["order-highlight"]}>{order.readyBy}</span>
                </div>
                <div className={styles["order-detail-text"]}>
                  Chef's address: <span
                  className={styles["order-highlight"]}>{order.chefStreet}, {order.chefPostalCode} {order.chefCity}</span>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}