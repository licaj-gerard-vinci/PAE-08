// eslint-disable-next-line no-unused-vars
import { Navbar as BootstrapNavbar } from 'bootstrap';
import {isAuthenticated, getAuthenticatedUser} from '../../utils/auths';
import logo from '../../img/download 2.png';

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
          <div class="row w-100">
            <div class="col-lg-4 d-flex align-items-center">
              <a class="navbar-brand" href="#" style="color: #fff;">VinciTech Solutions</a>
            </div>
            <div class="col-lg-4 text-center d-flex align-items-center">
              <img src="${logo}" alt="Logo">
            </div>
            <div class="col-lg-4 d-flex align-items-center">
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
        <div class="row w-100">
          <div class="col-lg-4 d-flex align-items-center">
            <a class="navbar-brand" href="#" style="color: #fff;">VinciTech Solutions</a>
          </div>
          <div class="col-lg-4 text-center d-flex align-items-center">
            <img src="${logo}" alt="Logo">
          </div>
          <div class="col-lg-4 d-flex align-items-center">
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
              <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="nav-item ms-auto">
                  <a class="nav-link" href="#" data-uri="/logout" style="color: #fff;">Se deconnecter</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" style="color: #fff;">${userFirstName} ${userName}</a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </nav>
    `;

    const navbar = document.querySelector('#navbarWrapper');



    if (isAuthenticated()) {
      navbar.innerHTML = authenticatedUser;
    }

    else  {
      navbar.innerHTML = unauthenticatedUser;
    }

  }


export default Navbar;
