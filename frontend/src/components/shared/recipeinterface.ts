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

/* Enum type system, used to define exact keys allowed for allergens */
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

/* Key-value lookup map, helps instantiate checkboxes to false in components */
export const initialAllergens: Record<Allergen, boolean> = {
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

/* Provides an ordered list with display labels for UI */
export const allergensList: { id: Allergen; label: string }[] = [
  {id: Allergen.GLUTEN, label: "Gluten"},
  {id: Allergen.CRUSTACEANS, label: "Crustaceans"},
  {id: Allergen.EGGS, label: "Eggs"},
  {id: Allergen.FISH, label: "Fish"},
  {id: Allergen.PEANUTS, label: "Peanuts"},
  {id: Allergen.SOYBEANS, label: "Soybeans"},
  {id: Allergen.MILK, label: "Milk"},
  {id: Allergen.NUTS, label: "Nuts"},
  {id: Allergen.CELERY, label: "Celery"},
  {id: Allergen.MUSTARD, label: "Mustard"},
  {id: Allergen.SESAME, label: "Sesame seeds"},
  {id: Allergen.SULFITES, label: "Sulfites"},
  {id: Allergen.LUPIN, label: "Lupin"},
  {id: Allergen.MOLLUSCS, label: "Molluscs"},
];

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