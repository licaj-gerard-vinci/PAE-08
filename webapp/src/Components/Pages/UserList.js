/* eslint-disable no-nested-ternary */
const { getAllUsers } = require("../../model/users");

const UserList = () => renderUserList();

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


    if (!userList || userList.length === 0) {
        document.getElementById('user-list-table-container').innerHTML = `
      <p class="text-center text-muted">Aucun utilisateur n'est disponible pour le moment ou vous n'avez pas les droits pour accéder à la page.</p>
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
          ${userList.map(user => `
            <tr class="${user.hasInternship ? 'table-success' : ''}">
              <td>${user.lastname}</td>
              <td>${user.firstname}</td>
              <td>${user.role === 'E' ? 'Étudiant' : user.role === 'P' ? 'Professeur' : 'Administratif'}</td>
              <td>${user.hasInternship ? 'Oui' : 'Non'}</td>
              <td>${user.year === null ? 'N/A' : user.year}</td>
            </tr>
          `).join('')}
        </tbody>
      </table>`;

        document.getElementById('user-list-table-container').innerHTML = tableHtml;
    }
}

module.exports = UserList;
