import {NavLink} from "react-router-dom";
import {useState} from "react";
import styles from "./banner.module.css";

export function Banner() {
  /* When we hook this up to UserContext, we can replace the code below with this
  * const { role } = useUserContext() */
  const [role, setRole] = useState<"chef" | "customer">("chef");

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
    { link: "/user/customer/cart", text: "Cart" },
  ]

  const navigation = role === "chef" ? chefNavigation : customerNavigation;

  const toggleRole = () => {
    setRole(prev => (prev === "chef" ? "customer" : "chef"));
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

      {/* // FIX This is a debugger function, styling doesn't belong here either, but I am avoiding dead code in CSS */}
      <div style={{ padding: "1rem", marginTop: "auto" }}>
        <button
          onClick={toggleRole}
          style={{
            backgroundColor: role === "chef" ? "#0a4c86" : "#79AE6F",
            color: role === "chef" ? "#ffffff" : "#346739",
            border: role === "chef" ? "none" : "1px solid #F2EDC2",
            padding: "0.6rem 1rem",
            borderRadius: "4px",
            cursor: "pointer",
            fontWeight: "bold",
            fontSize: "0.85rem",
            width: "100%",
            transition: "background-color 0.2s"
          }}
        >
          {role === "chef" ? "👨‍🍳 Chef" : "🛒 Customer"}
        </button>
      </div>
    </div>
  )
}