// eslint-disable-next-line no-unused-vars
import { Navbar as BootstrapNavbar } from 'bootstrap';
import {isAuthenticated} from '../../utils/auths';

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
<nav class="navbar navbar-expand-lg navbar-light bg-light">
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
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ms-auto mb-2 mb-lg-0 justify-content-end">

        <li class="nav-item">
          <a class="nav-link" id="login" href="" data-uri="/login">Se connecter</a>
        </li>
                         
        </ul>
      </div>
    </div>
  </nav>
`;
    
    

  const authenticatedUser = `
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
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
       <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
    
          
    
          <li class="nav-item">
            <a class="nav-link" href="" data-uri="/logout">Se deconnecter</a>
          </li> 
         
                                    
          </ul>
        </div>
      </div>
    </nav>
   `

    const navbar = document.querySelector('#navbarWrapper');



    if (isAuthenticated()) {
      navbar.innerHTML = authenticatedUser;
    }

    else  {
      navbar.innerHTML = unauthenticatedUser;
    }

  }


export default Navbar;
