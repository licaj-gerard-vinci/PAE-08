import 'bootstrap/dist/css/bootstrap.min.css';
import { clearPage, renderPageTitle } from '../../utils/render';
import { getRememberMe, setRememberMe } from '../../utils/auths';


// eslint-disable-next-line import/named
import loginUser from '../../model/users';
import Navigate from '../Router/Navigate';


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

    try {
      await loginUser(email.value, password.value)
      Navigate('/');
      window.location.reload();
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
        <label for="email">Email</label>
        <input type="email" id="email" name="email" placeholder="email" required>

        <br>

        <label for="password">Mot de passe</label>
        <input type="password" id="password" name="password" placeholder="mot de passe" required>

        <br>

        <div class="mb-3 form-check mb-0"> 
          <input type="checkbox" class="form-check-input" id="rememberme">
          <label class="form-check-label" for="rememberme">Remember me</label>
        </div>

        <button type="submit" class="btn btn-primary mt-0">Se connecter</button> 

      </form>
      <p>Pas de compte ? <a href="#" id="registerLink">Enregistrez-vous !</a></p>
    </div>
  `;

  const rememberme = document.querySelector('#rememberme');
  const remembered = getRememberMe();
  rememberme.checked = remembered;
  rememberme.addEventListener('click', onCheckboxClicked);
}

  function onCheckboxClicked(e) {
    setRememberMe(e.target.checked);
  }


  export default Login;
