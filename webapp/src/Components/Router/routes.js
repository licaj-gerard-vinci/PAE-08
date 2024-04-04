import HomePage from '../Pages/HomePage';
import Login from '../Pages/Login';
import Logout from '../Logout/Logout';
import ProfilePage from '../Pages/Profile';
import UserList from '../Pages/UserList';
import RegisterPage from "../Pages/RegisterPage";
import Internship from "../Pages/Internship";
import CompanyPage from "../Pages/CompanyPage";
import UserDetailsPage from "../Pages/UserDetailsPage";

const routes = {
  '/': HomePage,
  '/login': Login,
  '/logout': Logout,
  '/register': RegisterPage,
  '/profile': ProfilePage,
  '/studentInfo': UserDetailsPage,
  '/users': UserList,
  '/internship': Internship,
  '/company': CompanyPage,
};

export default routes;

