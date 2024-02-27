import { clearAuthenticatedUser } from '../../utils/auths';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

// deconnect the user and clear the session
const Logout = () => {
  clearAuthenticatedUser();
  Navbar();
  Navigate('/login');
};

export default Logout;


