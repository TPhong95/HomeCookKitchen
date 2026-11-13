import {BrowserRouter} from 'react-router-dom'
import {createRoot} from 'react-dom/client'
import {UserProvider} from "./components/context/UserProvider.tsx";
import {NotificationProvider} from "./components/context/NotificationProvider.tsx";
import {Application} from "./application.tsx";

createRoot(document.getElementById('root')!).render(
  <BrowserRouter>
    <UserProvider>
      <NotificationProvider>
        <Application/>
      </NotificationProvider>
    </UserProvider>
  </BrowserRouter>,
)
