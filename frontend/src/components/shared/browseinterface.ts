import {
  DishCategory,
  DishPreference,
  Allergen
} from "./recipeinterface.ts";

export interface BrowseItem {
  listingId: number;
  chefId: number;
  chefName: string;
  recipeName: string;
  description: string;
  image: string;
  category: DishCategory;
  preference: DishPreference;
  allergens: Record<Allergen, boolean>;
  unitPrice: number;
  quantity: number;
  quantityPurchased: number;
  readyBy: string;
  purchaseDeadline: string;
}