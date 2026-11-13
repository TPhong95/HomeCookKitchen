import {useEffect, useRef, useState} from "react";
import {Link} from "react-router-dom";
import {Banner} from "../general/banner.tsx";
import styles from "./recipe.module.css";
import {
  type RecipeStruct,
  DishCategory,
  type Allergen,
  initialAllergens,
  allergensList
} from "../shared/recipeinterface.ts";
import {type ChefResponse} from "../shared/chefinterface.ts";
import {useUserContext} from "../context/useUserContext.tsx";
import {sendJson} from "../util/api.ts";
import toast from "react-hot-toast";

function allergensFromRecipe(recipe: RecipeStruct): Record<Allergen, boolean> {
  return Object.fromEntries(
    allergensList.map(allergen => [allergen.id, recipe[allergen.id]])
  ) as Record<Allergen, boolean>;
}

export function Recipe() {
  const {user} = useUserContext();
  const chefId = user?.role === "chef" ? user.id : null;

  const [name, setName] = useState("");
  const [activeFilter, setActiveFilter] = useState<"ALL" | "ACTIVE" | "INACTIVE">("ACTIVE");
  const [recipes, setRecipes] = useState<RecipeStruct[]>([]);
  const [refreshKey, setRefreshKey] = useState(0);

  const [editingRecipe, setEditingRecipe] = useState<RecipeStruct | null>(null);
  const [tempRecipeName, setTempRecipeName] = useState("");
  const [tempDescription, setTempDescription] = useState("");
  const [tempCategory, setTempCategory] = useState<DishCategory>("UNDEFINED");
  const [tempAllergens, setTempAllergens] = useState<Record<Allergen, boolean>>(initialAllergens);
  const [tempImage, setTempImage] = useState("");

  const recipeDialogRef = useRef<HTMLDialogElement>(null);

  useEffect(() => {
    if (chefId === null) return;

    async function loadChef(id: number) {
      try {
        const res = await fetch(`http://localhost:8100/api/chef/${id}`);
        if (!res.ok) throw new Error(`Chef request failed: ${res.status}`);
        const chef: ChefResponse = await res.json();
        setName(chef.name);
      } catch (error) {
        toast.error(error instanceof Error ? error.message : "Something went wrong");
      }
    }

    loadChef(chefId);
  }, [chefId]);

  useEffect(() => {
    if (chefId === null) return;

    async function loadRecipes(id: number) {
      try {
        const res = await fetch(`http://localhost:8100/api/recipe/chef/${id}`);
        if (!res.ok) throw new Error(`Recipe request failed: ${res.status}`);
        const data: unknown = await res.json();
        setRecipes(Array.isArray(data) ? data as RecipeStruct[] : []);
      } catch (error) {
        toast.error(error instanceof Error ? error.message : "Something went wrong");
      }
    }

    loadRecipes(chefId);
  }, [chefId, refreshKey]);

  const handleAddRecipeModal = () => {
    setEditingRecipe(null);
    setTempRecipeName("");
    setTempDescription("");
    setTempCategory("UNDEFINED");
    setTempAllergens(initialAllergens);
    setTempImage("");
    recipeDialogRef.current?.showModal();
  }

  const handleEditRecipeModal = (recipe: RecipeStruct) => {
    setEditingRecipe(recipe);
    setTempRecipeName(recipe.recipeName);
    setTempDescription(recipe.description ?? "");
    setTempCategory(recipe.foodCategory ?? "UNDEFINED");
    setTempAllergens(allergensFromRecipe(recipe));
    setTempImage(recipe.pictureUrl ?? "");
    recipeDialogRef.current?.showModal();
  }

  const handleSaveRecipe = async () => {
    if (chefId === null) return;
    if (!tempRecipeName.trim()) {
      toast.error("Recipe name is required");
      return;
    }

    const body = {
      cookId: chefId,
      name: tempRecipeName,
      active: editingRecipe?.active ?? true,
      pictureUrl: tempImage,
      description: tempDescription,
      instanceOf: editingRecipe?.recipeId ?? null,
      foodCategory: tempCategory,
      ...tempAllergens,
    };

    try {
      if (editingRecipe === null) {
        await sendJson<RecipeStruct>("POST", "http://localhost:8100/api/recipe", body);
        toast.success("Recipe created");
      } else {
        await sendJson<RecipeStruct>(
          "PATCH",
          `http://localhost:8100/api/recipe/${editingRecipe.recipeId}`,
          body
        );
        toast.success("Recipe updated");
      }
      setRefreshKey(key => key + 1);
      recipeDialogRef.current?.close();
    } catch (error) {
      toast.error(error instanceof Error ? error.message : "Something went wrong");
    }
  }

  const handleToggleActive = async (recipe: RecipeStruct) => {
    if (chefId === null) return;

    try {
      await sendJson<RecipeStruct>(
        "PATCH",
        `http://localhost:8100/api/recipe/${recipe.recipeId}/active`,
        {cookId: chefId, active: !recipe.active}
      );
      toast.success(`${recipe.recipeName} is now ${recipe.active ? "inactive" : "active"}`);
      setRefreshKey(key => key + 1);
    } catch (error) {
      toast.error(error instanceof Error ? error.message : "Something went wrong");
    }
  };

  const handleAllergenChange = (allergenKey: Allergen, checked: boolean) => {
    setTempAllergens(prev => ({
      ...prev,
      [allergenKey]: checked
    }));
  };

  const filteredRecipes = recipes.filter((recipe) => {
    if (activeFilter === "ACTIVE") return recipe.active;
    if (activeFilter === "INACTIVE") return !recipe.active;
    return true;
  });

  const filterButtonClass = (filter: "ALL" | "ACTIVE" | "INACTIVE") =>
    `${styles["filter-recipe-btn"]} ${activeFilter === filter ? styles["active-filter"] : ""}`;

  return (
    <div className={styles["recipe-layout"]}>
      <Banner/>
      <div className={styles["recipe-wrapper"]}>
        <div className={styles["recipe-top-banner"]}>
          {name ? <h1>{name}'s Recipes</h1> : <h1>Recipes</h1>}
          {chefId !== null && (
            <button
              className={styles["add-recipe-btn"]}
              onClick={handleAddRecipeModal}
            >
              + Add Recipe
            </button>
          )}
        </div>

        {chefId === null ? (
          <div className={styles["all-recipes-container"]}>
            <p>Not logged in as a chef. Pick a chef on the front page.</p>
          </div>
        ) : (
          <>
            <div className={styles["recipe-filter-container"]}>
              <button className={filterButtonClass("ALL")} onClick={() => setActiveFilter("ALL")}>
                All
              </button>
              <button className={filterButtonClass("ACTIVE")} onClick={() => setActiveFilter("ACTIVE")}>
                Active
              </button>
              <button className={filterButtonClass("INACTIVE")} onClick={() => setActiveFilter("INACTIVE")}>
                Inactive
              </button>
            </div>

            <div className={styles["all-recipes-container"]}>
              {filteredRecipes.length === 0 && <p>No recipes to show.</p>}
              {filteredRecipes.map((recipe) => (
                <div className={styles["recipe-item-container"]} key={recipe.recipeId}>
                  <div className={styles["recipe-picture-container"]}>
                    <img src={recipe.pictureUrl} alt={recipe.recipeName}/>
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
                      {recipe.active ? (
                        <Link to={"/chef/listings"} className={styles["create-listing-btn"]}>
                          + Create listing
                        </Link>
                      ) : (
                        <div></div> /* DO NOT REMOVE, OTHERWISE INACTIVE BUTTON APPEARS ON WRONG SIDE */
                      )}
                      <div
                        className={styles["active-inactive-btn"]}
                        onClick={() => handleToggleActive(recipe)}
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
          </>
        )}

        <dialog
          className={styles["add-recipe-dialog"]}
          ref={recipeDialogRef}
        >
          <div className={styles["header-picture-name-description"]}>
            <div className={styles["recipe-image-upload-section"]}>
              {tempImage ? (
                <div className={styles["image-preview-container"]}>
                  <img src={tempImage} alt="Recipe Preview"/>
                </div>
              ) : (
                <div className={styles["image-placeholder-box"]}>
                  <span className={styles["placeholder-text"]}>No photo</span>
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
                <label htmlFor={"picture-url-input"}>Picture URL:</label>
                <input
                  id={"picture-url-input"}
                  type={"text"}
                  value={tempImage}
                  onChange={(e) => setTempImage(e.target.value)}
                  placeholder={"https://..."}
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
                {Object.entries(DishCategory).map(([key, label]) => (
                  <option key={key} value={key}>{label}</option>
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

          <p className={styles["add-recipe-question"]}>
            {editingRecipe === null ? "Add recipe?" : "Save changes?"}
          </p>
          <div className={styles["save-recipe-box"]}>
            <button
              className={styles["yes-btn"]}
              onClick={handleSaveRecipe}
            >
              Yes
            </button>
            <button
              className={styles["no-btn"]}
              onClick={() => recipeDialogRef.current?.close()}
            >
              No
            </button>
          </div>
        </dialog>
      </div>
    </div>
  )
}