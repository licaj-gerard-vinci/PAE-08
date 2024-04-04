/* eslint-disable no-return-assign */
/* eslint-disable no-param-reassign */
/* eslint-disable prefer-template */
import { getAllUsers } from '../../model/users';

// Function to render individual student's profile
const profileStudent = async () => {
    const main = document.querySelector('main');
    clearPage(main); // Assuming you have a function like clearPage to clear the content

    const container = document.createElement('div');
    container.className = 'container my-5';

    const row = document.createElement('div');
    row.className = 'row';

    const col = document.createElement('div');
    col.className = 'col-12';

    const tableContainer = document.createElement('div');
    tableContainer.id = 'user-list-table-container';
    tableContainer.className = 'table-responsive';

    col.appendChild(tableContainer);
    row.appendChild(col);
    container.appendChild(row);
    main.appendChild(container);

    const userList = await getAllUsers();

    if (!userList || userList.length === 0) {
        tableContainer.innerHTML = '<p class="text-center text-muted">Aucun étudiant disponible pour le moment.</p>';
    } else {
        const table = document.createElement('table');
        table.className = 'table table-hover shadow-sm';

        const thead = document.createElement('thead');
        thead.className = 'table-dark';
        thead.innerHTML = `
      <tr>
        <th scope="col">Nom</th>
        <th scope="col">Prénom</th>
        <th scope="col">Rôle</th>
        <th scope="col">Stage Trouvé</th>
        <th scope="col">Année Académique</th>
      </tr>
    `;

        const tbody = document.createElement('tbody');
        userList.forEach((user) => {
            if (user.role === 'E') { // Ensuring only students are included
                const tr = document.createElement('tr');
                tr.className = user.hasInternship ? 'table-success' : '';

                const tdName = document.createElement('td');
                tdName.className = 'profile-link';
                tdName.textContent = user.lastname;
                tdName.style.cursor = 'pointer';
                tdName.addEventListener('click', () => renderStudentInfo(user));

                const tdFirstName = document.createElement('td');
                tdFirstName.textContent = user.firstname;

                const tdRole = document.createElement('td');
                tdRole.textContent = 'Étudiant';

                const tdInternship = document.createElement('td');
                tdInternship.textContent = user.hasInternship ? 'Oui' : 'Non';

                const tdYear = document.createElement('td');
                tdYear.textContent = user.year.annee === null ? 'N/A' : user.year.annee;

                tr.appendChild(tdName);
                tr.appendChild(tdFirstName);
                tr.appendChild(tdRole);
                tr.appendChild(tdInternship);
                tr.appendChild(tdYear);

                tbody.appendChild(tr);
            }
        });

        table.appendChild(thead);
        table.appendChild(tbody);
        tableContainer.appendChild(table);
    }
};

// Function to render the clicked student's detailed info
function renderStudentInfo(student) {
    clearPage(); // Clear the current view to display the student's info

    const main = document.querySelector('main');
    const studentInfoDiv = document.createElement('div');
    studentInfoDiv.className = 'student-info';

    studentInfoDiv.innerHTML = `
    <h2>${student.lastname} ${student.firstname}</h2>
    <p>Rôle: Étudiant</p>
    <p>Stage Trouvé: ${student.hasInternship ? 'Oui' : 'Non'}</p>
    <p>Année Académique: ${student.year.annee}</p>
    // Add more student details as needed
  `;

    main.appendChild(studentInfoDiv);
}

// Function to clear the main content area (assuming you have a similar function)
function clearPage(mainElement) {
    while (mainElement.firstChild) {
        mainElement.removeChild(mainElement.firstChild);
    }
}

export default profileStudent;
