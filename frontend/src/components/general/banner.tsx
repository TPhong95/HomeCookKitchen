import {NavLink} from "react-router-dom";
import "./banner.css";

export function Banner() {

  const navigation = [
    { link: "/", text: "Home" },
    { link: "/user/chef/profile", text: "Profile" },
    { link: "/user/chef/listings", text: "Listings" },
    { link: "/user/chef/recipe", text: "Recipes" },
    { link: "/user/chef/history", text: "History" },
  ]

  // Note that NavLink is used if we want to highlight which page we are on (for future styling)

  return (
    <div className={"banner-sidebar"}>
      <div className={"profile-picture-container"}>
        <img src="../../../testdata/profilepic.jpg" alt={"profile picture goes here"}/>
      </div>
      <div className={"nav-container"}>
      <ul>
        {navigation.map((nav) => (
          <li
            className={"list-item"}
            key={nav.text}
          >
            <NavLink to={nav.link}>
              {nav.text}
            </NavLink>
          </li>
        ))}
      </ul>
      </div>
    </div>
  )
}