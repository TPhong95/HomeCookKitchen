import {createContext} from "react";

export type Role = "chef" | "customer";

export interface User {
  id: number;
  role: Role;
}

export interface UserContextValue {
  user: User | null;
  login: (user: User) => void;
  logout: () => void;
}

export const UserContext = createContext<UserContextValue | null>(null);