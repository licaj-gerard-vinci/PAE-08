/* eslint-disable no-nested-ternary */
import Navigate from "../Router/Navigate";
import { getStagePresent } from "../../model/internships";
import { getContactsById } from "../../model/contacts";

const StudentInfoPage = async (user) => {
  if (!user) {
    Navigate('/users');
    return;
  }

  const main = document.querySelector('main');
  main.innerHTML = '';

  const topRow = document.createElement('div');
  topRow.className = 'row justify-content-center mt-4';

  const studentInfoCol = document.createElement('div');
  studentInfoCol.className = 'col-md-4';

  studentInfoCol.innerHTML = `
    <div class="card">
    <div class="card-body">
      <h5 class="card-title">Information sur l'étudiant(e)</h5>
      <p class="card-text"><strong>Email:</strong> ${user.email}</p>
      <p class="card-text"><strong>Nom:</strong> ${user.lastname}</p>
      <p class="card-text"><strong>Prénom:</strong> ${user.firstname}</p>
      <p class="card-text"><strong>Numéro de téléphone:</strong> ${user.phone}</p>
      <p class="card-text"><strong>Rôle:</strong> ${user.role === 'E' ? 'Etudiant' : user.role === 'P' ? 'Professeur' : 'Admin'}</p>
    </div>
  </div>
  `;

  topRow.appendChild(studentInfoCol);

  const internshipCol = document.createElement('div');
  internshipCol.className = 'col-md-4';

  const stage = await getStagePresent(user.id);
  internshipCol.innerHTML = `
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">${stage !== "Aucun stage n'est en cours" ? 'Stage actuel' : 'Informations sur le stage'}</h5>
        ${
          stage !== "Aucun stage n'est en cours" ?
          `<p class="card-text"><strong>Responsable:</strong> ${stage.responsable.nom} ${stage.responsable.prenom}</p>
          <p class="card-text"><strong>Entreprise:</strong> ${stage.entreprise.nom}</p>
          <p class="card-text"><strong>Date signature:</strong> ${stage.dateSignature}</p>
          <p class="card-text"><strong>Sujet:</strong> ${stage.sujet || 'Pas de sujet'}</p>`
          :
          `<p class="card-text">Vous n'avez pas de stage en cours.</p>`
        }
      </div>
    </div>
  `;

  topRow.appendChild(internshipCol);

  main.appendChild(topRow);

  const contactsDiv = await displayContacts(user);
  main.appendChild(contactsDiv);
};

async function displayContacts(user) {
  const contacts = await getContactsById(user.id);
  const contactsDiv = document.createElement('div');
  contactsDiv.className = 'container mt-5 shadow-sm p-3 mb-5 bg-white rounded';
  
  let contactsHTML = '<div class="row"><div class="col-lg-12 mx-auto">';
  contactsHTML += '<h2 class="text-center mb-4">Liste des contacts</h2>';

  if (!Array.isArray(contacts) || contacts.length === 0) {
    contactsHTML += '<p class="text-center">Aucun contact disponible.</p>';
  } else {
    contactsHTML += `
    
      <div class="table-responsive">
        <table class="table table-hover">
          <thead class="thead-dark">
            <tr>
              <th scope="col">Entreprise</th>
              <th scope="col">État contact</th>
              <th scope="col">Lieu rencontre</th>
              <th scope="col">Raison refus</th>
            </tr>
          </thead>
          <tbody>
    `;

    contacts.forEach((contact) => {
      contactsHTML += `
        <tr>
          <td>${contact.entreprise.nom || 'N/A'}</td>
          <td>${contact.etatContact || 'N/A'}</td>
          <td>${contact.lieuxRencontre || 'N/A'}</td>
          <td>${contact.raisonRefus || 'N/A'}</td>
        </tr>
      `;
    });

    contactsHTML += '</tbody></table></div>';
  }

  contactsHTML += '</div></div>';
  contactsDiv.innerHTML = contactsHTML;

  const contactButton = document.createElement('button');
  contactButton.className = 'btn btn-primary';
  contactButton.textContent = 'Retours aux utilisateurs';
  contactButton.style = 'margin-top: 20px;';
  contactButton.addEventListener('click', () => {
    Navigate('/users'); 
  });
  
  contactsDiv.appendChild(contactButton);
  
  return contactsDiv;
}

export default StudentInfoPage;
