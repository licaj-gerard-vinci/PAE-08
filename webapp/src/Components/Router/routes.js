import HomePage from '../Pages/HomePage';
import Login from '../Pages/Login';
import Logout from '../Logout/Logout';
import ProfilePage from '../Pages/Profile';
import UserList from '../Pages/UserList';
import RegisterPage from "../Pages/RegisterPage";
import Internship from "../Pages/Internship";

const routes = {
  '/': HomePage,
  '/login': Login,
  '/logout': Logout,
  '/register': RegisterPage,
  '/profile': ProfilePage,
  '/users': UserList,
  '/internship': Internship,
};

export default routes;

