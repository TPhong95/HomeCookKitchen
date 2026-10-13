import {useRef, useState} from "react";
import {Banner} from "../general/banner.tsx";
import "./recipe.css";

const DishCategory = {
  BURGERS: "Burgers",
  PASTA: "Pasta",
  CURRIES_AND_STEWS: "Curries and Stews",
} as const;

type DishCategory = typeof DishCategory [keyof typeof DishCategory];

const DishPreference = {
  VEGAN: "Vegan",
  VEGETARIAN: "Vegetarian",
  PESCETARIAN: "Pescetarian",
  NONE: "None",
} as const;

type DishPreference = typeof DishPreference [keyof typeof DishPreference];

interface Recipe {
  id?: number;
  recipeName: string;
  description: string;
  active: boolean;
  category: DishCategory;
  preference: DishPreference;
  image?: string;
}

const testRecipes: Recipe[] = [
  {
    id: 1,
    recipeName: "Falafel Baguette",
    description: "I put falafel in a baguette, it tastes alright",
    active: true,
    category: DishCategory.PASTA,
    preference: DishPreference.VEGAN,
    image: "../../../testdata/falafelbaguette.jpg"
  },
  {
    id: 2,
    recipeName: "Lasagne",
    description: "My grandpa's shoe tastes better",
    active: false,
    category: DishCategory.PASTA,
    preference: DishPreference.NONE,
    image: "../../../testdata/lasagne.jpg"
  }
]

export function Recipe() {
  // const [name, setName] = useState<string>("");
  const name = "Kieran";

  const [activeFilter, setActiveFilter] = useState<"ALL" | "ACTIVE" | "INACTIVE">("ACTIVE");

  const [recipes, setRecipes] = useState<Recipe[]>(testRecipes);
  const [tempRecipeName, setTempRecipeName] = useState("");
  const [tempDescription, setTempDescription] = useState("");
  const [tempCategory, setTempCategory] = useState<DishCategory>(DishCategory.BURGERS);
  const [tempPreference, setTempPreference] = useState<DishPreference>(DishPreference.NONE);

  const addRecipeDialogRef = useRef<HTMLDialogElement>(null);

  const handleAddRecipeModal = () => {
    setTempRecipeName("");
    setTempDescription("");
    setTempCategory(DishCategory.BURGERS);
    setTempPreference(DishPreference.NONE);
    addRecipeDialogRef.current?.showModal();
  }

  const handleSaveRecipe = () => {
    if (!tempRecipeName.trim()) return;

    setRecipes(
      [...recipes,
        {
          recipeName: tempRecipeName,
          description: tempDescription,
          active: true,
          category: tempCategory,
          preference: tempPreference,
        }
      ]);

    addRecipeDialogRef.current?.close();
  }

  const allergensList = [
    {id: "gluten", label: "Gluten"},
    {id: "crustaceans", label: "Crustaceans"},
    {id: "eggs", label: "Eggs"},
    {id: "fish", label: "Fish"},
    {id: "peanuts", label: "Peanuts"},
    {id: "soybeans", label: "Soybeans"},
    {id: "milk", label: "Milk"},
    {id: "nuts", label: "Nuts"},
    {id: "celery", label: "Celery"},
    {id: "mustard", label: "Mustard"},
    {id: "sesame", label: "Sesame seeds"},
    {id: "sulfites", label: "Sulfites"},
    {id: "lupin", label: "Lupin"},
    {id: "molluscs", label: "Molluscs"},
  ];

  function handleShowAllRecipes() {
    setActiveFilter("ALL");
  }

  function handleShowActiveRecipes() {
    setActiveFilter("ACTIVE");
  }

  function handleShowInactiveRecipes() {
    setActiveFilter("INACTIVE");
  }

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
          {recipes.map((recipe) => (
            <div className={"recipe-item-container"} key={recipe.id }>
              <div className={"recipe-picture-container"}>
                <img src={recipe.image} alt={recipe.recipeName} />
              </div>
              <div className={"recipe-info-container"}>
                <div className={"name-and-edit-container"}>
                  <div className={"recipe-title"}>{recipe.recipeName}</div>
                  <button className={"edit-recipe-btn"}>Edit recipe</button>
                </div>

                <div className={"listing-and-active-container"}>
                  <button className={"create-listing-btn"}>+ Create listing</button>
                  <div className={"active-inactive-btn"}>
                    {recipe.active ? "Active" : "Inactive"}
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>

        <dialog
          className={"add-recipe-dialog"}
          ref={addRecipeDialogRef}
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
              onClick={() => addRecipeDialogRef.current?.close()}
            >
              No
            </button>
          </div>
        </dialog>
      </div>
    </div>
  )
}