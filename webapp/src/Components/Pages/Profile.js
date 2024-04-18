/* eslint-disable no-return-assign */
/* eslint-disable no-param-reassign */
/* eslint-disable prefer-template */
// eslint-disable-next-line import/no-cycle
import { getAuthenticatedUser } from '../../utils/auths';
import { clearPage } from '../../utils/render';
import { getStagePresent,updateInternship } from '../../model/internships';
import { checkPassword, refreshUser, updateUser } from '../../model/users';
import { getContacts } from '../../model/contacts';

const ProfilePage = async () => {
  clearPage();
  createUpdateModal(); 
  

  const main = document.querySelector('main');

  // Create the container for the profile and stage info
  const container = document.createElement('div');
  container.className = 'container';
  container.style = 'display: flex; flex-direction: column; align-items: center;';

  // Create the top container which will hold the profile greeting and the stage info
  const topContainer = document.createElement('div');
  topContainer.style =
      'display: flex; justify-content: center; align-items: flex-start; flex-wrap: wrap;';

  // Add the profile greeting to the top container
  topContainer.appendChild(renderProfile(getAuthenticatedUser()));

  // Add the stage info to the top container if the user is a student
  const user = getAuthenticatedUser();
  if (user.user.role === 'E') {
    topContainer.appendChild(await displayStage());
  }

  // Append the top container to the main container
  container.appendChild(topContainer);

  // Append the contacts list below the top container if the user is a student
  if (user.user.role === 'E') {
    container.appendChild(await displayContacts());
  }

  // Append the main container to the main element
  main.appendChild(container);
};

function renderRole(user) {
  switch (user.role) {
    case 'E':
      return 'Etudiant';
    case 'P':
      return 'Professeur';
    default:
      return 'Admin';
  }
}

// Create the modal for the update success message
function createUpdateModal() {
  const modal = document.createElement('div');
  modal.id = 'update-modal';
  modal.style = 'position: fixed; bottom: 20px; right: 20px; background-color: green; color: white; padding: 20px; border-radius: 10px; display: none; z-index: 1000;';
  modal.textContent = 'Les informations ont été mises à jour avec succès !';
  document.body.appendChild(modal);
}
// Toggle the visibility of the modals for the password modification
function toggleUpdateModal() {
  const modal = document.getElementById('update-modal');
  modal.style.display = 'block';
  setTimeout(() => {
    modal.style.display = 'none';
  }, 5000);
}

function renderProfile(user) {
  const profileDiv = document.createElement('div');
  profileDiv.className = 'profile-div';
  
  profileDiv.style = 'background-color: #f2f2f2; padding: 30px; border-radius: 10px; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);';

  profileDiv.innerHTML = `

  <div class="container mt-4">
  
 
  <h2 class="mb-4">Bonjour ${user.user.firstname} ! 👋</h2>

  <div class="row mb-3">
          <strong>Email :</strong>
          <p>${user.user.email}</p>
  </div>
  <div class="row mb-3">
          <strong>Nom :</strong>
          <p>${user.user.lastname}</p>
  </div>
  <div class="row mb-3">
          <strong>Prénom :</strong>
          <p>${user.user.firstname}</p>
  </div>
  <div class="row mb-3">
    
          <strong>Numéro de téléphone :</strong>
          <p>${user.user.phone}</p>
  
  </div>
  <div class="row mb-4">

          <strong>Rôle :</strong>
          <p>${renderRole(user.user)}</p>
      
     
  
    <button id="edit-profile" class="styled-button">Modifier les informations</button>
    </div>
      </div>
    <div id="edit-form" style="display: none;">

    <div class="container mt-4">
      <div class="row justify-content-center">
      
        <div class="col-12 col-md-13">
          <h2 class="mb-1">Modifier les informations</h2>
          

          <div class="mb-3">
            <label for="lastname" class="form-label">Nom</label>
            <input type="text" class="form-control" id="lastname" value="${user.user.lastname}" readonly>
          </div>

            <div class="mb-3">
              <label for="firstname" class="form-label">Prénom</label>
              <input type="text" class="form-control" id="firstname" value="${user.user.firstname}" readonly>
            </div>

            <div class="mb-3">
              <label for="email" class="form-label">Adresse email</label>
              <input type="email" class="form-control" id="email" value="${user.user.email}" readonly>
            </div>
            <div class="mb-3">
              <label for="phone" class="form-label">Numéro de téléphone</label>
              <input type="tel" class="form-control" id="phone" value="${user.user.phone}" autocomplete="tel">
            </div>
            <div class="mb-3">
              <label for="old-password" class="form-label">Ancien mot de passe</label>
              <input type="password" class="form-control" id="old-password" autocomplete="current-password">
            </div>
            <div class="mb-3">
              <label for="new-password" class="form-label">Nouveau mot de passe</label>
              <input type="password" class="form-control" id="new-password" autocomplete="new-password">
            </div>
            <div class="mb-3">
              <label for="confirm-password" class="form-label">Confirmer votre mot de passe</label>
              <input type="password" class="form-control" id="confirm-password" autocomplete="new-password">
            </div>
              <div class="mb-3" id="password-error" style="color: red; font-size: large;"></div>
              <div class="mb-3" id="new-password-error" style="color: red; font-size: large;"></div>        
            <button id="save-changes" class="btn btn-primary">Sauvegarder les changements</button>
         
        </div>
      </div>
    </div>
  `;

  const styledButtons = profileDiv.querySelectorAll('.styled-button');
  styledButtons.forEach((button) => {
    button.style =
        'background-color: #0056b3; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer; font-weight: bold; margin-top: 10px;';
    // Add hover effect
    button.onmouseover = () => (button.style.backgroundColor = '#003d82');
    button.onmouseout = () => (button.style.backgroundColor = '#0056b3');
  });

  // add event listener for the edit profile button
  profileDiv.querySelector('#edit-profile').addEventListener('click', () => {
    const editForm = document.getElementById('edit-form');
    editForm.style.display = editForm.style.display === 'none' ? 'block' : 'none';
  });
 

  // add event listener for the save changes button
  profileDiv.querySelector('#save-changes').addEventListener('click', async () => {
    const updatedUser = {
      firstname: document.getElementById('firstname').value,
      lastname: document.getElementById('lastname').value,
      email: document.getElementById('email').value,
      phone: document.getElementById('phone').value,
      oldPassword: document.getElementById('old-password').value,
      newPassword: document.getElementById('new-password').value,
      confirmPassword: document.getElementById('confirm-password').value,
      
    };
    console.log('updatedUser', updatedUser);
    document.getElementById('password-error').textContent = '';
    document.getElementById('new-password-error').textContent = '';


      // Vérification des mots de passe
  if (updatedUser.newPassword !== updatedUser.confirmPassword) {
    document.getElementById('new-password-error').textContent = 'Les mots de passe ne correspondent pas';
    return;
  }



    if (updatedUser.oldPassword) {
      if (!updatedUser.newPassword) {
        document.getElementById('new-password-error').textContent = "Veuillez entrer un nouveau mot de passe."
        return;
    }

    const check = await checkPassword(updatedUser);
      if (check === "Les mots de passe ne correspondent pas") {
        document.getElementById('new-password-error').textContent = "L'ancien mots de passe est incorrect."
        return;
      }

      

} else if (updatedUser.newPassword && !updatedUser.oldPassword) {
  document.getElementById('password-error').textContent = "Veuillez entrer votre ancien mot de passe."
  return;
}

  console.log('updatedUser', updatedUser);
    await updateUser(updatedUser);
    await refreshUser();
    toggleUpdateModal();
    ProfilePage();
  });



  return profileDiv;
}

async function displayStage() {
  const stageDiv = document.createElement('div');
  stageDiv.classList.add('stage-container', 'shadow', 'p-3', 'bg-white', 'rounded');

  stageDiv.style =
      'flex: 1; min-width: 450px; padding: 20px; margin: 10px; border-radius: 8px; background-color: white; box-shadow: 0 4px 8px rgba(0,0,0,0.1);';
  const id = getAuthenticatedUser();
  const stage = await getStagePresent(id.user.id);
  const date = new Date(stage.dateSignature);
  const year = date.getFullYear();
  let month = date.getMonth() + 1; // Les mois sont basés sur zéro en JavaScript
  let day = date.getDate();

// Ajoutez des zéros au début du mois et du jour si nécessaire
  if (month < 10) month = month.toString().padStart(2, '0');
  if (day < 10) day = day.toString().padStart(2, '0');

  const formattedDate = `${year}-${month}-${day}`; // Utilisez les littéraux de modèle pour formater la date

  let stageHTML;
  if (stage !== "Aucun stage n'est en cours") {
    stageHTML = `
      <h2>Stage actuel</h2>
      <p><strong>Responsable :</strong> ${stage.responsable.nom} ${stage.responsable.prenom}</p>
      <p><strong>Entreprise :</strong> ${stage.entreprise.nom}, ${stage.entreprise.appellation}</p>
      <p><strong>Date signature :</strong> ${formattedDate}</p>
      <p><strong>Sujet :</strong> <span id="sujet-text">${stage.sujet || 'Pas de sujet'}</span></p>
      <button id="modifier-sujet" class="btn btn-outline-primary btn-block mt-2">Modifier sujet</button>
    `;
  } else {
    stageHTML = `<p>${stage}</p>`;
  }


  stageDiv.innerHTML = stageHTML;
  document.body.appendChild(stageDiv);

  const modifierSujetButton = stageDiv.querySelector('#modifier-sujet');
  const sujetText = stageDiv.querySelector('#sujet-text');
  if (modifierSujetButton) {
    modifierSujetButton.addEventListener('click', async () => {
      const isEditing = modifierSujetButton.getAttribute('data-editing');

      if (isEditing) {
        const sujetInput = stageDiv.querySelector('#sujet-input');
        const newValue = sujetInput.value.trim();
        sujetText.textContent = newValue || 'Pas de sujet';
        stage.sujet = newValue;
        modifierSujetButton.textContent = 'Modifier sujet';
        modifierSujetButton.removeAttribute('data-editing');

        // Save the new value to the server
        await updateInternship(stage);
      } else {
        const currentValue = sujetText.textContent;
        sujetText.innerHTML = `<input id="sujet-input" class="form-control" type="text" value="${currentValue}" />`;
        const sujetInput = stageDiv.querySelector('#sujet-input');
        sujetInput.focus();
        modifierSujetButton.textContent = 'Confirmer le Sujet';
        modifierSujetButton.setAttribute('data-editing', 'true');
      }
    });
  }

  return stageDiv;
}

async function displayContacts() {
  const contacts = await getContacts();
  const contactsDiv = document.createElement('div');
  contactsDiv.classList.add('contacts-container', 'shadow', 'p-4', 'bg-white', 'rounded');
  contactsDiv.style =
      'width: 90%; margin-top: 20px; padding: 20px; border-radius: 8px; background-color: white; box-shadow: 0 4px 8px rgba(0,0,0,0.1);';

  let contactsHTML = '<h2 style="text-align: center; margin-bottom: 30px;">Liste des contacts</h2>';

  if (Array.isArray(contacts) && contacts.length > 0) {
    contactsHTML += `
      <div class="contacts-list" style="overflow-x: auto;">
        <table style="width: 100%; margin-left: 20px;">
          <thead>
            <tr style="background-color: #f9f9f9; border-bottom: 2px solid #eee;">
              <th style="padding: 10px 0; text-align: left; padding-left: 10px;">Entreprises</th>
              <th style="padding: 10px 0; text-align: left;">État contact</th>
              <th style="padding: 10px 0; text-align: left;">Lieu rencontre</th>
              <th style="padding: 10px 0; text-align: left;">Raison refus</th>
            </tr>
          </thead>
          <tbody>`;

    contacts.forEach((contact) => {
      contactsHTML += `
        <tr style="border-bottom: 1px solid #eee;">
          <td style="padding: 15px 0; text-align: left; padding-left: 10px;">${contact.entreprise.nom} ${contact.appellation ? contact.appellation : ''}</td>
          <td style="padding: 15px 0; text-align: left;">${contact.etatContact}</td>
          <td style="padding: 15px 0; text-align: left;">${contact.lieuxRencontre ? contact.lieuxRencontre : 'N/A'}</td>
          <td style="padding: 15px 0; text-align: left; max-width: 250px; word-wrap: break-word;">${contact.raisonRefus ? contact.raisonRefus : 'N/A'}</td>
        </tr>
      `;
    });

    contactsHTML += `</tbody></table></div>`;
  } else {
    contactsHTML += `<p style="text-align: center;">Aucun contact n'as été passé</p>`;
  }

  contactsDiv.innerHTML = contactsHTML;
  return contactsDiv;
}

export default ProfilePage;


