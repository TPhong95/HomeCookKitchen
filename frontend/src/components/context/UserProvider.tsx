import {useState, type ReactNode} from "react";
import {UserContext, type User} from "./UserContext.tsx";

// FIX: Replace with the JWT cookie once backend login exists
const STORAGE_KEY = "user";

function loadStoredUser(): User | null {
  try {
    const stored = sessionStorage.getItem(STORAGE_KEY);
    if (!stored) return null;

    const parsed = JSON.parse(stored);
    const isValid =
      typeof parsed?.id === "number" &&
      (parsed?.role === "chef" || parsed?.role === "customer");

    return isValid ? {id: parsed.id, role: parsed.role} : null;
  } catch {
    // Malformed JSON or storage unavailable: treat as not logged in
    return null;
  }
}

export function UserProvider({children}: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(loadStoredUser);

  const login = (newUser: User) => {
    setUser(newUser);
    try {
      sessionStorage.setItem(STORAGE_KEY, JSON.stringify(newUser));
    } catch {
      // Storage unavailable: login still works, it just won't survive a refresh
    }
  };

  // FIX: Call backend logout endpoint to clear the cookie once it exists
  const logout = () => {
    setUser(null);
    try {
      sessionStorage.removeItem(STORAGE_KEY);
    } catch {
      // Nothing to clean up if storage is unavailable
    }
  };

  return (
    <UserContext value={{user, login, logout}}>
      {children}
    </UserContext>
  );
}
