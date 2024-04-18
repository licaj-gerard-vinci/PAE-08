/* eslint-disable import/no-extraneous-dependencies */
import Chart from "chart.js/auto";
import {clearPage} from "../../utils/render";
import {getEntreprises} from "../../model/entreprises";
import Navigate from "../Router/Navigate";
import {getAllUsers} from "../../model/users";
import {getAllInternships} from "../../model/internships";

const Dashboard = async () => {
    clearPage();
    const main = document.querySelector('main');
    main.innerHTML = `
        <h1 class="centered-title title-with-line">Tableau de bord</h1>
    <div id="chart-container"></div>
    <div id="companies-container"></div>
    `;
    await renderStatistics();
    await renderCompaniesList();
}

async function renderCompaniesList() {
    const companiesContainer = document.querySelector('#companies-container');

    companiesContainer.innerHTML = `
    <div class="container my-5">
        <h1 class="text-center mb-3">Liste des entreprises</h1>
        <div class="row">
            <div class="col-12">
                <div class="filter-container d-flex align-items-center mb-3">
                    <label for="academicYearFilter" class="me-2">Filtrer par année académique:</label>
                    <select id="academicYearFilter" class="form-select" style="width: auto;">
                        <option value="">Toutes les années</option>
                        <option value="2023-2024">2023-2024</option>
                        <option value="2024-2025">2024-2025</option>
                    </select>
                </div>
                <div id="company-list-table-container" class="table-responsive">
                    <!-- Table will be inserted here -->
                </div>
            </div>
        </div>
    </div>`;

    const companies = await getEntreprises();
    const internships = await getAllInternships();

    // Add event listener to the filter
    document.getElementById('academicYearFilter').addEventListener('change', (event) => {
        const selectedYear = event.target.value;
        renderCompanies(companies, internships, selectedYear);
    });

    renderCompanies(companies, internships);
}

function renderCompanies(companies, internships, selectedYear = '') {
    if (!companies || companies.length === 0) {
        document.getElementById('company-list-table-container').innerHTML = `
        <p class="text-center text-muted">Aucune entreprise n'est disponible pour le moment.</p>
    `;
    } else {
        document.getElementById('company-list-table-container').innerHTML = `
    <table class="table table-hover shadow-sm rounded">
        <thead class="table-dark">
            <tr>
                <th scope="col" class="text-center">Nom</th>
                <th scope="col" class="text-center">Numéro de téléphone</th>
                <th scope="col" class="text-center">Nombre d'étudiant</th>
                <th scope="col" class="text-center">Est blacklisté</th>
            </tr>
        </thead>
        <tbody>
        ${companies.map(company => {
            const companyInternships = internships.filter(internship => internship.entreprise.id === company.id);
            let studentCount;
            if (selectedYear) {
                studentCount = companyInternships.filter(internship => {
                    const date = new Date(internship.dateSignature);
                    const year = date.getFullYear();
                    const month = date.getMonth();
                    let academicYear;
                    if (month >= 9) {
                        academicYear = `${year}-${year + 1}`;
                    } else {
                        academicYear = `${year - 1}-${year}`;
                    }
                    return academicYear === selectedYear;
                }).length;
            } else {
                studentCount = companyInternships.length;
            }
            return `
            <tr data-id="${company.id}" class="company-row cursor-pointer text-center">
              <td class="text-center">${company.nom} </br> ${company.appellation}</td>
              <td class="text-center">${company.numTel}</td>
              <td class="text-center">${studentCount}</td>
              <td class="text-center">${company.blackListed ? 'Oui' : 'Non'}</td>
            </tr>
          `;
        }).join('')}
      </tbody>
    </table>`;

        document.querySelectorAll('.company-row').forEach(row => {
            row.addEventListener('click', (event) => {
                const companyId = event.currentTarget.getAttribute('data-id');
                Navigate('/company', companyId);
            });
        });
    }
}


async function renderStatistics() {
    let totalStudents = await getAllUsers();
    totalStudents = totalStudents.filter(student => student.role === 'E');
    const chartContainer = document.querySelector('#chart-container');

    const currentDate = new Date();
    const currentYear = currentDate.getFullYear();
    const currentMonth = currentDate.getMonth();
    let academicYear;

    if (currentMonth < 9) {
        academicYear = `${currentYear - 1}-${currentYear}`;
    } else {
        academicYear = `${currentYear}-${currentYear + 1}`;
    }

    const internships = await getAllInternships();
    const internshipsThisYear = internships.filter(internship => {
        const date = new Date(internship.dateSignature);
        const year = date.getFullYear();
        const month = date.getMonth();
        let internshipAcademicYear;
        if (month >= 9) {
            internshipAcademicYear = `${year}-${year + 1}`;
        } else {
            internshipAcademicYear = `${year - 1}-${year}`;
        }
        return internshipAcademicYear === academicYear;
    });

    const studentsWithInternship = internshipsThisYear.length;
    const studentsWithoutInternship = totalStudents.length - studentsWithInternship;

    chartContainer.innerHTML =
        `<h3 class="chart-title centered-title">Internship Statistics</h3>
        <p class="text-center">Année académique: ${academicYear}</p>
        <p class="text-center">Nombre total d'étudiants: ${totalStudents.length}</p>
        <div class="p-3 mb-2 bg-white rounded shadow" style="animation: fadeInAnimation 1s;"><canvas id="myChart"></canvas></div>`
    ;

    const canvas = document.getElementById('myChart');
    new Chart(canvas, {
        type: 'pie',
        data: {
            labels: ['Avec Stage', 'Sans stage'],
            datasets: [{
                label: 'Nombre d\'étudiants',
                data: [studentsWithInternship, studentsWithoutInternship],
                backgroundColor: ['#007bff', '#343a40'],
                borderColor: ['#fff', '#fff'],
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            animation: {
                animateScale: true,
                animateRotate: true
            },
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
                enabled: true,
                mode: 'single',
                callbacks: {
                    label(tooltipItems, data) {
                        return `${data.labels[tooltipItems.index]}: ${data.datasets[0].data[tooltipItems.index]}`;
                    }
                }
            }
        }
    });
}
export default Dashboard;