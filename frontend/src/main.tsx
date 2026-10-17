import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { createRoot } from 'react-dom/client'
import {FrontPage} from "./components/general/frontpage.tsx";
import {ChefProfile} from "./components/chef/chefprofile.tsx";
import {Recipe} from "./components/chef/recipe.tsx";
import {Listings} from "./components/chef/listings.tsx";
import {History} from "./components/chef/history.tsx";

createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <Routes>
      <Route path={"/"} element={<FrontPage />} />
      <Route path={"/user/chef/profile"} element={<ChefProfile />} />
      <Route path={"/user/chef/recipe"} element={<Recipe />} />
      <Route path={"/user/chef/listings"} element={<Listings />} />
      <Route path={"/user/chef/history"} element={<History />} />
    </Routes>
  </BrowserRouter>,
)
