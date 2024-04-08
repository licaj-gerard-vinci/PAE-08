import HomePage from '../Pages/HomePage';
import Login from '../Pages/Login';
import Logout from '../Logout/Logout';
import ProfilePage from '../Pages/Profile';
import UserList from '../Pages/UserList';
import RegisterPage from "../Pages/RegisterPage";
import NewCompany from "../Pages/NewCompany";
import Internship from "../Pages/Internship";
import CompanyPage from "../Pages/CompanyPage";
import StudentInfoPage from "../Pages/StudentInfoPage";

const routes = {
  '/': HomePage,
  '/login': Login,
  '/logout': Logout,
  '/register': RegisterPage,
  '/profile': ProfilePage,
  '/users': UserList,
  '/addCompany': NewCompany,
  '/internship': Internship,
  '/company': CompanyPage,
  '/studentInfo': StudentInfoPage,
};

export default routes;

