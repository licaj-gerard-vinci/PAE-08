// eslint-disable-next-line no-unused-vars
import { Navbar as BootstrapNavbar } from 'bootstrap';
import {isAuthenticated, getAuthenticatedUser} from '../../utils/auths';
import logo from '../../img/HELOGO.png';

/**
 * Render the Navbar which is styled by using Bootstrap
 * Each item in the Navbar is tightly coupled with the Router configuration :
 * - the URI associated to a page shall be given in the attribute "data-uri" of the Navbar
 * - the router will show the Page associated to this URI when the user click on a nav-link
 */

const Navbar = () => {
    renderNavbar();
};


function renderNavbar() {

    const unauthenticatedUser = `
    <nav class="navbar navbar-expand-lg" style="background-color: #00609D;">
      <div class="container-fluid">
       <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>


        <a class="navbar-brand" href="#" data-uri="/" style="color: #fff; display: flex; align-items: center;">
          <img src="${logo}" alt="Logo" style="margin-right: 10px;">
          VinciTech Solutions
        </a>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <a class="nav-link" id="login" href="#" data-uri="/login" style="color: #fff;">Se connecter</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#" data-uri="/register" style="color: #fff;">Inscription</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  `;




    const user = getAuthenticatedUser();
    const userFirstName = user?.firstName || '';
    const userName = user?.name || '';

    const authenticatedUser = `
  <nav class="navbar navbar-expand-lg" style="background-color: #00609D;">
    <div class="container-fluid">

      <button class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
      </button>

        <a class="navbar-brand d-flex align-items-center" href="#" data-uri="/" style="color: #fff;">
        
          <img src="${logo}" alt="Logo" style="margin-right: 10px;">
          VinciTech Solutions
        </a>

        <div class="d-flex align-items-center ms-auto text-white">
          <a class="nav-link" href="#" data-uri="/logout" style="margin-right: 15px;">Se déconnecter</a>
          <a class="nav-link" href="#" data-uri="/profile"> ${userFirstName} ${userName} </a>
        </div>

        
    
    </div>
  </nav>
`;



    const navbarWrapper = document.querySelector('#navbarWrapper');

    if (isAuthenticated()) {
        navbarWrapper.innerHTML = authenticatedUser;
    } else {
        navbarWrapper.innerHTML = unauthenticatedUser;
    }


}


export default Navbar;
