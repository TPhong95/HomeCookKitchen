// Must match the backend Genre enum exactly
export const GENRES = ["JAZZ", "POP", "ROCK", "CLASSICAL", "NONE"] as const;
export type Genre = typeof GENRES[number];

export const GENRE_LABELS: Record<Genre, string> = {
  JAZZ: "Jazz",
  POP: "Pop",
  ROCK: "Rock",
  CLASSICAL: "Classical",
  NONE: "No music",
};

export interface HomeVibe {
  description: string;
  music: Genre;
  pets: boolean;
  outdoor: boolean;
  familyFriendly: boolean;
}

export interface ChefAddress {
  id: number;
  street: string;
  postalCode: string;
  city: string;
  chefId: number;
  homeVibe: HomeVibe | null;
}

export interface ChefResponse {
  id: number;
  name: string;
  email: string;
  phoneNumber: string;
  addresses: ChefAddress[];
}