import {NavLink, useNavigate} from "react-router-dom";
import styles from "./banner.module.css";
import {useUserContext} from "../context/useUserContext.tsx";
import {useNotificationContext} from "../context/useNotificationContext.tsx";

export function Banner() {
  const {user, logout} = useUserContext();
  const {unreadCount, isFlashing} = useNotificationContext();
  const navigate = useNavigate();
  const role = user?.role ?? null;

  const chefNavigation = [
    { link: "/", text: "Home" },
    { link: "/chef/profile", text: "Profile" },
    { link: "/chef/listings", text: "Listings" },
    { link: "/chef/recipe", text: "Recipes" },
    { link: "/chef/history", text: "History" },
  ]

  const customerNavigation = [
    { link: "/", text: "Home" },
    { link: "/customer/profile", text: "Profile" },
    { link: "/customer/browse", text: "Browse" },
    { link: "/customer/upcoming", text: "Upcoming" },
    { link: "/customer/history", text: "History" },
  ]

  const loggedOutNavigation = [
    { link: "/", text: "Home" },
    { link: "/customer/browse", text: "Browse" },
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
      {user && (
        <div className={styles["notification-container"]}>
          <NavLink
            to={`/${user.role}/notifications`}
            className={isFlashing ? styles["notification-flash"] : ""}
            title={"Notifications"}
            aria-label={`Notifications (${unreadCount})`}
          >
            {unreadCount > 0 ? "📬" : "📭"} ({unreadCount})
          </NavLink>
        </div>
      )}
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
