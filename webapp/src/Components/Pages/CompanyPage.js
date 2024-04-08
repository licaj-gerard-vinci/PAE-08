import {blackListEntreprise, getEntrepriseById} from '../../model/entreprises';
import { clearPage } from '../../utils/render';
import {getContactByCompanyId} from "../../model/contacts";
import Navigate from "../Router/Navigate";
import {getAuthenticatedUser} from "../../utils/auths";

const CompanyPage = async (companyId) => {
  if (!companyId) {
    Navigate('/dashboard');
    return;
  }
  clearPage();
  const authenticatedUser = getAuthenticatedUser();
  if (!authenticatedUser) {
    Navigate('/');
    return;
  }
  const entreprise = await getEntrepriseById(companyId);
    console.log(entreprise);
  const contacts = await getContactByCompanyId(entreprise.id);
  const main = document.querySelector('main');

  // Création du tableau de contacts
  let contactsTable = '<table class="table table-hover shadow-sm">';
  contactsTable += '<thead class="table-dark"><tr><th>Etudiant</th><th>Etat du contact</th><th>Lieu de rencontre</th><th>Raison du refus</th></tr></thead>';
  contactsTable += '<tbody>';
  contacts.forEach(contact => {
    contactsTable += `<tr>
    <td>${contact.utilisateur.lastname}
        ${contact.utilisateur.firstname} </br>
        ${contact.utilisateur.email}</td>
    <td>${contact.etatContact}</td>
    <td>${contact.lieuRencontre || 'N/A'}</td>
    <td>${contact.raisonRefus || 'N/A'}</td>
  </tr>`;
  });
  contactsTable += '</tbody></table>';

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
        formOrMessage = `<p>Cette entreprise est blacklistée</p> </br> <p> Raison: ${entreprise.motivation_blacklist}</p>`;
    }

  main.innerHTML = `
  <div class="container">
      <div class="row">
          <div class="col-md-8 mt-5">
              <h3>${entreprise.nom} ${entreprise.appellation || ''}</h3>
              <p>Adresse: ${entreprise.adresse}</p>
              <p>numéro de tel: ${entreprise.numTel}</p>
              <p>email: ${entreprise.email || ''}</p>
              <p>Est blacklisté: ${entreprise.blackListed ? 'Oui' : 'Non'}</p>
          </div>
          <div class="col-md-4 mt-5">
              ${formOrMessage}
          </div>
          <div class="col-md-12 mt-5">
              <h3>Contacts</h3>
              ${contactsTable}
          </div>
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

}
export default CompanyPage;