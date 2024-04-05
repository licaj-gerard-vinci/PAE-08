import Navigate from "../Router/Navigate";
import {getStagePresent} from "../../model/internships";
import {getContactsById} from "../../model/contacts";

const StudentInfoPage = async (user) => {
  // Check if user data is available
  if (!user) {
    Navigate('/users');
    return;
  }

  // Clear the main element
  const main = document.querySelector('main');
  main.innerHTML = '';

  // Create the container for student info
  const container = document.createElement('div');
  container.className = 'container';
  container.style = 'display: flex; flex-direction: column; align-items: center;';

  // Create the profile div
  const profileDiv = document.createElement('div');
  profileDiv.className = 'profile-div';
  profileDiv.style = 'background-color: #f2f2f2; padding: 30px; border-radius: 10px; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);';

  // Determine role text based on user's role
  let roleText;
  if (user.role === 'E') {
    roleText = 'Etudiant';
  } else if (user.role === 'P') {
    roleText = 'Professeur';
  } else {
    roleText = 'Admin';
  }

  // Populate the profile div with user information
  profileDiv.innerHTML = `
    <div class="container mt-4">
      <h2 class="mb-4">Informations sur l'étudiant</h2>
      <div class="row mb-3">
        <strong>Email :</strong>
        <p>${user.email}</p>
      </div>
      <div class="row mb-3">
        <strong>Nom :</strong>
        <p>${user.lastname}</p>
      </div>
      <div class="row mb-3">
        <strong>Prénom :</strong>
        <p>${user.firstname}</p>
      </div>
      <div class="row mb-3">
        <strong>Numéro de téléphone :</strong>
        <p>${user.phone}</p>
      </div>
      <div class="row mb-4">
        <strong>Rôle :</strong>
        <p>${roleText}</p>
      </div>
    </div>
  `;

  // Append the profile div to the container
  container.appendChild(profileDiv);

  // Append the container to the main element
  main.appendChild(container);

  // Add a back button into the user info div
    const backButton = document.createElement('button');
    backButton.className = 'btn btn-primary';
    backButton.textContent = 'Retour';
    backButton.style = 'margin-top: 20px;';
    backButton.addEventListener('click', () => {
        Navigate('/users');
    });
    container.appendChild(backButton);

  // Display internship information if user is a student
    if (user.role === 'E') {
        // Create the internship div
        const internshipDiv = document.createElement('div');
        internshipDiv.className = 'internship-div';
        internshipDiv.style = 'background-color: #f2f2f2; padding: 30px; border-radius: 10px; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2); margin-top: 20px;';

        // Fetch user's internship information
        const stage = await getStagePresent(user.id);

        // Populate the internship div with internship information
        if (stage) {
            internshipDiv.innerHTML = `
                 <h2>Stage actuel</h2>
      <p><strong>Responsable :</strong> ${stage.responsable.nom} ${stage.responsable.prenom}</p>
      <p><strong>Entreprise :</strong> ${stage.entreprise.nom}, ${stage.entreprise.appellation}</p>
      <p><strong>Date signature :</strong> ${stage.dateSignature}</p>
      <p><strong>Sujet :</strong> <span id="sujet-text">${stage.sujet || 'Pas de sujet'}</span></p>
    `;
        } else {
            internshipDiv.innerHTML = `
                <div class="container mt-4">
                    <h2 class="mb-4">Informations sur le stage</h2>
                    <div class="row mb-3">
                        <strong>Vous n'avez pas de stage en cours.</strong>
                    </div>
                </div>
            `;
        }
        // Append the internship div to the container
        container.appendChild(internshipDiv);
        // Append the contacts div to the container
        container.appendChild(await displayContacts());
    }

    // Display all contacts of a student
async function displayContacts() {
    const contacts = await getContactsById(user.id);
    const contactsDiv = document.createElement('div');
    contactsDiv.classList.add('contacts-container', 'shadow', 'p-4', 'bg-white', 'rounded');
    contactsDiv.style = 'width: 90%; margin-top: 20px; padding: 20px; border-radius: 8px; background-color: white; box-shadow: 0 4px 8px rgba(0,0,0,0.1);';

    let contactsHTML = '<h2 style="text-align: center; margin-bottom: 30px;">Liste des contacts</h2>';

    if (contacts.length === 0) {
        contactsHTML += '<p style="text-align: center;">Aucun contact n\'a été passé.</p>';
    } else {
        contactsHTML += `
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">Nom</th>
                        <th scope="col">Prénom</th>
                        <th scope="col">Entreprise</th>
                        <th scope="col">État</th>
                    </tr>
                </thead>
                <tbody>
        `;
        contacts.forEach((contact) => {
            contactsHTML += `
                <tr>
                    <td>${contact.utilisateur.nom}</td>
                    <td>${contact.utilisateur.prenom}</td>
                    <td>${contact.entreprise.nom}</td>
                    <td>${contact.etatContact}</td>
                </tr>
            `;
        });
        contactsHTML += '</tbody></table>';
    }


    contactsDiv.innerHTML = contactsHTML;
    return contactsDiv;
}
};

export default StudentInfoPage;
