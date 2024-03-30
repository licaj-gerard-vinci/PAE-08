import { getEntrepriseById }  from '../../model/entreprises';
import { clearPage, renderPageTitle } from '../../utils/render';
import {getContactByCompanyId} from "../../model/contacts";

const CompanyPage = async () => {
  clearPage();
  renderPageTitle('Entreprise');
  const entreprise = await getEntrepriseById(1);
  const contacts = await getContactByCompanyId(entreprise.id);
  console.log(entreprise);
  const main = document.querySelector('main');

  // Création du tableau de contacts
  let contactsTable = '<table class="table">';
  contactsTable += '<thead><tr><th>Nom</th><th>Prénom</th><th>Email</th><th>Etat du contact</th><th>Lieu de rencontre</th><th>Raison du refus</th></tr></thead>';
  contactsTable += '<tbody>';
  contacts.forEach(contact => {
    contactsTable += `<tr>
      <td>${contact.utilisateur.lastname}</td>
      <td>${contact.utilisateur.firstname}</td>
      <td>${contact.utilisateur.email}</td>
      <td>${contact.etatContact}</td>
      <td>${contact.lieuRencontre || 'N/A'}</td>
      <td>${contact.raisonRefus || 'N/A'}</td>
    </tr>`;
  });
  contactsTable += '</tbody></table>';

  main.innerHTML = `
  <div class="container">
      <div class="row">
          <div class="col-md-6 mt-5">
              <h3>${entreprise.nom} ${entreprise.appellation || ''}</h3>
              <p>Adresse: ${entreprise.adresse}</p>
              <p>numéro de tel: ${entreprise.numTel}</p>
              <p>email: ${entreprise.email || ''}</p>
              <p>Est blacklisté: ${entreprise.blackListed ? 'Oui' : 'Non'}</p>
          </div>
          <div class="col-md-6 mt-5">
              <form class="d-flex flex-column">
                  <div class="form-group flex-grow-1">
                      <label for="blacklistReason">Raison de la blacklist</label>
                      <input type="text" class="form-control" id="blacklistReason" placeholder="Entrez la raison">
                  </div>
                  <button type="submit" class="btn mt-2 align-self-end" style="background-color: blue; color: white;">Ajouter à la blacklist</button>
              </form>
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