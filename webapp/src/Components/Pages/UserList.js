/* eslint-disable no-nested-ternary */
import Navigate from '../Router/Navigate';
import { getAuthenticatedUser } from '../../utils/auths';
import { getAllUsers } from '../../model/users';

let originalUserList = [];
let filtered = [];

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

  const main = document.querySelector('main');

  main.innerHTML = `
    <div class="container my-5">
      <h1 class="text-center mb-3">Recherche Utilisateurs</h1>
      <div id="filters"></div>
      <div class="row">
        <div class="col-12">
          <div id="user-list-table-container" class="table-responsive"> 
            <!-- Table will be inserted here -->
          </div>
        </div>
      </div>
    </div>`;

  originalUserList = await getAllUsers();
  displayFilters();
  await renderUserList(originalUserList);
  await filterUsers();
}

async function renderUserList(userList) {
  if (!userList || userList.length === 0) {
    document.getElementById('user-list-table-container').innerHTML = `
      <p class="text-center text-muted">Aucun utilisateur n'est disponible pour le moment.</p>
    `;
  } else {
    const tableHtml = `
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
            <td>${user.schoolyear.annee === null ? 'N/A' : user.schoolyear.annee}</td>
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
  }
}

function displayFilters() {
  // Adjusted sizes for select elements and added dropdown indicators
  const filterHtml = `
    <div class="container">
      <div class="row align-items-end">
        <div class="col-md">
          <div class="form-group mb-3">
            <label for="search">Recherche d'utilisateur :</label>
            <div class="input-group">
              <input type="text" class="form-control" id="search" placeholder="nom ou prénom">
            </div>
          </div>
        </div>
        <div class="col-md">
          <div class="form-group mb-3">
            <label for="filter">Filtrer par rôle :</label>
            <div class="input-group">
              <select class="form-control" id="filter">
                <option value="all">Tous</option>
                <option value="E">Étudiant</option>
                <option value="P">Professeur</option>
                <option value="A">Administratif</option>
              </select>
              <div class="input-group-append">
                <span class="input-group-text"><i class="fa fa-chevron-down"></i></span>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md">
          <div class="form-group mb-3">                 
            <label for="year">Filtrer par année académique :</label>
            <div class="input-group">
              <select class="form-control" id="year">
                <option value="all">Toutes</option>
                <option value="2022-2023">2022/2023</option>
                <option value="2023-2024">2023/2024</option>
                <option value="2024-2025">2024/2025</option>    
              </select>
              <div class="input-group-append">
                <span class="input-group-text"><i class="fa fa-chevron-down"></i></span>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-auto mb-3 d-flex align-items-center">
          <button id="reset-filters" class="btn btn-primary mt-3 ms-2">Tous les utilisateurs</button>
        </div>
      </div>
    </div>
  `;
  document.getElementById("filters").innerHTML = filterHtml;
  document.getElementById('reset-filters').addEventListener('click', async () => {
    document.getElementById('search').value = '';
    document.getElementById('filter').value = 'all';
    document.getElementById('year').value = 'all';
    await renderUserList(originalUserList);
  });
}

async function filterUsers() {
  // Search by name
  const searchInput = document.getElementById('search');
  searchInput.addEventListener('input', async () => {
    const search = searchInput.value.trim().toLowerCase();
    if(search.length !== 0) {
      filtered = filtered.length === 0 ?
          originalUserList.filter(
          user => user.lastname.toLowerCase().normalize("NFD").replace(
                  /[\u0300-\u036f]/g, "").includes(search.toLowerCase())
              || user.firstname.toLowerCase().normalize("NFD").replace(
                  /[\u0300-\u036f]/g, "").includes(search.toLowerCase()))
          : filtered.filter(
              user => user.lastname.toLowerCase().normalize("NFD").replace(
                      /[\u0300-\u036f]/g, "").includes(search.toLowerCase())
                  || user.firstname.toLowerCase().normalize("NFD").replace(
                      /[\u0300-\u036f]/g, "").includes(search.toLowerCase()));
      if (filtered.length === 0) {
        const tableBody = document.querySelector('tbody');
        if (tableBody) {
          if (filtered.length === 0) {
            tableBody.innerHTML = `
              <tr>
                <td colspan="5" id="errorFiter" class="text-center" >Aucun résultat trouvé</td>
              </tr>
            `;
          } else {
            await renderUserList(filtered);
          }
        }
      }
      await renderUserList(filtered);
    }else {
        await renderUserList(originalUserList);
      }
  });

  // Filter by role
  const filter = document.getElementById('filter');
  filter.addEventListener('change', async () => {
    const selectedRole = filter.value;
    const filteredUsers = filtered.length === 0 ?
        originalUserList.filter(user => selectedRole === 'all' ? true : user.role === selectedRole)
        : filtered.filter(user => selectedRole === 'all' ? true : user.role === selectedRole);
    await renderUserList(filteredUsers);
  });

  // Filter by year
  const year = document.getElementById('year');
  year.addEventListener('change', async () => {
    const selectedYear = year.value;
    const filteredUsers = originalUserList.filter(
        user => selectedYear === 'all' ? true : user.year.annee
            === selectedYear);
    await renderUserList(filteredUsers);
  });
}

export default UserList;
