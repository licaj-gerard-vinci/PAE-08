import { getEntrepriseById }  from '../../model/entreprises';
import { clearPage } from '../../utils/render';
import {getContactByCompanyId} from "../../model/contacts";

const CompanyPage = async () => {
  clearPage();
  const entreprise = await getEntrepriseById(1);
  const contacts = await getContactByCompanyId(entreprise.id);
  console.log(entreprise);
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
  if (entreprise.blackListed) {
    formOrMessage = '<p>L\'entreprise est blacklistée</p>';
  } else {
    formOrMessage = `
      <form class="d-flex flex-column">
          <div class="form-group flex-grow-1">
              <label for="blacklistReason">Raison de la blacklist</label>
              <input type="text" class="form-control" id="blacklistReason" placeholder="Entrez la raison">
          </div>
          <button type="submit" class="btn mt-2 align-self-end" style="background-color: blue; color: white;">Ajouter à la blacklist</button>
      </form>
    `;
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
}
export default CompanyPage;