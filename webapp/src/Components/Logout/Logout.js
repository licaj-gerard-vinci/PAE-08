import { clearAuthenticatedUser } from '../../utils/auths';
import Navigate from '../Router/Navigate';
import Navbar from '../Navbar/Navbar';


const Logout = () => {
  clearAuthenticatedUser();
  Navbar();
  Navigate('/login');
};

export default Logout;