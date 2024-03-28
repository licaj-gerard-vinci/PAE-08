import HomePage from '../Pages/HomePage';
import Login from '../Pages/Login';
import Logout from '../Logout/Logout';
import ProfilePage from '../Pages/Profile';
import UserList from '../Pages/UserList';
import RegisterPage from "../Pages/RegisterPage";

const routes = {
  '/': HomePage,
  '/login': Login,
  '/logout': Logout,
  '/register': RegisterPage,
  '/profile': ProfilePage,
  '/users': UserList,
};

export default routes;

