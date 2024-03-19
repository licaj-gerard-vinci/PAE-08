import HomePage from '../Pages/HomePage';
import NewPage from '../Pages/NewPage';
import Login from '../Pages/Login';
import Logout from '../Logout/Logout';
import ProfilePage from '../Pages/Profile';
import UserList from '../Pages/UserList';


const routes = {
  '/': HomePage,
  '/new': NewPage,
  '/login': Login,
  '/logout': Logout,
  '/profile': ProfilePage,
  '/users': UserList,
};

export default routes;

