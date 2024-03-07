import { getAuthenticatedUser } from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';
import { getStagePresent } from '../../model/users';

const ProfilePage = async () => {
  clearPage();
  renderPageTitle('Profile');

  const main = document.querySelector('main'); 
  main.innerHTML = '<div class="container" style="display: flex; justify-content: center; align-items: flex-start;"></div>';

  const container = document.querySelector('.container');
  container.appendChild(renderProfile(getAuthenticatedUser()));
  container.appendChild(await displayStage());
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
  profileDiv.style = "flex: 1; max-width: 400px; padding: 20px; border-radius: 8px; background-color: white; box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin-right: 10px;";

  const profileHTML = `
    <h2 style="text-align: center; margin-bottom: 30px;">Bonjour</h2>
    <p><strong>Email :</strong> ${user.email}</p>
    <p><strong>Nom :</strong> ${user.name}</p>
    <p><strong>Prénom :</strong> ${user.firstName}</p>
    <p><strong>Nr de téléphone :</strong> ${user.numTel}</p>
    ${user.role && user.role !== 'E' ? `<p><strong>Rôle :</strong> ${renderRole(user.role)}</p>` : ''}
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
  stageDiv.classList.add('stage-container');
  stageDiv.style = "flex: 1; max-width: 400px; padding: 20px; border-radius: 8px; background-color: white; box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin-left: 10px;";

  const stage = await getStagePresent();
  let stageHTML;
  if (stage !== "Aucun stage n'est en cours") {
    stageHTML = `
      <h2>Stage actuel</h2>
      <p><strong>Responsable :</strong> ${stage.responsableNom} ${stage.responsablePrenom}</p>
      <p><strong>Entreprise :</strong> ${stage.entrepriseNom}, ${stage.entrepriseAppellation}</p>
      <p><strong>Date signature :</strong> ${stage.dateSignature}</p>
      <p><strong>Sujet :</strong> ${stage.sujet}</p>
    `;
  } else {
    stageHTML = `<p>${stage}</p>`;
  }

  stageDiv.innerHTML = stageHTML;
  return stageDiv;
}

export default ProfilePage;
