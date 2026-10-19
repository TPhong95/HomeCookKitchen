import {useRef, useState} from "react";
import * as React from "react";
import {useNavigate} from "react-router-dom";
import styles from './browse.module.css';
import {Banner} from "../general/banner.tsx";
import {
  Allergen,
  DishPreference,
  initialAllergens,
  allergensList,
  DishCategory
} from "../shared/recipeinterface.ts";
import {type BrowseItem} from "../shared/browseinterface.ts";


const testBrowseItems: BrowseItem[] = [
  {
    listingId: 1,
    chefId: 1,
    chefName: "George",
    chefStreet: "Ingenmannsland 3",
    chefPostalCode: "0123",
    chefCity: "Oslo",
    recipeName: "Falafel Baguette",
    description: "It tastes like falafel and baguette at the same time",
    image: "../../../testdata/falafelbaguette.jpg",
    category: DishCategory.PASTA,
    preference: DishPreference.VEGAN,
    allergens: {
      [Allergen.GLUTEN]: true,
    },
    unitPrice: 50,
    quantity: 4,
    quantityPurchased: 3,
    readyBy: "2026-06-06T14:30",
    purchaseDeadline: "2026-06-06T12:00",
  },
  {
    listingId: 2,
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
    quantityPurchased: 4,
    readyBy: "2026-06-07T17:00",
    purchaseDeadline: "2026-06-07T15:00",
  },
  {
    listingId: 3,
    chefId: 1,
    chefName: "George",
    chefStreet: "Ingenmannsland 3",
    chefPostalCode: "0123",
    chefCity: "Oslo",
    recipeName: "Chicken Salad",
    description: "It's chicken... IN A SALAD WOW!! Never been done before",
    image: "../../../testdata/chickensalad.jpg",
    category: DishCategory.CURRIES_AND_STEWS,
    preference: DishPreference.NONE,
    allergens: {},
    unitPrice: 40,
    quantity: 4,
    quantityPurchased: 4,
    readyBy: "2026-06-08T16:30",
    purchaseDeadline: "2026-06-07T16:00",
  },
];

export function BrowseListing() {
  const navigate = useNavigate();

  const [searchQuery, setSearchQuery] = useState("");
  const [orderQuantity, setOrderQuantity] = useState(0);

  const [browseItems, /* setBrowseItems */] = useState<BrowseItem[]>(testBrowseItems);
  const [selectedBrowseItem, setSelectedBrowseItem] = useState<BrowseItem | null>(null);

  const [tempPreference, setTempPreference] = useState<DishPreference>(DishPreference.NONE);
  const [tempCategory, setTempCategory] = useState<DishCategory>(DishCategory.BURGERS);
  const [tempAllergens, setTempAllergens] = useState<Record<Allergen, boolean>>(initialAllergens);
  /* create a way for page to remember which allergens were previously selected so user doesn't have
  to keep re-selecting every time they edit their preferences */

  const showFilterDialogRef = useRef<HTMLDialogElement>(null);
  const seeBrowseItemDialogRef = useRef<HTMLDialogElement>(null);
  const cartOrBrowseDialogRef = useRef<HTMLDialogElement>(null);

  const handleSearchForListing = (e: React.SyntheticEvent) => {
    e.preventDefault();
    console.log("Searching listings:", {
      query: searchQuery,
      category: tempCategory,
      preference: tempPreference,
      allergens: tempAllergens
    });
  }

  const handleEditPreferencesModal = () => {
    showFilterDialogRef.current?.showModal();
  }

  const handleSavePreferences = () => {
    showFilterDialogRef.current?.close();
  }

  const handleResetPreferences = () => {
    setTempPreference(DishPreference.NONE);
    setTempCategory(DishCategory.BURGERS);
    setTempAllergens(initialAllergens);
    showFilterDialogRef.current?.close();
  }

  const handleAllergenChange = (allergenKey: Allergen, checked: boolean) => {
    setTempAllergens(prev => ({
      ...prev,
      [allergenKey]: checked
    }));
  };

  const handleMoreInfo = (browseItem: BrowseItem) => {
    setSelectedBrowseItem(browseItem);
    seeBrowseItemDialogRef.current?.showModal();
  }

  const handleAddToOrderButton = () => {
    if (!selectedBrowseItem) return;

    console.log("Added to order:", {
      // FIX backend needs customerId
      listingId: selectedBrowseItem.listingId,
      quantity: orderQuantity,
    });

    seeBrowseItemDialogRef.current?.close();
    cartOrBrowseDialogRef.current?.showModal();
  };

  const handleGoToCart = () => {
    cartOrBrowseDialogRef.current?.close();
    navigate("/user/customer/cart");
  }


  return (
    <div className={styles["browse-layout"]}>
      <Banner/>
      <div className={styles["browse-wrapper"]}>
        <div className={styles["search-bar"]}>
          <form onSubmit={handleSearchForListing}>
            <button className={styles["search-icon"]}>🔎</button>
            <input
              id={"search-bar"}
              type={"text"}
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              placeholder={"e.g. Veggie Burger"}
            />
            <button
              type={"button"}
              className={styles["filter-btn"]}
              onClick={handleEditPreferencesModal}
            >
              Filter
            </button>
          </form>
        </div>

        <div className={styles["all-browse-items-container"]}>
          {browseItems.map((browseItem: BrowseItem) => (
            <div
              className={styles["browse-item-container"]}
              key={browseItem.listingId}
            >
              <div className={styles["browse-item-picture-container"]}>
                <img src={browseItem.image} alt={browseItem.recipeName} />
              </div>
              <div className={styles["browse-item-info-container"]}>
                <div className={styles["name-and-edit-container"]}>
                  <div className={styles["browse-item-title"]}>{browseItem.recipeName}</div>
                  <button
                    className={styles["more-info-btn"]}
                    onClick={() => handleMoreInfo(browseItem)}
                  >
                    More info & Order
                  </button>
                </div>
                <div className={"chef-name-box"}>Chef: {browseItem.chefName}</div>
                <div className={styles["browse-item-quantity-text"]}>
                  Price per portion: <span className={styles["browse-item-quantity-numbers"]}>{browseItem.unitPrice} NOK</span>
                </div>
                <div className={styles["browse-item-quantity-text"]}>
                  Seats left: <span className={styles["browse-item-quantity-numbers"]}>{browseItem.quantity - browseItem.quantityPurchased} / {browseItem.quantity}</span>
                </div>
                <div className={styles["order-detail-text"]}>
                  Chef's address: <span
                  className={styles["order-highlight"]}>{browseItem.chefStreet}, {browseItem.chefPostalCode} {browseItem.chefCity}</span>
                </div>
                <div className={styles["browse-item-ready-by"]}>Ready by: {browseItem.readyBy}</div>
                <div className={styles["browse-item-description"]}>{browseItem.description}</div>
              </div>
            </div>
          ))}
        </div>

        <dialog
          className={styles["see-item-dialog"]}
          ref={seeBrowseItemDialogRef}
        >
          {selectedBrowseItem && (
            <>
              <div className={styles["modal-pic-name-close-container"]}>
                <div className={styles["browse-item-picture-container"]}>
                  <img src={selectedBrowseItem.image} alt={selectedBrowseItem.recipeName} />
                </div>
                <div>
                  <h1>{selectedBrowseItem.recipeName}</h1>
                  <div className={styles["food-category-container"]}>{selectedBrowseItem.category}</div>
                </div>
                <div
                  className={styles["close-btn"]}
                  onClick={() => seeBrowseItemDialogRef.current?.close()}
                >
                  ❌
                </div>
              </div>
              <div className={"chef-dialog-info-box"}>Chef: {selectedBrowseItem.chefName}</div>
              <div className={styles["allergens-modal-list"]}>
                {Object.entries(selectedBrowseItem.allergens)
                  .filter(([_, hasAllergen]) => hasAllergen)
                  .map(([allergenKey]) => (
                    <div key={allergenKey}>{allergenKey}</div>
                  ))}
              </div>
              <div className={styles["modal-description"]}>{selectedBrowseItem.description}</div>
              <div className={styles["both-dates-container"]}>
                <div className={styles["date-container"]}>
                  <div className={styles["modal-date-descriptions"]}>Ready by:</div>
                  <div className={styles["modal-date-text"]}>{selectedBrowseItem.readyBy}</div>
                </div>
                <div>
                  <div className={styles["modal-date-descriptions"]}>Order by:</div>
                  <div className={styles["modal-date-text"]}>{selectedBrowseItem.purchaseDeadline}</div>
                </div>
              </div>
              <div className={styles["order-quantity-box"]}>
                <button
                  className={styles["order-quantity-btn"]}
                  onClick={() => setOrderQuantity(orderQuantity + 1)}
                >
                  +
                </button>
                <button
                  className={styles["order-quantity-btn"]}
                  onClick={() => setOrderQuantity(orderQuantity - 1)}
                >
                  -
                </button>
                <div>{orderQuantity}</div>
              </div>
              <button
                className={styles["add-to-order-btn"]}
                onClick={handleAddToOrderButton}
              >
                Add to order
              </button>
            </>
          )}

        </dialog>

        <dialog
          className={styles["cart-or-browse-dialog"]}
          ref={cartOrBrowseDialogRef}
        >
          {selectedBrowseItem && (
            <>
              <div className={styles["add-to-cart-txt"]}>{selectedBrowseItem.recipeName} has been added to cart! Keep browsing, or go to cart?</div>
              <button
                className={styles["go-to-cart-btn"]}
                onClick={handleGoToCart}
              >
                Go to cart
              </button>
              <button
                className={styles["keep-browsing-btn"]}
                onClick={() => cartOrBrowseDialogRef.current?.close()}
              >
                Continue browsing
              </button>
            </>
          )}

        </dialog>

        <dialog
          className={styles["filter-dialog"]}
          ref={showFilterDialogRef}
        >

          <div className={styles["category-dropdowns"]}>
            <div className={styles["form-field"]}>
              <label htmlFor={"category-input"}>Category:</label>
              <select
                id={"category-input"}
                value={tempCategory}
                onChange={(e) => setTempCategory(e.target.value as DishCategory)}
              >
                {Object.values(DishCategory).map((value) => (
                  <option key={value} value={value}>{value}</option>
                ))}
              </select>
            </div>

            <div className={styles["form-field"]}>
              <label htmlFor={"preference-input"}>Diet preference:</label>
              <select
                id={"preference-input"}
                value={tempPreference}
                onChange={(e) => setTempPreference(e.target.value as DishPreference)}
              >
                {Object.values(DishPreference).map((value) => (
                  <option key={value} value={value}>{value}</option>
                ))}
              </select>
            </div>
          </div>

          <div className={styles["form-field"]}>
            <div className={styles["allergens-title"]}>Omit allergens:</div>
            <div className={styles["allergens-checkboxes"]}>
              {allergensList.map((allergen) => (
                <div className={styles["allergen-item"]} key={allergen.id}>
                  <input
                    id={`${allergen.id}-checkbox`}
                    type={"checkbox"}
                    name={`${allergen.id}-checkbox`}
                    value={allergen.id}
                    checked={tempAllergens[allergen.id] || false}
                    onChange={(e) => handleAllergenChange(allergen.id, e.target.checked)}
                  />
                  <label htmlFor={`${allergen.id}-checkbox`}>{allergen.label}</label>
                </div>
              ))}
            </div>
          </div>
          <div className={styles["save-preferences-box"]}>
            <button
              className={styles["yes-btn"]}
              onClick={handleSavePreferences}
            >
              Set preferences
            </button>
          </div>
          <button
            className={styles["reset-btn"]}
            onClick={handleResetPreferences}
          >
            Clear filter
          </button>
        </dialog>
      </div>
    </div>
  )
}