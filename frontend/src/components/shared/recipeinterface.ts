export const DishCategory = {
  UNDEFINED: "Undefined",
  ASIAN: "Asian",
  BURGER: "Burger",
  PASTA: "Pasta",
  VEGETARIAN: "Vegetarian",
  MEXICAN: "Mexican",
  SKANDINAVIAN: "Skandinavian",
  AFRICAN: "African"
} as const;

export type DishCategory = keyof typeof DishCategory;

/* Enum type system, used to define exact keys allowed for allergens */
export const Allergen = {
  GLUTEN: "gluten",
  CRUSTACEANS: "crustaceans",
  EGGS: "eggs",
  FISH: "fish",
  PEANUTS: "peanuts",
  SOYBEANS: "soybeans",
  MILK: "milk",
  NUTS: "nuts",
  CELERY: "celery",
  MUSTARD: "mustard",
  SESAME: "sesame",
  SULFITES: "sulfites",
  LUPIN: "lupin",
  MOLLUSCS: "molluscs",
} as const;

export type Allergen = typeof Allergen[keyof typeof Allergen];

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
  recipeId: number;
  cookId: number;
  recipeName: string;
  invalidated: boolean;
  active: boolean;
  pictureUrl: string;
  description: string;
  instanceOf: number;
  foodCategory: DishCategory;
  allergens: Record<Allergen, boolean> // Record allows tidy representation of key value pairs for allergen and state
}