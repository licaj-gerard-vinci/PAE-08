  /* eslint-disable no-console */
import {
  getContacts,
  insertContact,
  updateContact
} from "../../model/contacts";
import {getEntreprises} from "../../model/entreprises";
import logo from '../../img/companyLogo.png';
import Navigate from '../Router/Navigate';
import { refreshUser } from "../../model/users";

let entreprises;
let searchResult = [];


const HomePage = async () => {
  const user = await refreshUser();
  if (user.role === 'A') {
    Navigate('/users');
    return;
  }
  await renderEntreprises();
  await renderSearchBar();
  await renderHomePage();
};

async function renderEntreprises(){
  entreprises = await getEntreprises();
  entreprises = entreprises.filter(entreprise => entreprise.blackListed === false);
  searchResult = entreprises;
}

async function renderSearchBar() {
  const main = document.querySelector('main');
  const searchBar = `
    <div class="container-fluid mt-4">
      <!-- Bouton "Ajouter l'entreprise" aligné à gauche avec du padding -->
      <div class="d-flex justify-content-end">
        <button type="button" class="btn btn-primary px-4 py-2" id="button-addon3"> + Ajouter l'entreprise</button>
      </div>
    </div>
    
    <div class="container-fluid">
      <div class="row justify-content-center">
        <div class="col-10 col-md-8 col-lg-6">
          <div class="input-group mb-3">
            <input type="text" class="form-control" id="searchInput" placeholder="Rechercher une entreprise 🔍" aria-label="Rechercher une entreprise" aria-describedby="button-addon2">
          </div>
        </div>
      </div>
    </div>
    <div id="searchResults"></div>`;
  main.innerHTML = searchBar;

  const searchInput = document.getElementById('searchInput');

  searchInput.addEventListener('input', async () => {
    const searchValue = searchInput.value.trim().toLowerCase();
    if (searchValue !== '') {
      searchResult = entreprises.filter(entreprise =>
          entreprise.nom.toLowerCase().includes(searchValue) || entreprise.appellation.toLowerCase().includes(searchValue)
      );
    } else {
      await renderEntreprises();
    }
    await renderHomePage();
  });
}


async function renderHomePage(){
  const resultsContainer = document.getElementById('searchResults');
  const user = await refreshUser();

  if(user.hasInternship === true) {
    Navigate('/profile')
    return;
  }

  if(user.role === "P"){
    Navigate('/dashboard');
  } else if (user.role === "E") {
    const contacts = await getContacts();

    if(!searchResult || searchResult.length === 0) {
      resultsContainer.innerHTML = `
      <p>Aucune entreprise n'a été trouvé.</p>
      `;
    } else {
      resultsContainer.innerHTML = `
        <div class="container-fluid">
        <div class="row justify-content-center">
          <div class="col-10 col-md-8 col-lg-6">
          ${searchResult.map(entreprise => {
        let button;
        if(contacts){
          const contactFound = contacts.find(contact => contact.idCompany === entreprise.id && contact.year.id === user.idSchoolYear);
          if(!contactFound){
            button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <button type='button' class='btn btn-primary' id='startedButton${entreprise.id}'>Contacter l'entreprise</button>
                  </div>
                  <div class="col"></div>
                </div>`;
          } else if (contactFound.contactStatus === 'initié') {
            button = `
            <div class="row">
              <div class="col"></div>
              <div class="col d-flex justify-content-center">
                <button type='button' class='btn btn-orange' id='unsupervisedButton${entreprise.id}'>Ne plus suivre</button>
              </div>
              <div class="col d-flex justify-content-end">
                <button type='button' class='btn btn-success' id='admittedButton${entreprise.id}'>Contact pris</button>
              </div>
            </div>
        
            <div id='form${entreprise.id}' style='display: none;'>
              <select class="form-control my-2" id='textInput${entreprise.id}'>
                <option value='distance'>À distance</option>
                <option value='sur place'>Sur place</option>
              </select>
              <div class="d-flex justify-content-center col-12">
                <button type='button' class='btn btn-primary btn-block shadow-sm my-2' id='saveMeetingButton${entreprise.id}'>Sauvegarder</button>
              </div>
            </div>
            `;
        
        
        
          } else if (contactFound.contactStatus === 'pris'){
            button = `
                <div class="row">
                  <div class="col d-flex justify-content-start">
                    <button type='button' class='btn btn-danger' id='turnedDownButton${entreprise.id}'>Contact refusé</button>
                  </div>
                  <div class="col d-flex justify-content-center">
                    <button type='button' class='btn btn-orange' id='unsupervisedButton${entreprise.id}'>Ne plus suivre</button>
                  </div>
                  <div class="col d-flex justify-content-end">
                    <button type='button' class='btn btn-success' id='acceptedButton${entreprise.id}'>Stage accepté</button>
                  </div>
                </div>
                <div id='form${entreprise.id}' style='display: none;'>
                   <input type='text' class="form-control my-2" id='textInput${entreprise.id}' placeholder='Entrez la raison du refus'>
                   <div class="d-flex justify-content-center col-12">
                     <button type='button' class='btn btn-primary btn-block shadow-sm my-2' id='saveRefusalReasonButton${entreprise.id}'>Sauvegarder</button>
                   </div> 
                </div>`;
          } else if(contactFound.contactStatus === 'refusé'){
            button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <p>Contact refusé</p>
                  </div>
                  <div class="col"></div>
                </div>`;
          } else if (contactFound.contactStatus === 'non suivi'){
            button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <p>Contact plus suivi</p>
                  </div>
                  <div class="col"></div>
                </div>`;
          } else if (contactFound.contactStatus === 'suspendu'){
            button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <p>Contact suspendu</p>
                  </div>
                  <div class="col"></div>
                </div>`;
          }
        } else {
          button = `
              <div class="row">
                <div class="col"></div>
                <div class="col d-flex justify-content-center">
                  <button type='button' class='btn btn-primary' id='StartedButton${entreprise.id}'>Contacter l'entreprise</button>
                </div>
                <div class="col"></div>
              </div>`;
        }

        return `
              <div class="border rounded p-3 d-flex flex-column justify-content-between my-4" style="border-radius: 50px;">
                  <div>
                      <div class="d-flex justify-content-between">
                          <div class="mr-auto text-left">
                              <h1 class="mb-auto">${entreprise.name}</h1>
                              <ul class="list-unstyled">
                                  <li>Appellation: ${entreprise.designation ? entreprise.designation : '/'}</li>                                  <li>Adresse: ${entreprise.adresse}</li>
                                  <li>Téléphone: ${entreprise.phone}</li>
                              </ul>
                          </div>
                          <img src="${logo}" alt="Logo" class="ml-3" style="width: 100px; height: 100px;">
                      </div>
                  </div>
                  ${button}
              </div>
            `;
      }).join('')}
          </div> 
        </div> 
      </div>
      `;

      entreprises.forEach(entreprise => {
        const startedButton = document.querySelector(`#startedButton${entreprise.id}`);
        const admittedButton = document.querySelector(`#admittedButton${entreprise.id}`);
        const acceptedButton = document.querySelector(`#acceptedButton${entreprise.id}`);
        const turnedDownButton = document.querySelector(`#turnedDownButton${entreprise.id}`);
        const unsupervisedButton = document.querySelector(`#unsupervisedButton${entreprise.id}`);
        const contactFound = contacts.find(contact => contact.idCompany === entreprise.id && contact.year.id === user.idSchoolYear);

        if (startedButton) {
          startedButton.addEventListener('click', async () => {
            // to make sure the insertion isn't done twice
            startedButton.disabled = true;
            await insertContact(entreprise, user, "initié");
            await renderHomePage();
            startedButton.disabled = false;
          });
        }

        if (admittedButton) {
          admittedButton.addEventListener('click', async () => {
            document.querySelector(`#form${entreprise.id}`).style.display = 'block';
          });
        }

        if (document.querySelector(`#saveMeetingButton${entreprise.id}`)) {
          document.querySelector(`#saveMeetingButton${entreprise.id}`).addEventListener('click', async () => {
            const contactVersion = contactFound.version;
            const textInputValue = document.querySelector(`#textInput${entreprise.id}`).value;
            if(textInputValue){
              await updateContact(contactFound.id, entreprise, user, "pris", null, textInputValue, contactVersion);
              await renderHomePage();
            }

          });
        }

        if (acceptedButton) {
          acceptedButton.addEventListener('click', async () => {
            acceptedButton.disabled = true;
            // Stock the contactId inside a sessionStorage
            sessionStorage.setItem('contactId', contactFound.id); 
            Navigate('/internship', contactFound.id);
            acceptedButton.disabled = false;
          });
        }

        if (unsupervisedButton) {
          unsupervisedButton.addEventListener('click', async () => {
            const contactVersion = contactFound.version;
            // to make sure the insertion isn't done twice
            unsupervisedButton.disabled = true;
            await updateContact(contactFound.id, entreprise, user, "non suivi", null, null, contactVersion);
            await renderHomePage();
            unsupervisedButton.disabled = false;
          });
        }

        if (turnedDownButton) {
          turnedDownButton.addEventListener('click', () => {
            document.querySelector(`#form${entreprise.id}`).style.display = 'block';
          });
        }

        if (document.querySelector(`#saveRefusalReasonButton${entreprise.id}`)) {
          document.querySelector(`#saveRefusalReasonButton${entreprise.id}`).addEventListener('click', async () => {
            const textInputValue = document.querySelector(`#textInput${entreprise.id}`).value;
            const contactVersion = contactFound.version;
            if(textInputValue){
              await updateContact(contactFound.id, entreprise, user, "refusé", textInputValue, null, contactVersion);
              await renderHomePage();
            }
          });
        }
      });

    }
  } else {
    console.log("Unknown user role");
  }
if(user.role === "E"){
const addCompanie = document.getElementById('button-addon3');
  addCompanie.addEventListener('click', () => {
    window.location.href = '/addCompany';
  });

}
}

export default HomePage;