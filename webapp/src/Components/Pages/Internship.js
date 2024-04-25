/* eslint-disable no-console */
import {
    getContact
} from "../../model/contacts";
import { getManagers, addManager} from "../../model/managers";
import { insertInternship } from "../../model/internships";
import Navigate from '../Router/Navigate';
import { clearPage } from "../../utils/render";

function createSuccesModal() {
  const modal = document.createElement('div');
  modal.id = 'succesManagerModal';
  modal.style = 'position: fixed; bottom: 20px; right: 20px; background-color: green; color: white; padding: 20px; border-radius: 10px; display: none; z-index: 1000;';
  modal.textContent = 'Le responsable a été ajouté avec succès !';  
  document.body.appendChild(modal);
}
function createErrorModal() {
  const modal = document.createElement('div');
  modal.id = 'ErrorManagerModal';
  modal.style = 'position: fixed; bottom: 20px; right: 20px; background-color: red; color: white; padding: 20px; border-radius: 10px; display: none; z-index: 1000;';
  modal.textContent = 'Le responsable existe déjà !';
  document.body.appendChild(modal);
}
// Toggle the visibility of the modals for the password modification
function toggleModalError() {
  const modal = document.getElementById('ErrorManagerModal');
  modal.style.display = 'block';
  setTimeout(() => {
    modal.style.display = 'none';
  }, 5000);
}
function toggleModalSucces() {
  const modal = document.getElementById('succesManagerModal');
  modal.style.display = 'block';
  setTimeout(() => {
    modal.style.display = 'none';
  }, 5000);
}

let managers = [];

const Internship = async (contactFound) => {
  if (!contactFound) {
    Navigate('/');
    return;
  }
  clearPage();
  createSuccesModal();
  createErrorModal();

  const main = document.querySelector('main');
  const contactId = sessionStorage.getItem('contactId');
  const contact = await getContact(contactId);
  managers = await getManagers(contact.company.id);
  let managerOptions = [];
  let managerNotFound = ``;

  managerOptions = managers.map(manager => `<option value="${manager.id}">${manager.firstName} ${manager.name} (${manager.phone})</option>`).join('');
  if (managerOptions.length === 0) {
    managerNotFound = `<p id="notfound" class="text-danger">Pas de manager trouvé</p>`
  }

  main.innerHTML =  `
  <div class="container">
    <div class="row">
      <div class="col-md-6">
        <form id="internshipForm" class="form-horizontal">
        <div class="form-group">
          <label for="managerId" class="col control-label">Identifiant du responsable<span class="text-danger">*</span></label>
          <div class="col-sm-10">
            <select id="managerId" name="managerId" class="form-control">
              ${managerOptions}
            </select>
            <div>
              <button type='button' class='btn btn-primary mt-3' id='insertNewManager'>Ajouter un responsable</button>
            </div>
            ${managerNotFound}
          </div>
        </div>
        <div class="form-group">
          <label for="topic" class="col control-label">Sujet du stage</label>
          <div class="col-sm-10">
            <input type="text" id="topic" name="topic" class="form-control" placeholder="Sujet du stage">
          </div>
        </div>
          <div class="form-group">
            <label for="signatureDate" class="col control-label">Date de signature<span class="text-danger">*</span></label>
              <div class="col-sm-10">
                <input type="date" id="signatureDate" name="signatureDate" class="form-control" required>
              <p id="dateError" style="color: red;"></p> <!-- Placeholder for the error message -->
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
            <input type="submit" value="Submit" class="btn btn-primary">
          </div>
        </div>
      </form>
    </div>
    <div class="col-md-6" id="managerForm" style="display: none;">
      <form id="addManagerForm" class="form-horizontal">
        <div class="form-group">
          <label for="lastname" class="col control-label">Nom<span class="text-danger">*</span></label>
          <div class="col-sm-10">
            <input type="text" id="lastname" name="lastname" class="form-control" placeholder="Nom de famille" required>
          </div>
        </div>
        <div class="form-group">
          <label for="firstname" class="col control-label">Prénom<span class="text-danger">*</span></label>
            <div class="col-sm-10">
              <input type="text" id="firstname" name="firstname" class="form-control" placeholder="Prénom" required>
            </div>
          </div>
          <div class="form-group">
            <label for="phoneNumber" class="col control-label">Numéro de téléphone<span class="text-danger">*</span></label>
            <div class="col-sm-10">
              <input type="number" id="phoneNumber" name="phoneNumber" class="form-control" placeholder="0400 00 00 00" required>
            </div>
          </div>
          <div class="form-group">
            <label for="email" class="col control-label">Adresse mail</label>
            <div class="col-sm-10">
              <input type="email" id="email" name="email" class="form-control" placeholder="mail@mail.com">
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <input type="submit" value="Ajouter responsable" class="btn btn-primary">
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <div id="successModal" class="modal">
    <div class="modal-content">
      <span class="close">&times;</span>
      <p>Manager has been added successfully!</p>
    </div>
But I have to verify based on the school year so 15 september 2023 till 1 june 2024 dinamically with years  </div>
  <div id="errorModal" class="modal">
    <div class="modal-content">
      <span class="close">&times;</span>
      <p id="errorMessage">An error occurred while adding a new manager.</p>
    </div>
  </div>
    `;

  const insertNewManager = document.querySelector(`#insertNewManager`);
  if (insertNewManager) {
    insertNewManager.addEventListener('click', async () => {
      document.getElementById('managerForm').style.display = 'block';
    });
  }

  // Add a new manager
  document.getElementById('managerForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const lastname = document.getElementById("lastname").value;
    const firstname = document.getElementById("firstname").value;
    const phoneNumber = document.getElementById("phoneNumber").value;
    const emailManager = document.getElementById("email").value;

    // Check if contact and contact.entreprise are not undefined
    if (contact && contact.company) {
      // Create a manager object
      const manager = {
        name: lastname,
        firstName: firstname,
        phone: phoneNumber,
        email: emailManager,
        idCompany: contact.company.id,
      };
      const newManager = await addManager(manager);
      if (newManager === null) {
        // Display an error message
        toggleModalError();
        return;
      }
      managers.push(newManager);
      managers = await getManagers(contact.company.id);
      managerOptions = managers.map(managerItem => `<option value="${managerItem.id}">${managerItem.firstName} ${managerItem.name}</option>`).join('');

      document.getElementById('managerId').innerHTML = managerOptions;

      if(document.getElementById('notfound')) {
        document.getElementById('notfound').textContent = '';
      }

      // Show the success modal
      toggleModalSucces();

      // Show success message
      document.getElementById('managerForm').style.display = 'none';
      Navigate('/internship', contactFound);
    } else {
      console.error('contact or contact.company is undefined');
    }
  });

  document.getElementById('internshipForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const managerId = document.getElementById("managerId").value;
    const topic = document.getElementById("topic").value;
    const signatureDate = new Date(document.getElementById("signatureDate").value);

    // Get the current date
    const currentDate = new Date();

    // Get the current year and month
    const currentYear = currentDate.getFullYear();
    const currentMonth = currentDate.getMonth();

    // If the current month is before or equal to June, set the start date to September 15th of the previous year and the end date to June 1st of the current year
    // If the current month is after June, set the start date to September 15th of the current year and the end date to June 1st of the next year
    const startDate = currentMonth <= 5 ? new Date(currentYear - 1, 8, 15) : new Date(currentYear, 8, 15); // September 15th
    const endDate = currentMonth <= 5 ? new Date(currentYear, 5, 1) : new Date(currentYear + 1, 5, 1); // June 1st

    // Check if the selected date is within the range
    if (signatureDate < startDate || signatureDate > endDate) {
      // Display an error message and stop the form submission
      document.getElementById('dateError').textContent = `Entrez une date valide (min: 15/09/${startDate.getFullYear()} - max: 01/06/${endDate.getFullYear()}).`;
      return;
    }

    if(!managerId) {
      document.getElementById('managerForm').style.display = 'block';
    } else {
      await insertInternship(managerId, contact.student, contact, contact.company, topic, signatureDate);
      Navigate('/')
    }
  });

  // When the page is about to be unloaded
  window.addEventListener('beforeunload', () => {
    // Clear the contact ID from sessionStorage
    sessionStorage.removeItem('contactId');
  });
};

export default Internship;