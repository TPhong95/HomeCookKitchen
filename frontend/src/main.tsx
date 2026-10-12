import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { createRoot } from 'react-dom/client'
import {FrontPage} from "./components/general/frontpage.tsx";
import {ChefProfile} from "./components/chef/chefprofile.tsx";

createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <Routes>
      <Route path={"/"} element={<FrontPage />} />
      <Route path={"/user/chef/profile"} element={<ChefProfile />} />
    </Routes>
  </BrowserRouter>,
)
