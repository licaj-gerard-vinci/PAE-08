// eslint-disable-next-line no-unused-vars
import { Navbar as BootstrapNavbar } from 'bootstrap';
import { isAuthenticated, getAuthenticatedUser } from '../../utils/auths';
import logo from '../../img/HELOGO.png';

const Navbar = () => {
    renderNavbar();
};

function renderNavbar() {
    const user = getAuthenticatedUser();
    const userFirstName = user?.user?.firstname || '';
    const userName = user?.user?.lastname || '';

    // Déterminer si le bouton de recherche utilisateur doit être affiché
    const showSearchUserButton = user?.user?.role === 'A' || user?.user?.role === 'P';
    const showCompaniesListButton = user?.user?.role === 'E';



    const authenticatedUser = `
    <nav class="navbar navbar-expand-lg" style="background-color: #00609D;">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <a class="navbar-brand d-flex align-items-center" href="#" data-uri="/" style="color: #fff; font-size: 0.9em;">
                <img src="${logo}" alt="Logo" style="margin-right: 10px;">
            </a>
            ${showSearchUserButton ? `
            <a id="dashboard" class="nav-link" href="#" data-uri="/dashboard" style="color: #fff; margin-right: 15px; font-size: 1.1em;">Tableau de bord</a>
            <a id="searchUser" class="nav-link" href="#" data-uri="/users" style="color: #fff; margin-right: 15px; font-size: 1.1em;">Recherche utilisateur</a>` : ''}
            ${showCompaniesListButton ? `
            <a id="companiesList" class="nav-link" href="#" data-uri="/" style="color: #fff; margin-right: 15px; font-size: 1.1em;">Liste des entreprises</a>` : ''}
            <div class="d-flex align-items-center ms-auto text-white" style="font-size: 1.1em;">
                <a class="nav-link" href="#" data-uri="/logout" style="color: #fff; margin-right: 15px;">Se déconnecter</a>
                <a class="nav-link" href="#" data-uri="/profile" style="color: #fff;"> ${userFirstName} ${userName} </a>
            </div>
        </div>
    </nav>
    `;




    const unauthenticatedUser = `
    <nav class="navbar navbar-expand-lg" style="background-color: #00609D;">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <a class="navbar-brand" href="#" data-uri="/" style="color: #fff; display: flex; align-items: center; font-size: 0.9em;">
                <img src="${logo}" alt="Logo" style="margin-right: 10px;">
            </a>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0" style="font-size: 1.1em;">
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

    const navbarWrapper = document.querySelector('#navbarWrapper');
    navbarWrapper.innerHTML = isAuthenticated() ? authenticatedUser : unauthenticatedUser;
}

export default Navbar;
