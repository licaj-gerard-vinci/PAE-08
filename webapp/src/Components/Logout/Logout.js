import { clearAuthenticatedUser } from '../../utils/auths';
import Navbar from '../Navbar/Navbar';
// deconnect the user and clear the session
const Logout = () => {
  clearAuthenticatedUser();
  Navbar();
  window.location.reload();
};

export default Logout;


