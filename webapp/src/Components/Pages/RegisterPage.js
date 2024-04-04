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


                  <p class="text-danger">* Champs obligatoires</p>

                  <button type="submit" class="btn btn-primary" id="registerSubmit">S'inscrire</button>

                </form>

                <p class="text-center">Déjà un compte ? <a href="#" data_uri="/login" id="loginLink" class="text-primary" data-uri="login">Connectez vous !</a></p>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  `;}

function checkUser(){
  const email = document.getElementById('RegisterEmail');
  const role = document.getElementById('RegisterRole');
  const roleLabel = document.getElementById('RegisterRoleLabel');
  const password = document.querySelector('#RegisterPassword');
  const lastname = document.querySelector('#RegisterLastname');
  const firstname = document.querySelector('#RegisterFirstname');
  const phone = document.querySelector('#RegisterPhone');
  const registerSubmit = document.querySelector('#registerSubmit');

  // Créez un élément de message d'erreur pour chaque champ
  const emailError = document.createElement('p');
  const passwordError = document.createElement('p');
  const lastnameError = document.createElement('p');
  const firstnameError = document.createElement('p');
  const phoneError = document.createElement('p');

  // Définissez la couleur de ces éléments en rouge
  emailError.style.color = 'red';
  passwordError.style.color = 'red';
  lastnameError.style.color = 'red';
  firstnameError.style.color = 'red';
  phoneError.style.color = 'red';

  // Insérez chaque élément de message d'erreur après le champ correspondant
  email.parentNode.insertBefore(emailError, email.nextSibling);
  password.parentNode.insertBefore(passwordError, password.nextSibling);
  lastname.parentNode.insertBefore(lastnameError, lastname.nextSibling);
  firstname.parentNode.insertBefore(firstnameError, firstname.nextSibling);
  phone.parentNode.insertBefore(phoneError, phone.nextSibling);

  role.style.display = 'none';
  roleLabel.style.display = 'none';

  email.addEventListener('input', () => {
    const emailValue = email.value;
    const firstnameValue = firstname.value.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
    const lastnameValue = lastname.value.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
    
    const expectedEmail = `${firstnameValue.toLowerCase()}.${lastnameValue.toLowerCase()}@`;
  
    if (emailValue.startsWith(expectedEmail)) {
      if (emailValue.endsWith('@student.vinci.be')) {
        role.innerHTML = '<option>Etudiant</option>';
        role.style.display = 'block';
        roleLabel.style.display = 'block';
        emailError.textContent = ''; // Effacez le message d'erreur
      } 
      else if (emailValue.endsWith('@vinci.be')) {
        role.innerHTML = '<option>Professeur</option><option>Administratif</option>';
        role.style.display = 'block';
        roleLabel.style.display = 'block';
        emailError.textContent = ''; // Effacez le message d'erreur
      } 
      else {
        role.style.display = 'none';
        emailError.textContent = 'L\'email doit se terminer par @student.vinci.be ou @vinci.be';
      }
    } else {
      emailError.textContent = 'L\'email doit commencer par prenom.nom@';
    }
  });
  
  registerSubmit.addEventListener('click', async (e) => {
    e.preventDefault();

    // Réinitialisez tous les messages d'erreur
    emailError.textContent = '';
    passwordError.textContent = '';
    lastnameError.textContent = '';
    firstnameError.textContent = '';
    phoneError.textContent = '';
    
    // Vérifiez chaque champ et affichez un message d'erreur s'il est vide
    if (!email.value) emailError.textContent = 'Email est vide';
    if (!password.value) passwordError.textContent = 'Mot de passe est vide';
    if (!lastname.value) lastnameError.textContent = 'Nom est vide';
    if (!firstname.value) firstnameError.textContent = 'Prénom est vide';
    if (!phone.value) phoneError.textContent = 'Numéro de téléphone est vide';

    // Si tous les champs sont remplis, continuez avec le reste du code...
    if (emailError.textContent || passwordError.textContent || lastnameError.textContent || firstnameError.textContent || phoneError.textContent) return;

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
