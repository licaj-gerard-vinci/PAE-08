import Chart from "chart.js/auto";
import {clearPage} from "../../utils/render";
import {getEntreprises} from "../../model/entreprises";
import Navigate from "../Router/Navigate";
import {getAllUsers} from "../../model/users";

const Dashboard = async () => {
    clearPage();
    const main = document.querySelector('main');
    main.innerHTML = `
        <h1 class="centered-title title-with-line">Tableau de bord</h1>
    <div id="chart-container"></div>
    <div id="companies-container"></div>
    `;
    renderStatistics();
    await renderCompaniesList();
}

async function renderCompaniesList() {
    const companiesContainer = document.querySelector('#companies-container');

    companiesContainer.innerHTML = `
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
        document.getElementById('company-list-table-container').innerHTML = `
    <table class="table table-hover shadow-sm">
        <thead class="table-dark">
            <tr>
                <th scope="col">Nom</th>
                <th scope="col">Numéro de téléphone</th>
                <th scope="col">Nombre d'étudiant</th>
                <th scope="col">Est blacklisté</th>
            </tr>
        </thead>
        <tbody>
        ${companies.map(company => `
            <tr data-id="${company.id}" class="company-row">
              <td>${company.nom}</td>
              <td>${company.numTel}</td>
              <td>${company.nbEtudiant}</td>
              <td>${company.blacklist ? 'Oui' : 'Non'}</td>
            </tr>
          `).join('')}
      </tbody>
    </table>`;


        document.querySelectorAll('.company-row').forEach(row => {
            row.addEventListener('click', (event) => {
                const companyId = event.currentTarget.getAttribute('data-id');
                console.log(companyId);
                Navigate('/company', companyId);
            });
        });
    }
}


async function renderStatistics() {
    const totalStudents = await getAllUsers();
    const chartContainer = document.querySelector('#chart-container');
    chartContainer.innerHTML =
        `<h3 class="chart-title centered-title">Internship Statistics</h3>
        <div><canvas id="myChart"></canvas></div>`
    ;
    const canvas = document.getElementById('myChart');
    new Chart(canvas, {
        type: 'pie',
        data: {
            labels: ['With Internships', 'Without Internships'],
            datasets: [{
                label: 'Number of students',
                data: [totalStudents.filter(
                    student => student.role === 'E' && student.hasInternship
                        === true).length, totalStudents.filter(
                    student => student.role === 'E' && student.hasInternship
                        === false).length],
                backgroundColor: ['#007bff', '#343a40'],
                borderColor: ['#fff', '#fff'],
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            title: {
                display: true,
                text: 'Internship Statistics',
                fontColor: '#007bff',
                fontSize: 20
            },
            legend: {
                display: true,
                labels: {
                    fontColor: '#007bff',
                    fontSize: 16
                }
            },
            tooltips: {
                titleFontSize: 16,
                bodyFontSize: 14,
                backgroundColor: '#343a40',
                titleFontColor: '#fff',
                bodyFontColor: '#fff',
                borderColor: '#007bff',
                borderWidth: 1
            }
        }
    });
}
export default Dashboard;