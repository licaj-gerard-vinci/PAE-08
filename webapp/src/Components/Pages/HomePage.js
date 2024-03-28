/* eslint-disable no-console */
import {
  getContacts,
  insertContact,
  updateContact
} from "../../model/contacts";
import getEntreprises from "../../model/entreprises";
import logo from '../../img/HELOGO.png';
import {getAuthenticatedUser} from "../../utils/auths";

let entreprises; // Declare a variable to hold the list of companies
let searchResult = [] // Declare a variable to hold the search results

// Function to fetch the list of companies from the database
async function renderEntreprises(){
  // Fetch the list of companies from the database
  entreprises = await getEntreprises();
  // By default, set the search results to include all companies that are not blacklisted
  searchResult = entreprises;
}

// Function to render the home page
const HomePage = async () => {
  // Fetch and render the list of companies
  await renderEntreprises();
  // Render the home page
  await renderHomePage();
};

// Function to render the home page based on the user's role
async function renderHomePage(){
  const main = document.querySelector('main'); // Select the main element on the page
  const user = getAuthenticatedUser(); // Get the authenticated user
  console.log(user.user); // Log the user object to the console

  // Check if the user's role is either "A" (Administrative) or "P" (Professor)
  if(user.user.role === "A" || user.user.role === "P"){
    // If the user is an Administrative staff or a Professor, render a special dashboard for them
    main.innerHTML = `
    <div style="display: flex; justify-content: center; align-items: center; height: 100vh;">
      <h1 style="font-size: 3em;">Welcome to the Home Page for professors and administrative staff only!</h1>
    </div>
  `;
  }
  // Check if the user's role is "E" (Student)
  else if (user.user.role === "E") {
    // Fetch the list of contacts
    const contacts = await getContacts();
    console.log('contacts: ', contacts); // Log the contacts to the console

    // Define the search bar HTML
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

        // Check if there are no search results or if the search results array is empty
    if(!searchResult || searchResult.length === 0) {
      // If there are no search results, display a message indicating that no companies were found
      main.innerHTML = `
      ${searchBar}
      <p>Aucun entreprise n'a été trouvé.</p>
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
              console.log('contactFound: ', contactFound); // Log the found contact to the console
                
              // Check the state of the contact and display the appropriate button
                if(!contactFound){
                // If there is no contact with the specific company, display a button to contact the company
                button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <button type='button' class='btn btn-primary' id='startedButton${entreprise.id}'>Contacter l'entreprise</button>
                  </div>
                  <div class="col"></div>
                </div>`;
              } else if (contactFound.etatContact === 'initié') {
                // If the contact has been initiated, display buttons to stop following or to indicate that contact has been made
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
              </div>`; // Dropdown menu for meeting type (remote or on-site) appears with a “Save” button after clicking the "contact made" button.
              
              } else if (contactFound.etatContact === 'pris'){
                // If the contact has been made, display buttons to indicate that the contact was refused, to stop following, or to indicate that the internship was accepted
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
                // If the contact has been accepted, display a message indicating that the contact was accepted
                button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <p>Contact accepté</p>
                  </div>
                  <div class="col"></div>
                </div>`;
              } else if(contactFound.etatContact === 'refusé'){
                // If the contact has been refused, display a message indicating that the contact was refused
                button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <p>Contact refusé</p>
                  </div>
                  <div class="col"></div>
                </div>`;
              } else if (contactFound.etatContact === 'non suivi'){
                // If the contact is no longer being followed, display a message indicating that the contact is no longer being followed
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
                // If the contact has been suspended, display a message indicating that the contact has been suspended
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
              // If there is no contact, display a button to contact the company for each company. All companies will have this button in this else clause.
              button = `
              <div class="row">
                <div class="col"></div>
                <div class="col d-flex justify-content-center">
                  <button type='button' class='btn btn-primary' id='StartedButton${entreprise.id}'>Contacter l'entreprise</button>
                </div>
                <div class="col"></div>
              </div>`;
            }
            
            // Return the HTML for the company card, which includes the company details and the appropriate button or message based on the state of the contact
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

      // Loop through each company
      entreprises.forEach(entreprise => {
        console.log('entreprise: ', entreprise) // Log the current company to the console

        // Select the buttons for each possible state of contact
        const startedButton = document.querySelector(`#startedButton${entreprise.id}`);
        const admittedButton = document.querySelector(`#admittedButton${entreprise.id}`);
        const acceptedButton = document.querySelector(`#acceptedButton${entreprise.id}`);
        const turnedDownButton = document.querySelector(`#turnedDownButton${entreprise.id}`);
        const unsupervisedButton = document.querySelector(`#unsupervisedButton${entreprise.id}`);

        // Find the contact for the current company
        const contactFound = contacts.find(contact => contact.idEntreprise === entreprise.id);

        // If the "Contact Company" button exists
        if (startedButton) {
          console.log('startedButton: ', startedButton) // Log the button to the console
          // Add a click event listener to the button
          startedButton.addEventListener('click', async () => {
            // Disable the button to prevent double-clicks
            startedButton.disabled = true;
            console.log('before insert informations: entreprise: ', entreprise, ', user: ', user.user) // Log the current company and user to the console
            // Insert a new contact with the state "initiated"
            await insertContact(entreprise, user.user, "initié");
            // Render the home page
            await renderHomePage();
            // Re-enable the button
            startedButton.disabled = false;
          });
        }

        // If the "Contact Made" button exists
        if (admittedButton) {
          console.log('admittedButton: ', admittedButton) // Log the button to the console
          // Add a click event listener to the button
          admittedButton.addEventListener('click', async () => {
            // Show the form for selecting the type of meeting
            document.querySelector(`#form${entreprise.id}`).style.display = 'block';
          });
        }

        // If the "Save Meeting Type" button exists
        if (document.querySelector(`#saveMeetingButton${entreprise.id}`)) {
          // Add a click event listener to the button
          document.querySelector(`#saveMeetingButton${entreprise.id}`).addEventListener('click', async () => {
            const contactVersion = contactFound.version; // Get the version of the contact
            const textInputValue = document.querySelector(`#textInput${entreprise.id}`).value; // Get the selected meeting type
            if(textInputValue){
              // Update the contact with the state "made" and the selected meeting type
              await updateContact(contactFound.id, entreprise, user.user, "pris", null, textInputValue, contactVersion);
              // Render the home page
              await renderHomePage();
            }
          });
        }

        // If the "Internship Accepted" button exists
        if (acceptedButton) {
          console.log('acceptedButton: ', acceptedButton) // Log the button to the console
          // Add a click event listener to the button
          acceptedButton.addEventListener('click', async () => {
            const contactVersion = contactFound.version; // Get the version of the contact
            acceptedButton.disabled = true; // Disable the button to prevent double-clicks
            console.log('before update informations: entreprise: ', entreprise, ', user: ', user.user) // Log the current company and user to the console
            // Update the contact with the state "accepted"
            await updateContact(contactFound.id, entreprise, user.user, "accepté", null, null, contactVersion);
            console.log('after update') // Log a message to the console after the update
            // Render the home page
            await renderHomePage();
            acceptedButton.disabled = false; // Re-enable the button
          });
        }

        // If the "Stop Following" button exists
        if (unsupervisedButton) {
          console.log('unsupervisedButton: ', unsupervisedButton) // Log the button to the console
          // Add a click event listener to the button
          unsupervisedButton.addEventListener('click', async () => {
            const contactVersion = contactFound.version; // Get the version of the contact
            unsupervisedButton.disabled = true; // Disable the button to prevent double-clicks
            console.log('before update informations: entrepriseId: ', entreprise, ', userId: ', user.user) // Log the current company and user to the console
            // Update the contact with the state "not followed"
            await updateContact(contactFound.id, entreprise, user.user, "non suivi", null, null, contactVersion);
            console.log('after update') // Log a message to the console after the update
            // Render the home page
            await renderHomePage();
            unsupervisedButton.disabled = false; // Re-enable the button
          });
        }

        // If the "Contact Refused" button exists
        if (turnedDownButton) {
          console.log('turnedDownButton: ', turnedDownButton) // Log the button to the console
          // Add a click event listener to the button
          turnedDownButton.addEventListener('click', () => {
            // Show the form for entering the refusal reason
            document.querySelector(`#form${entreprise.id}`).style.display = 'block';
          });
        }

        // If the "Save Refusal Reason" button exists
        if (document.querySelector(`#saveRefusalReasonButton${entreprise.id}`)) {
          // Add a click event listener to the button
          document.querySelector(`#saveRefusalReasonButton${entreprise.id}`).addEventListener('click', async () => {
            const textInputValue = document.querySelector(`#textInput${entreprise.id}`).value; // Get the entered refusal reason
            const contactVersion = contactFound.version; // Get the version of the contact
            if(textInputValue){
              // Update the contact with the state "refused" and the entered refusal reason
              await updateContact(contactFound.id, entreprise, user.user, "refusé", textInputValue, null, contactVersion);
              // Render the home page
              await renderHomePage();
            }
          });
        }
      }); // ends the company for each
    }
   // Select the search button
    const searchButton = document.getElementById('button-addon2');

    // Add a click event listener to the search button
    searchButton.addEventListener('click', async () => {
      const searchInput = document.getElementById('searchInput').value.trim().toLowerCase(); // Get the entered search term
      console.log('searchInput: ', searchInput); // Log the search term to the console
      if (searchInput !== '') {
        // If a search term was entered, filter the companies based on the search term
        searchResult = entreprises.filter(entreprise =>
            entreprise.nom.toLowerCase().includes(searchInput)
        );
        console.log('searchResult: ', searchResult); // Log the search results to the console
      } else {
        // If no search term was entered, fetch and render the list of companies
        await renderEntreprises();
      }
      // Render the home page
      await renderHomePage();
    });

    // If the user's role is unknown
  } else {
    console.log("Unknown user role"); // Log a message to the console indicating that the user's role is unknown
  }
}

export default HomePage;