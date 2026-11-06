import {useContext} from "react";
import {UserContext, type UserContextValue} from "./UserContext.tsx";

export function useUserContext(): UserContextValue {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error("useUserContext must be used inside a UserProvider");
  }
  return context;
}
