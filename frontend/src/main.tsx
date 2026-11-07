import {BrowserRouter} from 'react-router-dom'
import {createRoot} from 'react-dom/client'
import {UserProvider} from "./components/context/UserProvider.tsx";
import {Application} from "./application.tsx";

createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <UserProvider>
      <Application />
    </UserProvider>
  </BrowserRouter>,
)
