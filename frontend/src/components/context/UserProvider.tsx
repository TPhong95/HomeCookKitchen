import {useState, type ReactNode} from "react";
import {UserContext, type User} from "./UserContext.tsx";

export function UserProvider({children}: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(null);

  const login = (newUser: User) => {
    setUser(newUser);
  };

  // FIX: Call backend logout endpoint to clear the cookie once it exists
  const logout = () => {
    setUser(null);
  };

  return (
    <UserContext value={{user, login, logout}}>
      {children}
    </UserContext>
  );
}
