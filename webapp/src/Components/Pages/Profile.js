/* eslint-disable no-return-assign */
/* eslint-disable no-param-reassign */
/* eslint-disable prefer-template */
import { getAuthenticatedUser } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
// eslint-disable-next-line import/no-cycle
import { getStagePresent, refreshUser, updateUser } from '../../model/users';
import { getContacts } from '../../model/contacts';

const ProfilePage = async () => {
  clearPage();
  renderPageTitle('Profile');

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

function renderProfile(user) {
  const profileDiv = document.createElement('div');
  profileDiv.className = 'profile-div';
  profileDiv.style =
      'background-color: #f2f2f2; padding: 20px; border-radius: 10px; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);';
  profileDiv.innerHTML = `
    <h2>Bonjour, ${user.user.firstname}</h2>
    <p>Email : ${user.user.email}</p>
    <p>Nom : ${user.user.lastname}</p>
    <p>Prénom : ${user.user.firstname}</p>
    <p>Numéro de téléphone : ${user.user.phone}</p>
    <p>Rôle : ${renderRole(user.user.role)}</p>
    <button id="edit-profile" class="styled-button">Modifier les informations</button>
    <div id="edit-form" style="display: none;">
      <input type="text" id="firstname" placeholder="Prénom" value="${user.user.firstname}">
      <input type="text" id="lastname" placeholder="Nom" value="${user.user.lastname}">
      <input type="email" id="email" placeholder="Email" value="${user.user.email}">
      <input type="tel" id="phone" placeholder="Numéro de téléphone" value="${user.user.phone}">
      <button id="save-changes" class="styled-button">Sauvegarder les changements</button>
    </div>
    <button id="change-password" class="styled-button">Changer le mot de passe</button>
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
    document.getElementById('edit-form').style.display = 'block';
  });

  // add event listener for the save changes button
  profileDiv.querySelector('#save-changes').addEventListener('click', async () => {
    const updatedUser = {
      firstname: document.getElementById('firstname').value,
      lastname: document.getElementById('lastname').value,
      email: document.getElementById('email').value,
      phone: document.getElementById('phone').value,
      // Ajoutez d'autres champs si nécessaire
    };
    console.log('updatedUser', updatedUser);


    await updateUser(updatedUser);
    await refreshUser();
    alert('Les informations ont été mises à jour.');
    ProfilePage();
  });

  // add event listener for the change password button
  profileDiv.querySelector('#change-password').addEventListener('click', () => {

  });

  return profileDiv;
}

async function displayStage() {
  const stageDiv = document.createElement('div');
  stageDiv.classList.add('stage-container', 'shadow', 'p-3', 'bg-white', 'rounded');

  stageDiv.style =
      'flex: 1; min-width: 450px; padding: 20px; margin: 10px; border-radius: 8px; background-color: white; box-shadow: 0 4px 8px rgba(0,0,0,0.1);';
  const stage = await getStagePresent();

  let stageHTML;
  if (stage !== "Aucun stage n'est en cours") {
    stageHTML = `
      <h2>Stage actuel</h2>
      <p><strong>Responsable :</strong> ${stage.responsable.nom} ${stage.responsable.prenom}</p>
      <p><strong>Entreprise :</strong> ${stage.entreprise.nom}, ${stage.entreprise.appellation}</p>
      <p><strong>Date signature :</strong> ${stage.dateSignature}</p>
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
    modifierSujetButton.addEventListener('click', () => {
      const isEditing = modifierSujetButton.getAttribute('data-editing');

      if (isEditing) {
        const sujetInput = stageDiv.querySelector('#sujet-input');
        const newValue = sujetInput.value.trim();
        sujetText.textContent = newValue || 'Pas de sujet';
        stage.sujet = newValue;
        modifierSujetButton.textContent = 'Modifier sujet';
        modifierSujetButton.removeAttribute('data-editing');

        // Save the new value to the server
      } else {
        const currentValue = sujetText.textContent;
        sujetText.innerHTML = `<input id="sujet-input" class="form-control" type="text" value="${currentValue}" />`;
        const sujetInput = stageDiv.querySelector('#sujet-input');
        sujetInput.focus();
        modifierSujetButton.textContent = 'Confirmer le Sujet';
        modifierSujetButton.setAttribute('data-editing', 'true');

        // Add event listener to the input to save the new value when the user presses enter
        sujetInput.addEventListener('keypress', (event) => {
          if (event.key === 'Enter') {
            modifierSujetButton.click();
          }
        });
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


