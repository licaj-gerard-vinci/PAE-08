/* eslint-disable no-console */
import Chart from 'chart.js/auto';
import {
  getContacts,
  insertContact,
  updateContact
} from "../../model/contacts";
import getEntreprises from "../../model/entreprises";
import logo from '../../img/HELOGO.png';
import { getAuthenticatedUser } from "../../utils/auths";
import { getAllUsers } from "../../model/users";

let entreprises;
let searchResult = [];
const totalStudents = await getAllUsers();
console.log('totalStudents: ', totalStudents);

async function renderEntreprises() {
  entreprises = await getEntreprises();
  searchResult = entreprises;
}

const HomePage = async () => {
  await renderEntreprises();
  await renderHomePage();
};

async function renderHomePage() {
  const main = document.querySelector('main');
  const user = getAuthenticatedUser();
  console.log(user.user);

  if (user.user.role === "A" || user.user.role === "P") {
    main.innerHTML = `
     <canvas id="myChart"></canvas>
  `;

    // Render the chart
    const ctx = document.getElementById('myChart').getContext('2d');
    new Chart(ctx, {
      type: 'pie',
      data: {
        labels: ['With Internships', 'Without Internships'],
        datasets: [{
          data: [totalStudents.filter(student => student.role === 'E' && student.hasInternship === true).length, totalStudents.filter(student => student.role === 'E' && student.hasInternship === false).length], backgroundColor: ['rgba(75, 192, 192, 0.2)', 'rgba(255, 99, 132, 0.2)'],
          borderColor: ['rgba(75, 192, 192, 1)', 'rgba(255, 99, 132, 1)'],
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        width: 400,
        height: 400,
        title: {
          display: true,
          text: 'Chart Title',
          position: 'top' // the position of the title
        }
      }
    });
  } else if (user.user.role === "E") {
    const contacts = await getContacts();
    console.log('contactssasas: ', contacts);
    const searchBar = `<div class="container-fluid">
    <div class="row justify-content-center">
      <div class="col-10 col-md-8 col-lg-6">
        <div class="input-group mb-3">
          <input type="text" class="form-control" id="searchInput" placeholder="Rechercher une entreprise" aria-label="Rechercher une entreprise" aria-describedby="button-addon2">
          <button class="btn btn-primary" type="button" id="button-addon2">Rechercher</button>
        </div>
      </div>
    </div>
    </div>`

    if(!searchResult || searchResult.length === 0) {
      main.innerHTML = `
      ${searchBar}
      <p>Aucune entreprise n'a été trouvé.</p>
      `;
    } else {
      main.innerHTML = `
        ${searchBar}
        <div class="container-fluid">
        <div class="row justify-content-center">
          <div class="col-10 col-md-8 col-lg-6">
          ${searchResult.map(entreprise => {
            let button;
            if(contacts){
              
              const contactFound = contacts.find(contact => contact.idEntreprise === entreprise.id);
                console.log('contactFound: ', contactFound);
              if(!contactFound){
                button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <button type='button' class='btn btn-primary' id='startedButton${entreprise.id}'>Contacter l'entreprise</button>
                  </div>
                  <div class="col"></div>
                </div>`;
              } else if (contactFound.etatContact === 'initié') {
                button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <button type='button' class='btn btn-orange' id='unsupervisedButton${entreprise.id}'>ne plus suivre</button>
                  </div>
                  <div class="col d-flex justify-content-end">
                    <button type='button' class='btn btn-success' id='admittedButton${entreprise.id}'>contact pris</button>
                  </div>
                </div>
                <div id='form${entreprise.id}' style='display: none;'>
                <select class="w-80" id='textInput${entreprise.id}'>
                  <option value='distance'>à distance</option>
                  <option value='sur place'>sur place</option>
                </select>
                <button type='button' id='saveMeetingButton${entreprise.id}'>Save</button>
              </div>`;
              } else if (contactFound.etatContact === 'pris'){
                button = `
                <div class="row">
                  <div class="col d-flex justify-content-start">
                    <button type='button' class='btn btn-danger' id='turnedDownButton${entreprise.id}'>contact refusé</button>
                  </div>
                  <div class="col d-flex justify-content-center">
                    <button type='button' class='btn btn-orange' id='unsupervisedButton${entreprise.id}'>ne plus suivre</button>
                  </div>
                  <div class="col d-flex justify-content-end">
                    <button type='button' class='btn btn-success' id='acceptedButton${entreprise.id}'>stage accepté</button>
                  </div>
                </div>
                <div id='form${entreprise.id}' style='display: none;'>
                  <input class="w-80" type='text' id='textInput${entreprise.id}' placeholder='Entrez la raison du refus: '>
                  <button type='button' id='saveRefusalReasonButton${entreprise.id}'>Save</button>
                </div>`;
              } else if(contactFound.etatContact === 'accepté') {
                button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <p>Contact accepté</p>
                  </div>
                  <div class="col"></div>
                </div>`;
              } else if(contactFound.etatContact === 'refusé'){
                button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <p>Contact refusé</p>
                  </div>
                  <div class="col"></div>
                </div>`;
              }
              else if (contactFound.etatContact === 'non suivi'){
                button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <p>Contact plus suivi</p>
                  </div>
                  <div class="col"></div>
                </div>`;
              }
              else if (contactFound.etatContact === 'suspendu'){
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
              <div class="border rounded p-3 d-flex flex-column justify-content-between" style="border-radius: 50px;">
                  <div>
                      <div class="d-flex justify-content-between">
                          <div class="mr-auto text-left">
                              <h1 class="mb-auto">${entreprise.nom}</h1>
                              <ul class="list-unstyled">
                                  <li>Appellation: ${entreprise.appellation}</li>
                                  <li>Adresse: ${entreprise.adresse}</li>
                                  <li>Téléphone: ${entreprise.numTel}</li>
                              </ul>
                          </div>
                          <img src="${logo}" alt="Logo" class="ml-3" style="width: 100px; height: auto;">
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
        console.log('entreprise: ', entreprise)
        const startedButton = document.querySelector(`#startedButton${entreprise.id}`);
        const admittedButton = document.querySelector(`#admittedButton${entreprise.id}`);
        const acceptedButton = document.querySelector(`#acceptedButton${entreprise.id}`);
        const turnedDownButton = document.querySelector(`#turnedDownButton${entreprise.id}`);
        const unsupervisedButton = document.querySelector(`#unsupervisedButton${entreprise.id}`);
        const contactFound = contacts.find(contact => contact.idEntreprise === entreprise.id);


        if (startedButton) {
          console.log('startedButton: ', startedButton)
          startedButton.addEventListener('click', async () => {
            // to make sure the insertion isn't done twice
            startedButton.disabled = true;
            console.log('before insert informations: entreprise: ', entreprise, ', user: ', user.user)
            await insertContact(entreprise, user.user, "initié");
            await renderHomePage();
            startedButton.disabled = false;
          });
        }

        if (admittedButton) {
          console.log('admittedButton: ', admittedButton)
          admittedButton.addEventListener('click', async () => {
            document.querySelector(`#form${entreprise.id}`).style.display = 'block';
          });
        }
        
        if (document.querySelector(`#saveMeetingButton${entreprise.id}`)) {
          document.querySelector(`#saveMeetingButton${entreprise.id}`).addEventListener('click', async () => {
            const contactVersion = contactFound.version;
            const textInputValue = document.querySelector(`#textInput${entreprise.id}`).value;
            if(textInputValue){
              await updateContact(contactFound.id, entreprise, user.user, "pris", null, textInputValue, contactVersion);
              await renderHomePage();
            }

          });
        }

        if (acceptedButton) {
          console.log('acceptedButton: ', acceptedButton)
          acceptedButton.addEventListener('click', async () => {
            const contactVersion = contactFound.version;
            acceptedButton.disabled = true;
            console.log('before update informations: entreprise: ', entreprise, ', user: ', user.user)
            await updateContact(contactFound.id, entreprise, user.user, "accepté", null, null, contactVersion);
            console.log('after update')
            await renderHomePage();
            acceptedButton.disabled = false;
          });
        }

        if (unsupervisedButton) {
          console.log('unsupervisedButton: ', unsupervisedButton)
          unsupervisedButton.addEventListener('click', async () => {
            const contactVersion = contactFound.version;
            // to make sure the insertion isn't done twice
            unsupervisedButton.disabled = true;
            console.log('before update informations: entrepriseId: ', entreprise, ', userId: ', user.user)
            await updateContact(contactFound.id, entreprise, user.user, "non suivi", null, null, contactVersion);
            console.log('after update')
            await renderHomePage();
            unsupervisedButton.disabled = false;
          });
        }

        if (turnedDownButton) {
          console.log('turnedDownButton: ', turnedDownButton)
          turnedDownButton.addEventListener('click', () => {
            document.querySelector(`#form${entreprise.id}`).style.display = 'block';
          });
        }
        
        if (document.querySelector(`#saveRefusalReasonButton${entreprise.id}`)) {
          document.querySelector(`#saveRefusalReasonButton${entreprise.id}`).addEventListener('click', async () => {
            const textInputValue = document.querySelector(`#textInput${entreprise.id}`).value;
            const contactVersion = contactFound.version;
            if(textInputValue){
              await updateContact(contactFound.id, entreprise, user.user, "refusé", textInputValue, null, contactVersion);
              await renderHomePage();
            }
          });
        }
      });
      
    }
    const searchButton = document.getElementById('button-addon2');

    searchButton.addEventListener('click', async () => {
      const searchInput = document.getElementById('searchInput').value.trim().toLowerCase();
      console.log('searchInput: ', searchInput);
      if (searchInput !== '') {
        searchResult = entreprises.filter(entreprise =>
            entreprise.nom.toLowerCase().includes(searchInput)
        );
        console.log('searchResult: ', searchResult);
      } else {
        await renderEntreprises();
      }
      await renderHomePage();
    });
  } else {
    console.log("Unknown user role");
  }
}

export default HomePage;