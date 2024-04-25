import { isAuthenticated } from '../../utils/auths';
import logo from '../../img/logoNavBar.jpg';
import { refreshUser } from '../../model/users';
import Navigate from "../Router/Navigate";

const Navbar = () => {
    renderNavbar();
};

async function renderAuthenticatedNavbar(user) {
    const userFirstName = user?.firstname || '';
    const userName = user?.lastname || '';
    const showSearchUserButton = user?.role === 'A' || user?.role === 'P';
    const showDashboard = user?.role === 'P';
    const showCompaniesListButton = user?.role === 'E' && user?.hasInternship === false;

    return `
    <nav class="navbar navbar-expand-lg" style="background-color: #00609D;">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <a class="navbar-brand d-flex align-items-center" style="color: #fff; font-size: 0.9em;">
                <img class="logoIdNavbar hover-cursor" src="${logo}" alt="Logo" style="margin-right: 10px; width: 260px; height: 62px;">
            </a>
            ${showDashboard ? `
            <a id="dashboard" class="nav-link" href="#" data-uri="/dashboard" style="color: #fff; margin-right: 15px; font-size: 1.1em;">Tableau de bord</a>` : ''}
            ${showSearchUserButton ? `
            <a id="dashboard" class="nav-link" href="#" data-uri="/users" style="color: #fff; margin-right: 15px; font-size: 1.1em;">Recherche utilisateur</a>` : ''}
            ${showCompaniesListButton ? `
            <a id="companiesList" class="nav-link" href="#" data-uri="/" style="color: #fff; margin-right: 15px; font-size: 1.1em;">Liste des entreprises</a>` : ''}
            <div class="d-flex align-items-center ms-auto text-white" style="font-size: 1.1em;">
                <a class="nav-link" href="#" data-uri="/logout" style="color: #fff; margin-right: 15px;">Se déconnecter</a>
                <a class="nav-link" href="#" data-uri="/profile" style="color: #fff;"> ${userFirstName} ${userName} </a>
            </div>
        </div>
    </nav>
    `;
}

function renderUnauthenticatedNavbar() {
    return `
    <nav class="navbar navbar-expand-lg" style="background-color: #00609D;">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <a class="navbar-brand" style="color: #fff; display: flex; align-items: center; font-size: 0.9em;">
                <img src="${logo}" alt="Logo" style="margin-right: 10px; width: 260px; height: 62px;">
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
}

async function renderNavbar() {
    const user = await refreshUser();
    const navbarWrapper = document.querySelector('#navbarWrapper');
    navbarWrapper.innerHTML = isAuthenticated() ? await renderAuthenticatedNavbar(user) : renderUnauthenticatedNavbar();

    attachLogoClickEvent();
}

function attachLogoClickEvent() {
    const logoIdNavbar = document.querySelector('.logoIdNavbar');
    if (logoIdNavbar) {
        logoIdNavbar.addEventListener('click', () => {
            Navigate('/');
        });
    }
}

export default Navbar;