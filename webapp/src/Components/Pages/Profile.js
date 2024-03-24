/* eslint-disable prefer-template */
import { getAuthenticatedUser } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import { getStagePresent, getContacts } from '../../model/users';

const ProfilePage = async () => {
  clearPage();
  renderPageTitle('Profile');

  const main = document.querySelector('main');

  main.innerHTML = '<div class="container" style="display: flex; flex-direction: column; align-items: center;"></div>';

  const container = document.querySelector('.container');

  const topContainer = document.createElement('div');
  topContainer.style = "display: flex; justify-content: center; align-items: flex-start; flex-wrap: wrap;";
  topContainer.appendChild(renderProfile(getAuthenticatedUser()));

  const user = getAuthenticatedUser();
  if(user.user.role === 'E') {
    topContainer.appendChild(await displayStage());
    container.appendChild(await displayContacts());
  }

  container.appendChild(topContainer);
};

function renderRole(user) {
  switch(user.role) {
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
  profileDiv.classList.add('profile-container');
  profileDiv.style = "flex: 1; min-width: 450px; padding: 20px; border-radius: 8px; background-color: white; box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin: 10px;";

  const profileHTML = `
    <h2 style="text-align: center; margin-bottom: 30px;">Bonjour</h2>
    <p><strong>Email :</strong> ${user.user.email}</p>
    <p><strong>Nom :</strong> ${user.user.lastname}</p>
    <p><strong>Prénom :</strong> ${user.user.firstname}</p>
    <p><strong>Nr de téléphone :</strong> ${user.user.phone}</p>
    ${user.user.role && user.user.role !== 'E' ? `<p><strong>Rôle :</strong> ${renderRole(user.user)}</p>` : ''}
    <div style="text-align: center; margin-top: 30px;">
      <button class="btn btn-primary">Modifier informations</button>
      <button class="btn btn-secondary">Se déconnecter</button>
    </div>
  `;

  profileDiv.innerHTML = profileHTML;
  return profileDiv;
}

async function displayStage() {
  const stageDiv = document.createElement('div');
  stageDiv.classList.add('stage-container', 'shadow', 'p-3', 'bg-white', 'rounded');

  stageDiv.style = "flex: 1; min-width: 450px; padding: 20px; margin: 10px; border-radius: 8px; background-color: white; box-shadow: 0 4px 8px rgba(0,0,0,0.1);";
const stage = await getStagePresent();
console.log('stage:zad ', stage);

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
  console.log('contacts:zad zadzadzadza d zad za ', contacts);
  const contactsDiv = document.createElement('div');
  contactsDiv.classList.add('contacts-container', 'shadow', 'p-4', 'bg-white', 'rounded');
  contactsDiv.style = "width: 90%; margin-top: 20px; padding: 20px; border-radius: 8px; background-color: white; box-shadow: 0 4px 8px rgba(0,0,0,0.1);";

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

    contacts.forEach(contact => {
      contactsHTML += `
        <tr style="border-bottom: 1px solid #eee;">
          <td style="padding: 15px 0; text-align: left; padding-left: 10px;">${contact.entreprise.nom} ${contact.entreprise.appellation ? contact.entreprise.appellation : ''}</td>
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
