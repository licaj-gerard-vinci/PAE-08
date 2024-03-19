import { clearPage } from '../../utils/render';
import {registerUser} from '../../model/users';
import Navigate from '../Router/Navigate';

const RegisterPage = () => {
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
                  <div class="form-group">
                    <label for="name">Nom<span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="RegisterName" placeholder="Nom">
                  </div>

                  <div class="form-group">
                    <label for="firstname">Prénom<span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="RegisterFirstname" placeholder="Prénom">
                  </div>

                  <div class="form-group">
                    <label for="email">Email address<span class="text-danger">*</span></label>
                    <input type="email" class="form-control" id="RegisterEmail" aria-describedby="emailHelp" placeholder="Email">
                  </div>

                  <div class="form-group">
                    <label for="phone">Numéro de téléphone<span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="RegisterPhone" aria-describedby="emailHelp" placeholder="Numéro de téléphonne">
                  </div>

                  <div class="form-group">
                    <label for="role">Rôle<span class="text-danger">*</span></label>
                    <select class="form-control" id="RegisterRole">
                      <option>Professeur</option>
                      <option>Administratif</option>
                    </select>
                  </div>

                  <div class="form-group">
                    <label for="password">Mot de passe<span class="text-danger">*</span></label>
                    <input type="password" class="form-control" id="RegisterPassword" placeholder="Mot de passe">
                  </div>

                  <div class="form-group">
                    <label for="confirmPassword">Répétez mot de passe<span class="text-danger">*</span></label>
                    <input type="password" class="form-control" id="RegisterConfirmPassword" placeholder="Répétez mot de passe">
                  </div>

                  <button type="submit" class="btn btn-primary">S'inscrire</button>

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
    const form = document.querySelector('#RegisterForm');
    const email = document.querySelector('#RegisterEmail');
    const password = document.querySelector('#RegisterPassword');
    const name = document.querySelector('#RegisterName');
    const firstname = document.querySelector('#RegisterFirstname');
    const role = document.querySelector('#RegisterRole');
    const confirmPassword = document.querySelector('#RegisterConfirmPassword');
    const phone = document.querySelector('#RegisterPhone');



    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        if(!email.value || !password.value || !name.value || !firstname.value || !role.value || !confirmPassword.value || !phone) {
            return;
        }
        const user = {
            name,
            firstname,
            email,
            role,
            password,
            confirmPassword,
            phone
        }
        try {
            await registerUser(user);
            window.location.reload();
            Navigate('/');
        } catch (error) {

            console.error(error);


        }
    })
}

export default RegisterPage;
