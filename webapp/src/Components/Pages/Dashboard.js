import {clearPage} from "../../utils/render";
import {getEntreprises} from "../../model/entreprises";
import Navigate from "../Router/Navigate";

const Dashboard = () => {
    clearPage()
    const main = document.querySelector('main');
    main.innerHTML = `
        <h1>Home Page Admin</h1>
        <p>Welcome to the admin dashboard</p>
    `;
    renderCompaniesList();
}

async function renderCompaniesList() {
    const main = document.querySelector('main');

    main.innerHTML = `
    <div class="container my-5">
        <h1 class="text-center mb-3">Liste des entreprises</h1>
        <div class="row">
            <div class="col-12">
                <div id="company-list-table-container" class="table-responsive">
                    <!-- Table will be inserted here -->
                </div>
            </div>
        </div>
    </div>`;

    const companies = await getEntreprises();

    if (!companies || companies.length === 0) {
        document.getElementById('company-list-table-container').innerHTML = `
        <p class="text-center text-muted">Aucune entreprise n'est disponible pour le moment.</p>
    `;
    } else {
        const tableHtml = `
    <table class="table table-hover shadow-sm">
        <thead class="table-dark">
            <tr>
                <th scope="col">Nom</th>
                <th scope="col">Adresse</th>
                <th scope="col">Numéro de téléphone</th>
                <th scope="col">Ville</th>
            </tr>
        </thead>
        <tbody>
        ${companies.map(company => `
            <tr data-id="${company.id}" class="company-row">
              <td>${company.nom}</td>
              <td>${company.adresse}</td>
              <td>${company.numTel}</td>
              <td>${company.city}</td>
            </tr>
          `).join('')}
      </tbody>
    </table>`;

        document.getElementById('company-list-table-container').innerHTML = tableHtml;


        document.querySelectorAll('.company-row').forEach(row => {
            row.addEventListener('click', (event) => {
                const companyId = event.currentTarget.getAttribute('data-id');
                Navigate('/company', { id: companyId });
            });
        });
    }
}
export default Dashboard;