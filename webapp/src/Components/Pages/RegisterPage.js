/* eslint-disable no-console */
import { clearPage } from '../../utils/render';
import {registerUser} from '../../model/users';
import Navigate from '../Router/Navigate';
import {getAuthenticatedUser} from "../../utils/auths";
import Navbar from '../Navbar/Navbar';

const RegisterPage = () => {
    const authenticatedUser = getAuthenticatedUser();
    if(authenticatedUser) {
        Navigate('/');
        return;
    }
    clearPage();
    renderRegisterForm();
    checkUser();
};

function renderRegisterForm() {
    const main = document.querySelector('main');
    main.innerHTML = `
  <div class="container mt-5"> 
      <div class="row justify-content-center">
        <div class="col-md-6">
          <div class="card shadow mt-5"> 
            <div class="card-body">

              <h2 class="card-title text-center">S'inscrire</h2>
                <form>
                  <div class="mb-3 form-group">
                    <label for="name">Nom<span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="RegisterLastname" placeholder="Nom">
                  </div>

                  <div class="mb-3 form-group">
                    <label for="firstname">Prénom<span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="RegisterFirstname" placeholder="Prénom">
                  </div>

                  <div class="mb-3 form-group">
                    <label for="email">Email address<span class="text-danger">*</span></label>
                    <input type="email" class="form-control" id="RegisterEmail" aria-describedby="emailHelp" placeholder="Email">
                  </div>

                  <div class="mb-3 form-group">
                    <label for="role" id="RegisterRoleLabel">Role<span class="text-danger">*</span></label>
                    <select class="form-control" id="RegisterRole">
                      
                    </select>
                  </div>

                  <div class="mb-3 form-group">
                    <label for="phone">Numéro de téléphone<span class="text-danger">*</span></label>
                    <input type="number" class="form-control" id="RegisterPhone" aria-describedby="emailHelp" placeholder="Numéro de téléphonne">
                  </div>

                  <div class=" mb-3 form-group">
                    <label for="password">Mot de passe<span class="text-danger">*</span></label>
                    <input type="password" class="form-control" id="RegisterPassword" placeholder="Mot de passe">
                  </div>

                  <div class="mb-3 form-group">
                    <label for="confirmPassword">Répétez mot de passe<span class="text-danger">*</span></label>
                    <input type="password" class="form-control" id="RegisterConfirmPassword" placeholder="Répétez mot de passe">
                  </div>

                  <p class="text-danger">* Champs obligatoires</p>

                  <button type="submit" class="btn btn-primary" id="registerSubmit">S'inscrire</button>

                </form>

                <p class="text-center">Déjà un compte ? <a href="#" data_uri="/login" id="loginLink" class="text-primary" data-uri="login">Connectez vous !</a></p>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  `;

}

function checkUser(){
  const email = document.getElementById('RegisterEmail');
  const role = document.getElementById('RegisterRole');
  const roleLabel = document.getElementById('RegisterRoleLabel');
  const password = document.querySelector('#RegisterPassword');
  const lastname = document.querySelector('#RegisterLastname');
  const firstname = document.querySelector('#RegisterFirstname');
  const confirmPassword = document.querySelector('#RegisterConfirmPassword');
  const phone = document.querySelector('#RegisterPhone');
  const registerSubmit = document.querySelector('#registerSubmit');

  const errorMessage = document.createElement('p');
  errorMessage.style.color = 'red';
  email.parentNode.insertBefore(errorMessage, email.nextSibling);

  const passwordErrorMessage = document.createElement('p');
  passwordErrorMessage.style.color = 'red';
  confirmPassword.parentNode.insertBefore(passwordErrorMessage, confirmPassword.nextSibling);

  role.style.display = 'none';
  roleLabel.style.display = 'none';

  email.addEventListener('input', () => {
    const emailValue = email.value;
    const lastnameValue = lastname.value;
    const firstnameValue = firstname.value;
  
    const expectedEmail = `${firstnameValue.toLowerCase()}.${lastnameValue.toLowerCase()}@`;
  
    if (emailValue.startsWith(expectedEmail)) {
      if (emailValue.endsWith('@student.vinci.be')) {
        role.innerHTML = '<option>Etudiant</option>';
        role.style.display = 'block';
        roleLabel.style.display = 'block';
        errorMessage.textContent = ''; // Effacez le message d'erreur
      } 
      else if (emailValue.endsWith('@vinci.be')) {
        role.innerHTML = '<option>Professeur</option><option>Administratif</option>';
        role.style.display = 'block';
        roleLabel.style.display = 'block';
        errorMessage.textContent = ''; // Effacez le message d'erreur
      } 
      else {
        role.style.display = 'none';
        errorMessage.textContent = 'L\'email doit se terminer par @student.vinci.be ou @vinci.be';
      }
    } else {
      errorMessage.textContent = 'L\'email doit commencer par nom.prenom@';
    }
  });

  password.addEventListener('input', checkPasswords);
  confirmPassword.addEventListener('input', checkPasswords);

  function checkPasswords() {
    if (password.value !== confirmPassword.value) {
      passwordErrorMessage.textContent = 'Les mots de passe ne correspondent pas.';
    } else {
      passwordErrorMessage.textContent = '';
    }
  }
  

  registerSubmit.addEventListener('click', async (e) => {
      e.preventDefault();
      if(!email.value || !password.value || !lastname.value || !firstname.value || !role.value || !phone) {
          return;
      }
      
      const user = {
          lastname : lastname.value,
          firstname : firstname.value,
          password : password.value,
          email : email.value,
          role : role.value.charAt(0).toUpperCase(),
          phone : phone.value
      }
      try {
          await registerUser(user);
          Navbar();
          Navigate('/');
      } catch (error) {

          console.error(error);


      }
  });
}



export default RegisterPage;
