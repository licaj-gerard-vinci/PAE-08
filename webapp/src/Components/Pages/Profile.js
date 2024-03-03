import { getAuthenticatedUser} from '../../utils/auths';
import { clearPage, renderPageTitle } from '../../utils/render';




const ProfilePage = () => {
  clearPage();
  renderPageTitle('Profile');
  
  renderProfile(getAuthenticatedUser());
 
 
};

function renderRole(user){
  let role= null;
  if(user.role === 'E'){
    role = 'Etudiant'
  }else if(user.role === 'P'){
    role = 'professeur'
  }else{
    role = 'Admin'
  }
  return role;
  
}


function renderProfile(user) {
  const main = document.querySelector('main'); 
  

  // Construisez la structure HTML de la page de profil
  const profileHTML = `
    <div class="profile-container" style="max-width: 400px; margin: 50px auto; padding: 20px; border-radius: 8px; background-color: white; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
      <h2 style="text-align: center; margin-bottom: 30px;">Bonjour,</h2>
      <p><strong>Email :</strong> ${user.email}</p>
      <p><strong>Nom :</strong> ${user.name}</p>
      <p><strong>Prénom :</strong> ${user.firstName}</p>
      <p><strong>Nr de téléphone :</strong> ${user.numTel}</p>
      <p><strong>Rôle :</strong> ${renderRole(user)}</p>
      <div style="text-align: center; margin-top: 30px;">
        <button class="btn btn-primary">Modifier informations</button>
        <button class="btn btn-secondary">Se déconnecter</button>
      </div>
    </div>
  `;

  // Affectez la chaîne HTML à innerHTML de l'élément main
  main.innerHTML = profileHTML;
}




export default ProfilePage;