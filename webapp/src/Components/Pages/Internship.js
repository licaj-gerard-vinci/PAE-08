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
  managers = await getManagers(contact.entreprise.id);
  let managerOptions = [];
  let managerNotFound = ``;

  managerOptions = managers.map(manager => `<option value="${manager.id}">${manager.prenom} ${manager.nom}</option>`).join('');

  if (managerOptions.length === 0) {
    managerNotFound = `<p class="text-danger">Pas de manager trouvé</p>`
  }

  main.innerHTML =  `
  <div class="container">
    <div class="row">
      <div class="col-md-6">
        <form id="internshipForm" class="form-horizontal">
        <div class="form-group">
          <label for="managerId" class="col-sm-2 control-label">Manager ID:</label>
          <div class="col-sm-10">
            <select id="managerId" name="managerId" class="form-control">
              ${managerOptions}
            </select>
            <div>
              <button type='button' class='btn btn-primary' id='insertNewManager'>Ajouter un responsable</button>
            </div>
            ${managerNotFound}
          </div>
        </div>
        <div class="form-group">
          <label for="topic" class="col-sm-2 control-label">Topic:</label>
          <div class="col-sm-10">
            <input type="text" id="topic" name="topic" class="form-control" placeholder="topic">
          </div>
        </div>
        <div class="form-group">
          <label for="signatureDate" class="col-sm-2 control-label">Signature Date:</label>
          <div class="col-sm-10">
            <input type="date" id="signatureDate" name="signatureDate" class="form-control" required>
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
          <label for="lastname" class="col-sm-2 control-label">Last Name:</label>
          <div class="col-sm-10">
            <input type="text" id="lastname" name="lastname" class="form-control" placeholder="lastname" required>
          </div>
        </div>
        <div class="form-group">
          <label for="firstname" class="col-sm-2 control-label">First Name:</label>
            <div class="col-sm-10">
              <input type="text" id="firstname" name="firstname" class="form-control" placeholder="firstname" required>
            </div>
          </div>
          <div class="form-group">
            <label for="phoneNumber" class="col-sm-2 control-label">Phone Number:</label>
            <div class="col-sm-10">
              <input type="number" id="phoneNumber" name="phoneNumber" class="form-control" placeholder="0400 00 00 00" required>
            </div>
          </div>
          <div class="form-group">
            <label for="email" class="col-sm-2 control-label">Email:</label>
            <div class="col-sm-10">
              <input type="email" id="email" name="email" class="form-control">
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <input type="submit" value="Add Manager" class="btn btn-primary">
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
  </div>
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
    if (contact && contact.entreprise) {
      // Create a manager object
      const manager = {
        nom: lastname,
        prenom: firstname,
        numTel: phoneNumber,
        email: emailManager,
        idEntreprise: contact.entreprise.id,
      };
      const newManager = await addManager(manager);
      if (newManager === null) {
        // Display an error message
        toggleModalError();
        return;
      }

      console.log('manager options before push: ',managerOptions, ', managers: ', managers);
      managers.push(newManager);
      managers = await getManagers(contact.entreprise.id);
      managerOptions = managers.map(managerItem => `<option value="${managerItem.id}">${managerItem.prenom} ${managerItem.nom}</option>`).join('');
      console.log('manager options after push: ',managerOptions, ', managers: ', managers);

      // Update the select element with the new options
      document.getElementById('managerId').innerHTML = managerOptions;

      // Show the success modal
      toggleModalSucces();

      // Show success message
      document.getElementById('managerForm').style.display = 'none';
      Navigate('/internship', contactFound);
    } else {
      console.error('contact or contact.entreprise is undefined');
    }
  });

  document.getElementById('internshipForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const managerId = document.getElementById("managerId").value;
    const topic = document.getElementById("topic").value;
    const signatureDate = document.getElementById("signatureDate").value;

    if(!managerId) {
        document.getElementById('managerForm').style.display = 'block';
    } else {
        await insertInternship(managerId, contact.utilisateur, contact, contact.entreprise, topic, signatureDate);
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