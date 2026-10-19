export const DishCategory = {
  BURGERS: "Burgers",
  PASTA: "Pasta",
  CURRIES_AND_STEWS: "Curries and Stews",
} as const;

export type DishCategory = typeof DishCategory [keyof typeof DishCategory];

export const DishPreference = {
  VEGAN: "Vegan",
  VEGETARIAN: "Vegetarian",
  PESCETARIAN: "Pescetarian",
  NONE: "None",
} as const;

export type DishPreference = typeof DishPreference [keyof typeof DishPreference];

export enum Allergen {
  GLUTEN = "gluten",
  CRUSTACEANS = "crustaceans",
  EGGS = "eggs",
  FISH = "fish",
  PEANUTS = "peanuts",
  SOYBEANS = "soybeans",
  MILK = "milk",
  NUTS = "nuts",
  CELERY = "celery",
  MUSTARD = "mustard",
  SESAME = "sesame",
  SULFITES = "sulfites",
  LUPIN = "lupin",
  MOLLUSCS = "molluscs",
}

// This is in use, TypeScript is lying to you. DO NOT REMOVE
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

export interface RecipeStruct {
  id?: number;
  recipeName: string;
  description: string;
  active: boolean;
  category: DishCategory;
  preference: DishPreference;
  image?: string;
  allergens: Record<Allergen, boolean> // Record allows tidy representation of key value pairs for allergen and state
}