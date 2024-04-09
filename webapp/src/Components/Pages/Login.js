/* eslint-disable no-console */
import 'bootstrap/dist/css/bootstrap.min.css';
import { clearPage, renderPageTitle } from '../../utils/render';
import { getRememberMe, setRememberMe } from '../../utils/auths';

import {loginUser} from '../../model/users';
import Navigate from '../Router/Navigate';
import eyeOpen from '../../img/eyeOpen.svg';
import eyeClose from '../../img/eyeClose.svg';
import Navbar from '../Navbar/Navbar';



const Login = () => {
  clearPage();
  renderPageTitle('Login');
  renderLoginForm();
  checkUser();

};

function checkUser() {
  const form = document.querySelector('#loginForm');
  const email = document.querySelector('#email');
  const password = document.querySelector('#password');
  const errorMessage = document.createElement('p');
  errorMessage.classList.add('text-danger', 'mt-2');
  errorMessage.id = 'error-message';

  form.addEventListener('submit', async (e) => {
        e.preventDefault();


        const previousError = document.querySelector('#error-message');
        if (previousError) previousError.remove();


        try {
          await loginUser(email.value, password.value);
          Navbar();
          Navigate('/');
        } catch (error) {
          console.error(error);
          errorMessage.id = 'error-message';
          errorMessage.textContent = 'Erreur : Nom d\'utilisateur ou mot de passe invalide.';
          errorMessage.style.fontWeight = 'bold';
          errorMessage.style.backgroundColor = 'grey';
          errorMessage.style.color = 'black';
          errorMessage.style.padding = '10px';
          errorMessage.style.marginTop = '10px';
          errorMessage.style.borderRadius = '4px';


          const rememberMeElement = document.querySelector('.form-check');
          rememberMeElement.insertAdjacentElement('afterend', errorMessage);
        }
      }
  )};




function renderLoginForm() {
  const main = document.querySelector('main');
  main.innerHTML = `
    <div class="container mt-5"> 
      <div class="row justify-content-center">
        <div class="col-md-6">
          <div class="card shadow mt-5"> 
            <div class="card-body">

              <h2 class="card-title text-center">Se connecter</h2>

              <form id="loginForm" class="my-4">

                <div class="mb-3">
                  <label for="email" class="form-label">Email<span class="text-danger"> *</span></label>
                  <input type="email" id="email" name="email" class="form-control" placeholder="email" autocomplete="username" required>
                </div>

                <div class="mb-3">
                  <label for="password" class="form-label">Mot de passe<span class="text-danger"> *</span></label>
                  <div class="input-group">
                    <input type="password" id="password" name="password" class="form-control" placeholder="mot de passe" autocomplete="current-password" required>
                    <button id="togglePassword" class="btn btn-outline-secondary" type="button">
                      <img src="${eyeOpen}" alt="toggle password visibility">
                    </button>

                  </div>
                </div>

                <div class="mb-3 form-check">
                  <input type="checkbox" class="form-check-input" id="rememberme">
                  <label class="form-check-label" for="rememberme">Remember me</label>
                </div>

                <p class="text-danger">* champs obligatoire</p>

                <button type="submit" class="btn btn-primary w-100">Se connecter</button>

              </form>

              <p class="text-center">Pas de compte ? <a id="registerLink" class="text-primary" style="cursor:pointer;">Enregistrez-vous !</a></p>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  `;

  const registerLink = document.getElementById('registerLink');
  registerLink.addEventListener('click', () => {
    Navigate('/register');
  });


  document.getElementById('togglePassword').addEventListener('click', (e) => {
    e.preventDefault();
    const passwordInput = document.getElementById('password');
    const togglePasswordImage = document.getElementById('togglePassword');
    const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
    passwordInput.setAttribute('type', type);

    togglePasswordImage.src = type === 'text' ? eyeOpen : eyeClose;
  });

  const rememberme = document.querySelector('#rememberme');
  const remembered = getRememberMe();
  rememberme.checked = remembered;
  rememberme.addEventListener('click', onCheckboxClicked);
}

function onCheckboxClicked(e) {
  setRememberMe(e.target.checked);
}


export default Login;


