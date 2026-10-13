import {useRef, useState} from "react";
import {Banner} from "../general/banner.tsx";

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
  name: string;
  description: string;
  active: boolean;
  category: DishCategory;
  preference: DishPreference;
}


export function Recipe() {
  // const [name, setName] = useState<string>("");
  const name = "Kieran";

  const [recipes, setRecipes] = useState<Recipe[]>([]);
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
          name: tempRecipeName,
          description: tempDescription,
          active: true,
          category: tempCategory,
          preference: tempPreference,
        }
      ]);

    addRecipeDialogRef.current?.close();
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

        <dialog ref={addRecipeDialogRef}>
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

          <div className={"allergens-checkboxes"}>
            <input
              id={"gluten-checkbox"}
              type={"checkbox"}
              name={"gluten-checkbox"}
              value={"gluten"}
            />
            <label htmlFor={"gluten-checkbox"}>Gluten</label>

            <input
              id={"crustaceans-checkbox"}
              type={"checkbox"}
              name={"crusteceans-checkbox"}
              value={"crusteceans"}
            />
            <label htmlFor={"crusteceans-checkbox"}>Crusteceans</label>

            <input
              id={"eggs-checkbox"}
              type={"checkbox"}
              name={"eggs-checkbox"}
              value={"eggs"}
            />
            <label htmlFor={"eggs-checkbox"}>Eggs</label>

            <input
              id={"fish-checkbox"}
              type={"checkbox"}
              name={"fish-checkbox"}
              value={"fish"}
            />
            <label htmlFor={"fish-checkbox"}>Fish</label>

            <input
              id={"peanuts-checkbox"}
              type={"checkbox"}
              name={"peanuts-checkbox"}
              value={"peanuts"}
            />
            <label htmlFor={"peanuts-checkbox"}>Peanuts</label>

            <input
              id={"soybeans-checkbox"}
              type={"checkbox"}
              name={"soybeans-checkbox"}
              value={"soybeans"}
            />
            <label htmlFor={"soybeans-checkbox"}>Soybeans</label>

            <input
              id={"milk-checkbox"}
              type={"checkbox"}
              name={"milk-checkbox"}
              value={"milk"}
            />
            <label htmlFor={"milk-checkbox"}>Milk</label>

            <input
              id={"nuts-checkbox"}
              type={"checkbox"}
              name={"nuts-checkbox"}
              value={"nuts"}
            />
            <label htmlFor={"nuts-checkbox"}>Nuts</label>

            <input
              id={"celery-checkbox"}
              type={"checkbox"}
              name={"celery-checkbox"}
              value={"celery"}
            />
            <label htmlFor={"celery-checkbox"}>Celery</label>

            <input
              id={"mustard-checkbox"}
              type={"checkbox"}
              name={"mustard-checkbox"}
              value={"mustard"}
            />
            <label htmlFor={"mustard-checkbox"}>Mustard</label>

            <input
              id={"sesame-checkbox"}
              type={"checkbox"}
              name={"sesame-checkbox"}
              value={"sesame"}
            />
            <label htmlFor={"sesame-checkbox"}>Sesame seeds</label>

            <input
              id={"sulfites-checkbox"}
              type={"checkbox"}
              name={"sulfites-checkbox"}
              value={"sulfites"}
            />
            <label htmlFor={"sulfites-checkbox"}>Sulfites</label>

            <input
              id={"lupin-checkbox"}
              type={"checkbox"}
              name={"lupin-checkbox"}
              value={"lupin"}
            />
            <label htmlFor={"lupin-checkbox"}>Lupin</label>

            <input
              id={"molluscs-checkbox"}
              type={"checkbox"}
              name={"molluscs-checkbox"}
              value={"molluscs"}
            />
            <label htmlFor={"molluscs-checkbox"}>Molluscs</label>
          </div>
          <p>Add recipe?</p>
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
        </dialog>
      </div>
    </div>
  )
}