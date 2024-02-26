import { clearAuthenticatedUser } from '../../utils/auths';
import Navbar from '../Navbar/Navbar';
import Navigate from '../Router/Navigate';

// deconnect the user and clear the session
const Logout = () => {
  clearAuthenticatedUser();
  Navbar();
  Navigate('/');
};

export default Logout;

/*
**************************************************************************************
*    Title: <
Logout
  >
*    Author: <Baroni>
*    Date: <15/12/2023>
*    Code version: <code version>
*    Availability: <https://github.com/e-vinci/js-exercises/tree/main>

***************************************************************************************
*/
