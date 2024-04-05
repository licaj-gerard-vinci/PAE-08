/* eslint-disable no-nested-ternary */
import Navigate from '../Router/Navigate';
import { getAuthenticatedUser } from '../../utils/auths';
import { getAllUsers } from '../../model/users';

const UserList = async () => {
  const authenticatedUser = getAuthenticatedUser();
  if (!authenticatedUser) {
    Navigate('/');
    return;

  }
  if (authenticatedUser.user.role === 'E') {
    Navigate('/');
    return;
  }

  await renderUserList();
}

async function renderUserList(users) {
  const main = document.querySelector('main');

  main.innerHTML = `
    <div class="container my-5">
      <h1 class="text-center mb-3">Recherche Utilisateurs</h1>
      <div class="row">
        <div class="col-12">
          <div id="user-list-table-container" class="table-responsive"> 
            <!-- Table will be inserted here -->
          </div>
        </div>
      </div>
    </div>`;

  const userList = users ? users : await getAllUsers();

  if (!userList || userList.length === 0) {
    document.getElementById('user-list-table-container').innerHTML = `
      <p class="text-center text-muted">Aucun utilisateur n'est disponible pour le moment ou vous n'avez pas les droits pour accéder à la page.</p>
    `;
  } else {
    const tableHtml = `
        <div class="container">
        <div class="row-filter">
            <div class="col-12">
                <div class="form-group">
                <label for="search">Rechercher :</label>
                <input type="text" class="form-control" id="search" placeholder="Rechercher par nom ou prénom (nom.prenom / prenom.nom)">
                <button class="btn btn-primary mt-2" id="search-btn">Rechercher</button>
                </div>
                
                <div class="form-group2">
                <label for="filter">Filtrer par rôle :</label>
                <select class="form-control" id="filter">
                    <option value="all">Tous</option>
                    <option value="E">Étudiant</option>
                    <option value="P">Professeur</option>
                    <option value="A">Administratif</option>
                </select> 
                </div>
                <div class="form-group3">                 
                 <label for="year">Filtrer par année académique :</label>
                  <select class="form-control" id="year">
                  <option value="all">Toutes</option>
                  <option value="2022-2023">2022/2023</option>
                  <option value="2023-2024">2023/2024</option>
                  <option value="2024-2025">2024/2025</option>    
            </select>
            </div>
            </div>         
       </div>
    </div> 
</div>
      <table class="table table-hover shadow-sm">
        <thead class="table-dark">
          <tr>
            <th scope="col">Nom</th>
            <th scope="col">Prénom</th>
            <th scope="col">Rôle</th>
            <th scope="col">Stage Trouvé</th>
            <th scope="col">Année Académique</th>
          </tr>
        </thead>
      <tbody>
        ${userList.map((user, index) => `
          <tr id="user-${index}" class="${user.role === 'E' ? 'cursor-pointer' : ''} ${user.hasInternship ? 'table-success' : ''}">
            <td class="profile-link">${user.lastname}</td>
            <td>${user.firstname}</td>
            <td>${user.role === 'E' ? 'Étudiant' : user.role === 'P' ? 'Professeur' : 'Administratif'}</td>
            <td>${user.role !== 'E' ? 'N/A' : user.hasInternship ? 'Oui' : 'Non'}</td>
            <td>${user.year.annee === null ? 'N/A' : user.year.annee}</td>
          </tr>
        `).join('')}
      </tbody>
    </table>`;


    document.getElementById('user-list-table-container').innerHTML = tableHtml;

    userList.forEach((user, index) => {
      document.getElementById(`user-${index}`).addEventListener('click', () => {
        if (user.role !== 'E') {
            return;
        }
        Navigate('/studentInfo',user);
      })});
  };

  // Search by name
  const searchBtn = document.getElementById('search-btn');
  searchBtn.addEventListener('click', () => {
    const search = document.getElementById('search').value;
    const searchResults = userList.filter(
        user => user.lastname.toLowerCase().normalize("NFD").replace(
                /[\u0300-\u036f]/g, "").includes(search.toLowerCase())
            || user.firstname.toLowerCase().normalize("NFD").replace(
                /[\u0300-\u036f]/g, "").includes(search.toLowerCase()));
    if (searchResults.length === 0) {
      const tableBody = document.querySelector('tbody');
      tableBody.innerHTML = `
            <tr>
                <td colspan="5" id="errorFiter" class="text-center" >Aucun résultat trouvé</td>
            </tr>
            `;
      return;
    }

    renderUserList(searchResults);
  });

  // Filter by role
  const filter = document.getElementById('filter');
  filter.addEventListener('change', () => {
    const selectedRole = filter.value;
    const filteredUsers = userList.filter(
        user => selectedRole === 'all' ? true : user.role === selectedRole);
    renderUserList(filteredUsers);
  });

  // Filter by year
  const year = document.getElementById('year');
  year.addEventListener('change', () => {
    const selectedYear = year.value;
    const filteredUsers = userList.filter(
        user => selectedYear === 'all' ? true : user.year.annee === selectedYear);
    renderUserList(filteredUsers);
  });
}

export default UserList;