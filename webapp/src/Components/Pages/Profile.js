/* eslint-disable prefer-template */
import { getAuthenticatedUser } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import { getStagePresent, updateUser } from '../../model/users';
import { getContacts } from '../../model/contacts';



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
  profileDiv.innerHTML = `
    <h2>Bonjour, ${user.user.firstname}</h2>
    <p>Email : ${user.user.email}</p>
    <p>Nom : ${user.user.lastname}</p>
    <p>Prénom : ${user.user.firstname}</p>
    <p>Numéro de téléphone : ${user.user.phone}</p>
    <p>Rôle : ${renderRole(user.user.role)}</p>
    <button id="edit-profile">Modifier les informations</button>
    <div id="edit-form" style="display: none;">
      <input type="text" id="firstname" placeholder="Prénom" value="${user.user.firstname}">
      <input type="text" id="lastname" placeholder="Nom" value="${user.user.lastname}">
      <input type="email" id="email" placeholder="Email" value="${user.user.email}">
      <input type="tel" id="phone" placeholder="Numéro de téléphone" value="${user.user.phone}">
      <button id="save-changes">Sauvegarder les changements</button>
    </div>
    <button id="change-password">Changer le mot de passe</button>
  `;

  // Ajouter un écouteur d'événements pour le bouton de modification de profil
  profileDiv.querySelector('#edit-profile').addEventListener('click', () => {
    document.getElementById('edit-form').style.display = 'block';
  });

  // Ajouter un écouteur d'événements pour le bouton de sauvegarde des changements
  profileDiv.querySelector('#save-changes').addEventListener('click', async () => {
    const updatedUser = {
      firstname: document.getElementById('firstname').value,
      lastname: document.getElementById('lastname').value,
      email: document.getElementById('email').value,
      phone: document.getElementById('phone').value,
      // Ajoutez d'autres champs si nécessaire
    };
      console.log("updatedUser", updatedUser);
    // Mettre à jour les informations utilisateur via une requête API
    try {
      await updateUser(updatedUser);
      alert('Les informations ont été mises à jour.');
      document.getElementById('edit-form').style.display = 'none';
    } catch (error) {
      alert('Une erreur est survenue lors de la mise à jour : ' + error.message);
    }
  });

  // Ajouter un écouteur d'événements pour le bouton de changement de mot de passe
  profileDiv.querySelector('#change-password').addEventListener('click', () => {
    // Logique pour changer le mot de passe
    // Peut inclure l'affichage d'un formulaire de changement de mot de passe et la gestion de la soumission
  });

  return profileDiv;
}

async function displayStage() {
  const stageDiv = document.createElement('div');
  stageDiv.classList.add('stage-container', 'shadow', 'p-3', 'bg-white', 'rounded');

  stageDiv.style = "flex: 1; min-width: 450px; padding: 20px; margin: 10px; border-radius: 8px; background-color: white; box-shadow: 0 4px 8px rgba(0,0,0,0.1);";
const stage = await getStagePresent();

  let stageHTML;
  if (stage !== "Aucun stage n'est en cours") {
    stageHTML = `
      <h2>Stage actuel</h2>
      <p><strong>Responsable :</strong> ${stage.responsableNom} ${stage.responsablePrenom}</p>
      <p><strong>Entreprise :</strong> ${stage.entrepriseNom}, ${stage.entrepriseAppellation}</p>
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
