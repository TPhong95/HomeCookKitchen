import {Toaster} from "react-hot-toast";
import {Route, Routes} from "react-router-dom";
import {FrontPage} from "./components/general/frontpage.tsx";
import {ChefProfile} from "./components/chef/chefprofile.tsx";
import {Recipe} from "./components/chef/recipe.tsx";
import {Listings} from "./components/chef/listings.tsx";
import {History} from "./components/chef/history.tsx";
import {CustomerProfile} from "./components/customer/customerprofile.tsx";
import {BrowseListing} from "./components/customer/browse.tsx";
import {Purchases} from "./components/customer/purchases.tsx";
import {CustomerHistory} from "./components/customer/history.tsx";

export function Application() {
  return (
    <>
      <Toaster
        toastOptions={{
          style: {
            width: "fit-content",
            textAlign: "center",
          },
        }}
      />
      <Routes>
        <Route path={"/"} element={<FrontPage/>}/>
        <Route path={"/chef/profile"} element={<ChefProfile/>}/>
        <Route path={"/chef/recipe"} element={<Recipe/>}/>
        <Route path={"/chef/listings"} element={<Listings/>}/>
        <Route path={"/chef/history"} element={<History/>}/>
        <Route path={"/customer/profile"} element={<CustomerProfile/>}/>
        <Route path={"/customer/browse"} element={<BrowseListing/>}/>
        <Route path={"/customer/orders"} element={<Purchases/>}/>
        <Route path={"/customer/history"} element={<CustomerHistory/>}/>
      </Routes>
    </>
  )
}