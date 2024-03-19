import HomePage from '../Pages/HomePage';
import NewPage from '../Pages/NewPage';
import Login from '../Pages/Login';
import Logout from '../Logout/Logout';
import ProfilePage from '../Pages/Profile';
import RegisterPage from "../Pages/RegisterPage";


const routes = {
  '/': HomePage,
  '/new': NewPage,
  '/login': Login,
  '/logout': Logout,
  '/profile': ProfilePage,
  '/register': RegisterPage
};

export default routes;

