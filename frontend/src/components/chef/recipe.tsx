import {useRef, useState} from "react";
import {Banner} from "../general/banner.tsx";
import "./recipe.css";
import {type RecipeStruct, DishCategory, DishPreference, Allergen} from "../shared/recipeinterface.ts";

const initialAllergens: Record<Allergen, boolean> = {
  [Allergen.GLUTEN]: false,
  [Allergen.CRUSTACEANS]: false,
  [Allergen.EGGS]: false,
  [Allergen.FISH]: false,
  [Allergen.PEANUTS]: false,
  [Allergen.SOYBEANS]: false,
  [Allergen.MILK]: false,
  [Allergen.NUTS]: false,
  [Allergen.CELERY]: false,
  [Allergen.MUSTARD]: false,
  [Allergen.SESAME]: false,
  [Allergen.SULFITES]: false,
  [Allergen.LUPIN]: false,
  [Allergen.MOLLUSCS]: false,
};

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
  // const [name, setName] = useState<string>("");
  const name = "Kieran";

  const [activeFilter, setActiveFilter] = useState<"ALL" | "ACTIVE" | "INACTIVE">("ACTIVE");

  const [recipes, setRecipes] = useState<RecipeStruct[]>(testRecipes);
  const [tempRecipeName, setTempRecipeName] = useState("");
  const [tempDescription, setTempDescription] = useState("");
  const [tempCategory, setTempCategory] = useState<DishCategory>(DishCategory.BURGERS);
  const [tempPreference, setTempPreference] = useState<DishPreference>(DishPreference.NONE);
  const [tempAllergens, setTempAllergens] = useState<Record<Allergen, boolean>>(initialAllergens);

  const [tempPortions, setTempPortions] = useState("0"); // FIX: string to int conversion
  const [tempPricePerPortion, setTempPricePerPortion] = useState("0"); // FIX: string to int conversion
  const [tempBuyFrom, setTempBuyFrom] = useState("");
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
    showRecipeDialogRef.current?.showModal();
  }

  const handleEditRecipeModal = (recipe: RecipeStruct) => {
    setTempRecipeName(recipe.recipeName);
    setTempDescription(recipe.description);
    setTempCategory(recipe.category);
    setTempPreference(recipe.preference);
    setTempAllergens(recipe.allergens || initialAllergens);
    showRecipeDialogRef.current?.showModal();
  }

  const handleSaveRecipe = () => {
    if (!tempRecipeName.trim()) return;

    setRecipes(
      [...recipes,
        {
          id: Date.now(), // FIX!! Technical debt to check editing
          recipeName: tempRecipeName,
          description: tempDescription,
          active: true,
          category: tempCategory,
          preference: tempPreference,
          allergens: tempAllergens,
        }
      ]);

    showRecipeDialogRef.current?.close();
  }

  const handleCreateListingModal = (recipe: RecipeStruct) => {
    setTempRecipeName(recipe.recipeName);
    if (recipe.image) setTempRecipePicture(recipe.image);
    setTempBuyFrom("");
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
    <div className={"recipe-layout"}>
      <Banner/>
      <div className={"recipe-wrapper"}>
        <div className={"recipe-top-banner"}>
          <h1>{name}'s Recipes</h1>
          <button
            className={"add-recipe-btn"}
            onClick={handleAddRecipeModal}
          >
            + Add Recipe
          </button>
        </div>

        <div>

        </div>

        <div className={"recipe-filter-container"}>
          <button
            className={`filter-recipe-btn ${activeFilter === "ALL" ? "active-filter" : ""}`}
            onClick={handleShowAllRecipes}
          >
            All
          </button>
          <button
            className={`filter-recipe-btn ${activeFilter === "ACTIVE" ? "active-filter" : ""}`}
            onClick={handleShowActiveRecipes}
          >
            Active
          </button>
          <button
            className={`filter-recipe-btn ${activeFilter === "INACTIVE" ? "active-filter" : ""}`}
            onClick={handleShowInactiveRecipes}
          >
            Inactive
          </button>
        </div>

        <div className={"all-recipes-container"}>
          {filteredRecipes.map((recipe) => (
            <div className={"recipe-item-container"} key={recipe.id}>
              <div className={"recipe-picture-container"}>
                <img src={recipe.image} alt={recipe.recipeName}/>
              </div>
              <div className={"recipe-info-container"}>
                <div className={"name-and-edit-container"}>
                  <div className={"recipe-title"}>{recipe.recipeName}</div>
                  <button
                    className={"edit-recipe-btn"}
                    onClick={() => handleEditRecipeModal(recipe)}
                  >
                    Edit recipe
                  </button>
                </div>

                <div className={"listing-and-active-container"}>
                  {recipe.active ? (<button
                    className={"create-listing-btn"}
                    onClick={() => handleCreateListingModal(recipe)}
                  >
                    + Create listing
                  </button>) : (
                    <div></div> /* DO NOT REMOVE, OTHERWISE INACTIVE BUTTON APPEARS ON WRONG SIDE */
                  )}
                  <div
                    className={"active-inactive-btn"}
                    onClick={() => recipe.id !== undefined && handleToggleActive(recipe.id)}
                  >
                    <div className={`toggle-switch ${recipe.active ? "active" : ""}`}>
                      <div className={"toggle-knob"}></div>
                    </div>
                    <span className={"status-label"}>{recipe.active ? "Active" : "Inactive"}</span>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>

        <dialog
          className={"add-recipe-dialog"}
          ref={showRecipeDialogRef}
        >
          <div>
            <label htmlFor={"recipe-name-input"}>Recipe Name:</label>
            <input
              id={"recipe-name-input"}
              type={"text"}
              value={tempRecipeName}
              onChange={(e) => setTempRecipeName(e.target.value)}
            />
          </div>
          <div>
            <label htmlFor={"description-input"}>Description:</label>
            <textarea
              id={"description-input"}
              value={tempDescription}
              onChange={(e) => setTempDescription(e.target.value)}
            />
          </div>

          <div className={"category-dropdowns"}>
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

          <div className={"allergens-title"}>Allergens:</div>
          <div className={"allergens-checkboxes"}>
            {allergensList.map((allergen) => (
              <div className={"allergen-item"} key={allergen.id}>
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


          <p className={"add-recipe-question"}>Add recipe?</p>
          <div className={"save-recipe-box"}>
            <button
              className={"yes-btn"}
              onClick={handleSaveRecipe}
            >
              Yes
            </button>
            <button
              className={"no-btn"}
              onClick={() => showRecipeDialogRef.current?.close()}
            >
              No
            </button>
          </div>
        </dialog>

        <dialog
          className={"create-listing-dialog"}
          ref={createListingDialogRef}
        >
          <div className={"create-listing-header-container"}>
            <div className={"listing-recipe-image-container"}>
              <img src={tempRecipePicture}/>
            </div>
            <h1>{tempRecipeName}</h1>
          </div>
          <div className={"portion-control-container"}>
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
            <div className={"listing-total-preview"}>
              Total potential revenue: <strong>{calculatedTotal.toFixed(2)}NOK</strong>
            </div>
          </div>
          <div className={"buy-window-container"}>
            <div>
              <label htmlFor={"buy-from-window"}>From</label>
              <input
                id={"buy-from-window"}
                type={"datetime-local"}
                value={tempBuyFrom}
                onChange={(e) => setTempBuyFrom(e.target.value)}
              />
            </div>
            <div>
              <label htmlFor={"buy-to-window"}>To</label>
              <input
                id={"buy-to-window"}
                type={"datetime-local"}
                value={tempBuyTo}
                onChange={(e) => setTempBuyTo(e.target.value)}
              />
            </div>
          </div>
          <div className={"ready-by-container"}>
            <label htmlFor={"ready-by"}>Expected time of completion:</label>
            <input
              id={"buy-to-window"}
              type={"datetime-local"}
              value={tempReadyTime}
              onChange={(e) => setTempReadyTime(e.target.value)}
            />
          </div>
          <p className={"create-listing-question"}>Create listing?</p>
          <div className={"save-recipe-box"}>
            <button
              className={"yes-btn"}
              onClick={handleCreateListing}
            >
              Yes
            </button>
            <button
              className={"no-btn"}
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