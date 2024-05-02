import {blackListEntreprise, getEntrepriseById} from '../../model/entreprises';
import { clearPage } from '../../utils/render';
import {getContactByCompanyId} from "../../model/contacts";
import Navigate from "../Router/Navigate";
import {getAuthenticatedUser} from "../../utils/auths";
import blacklist from '../../img/blacklist.png';

let authenticatedUser = getAuthenticatedUser();

if (!authenticatedUser || authenticatedUser.user.role !== 'P') {
  Navigate('/');
}

function generateContactsTable(contacts) {
  let contactsTable = '<table class="table">';
  contactsTable += '<thead class="table-dark"><tr><th class="text-center">Etudiant</th><th class="text-center">Etat du contact</th><th class="text-center">Lieu de rencontre</th><th class="text-center">Raison du refus</th></tr></thead>';
  contactsTable += '<tbody>';
  contacts.forEach(contact => {
    contactsTable += `<tr class="text-center">
                          <td class="text-center">${contact.student.lastname}
                          ${contact.student.firstname} </br>
                          ${contact.student.email}</td>
                          <td class="text-center">${contact.contactStatus}</td>
                          <td class="text-center">${contact.meetingPlace || '/'}</td>
                          <td class="text-center">${contact.refusalReason || '/'}</td>
                      </tr>`;
      });
  contactsTable += '</tbody></table>';
  return contactsTable;
}

function generateBlacklistFormOrMessage(entreprise) {
  let formOrMessage = '';
  if (!entreprise.blackListed) {
    formOrMessage = `
                <form id="blacklistForm" class="d-flex flex-column">
                    <div class="form-group flex-grow-1">
                        <label for="blacklistReason">Raison de la blacklist</label>
                        <input type="text" class="form-control" id="blacklistReason" placeholder="Entrez la raison">
                    </div>
                    <button id="blacklistFormSubmit" type="submit" class="btn mt-2 align-self-end" style="background-color: blue; color: white;">Ajouter à la blacklist</button>
                </form>
    `;
  } else {
    formOrMessage = `
    <div class="row">
      <div class="col-md-8">
        <p>Cette entreprise est blacklistée</p> </br> <p> Raison: ${entreprise.motivation}</p>
      </div>
      <div class="col-md-4">
        <img src="${blacklist}" class="img-fluid d-block mx-auto" style="width: 100%; height: auto;"/>
      </div>
    </div>`;
  }
  return formOrMessage;
}

function renderInfoCompany(entreprise) {
  const formOrMessage = generateBlacklistFormOrMessage(entreprise);

  return `
          <div class="row">
              <div class="col-md-8 mt-5 border p-2">
                  <h3>${entreprise.name} ${entreprise.designation || ''}</h3>
                  <p><strong>Adresse:</strong> ${entreprise.adresse}</p>
                  <p><strong>numéro de téléphone:</strong> ${entreprise.phone}</p>
                  <p><strong>email: </strong>${entreprise.email || '/'}</p>
                  <p><strong>Est blacklisté: </strong> ${entreprise.blackListed ? 'Oui' : 'Non'}</p>
              </div>
              <div class="col-md-4 mt-5">
                ${formOrMessage}
              </div>
          </div>
   `;
}

const CompanyPage = async (companyId) => {
  if (!companyId) {
    Navigate('/dashboard');
    return;
  }
  clearPage();
  authenticatedUser = getAuthenticatedUser();
  if (!authenticatedUser) {
    Navigate('/');
    return;
  }
  const entreprise = await getEntrepriseById(companyId);
  const contacts = await getContactByCompanyId(entreprise.id);
  const main = document.querySelector('main');

  const contactsTable = generateContactsTable(contacts);
  const infoCompany = renderInfoCompany(entreprise);

  main.innerHTML = `
  <div class="container">
      ${infoCompany}
      <div class="col-md-12 mt-5">
          <h3>Contacts</h3>
          ${contactsTable}
          <button id="backButton" class="btn btn-primary mt-5">Retour</button>
      </div>
  </div>
  `;

  const blacklistForm = document.getElementById('blacklistForm');
  if (blacklistForm) {
    blacklistForm.addEventListener('submit', async (event) => {
      event.preventDefault();
      const reason = document.getElementById('blacklistReason').value;
      await blackListEntreprise(entreprise, reason);
      await CompanyPage(); // Call CompanyPage after blacklisting the company
    });
  }

  const backButton = document.getElementById('backButton');
  backButton.addEventListener('click', () => {
    Navigate('/dashboard');
  });
}

export default CompanyPage;


