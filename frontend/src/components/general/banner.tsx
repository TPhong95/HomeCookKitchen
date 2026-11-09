import {NavLink, useNavigate} from "react-router-dom";
import styles from "./banner.module.css";
import {useUserContext} from "../context/useUserContext.tsx";

export function Banner() {
  const {user, logout} = useUserContext();
  const navigate = useNavigate();
  const role = user?.role ?? null;

  const chefNavigation = [
    { link: "/", text: "Home" },
    { link: "/user/chef/profile", text: "Profile" },
    { link: "/user/chef/listings", text: "Listings" },
    { link: "/user/chef/recipe", text: "Recipes" },
    { link: "/user/chef/history", text: "History" },
  ]

  const customerNavigation = [
    { link: "/", text: "Home" },
    { link: "/user/customer/profile", text: "Profile" },
    { link: "/user/customer/browse", text: "Browse" },
    { link: "/user/customer/orders", text: "Orders" },
    { link: "/user/customer/history", text: "History" },
  ]

  // [NEW] Shown when nobody is logged in
  const loggedOutNavigation = [
    { link: "/", text: "Home" },
  ]

  const navigation =
    role === "chef" ? chefNavigation
      : role === "customer" ? customerNavigation
        : loggedOutNavigation;

  const handleLogout = () => {
    logout();
    navigate("/");
  }

  // Note that NavLink is used if we want to highlight which page we are on (for future styling)

  return (
    <div className={`${styles["banner-sidebar"]} ${role === "customer" ? styles.customer : ""}`}>
      <div className={styles["profile-picture-container"]}>
        <img src="../../../testdata/profilepic.jpg" alt={"profile picture goes here"}/>
      </div>
      <div className={styles["nav-container"]}>
        <ul>
          {navigation.map((nav) => (
            <li
              className={styles["list-item"]}
              key={nav.text}
            >
              <NavLink to={nav.link}>
                {nav.text}
              </NavLink>
            </li>
          ))}
        </ul>
      </div>

      {user && (
        <div className={styles["logout-container"]}>
          <button
            className={styles["logout-btn"]}
            onClick={handleLogout}
          >
            Log out
          </button>
        </div>
      )}
    </div>
  )
}
