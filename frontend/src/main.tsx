import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { createRoot } from 'react-dom/client'
import {FrontPage} from "./components/general/frontpage.tsx";
import {ChefProfile} from "./components/chef/chefprofile.tsx";
import {Recipe} from "./components/chef/recipe.tsx";
import {Listings} from "./components/chef/listings.tsx";
import {History} from "./components/chef/history.tsx";
import {CustomerProfile} from "./components/customer/customerprofile.tsx";
import {BrowseListing} from "./components/customer/browse.tsx";
import {Orders} from "./components/customer/orders.tsx";
import {Cart} from "./components/customer/cart.tsx"
import {CustomerHistory} from "./components/customer/history.tsx";

createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <Routes>
      <Route path={"/"} element={<FrontPage />} />
      <Route path={"/user/chef/profile"} element={<ChefProfile />} />
      <Route path={"/user/chef/recipe"} element={<Recipe />} />
      <Route path={"/user/chef/listings"} element={<Listings />} />
      <Route path={"/user/chef/history"} element={<History />} />
      <Route path={"/user/customer/profile"} element={<CustomerProfile />} />
      <Route path={"/user/customer/browse"} element={<BrowseListing />} />
      <Route path={"/user/customer/orders"} element={<Orders />} />
      <Route path={"/user/customer/cart"} element={<Cart />} />
      <Route path={"/user/customer/history"} element={<CustomerHistory />} />
    </Routes>
  </BrowserRouter>,
)
