import Navigate from '../Router/Navigate';
import {clearPage} from "../../utils/render";
import {getAuthenticatedUser} from "../../utils/auths";
import {getEntreprises,insertEntreprises} from "../../model/company";

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
                    <label for="address">Adresse<span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="RegisterAddress" placeholder="Adresse Entreprise">
                  </div>
                   <div class="mb-3 form-group">
                    <label for="city">Ville<span class="text-danger">*</label>
                    <input type="text" class="form-control" id="RegisterCity" placeholder="Ville Entreprise">
                  </div>
                  
                  <div class="mb-3 form-group">
                    <label for="CodePostal">Code Postal<span class="text-danger">*</span></label>
                    <input type="number" class="form-control" id="CodePostal" placeholder="Code Postal">
                  </div>
                  
                  
                  <div class="mb-3 form-group">
                    <label for="phone">Numéro de téléphone</label>
                    <input type="text" class="form-control" id="RegisterPhone" aria-describedby="emailHelp" placeholder="Numéro de téléphonne">
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
    const address = document.getElementById('RegisterAddress');
    const city = document.getElementById('RegisterCity');
    const codePostal = document.getElementById('CodePostal');

    const  NameError = document.createElement('p');
    const PhoneError = document.createElement('p');
    const AddressError = document.createElement('p');
    const SubmitError = document.createElement('p');
    const CityError = document.createElement('p');
    const EmailError = document.createElement('p');
    const CodePostalError = document.createElement('p');
    NameError.style.color = 'red';
    AddressError.style.color = 'red';
    SubmitError.style.color = 'red';
    PhoneError.style.color = 'red';
    CityError.style.color = 'red';
    EmailError.style.color = 'red';
    CodePostalError.style.color = 'red';


    name.parentNode.insertBefore(NameError, name.nextSibling);
    submitButton.parentNode.insertBefore(SubmitError, submitButton.nextSibling);
    address.parentNode.insertBefore(AddressError, address.nextSibling);
    phone.parentNode.insertBefore(PhoneError, phone.nextSibling);
    city.parentNode.insertBefore(CityError, city.nextSibling);
    email.parentNode.insertBefore(EmailError, email.nextSibling);
    codePostal.parentNode.insertBefore(CodePostalError, codePostal.nextSibling);

    submitButton.addEventListener('click', async (e) => {
        e.preventDefault(); // prevent the form from being submitted
        const nameValue = name.value;
        const addressValue = address.value;
        const phoneValue = phone.value;
        const emailValue = email.value;
        const cityValue = city.value;
        const appelationValue = appelation.value;
        const codePostalValue = codePostal.value;


        NameError.textContent = '';
        if(nameValue === '') {
            NameError.textContent = 'Le nom de l\'entreprise est obligatoire';
            return;
        }
        NameError.textContent = '';

        AddressError.textContent = '';
        if(addressValue === '') {
            AddressError.textContent = 'L\'adresse de l\'entreprise est obligatoire';
            return;
        }
        AddressError.textContent='';

        if(phoneValue !== '') {
            if(phoneValue.length < 9) {
                PhoneError.textContent = 'Le numéro de téléphone doit avoir au moins 9 chiffres';
                return;
            }
        }
        PhoneError.textContent = '';

        if(cityValue === '') {
            CityError.textContent = 'La ville de l\'entreprise est obligatoire';
            return;
        }
        CityError.textContent = '';

        EmailError.textContent = '';
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(emailValue) && emailValue !== '') {
            EmailError.textContent = 'Le format de l\'email n\'est pas correct';
            return;
        }
        if(codePostalValue === '') {
            CodePostalError.textContent = 'Le code postal est obligatoire';
            return;
        }
        CodePostalError.textContent = '';


        CityError.textContent = '';
        const entreprise = {
            name: nameValue,
            designation: appelationValue,
            phone: phoneValue,
            email: emailValue,
            address: addressValue,
            city: `${codePostalValue} ${cityValue}`,
            motivation: '',

        }

        const entrepriseList = await getEntreprises();


        entrepriseList.forEach(entreprisess => {
            if(entreprisess.designation === null) {
                if(entreprisess.name.toLowerCase() === nameValue.toLowerCase()) {
                    SubmitError.textContent = 'Cette entreprise existe déjà';

                }
            }
        });
        const entrepriseExist = entrepriseList.find(entreprises => entreprises.name.toLowerCase() === nameValue.toLowerCase() && entreprises.designation.toLowerCase() === appelationValue.toLowerCase());
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