/* eslint-disable no-console */
import {refreshUser} from '../../model/users';
import Navigate from './Navigate';
import routes from "./routes";

const Router = () => {
  onFrontendLoad();
  onNavBarClick();
  onHistoryChange();
};

function onNavBarClick() {
  const navbarWrapper = document.querySelector('#navbarWrapper');

  navbarWrapper.addEventListener('click', (e) => {
    e.preventDefault();
    const navBarItemClicked = e.target;
    const uri = navBarItemClicked?.dataset?.uri;
    if (uri) {
      const componentToRender = routes[uri];
      if (!componentToRender) throw Error(`The ${uri} ressource does not exist.`);

      componentToRender();
      window.history.pushState({}, '', uri);
    }
  });
}

function onHistoryChange() {
  window.addEventListener('popstate', (e) => {
    const uri = window.location.pathname;
    const componentToRender = routes[uri];
    if (!componentToRender) throw Error(`The ${uri} resource does not exist.`);
    componentToRender(e.state);
  });
}

async function onFrontendLoad() {
  window.addEventListener('load', async () => {
    const uri = window.location.pathname;
    const componentToRender = routes[uri];

    // Si le composant n'existe pas, redirigez vers une page d'erreur ou de connexion
    if (!componentToRender) {
      console.error(`The resource ${uri} does not exist.`);
      Navigate('/error'); // Changez '/error' par votre route d'erreur
      return;
    }

    // Tentez de rafraîchir l'utilisateur
    const result = await refreshUser();
    if (result === undefined || result === null) {
      // Autorisez l'accès à la page d'inscription même si l'utilisateur n'est pas connecté
      if (uri !== '/login' && uri !== '/register') {
        Navigate('/login');
        return;
      }
    } else if (uri === '/login' || uri === '/register') {
      Navigate('/'); // Redirigez vers la page d'accueil si l'utilisateur est déjà connecté
      return;
    }

    componentToRender();
  });
}

export default Router;