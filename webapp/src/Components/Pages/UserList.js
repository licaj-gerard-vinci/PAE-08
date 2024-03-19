const { getAllUsers } = require("../../model/users");

const UserList = () => renderUserList();

async function renderUserList(){
  const main = document.querySelector('main');
  main.innerHTML = `
    <div class="container-fluid">
      <div class="row justify-content-center">
        <div class="col-10 col"></div>
      </div>
    </div>`;

  const userList = await getAllUsers();
  console.log(userList);
  main.innerHTML = `<h1 class="text-center">Recherche Utilisateur</h1>`;
  if (!userList || userList.length === 0) {
    main.innerHTML = `
      <p>Aucun utilisateur n'est disponible pour le moment ou vous avez pas les droits pour acceder a la page.</p>
    `;
  } else {
    main.innerHTML += `
      <table class="table table-striped table-hover">
        <thead class="thead-dark">
          <tr>
            <th>Nom</th>
            <th>Prenom</th>
            <th>Role</th>
            <th>Stage Trouvé</th>
            <th>Année Académique</th>
          </tr>
        </thead>
        <tbody>
          ${userList.map(user => `
            <tr>
              <td>${user.nom}</td>
              <td>${user.prenom}</td>
              <td>${user.role === 'E' ? 'Etudiant' : user.role}</td>
              <td>${user.hasInternship ? 'Oui' : 'Non'}</td>
              <td>${user.year}</td>
            </tr>
          `).join('')}
        </tbody>
      </table>
    `;
  }
}
module.exports = UserList;
