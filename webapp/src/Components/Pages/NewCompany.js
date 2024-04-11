import Navigate from '../Router/Navigate';
import {clearPage} from "../../utils/render";
import {getAuthenticatedUser} from "../../utils/auths";
import {getEntreprises,insertEntreprises} from "../../model/entreprises";




const NewCompany = () => {
    const authenticatedUser = getAuthenticatedUser();
    if(!authenticatedUser) {
        Navigate('/');
        return;
    }
    clearPage();
    renderNewCompanyForm();
    checkCompany();
}
function renderNewCompanyForm ()  {
    const main = document.querySelector('main');
    main.innerHTML = `
    <div class="container mt-5"> 
      <div class="row justify-content-center">
        <div class="col-md-6">
          <div class="card shadow mt-5"> 
            <div class="card-body">

              <h2 class="card-title text-center">Nouvelle Entreprise</h2>
                <form>
                  <div class="mb-3 form-group">
                    <label for="name">Nom<span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="RegisterName" placeholder="Nom Entreprise">
                  </div>

                  <div class="mb-3 form-group">
                    <label for="appelation">Appelation</label>
                    <input type="text" class="form-control" id="RegisterAppelation" placeholder="Appelation Entreprise">
                  </div>
                  
                   <div class="mb-3 form-group">
                    <label for="adresse">Adresse<span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="RegisterAdresse" placeholder="Adresse Entreprise">
                  </div>
                   <div class="mb-3 form-group">
                    <label for="city">Ville</label>
                    <input type="text" class="form-control" id="RegisterCity" placeholder="Ville Entreprise">
                  </div>
                  
                  
                  <div class="mb-3 form-group">
                    <label for="phone">Numéro de téléphone</label>
                    <input type="number" class="form-control" id="RegisterPhone" aria-describedby="emailHelp" placeholder="Numéro de téléphonne">
                  </div>
                  
                  
                  
                  
                  <div class="mb-3 form-group">
                    <label for="email">Email de l'entreprise</label>
                    <input type="email" class="form-control" id="RegisterEmail" aria-describedby="emailHelp" placeholder="Email">
                  </div>
                  
                  <p class="text-danger">* Champs obligatoires</p>
                  <button type="submit" class="btn btn-primary" id="EntrepriseSubmit">Créer</button>
                </form>              
            </div>
          </div>
        </div>
      </div>
    </div>
    `;
}
async function checkCompany() {
    const submitButton = document.getElementById('EntrepriseSubmit');
    const name = document.getElementById('RegisterName');
    const appelation = document.getElementById('RegisterAppelation');
    const phone = document.getElementById('RegisterPhone');
    const email = document.getElementById('RegisterEmail');
    const adresse = document.getElementById('RegisterAdresse');
    const city = document.getElementById('RegisterCity');

    const  NameError = document.createElement('p');
    const PhoneError = document.createElement('p');
    const AdresseError = document.createElement('p');
    const SubmitError = document.createElement('p');
    const CityError = document.createElement('p');
    NameError.style.color = 'red';
    AdresseError.style.color = 'red';
    SubmitError.style.color = 'red';
    PhoneError.style.color = 'red';
    CityError.style.color = 'red';


    name.parentNode.insertBefore(NameError, name.nextSibling);
    submitButton.parentNode.insertBefore(SubmitError, submitButton.nextSibling);
    adresse.parentNode.insertBefore(AdresseError, adresse.nextSibling);
    phone.parentNode.insertBefore(PhoneError, phone.nextSibling);
    city.parentNode.insertBefore(CityError, city.nextSibling);

    submitButton.addEventListener('click', async (e) => {
        e.preventDefault(); // prevent the form from being submitted
        const nameValue = name.value;
        const adresseValue = adresse.value;
        const phoneValue = phone.value;
        const emailValue = email.value;
        const cityValue = city.value;
        const appelationValue = appelation.value;


        NameError.textContent = '';
        if(nameValue === '') {
            NameError.textContent = 'Le nom de l\'entreprise est obligatoire';
            return;
        }
        NameError.textContent = '';
        if(adresseValue === '') {
            AdresseError.textContent = 'L\'adresse de l\'entreprise est obligatoire';
            return;
        }
        AdresseError.textContent='';

        if(phoneValue !== '') {
            if(phoneValue.length < 10) {
                PhoneError.textContent = 'Le numéro de téléphone doit avoir au moins 10 chiffres';
                return;
            }
        }
        PhoneError.textContent = '';
        if(cityValue === '') {
            CityError.textContent = 'La ville de l\'entreprise est obligatoire';
            return;
        }
        CityError.textContent = '';
        const entreprise = {
            name: nameValue,
            appelation: appelationValue,
            phone: phoneValue,
            email: emailValue,
            adresse: adresseValue,
            city: cityValue,
            motivation: '',

        }
        const entrepriseList = await getEntreprises();
        const entrepriseExist = entrepriseList.find(entreprises => entreprises.nom === nameValue && entreprises.appellation === appelationValue);
        if(entrepriseExist) {
            SubmitError.textContent = 'Cette entreprise existe déjà';
            return;
        }
        SubmitError.textContent = '';
        await insertEntreprises(entreprise);
        Navigate('/');

    });
}

export default NewCompany;