import {useRef, useState} from "react";
import {Banner} from "../general/banner.tsx";
import styles from "./recipe.module.css";
import {type RecipeStruct, DishCategory, DishPreference, Allergen, initialAllergens} from "../shared/recipeinterface.ts";



const testRecipes: RecipeStruct[] = [
  {
    id: 1,
    recipeName: "Falafel Baguette",
    description: "I put falafel in a baguette, it tastes alright",
    active: true,
    category: DishCategory.PASTA,
    preference: DishPreference.VEGAN,
    image: "../../../testdata/falafelbaguette.jpg",
    allergens: {
      ...initialAllergens,
      [Allergen.GLUTEN]: true,
    }
  },
  {
    id: 2,
    recipeName: "Lasagne",
    description: "My grandpa's shoe tastes better",
    active: false,
    category: DishCategory.PASTA,
    preference: DishPreference.NONE,
    image: "../../../testdata/lasagne.jpg",
    allergens: {
      ...initialAllergens,
      [Allergen.MILK]: true,
      [Allergen.CELERY]: true,
      [Allergen.GLUTEN]: true,
    }
  }
]

export function Recipe() {
  const name = "Kieran";

  const [activeFilter, setActiveFilter] = useState<"ALL" | "ACTIVE" | "INACTIVE">("ACTIVE");

  const [recipes, setRecipes] = useState<RecipeStruct[]>(testRecipes);
  const [tempRecipeName, setTempRecipeName] = useState("");
  const [tempDescription, setTempDescription] = useState("");
  const [tempCategory, setTempCategory] = useState<DishCategory>(DishCategory.BURGERS);
  const [tempPreference, setTempPreference] = useState<DishPreference>(DishPreference.NONE);
  const [tempAllergens, setTempAllergens] = useState<Record<Allergen, boolean>>(initialAllergens);
  const [tempImage, setTempImage] = useState("");

  const [tempPortions, setTempPortions] = useState("0");
  const [tempPricePerPortion, setTempPricePerPortion] = useState("0");
  const [tempBuyTo, setTempBuyTo] = useState("");
  const [tempReadyTime, setTempReadyTime] = useState("");
  const [tempRecipePicture, setTempRecipePicture] = useState("");
  const portionsNum = Number(tempPortions) || 0;
  const priceNum = Number(tempPricePerPortion) || 0;
  const calculatedTotal = portionsNum * priceNum;

  const showRecipeDialogRef = useRef<HTMLDialogElement>(null);
  const createListingDialogRef = useRef<HTMLDialogElement>(null);

  const handleAddRecipeModal = () => {
    setTempRecipeName("");
    setTempDescription("");
    setTempCategory(DishCategory.BURGERS);
    setTempPreference(DishPreference.NONE);
    setTempAllergens(initialAllergens);
    setTempImage("");
    showRecipeDialogRef.current?.showModal();
  }

  const handleEditRecipeModal = (recipe: RecipeStruct) => {
    setTempRecipeName(recipe.recipeName);
    setTempDescription(recipe.description);
    setTempCategory(recipe.category);
    setTempPreference(recipe.preference);
    setTempAllergens(recipe.allergens || initialAllergens);
    setTempImage(recipe.image || "");
    showRecipeDialogRef.current?.showModal();
  }

  const handleSaveRecipe = () => {
    if (!tempRecipeName.trim()) return;

    setRecipes(
      [...recipes,
        {
          id: Date.now(),
          recipeName: tempRecipeName,
          description: tempDescription,
          active: true,
          category: tempCategory,
          preference: tempPreference,
          allergens: tempAllergens,
          image: tempImage,
        }
      ]);

    showRecipeDialogRef.current?.close();
  }

  const handleCreateListingModal = (recipe: RecipeStruct) => {
    setTempRecipeName(recipe.recipeName);
    if (recipe.image) setTempRecipePicture(recipe.image);
    setTempBuyTo("");
    setTempReadyTime("");
    createListingDialogRef.current?.showModal();
  }

  const handleCreateListing = () => {
    alert("Nothing happened, hook up to backend");
    createListingDialogRef.current?.close();
  }

  const handleToggleActive = (id: number) => {
    setRecipes(prevRecipes =>
      prevRecipes.map(recipe =>
        recipe.id === id ? {...recipe, active: !recipe.active} : recipe
      )
    );
  };

  const allergensList: { id: Allergen; label: string }[] = [
    {id: "gluten" as Allergen, label: "Gluten"},
    {id: "crustaceans" as Allergen, label: "Crustaceans"},
    {id: "eggs" as Allergen, label: "Eggs"},
    {id: "fish" as Allergen, label: "Fish"},
    {id: "peanuts" as Allergen, label: "Peanuts"},
    {id: "soybeans" as Allergen, label: "Soybeans"},
    {id: "milk" as Allergen, label: "Milk"},
    {id: "nuts" as Allergen, label: "Nuts"},
    {id: "celery" as Allergen, label: "Celery"},
    {id: "mustard" as Allergen, label: "Mustard"},
    {id: "sesame" as Allergen, label: "Sesame seeds"},
    {id: "sulfites" as Allergen, label: "Sulfites"},
    {id: "lupin" as Allergen, label: "Lupin"},
    {id: "molluscs" as Allergen, label: "Molluscs"},
  ];

  const handleAllergenChange = (allergenKey: Allergen, checked: boolean) => {
    setTempAllergens(prev => ({
      ...prev,
      [allergenKey]: checked
    }));
  };

  function handleShowAllRecipes() {
    setActiveFilter("ALL");
  }

  function handleShowActiveRecipes() {
    setActiveFilter("ACTIVE");
  }

  function handleShowInactiveRecipes() {
    setActiveFilter("INACTIVE");
  }

  const filteredRecipes = recipes.filter((recipe) => {
    if (activeFilter === "ACTIVE") return recipe.active;
    if (activeFilter === "INACTIVE") return !recipe.active;
    return true;
  })

  return (
    <div className={styles["recipe-layout"]}>
      <Banner/>
      <div className={styles["recipe-wrapper"]}>
        <div className={styles["recipe-top-banner"]}>
          <h1>{name}'s Recipes</h1>
          <button
            className={styles["add-recipe-btn"]}
            onClick={handleAddRecipeModal}
          >
            + Add Recipe
          </button>
        </div>

        <div className={styles["recipe-filter-container"]}>
          <button
            className={`${styles["filter-recipe-btn"]} ${activeFilter === "ALL" ? styles["active-filter"] : ""}`}
            onClick={handleShowAllRecipes}
          >
            All
          </button>
          <button
            className={`${styles["filter-recipe-btn"]} ${activeFilter === "ACTIVE" ? "active-filter" : ""}`}
            onClick={handleShowActiveRecipes}
          >
            Active
          </button>
          <button
            className={`${styles["filter-recipe-btn"]} ${activeFilter === "INACTIVE" ? "active-filter" : ""}`}
            onClick={handleShowInactiveRecipes}
          >
            Inactive
          </button>
        </div>

        <div className={styles["all-recipes-container"]}>
          {filteredRecipes.map((recipe) => (
            <div className={styles["recipe-item-container"]} key={recipe.id}>
              <div className={styles["recipe-picture-container"]}>
                <img src={recipe.image} alt={recipe.recipeName}/>
              </div>
              <div className={styles["recipe-info-container"]}>
                <div className={styles["name-and-edit-container"]}>
                  <div className={styles["recipe-title"]}>{recipe.recipeName}</div>
                  <button
                    className={styles["edit-recipe-btn"]}
                    onClick={() => handleEditRecipeModal(recipe)}
                  >
                    Edit recipe
                  </button>
                </div>

                <div className={styles["listing-and-active-container"]}>
                  {recipe.active ? (<button
                    className={styles["create-listing-btn"]}
                    onClick={() => handleCreateListingModal(recipe)}
                  >
                    + Create listing
                  </button>) : (
                    <div></div> /* DO NOT REMOVE, OTHERWISE INACTIVE BUTTON APPEARS ON WRONG SIDE */
                  )}
                  <div
                    className={styles["active-inactive-btn"]}
                    onClick={() => recipe.id !== undefined && handleToggleActive(recipe.id)}
                  >
                    <div className={`${styles["toggle-switch"]} ${recipe.active ? styles["active"] : ""}`}>
                      <div className={styles["toggle-knob"]}></div>
                    </div>
                    <span className={styles["status-label"]}>{recipe.active ? "Active" : "Inactive"}</span>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>

        <dialog
          className={styles["add-recipe-dialog"]}
          ref={showRecipeDialogRef}
        >
          <div className={styles["header-picture-name-description"]}>
            <div className={styles["recipe-image-upload-section"]}>
              {tempImage ? (
                <div className={styles["image-preview-container"]}>
                  <img src={tempImage} alt="Recipe Preview" />
                </div>
              ) : (
                <div className={styles["image-placeholder-box"]} title="Click to add image">
                  <span className={styles["plus-icon"]}>+</span>
                  <span className={styles["placeholder-text"]}>Add photo</span>
                </div>
              )}
            </div>
            <div className={styles["recipe-text-fields-section"]}>
              <div className={styles["form-field"]}>
                <label htmlFor={"recipe-name-input"}>Recipe Name:</label>
                <input
                  id={"recipe-name-input"}
                  type={"text"}
                  value={tempRecipeName}
                  onChange={(e) => setTempRecipeName(e.target.value)}
                  placeholder={"e.g. Reindeer Kebab Pizza"}
                />
              </div>
              <div className={styles["form-field"]}>
                <label htmlFor={"description-input"}>Description:</label>
                <textarea
                  id={"description-input"}
                  value={tempDescription}
                  onChange={(e) => setTempDescription(e.target.value)}
                  placeholder={"Briefly describe the dish..."}
                />
              </div>
            </div>
          </div>

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
            <div className={styles["allergens-title"]}>Allergens:</div>
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

          <p className={styles["add-recipe-question"]}>Add recipe?</p>
          <div className={styles["save-recipe-box"]}>
            <button
              className={styles["yes-btn"]}
              onClick={handleSaveRecipe}
            >
              Yes
            </button>
            <button
              className={styles["no-btn"]}
              onClick={() => showRecipeDialogRef.current?.close()}
            >
              No
            </button>
          </div>
        </dialog>

        <dialog
          className={styles["create-listing-dialog"]}
          ref={createListingDialogRef}
        >
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
              <label htmlFor={"buy-to-window"}>Customer Order Deadline</label>
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
              onClick={() => createListingDialogRef.current?.close()}
            >
              No
            </button>
          </div>
        </dialog>
      </div>
    </div>
  )
}