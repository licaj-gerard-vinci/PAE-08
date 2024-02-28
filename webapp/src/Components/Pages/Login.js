import 'bootstrap/dist/css/bootstrap.min.css';
import { clearPage, renderPageTitle } from '../../utils/render';
import { getRememberMe, setRememberMe } from '../../utils/auths';

import {loginUser} from '../../model/users';
import Navigate from '../Router/Navigate';
import eyeOpen from '../../img/eyeOpen.svg';
import eyeClose from '../../img/eyeClose.svg';



const Login = () => {
  clearPage();
  renderPageTitle('Login');
  renderLoginForm();
  checkUser();
};

function checkUser(){
  const form = document.querySelector('#loginForm');
  const email = document.querySelector('#email');
  const password = document.querySelector('#password');
  

  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    if(!email.value || !password.value) {
      return;
    }
    try {
      await loginUser(email.value, password.value)
      window.location.reload();
      Navigate('/');
    } catch (error) {

      console.error(error);
      
     
    }
  })
}
    
    
function renderLoginForm() {
  const main = document.querySelector('main');
  main.innerHTML = `
    <div class="login-container">
      <h2>Se connecter</h2>

      <form id="loginForm">
        <label for="email">Email<span style="color: red;">*</span></label>
        <input type="email" id="email" name="email" placeholder="email" required style="width: 100%;">

        <br>

        <div style="display: flex; flex-direction: column; align-items: start;">
          <label for="password">Mot de passe<span style="color: red;">*</span></label>
          <div style="display: flex; align-items: center; width: 100%;">
            <input type="password" id="password" name="password" placeholder="mot de passe" required style="flex-grow: 1;">
            <img id="togglePassword" src="${eyeOpen}" alt="toggle password visibility" style="height: 2em; margin-left: 10px;">
          </div>
        </div>

        <br>

        <div class="mb-3 form-check mb-0"> 
          <input type="checkbox" class="form-check-input" id="rememberme">
          <label class="form-check-label" for="rememberme">Remember me</label>
        </div>
        <p><span style="color: red;">*</span> champs obligatoire</p>

        <button type="submit" class="btn btn-primary mt-0">Se connecter</button> 

      </form>
      <p>Pas de compte ? <a href="#" id="registerLink">Enregistrez-vous !</a></p>
    </div>
  `;

  document.getElementById('togglePassword').addEventListener('click', () => {
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

