/* eslint-disable no-nested-ternary */
import Navigate from '../Router/Navigate';
import { getAuthenticatedUser } from '../../utils/auths';
import { getAllUsers } from '../../model/users';



const UserList = () => {
  const authenticatedUser = getAuthenticatedUser();
  if (!authenticatedUser) {
    Navigate('/');
    return;
    
  }
  if (authenticatedUser.user.role === 'E') {
    Navigate('/');
  }

}
let currentPage = 1;
const usersPerPage = 15;

async function renderUserList() {
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

  const userList = await getAllUsers();
  console.log(userList + "userList")

  // Get current users
  const indexOfLastUser = currentPage * usersPerPage;
  const indexOfFirstUser = indexOfLastUser - usersPerPage;
  const currentUsers = userList.slice(indexOfFirstUser, indexOfLastUser);
  console.log(currentUsers + "currentUsers")

  if (!currentUsers || currentUsers.length === 0) {
    document.getElementById('user-list-table-container').innerHTML = `
      <p class="text-center text-muted">Aucun utilisateur n'est disponible pour le moment ou vous n'avez pas les droits pour accéder à la page.</p>
    `;
  } else {
    const tableHtml = `
        <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="form-group">
                <label for="search">Rechercher :</label>
                <input type="text" class="form-control" id="search" placeholder="Rechercher par nom ou prénom (nom.prenom / prenom.nom)">
                <button class="btn btn-primary mt-3" id="search-btn">Rechercher</button>
                </div>
                <label for="filter">Filtrer par rôle :</label>
                <select class="form-control" id="filter">
                    <option value="all">Tous</option>
                    <option value="E">Étudiant</option>
                    <option value="P">Professeur</option>
                    <option value="A">Administratif</option>
                </select>
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
          ${currentUsers.map(user => `
            <tr class="${user.hasInternship ? 'table-success' : ''}">
              <td>${user.lastname}</td>
              <td>${user.firstname}</td>
              <td>${user.role === 'E' ? 'Étudiant' : user.role === 'P' ? 'Professeur' : 'Administratif'}</td>
              <td>${user.role !== 'E' ? 'N/A' : user.hasInternship ? 'Oui' : 'Non'}</td>
              <td>${user.year.annee === null ? 'N/A' : user.year.annee}</td>
            </tr>
          `).join('')}
        </tbody>
      </table>`;
    document.getElementById('user-list-table-container').innerHTML = tableHtml;
  }

  renderPagination(userList.length, usersPerPage);

  // Filter by role
  const filter = document.getElementById('filter');
  filter.addEventListener('change', () => {
    const selectedRole = filter.value;
    const filteredUsers = userList.filter(user => selectedRole === 'all' ? true : user.role === selectedRole);
    const tableBody = document.querySelector('tbody');
    tableBody.innerHTML = filteredUsers.map(user => `
          <tr class="${user.hasInternship ? 'table-success' : ''}">
            <td>${user.lastname}</td>
            <td>${user.firstname}</td>
            <td>${user.role === 'E' ? 'Étudiant' : user.role === 'P' ? 'Professeur' : 'Administratif'}</td>
            <td>${user.hasInternship ? 'Oui' : 'Non'}</td>
            <td>${user.year.annee === null ? 'N/A' : user.year.annee}</td>
            </tr>
        `).join('');
  });

  // Search by name
  const searchBtn = document.getElementById('search-btn');
  searchBtn.addEventListener('click', () => {
    const search = document.getElementById('search').value;
    const searchResults = userList.filter(user => user.lastname.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(search.toLowerCase()) || user.firstname.toLowerCase().normalize("NFD").replace(/[\u0300-\u036f]/g, "").includes(search.toLowerCase()));
    if (searchResults.length === 0) {
      const tableBody = document.querySelector('tbody');
      tableBody.innerHTML = `
            <tr>
                <td colspan="5" id="errorFiter" class="text-center" >Aucun résultat trouvé</td>
            </tr>
            `;
      return;
    }
    const tableBody = document.querySelector('tbody');
    tableBody.innerHTML = searchResults.map(user => `
          <tr class="${user.hasInternship ? 'table-success' : ''}">
            <td>${user.lastname}</td>
            <td>${user.firstname}</td>
            <td>${user.role === 'E' ? 'Étudiant' : user.role === 'P' ? 'Professeur' : 'Administratif'}</td>
            <td>${user.hasInternship ? 'Oui' : 'Non'}</td>
            <td>${user.year.annee === null ? 'N/A' : user.year.annee}</td>
            </tr>
        `).join('');

  });
}

function paginate(pageNumber) {
  currentPage = pageNumber;
  renderUserList();
}

function renderPagination(totalUsers) {
  const paginationContainer = document.createElement('div');

  for (let i = 1; i <= Math.ceil(totalUsers / usersPerPage); i+1) {
    const pageLink = document.createElement('a');
    pageLink.textContent = i;
    pageLink.addEventListener('click', () => paginate(i));
    paginationContainer.appendChild(pageLink);
  }

  document.body.appendChild(paginationContainer);
}

renderUserList();
export default UserList;