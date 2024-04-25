/* eslint-disable import/no-extraneous-dependencies */
import Chart from "chart.js/auto";
import {clearPage} from "../../utils/render";
import {getEntreprises} from "../../model/entreprises";
import Navigate from "../Router/Navigate";
import {getAllAcademicYears} from "../../model/years";
import {getContacts} from "../../model/contacts";
import {getAllInternships} from "../../model/internships";

const Dashboard = async () => {
    
    clearPage();
    const main = document.querySelector('main');
    const academicYears = await getAllAcademicYears();
    const academicYearOptions = academicYears.map(year => `<option value="${year.year}">${year.year}</option>`).join('\n');

    main.innerHTML = `
    <h1 class="centered-title">Tableau de bord</h1>
    <div class="filter-container d-flex align-items-center mb-3" style="display: flex; justify-content: center;">
        <label for="academicYearFilter" class="me-2">Sélectionner l'année académique:</label>
        <select id="academicYearFilter" class="form-select" style="width: auto;">
            <option value="">Toutes les années</option>
            ${academicYearOptions}
        </select>
    </div>
    <div id="chart-container"></div>
    <div id="company-list-table-container"></div>
`;

// Add event listener to the filter
document.getElementById('academicYearFilter').addEventListener('change', async (event) => {
    const selectedYear = event.target.value;
    await renderStatistics(selectedYear);
    await renderCompaniesList(selectedYear);
});

await renderStatistics();
await renderCompaniesList();
}

async function renderCompaniesList(selectedYear = '') {
    const companies = await getEntreprises();
    const internships = await getAllInternships();
    renderCompanies(companies, internships, selectedYear);
}

const sortOrder = {
    nom: true,
    numTel: true,
    studentCount: true,
    blackListed: true
};

async function sortAndRenderCompanies(property, companies, internships, selectedYear) {
    let updatedCompanies = [...companies];

    // If sorting by student count, add a studentCount property to each company
    if (property === 'studentCount') {
        updatedCompanies = companies.map(company => {
            const companyInternships = internships.filter(contact => contact.company.id === company.id);
            return {...company, studentCount: companyInternships.length};
        });
    }

    // Sort the companies
    updatedCompanies.sort((a, b) => {
        if (a[property] < b[property]) return sortOrder[property] ? -1 : 1;
        if (a[property] > b[property]) return sortOrder[property] ? 1 : -1;
        return 0;
    });

    // Invert the sort order for the next time
    sortOrder[property] = !sortOrder[property];

    // Render the sorted companies
    await renderCompanies(updatedCompanies, internships, selectedYear);
}

function renderCompanies(companies, internships, selectedYear = '') {
    if (!companies || companies.length === 0) {
        document.getElementById('company-list-table-container').innerHTML = `
        <div style="max-width: 80%; margin: auto;">
            <p class="text-center text-muted">Aucune entreprise n'est disponible pour le moment.</p>
        </div>
    `;
    } else {
        document.getElementById('company-list-table-container').innerHTML = `
    <div style="max-width: 80%; margin: auto;">
    <table class="table table-hover shadow-sm rounded">
        <thead class="table-dark">
            <tr>
               <th scope="col" id="sortByName" class="text-center cursor-pointer">Nom ${sortOrder.nom ? '↓' : '↑'}</th>
                <th scope="col" id="sortByPhone" class="text-center cursor-pointer">Numéro de téléphone ${sortOrder.numTel ? '↓' : '↑'}</th>
                <th scope="col" id="sortByStudentCount" class="text-center cursor-pointer">Nombre d'étudiant ${sortOrder.studentCount ? '↓' : '↑'}</th>
                <th scope="col" id="sortByBlacklisted" class="text-center cursor-pointer">Est blacklisté ${sortOrder.blackListed ? '↓' : '↑'}</th>
            </tr>
        </thead>
        <tbody>
        ${companies.map(company => {
            let companyInternships;
            if (selectedYear) {
                companyInternships = internships.filter(internship =>
                    internship.company.id === company.id && internship.year.year === selectedYear);

            } else {
                companyInternships = internships.filter(internship =>
                    internship.company.id === company.id);
            }
            const studentCount = companyInternships.length;
            return `
            <tr data-id="${company.id}" class="company-row cursor-pointer text-center">
              <td class="text-center">${company.name} </br> ${company.designation}</td>
              <td class="text-center">${company.phone}</td>
              <td class="text-center">${studentCount}</td>
              <td class="text-center">${company.blackListed ? 'Oui' : 'Non'}</td>
            </tr>
          `;
        }).join('')}
      </tbody>
    </table>
    </div>`;

        document.querySelectorAll('.company-row').forEach(row => {
            row.addEventListener('click', (event) => {
                const companyId = event.currentTarget.getAttribute('data-id');
                Navigate('/company', companyId);
            });
        });

        // Add event listeners to the table headers
        // Add event listeners to the table headers
        document.getElementById('sortByName').addEventListener('click', () => sortAndRenderCompanies('name', companies, internships, selectedYear)); // Changed 'nom' to 'name'
        document.getElementById('sortByPhone').addEventListener('click', () => sortAndRenderCompanies('phone', companies, internships, selectedYear)); // Changed 'numTel' to 'phone'
        document.getElementById('sortByStudentCount').addEventListener('click', () => sortAndRenderCompanies('studentCount', companies, internships, selectedYear));
        document.getElementById('sortByBlacklisted').addEventListener('click', () => sortAndRenderCompanies('blackListed', companies, internships, selectedYear));
    }
}


async function renderStatistics(selectedYear = '') {
    let totalStudentsByYear = await getContacts();
    // Filter out unique contacts
    const totalStudentsAllYears = totalStudentsByYear.filter(
        (contact, index, self) =>
            index === self.findIndex((t) => (
                t.student.id === contact.student.id
            ))
    );

    let studentsWithInternship;
    let studentsWithoutInternship;
    let totalStudents;
    const chartContainer = document.querySelector('#chart-container');

    if (!selectedYear) {
        studentsWithInternship = totalStudentsAllYears.filter(contact => contact.student.hasInternship === true).length;
        studentsWithoutInternship = totalStudentsAllYears.filter(contact => contact.student.hasInternship === false).length;
        totalStudents = studentsWithoutInternship + studentsWithInternship
    } else {
        totalStudentsByYear = totalStudentsByYear.filter(contact => contact.year.year === selectedYear);
        studentsWithInternship = totalStudentsByYear.filter(contact => contact.contactStatus === 'accepté').length;
        studentsWithoutInternship = totalStudentsByYear.length - studentsWithInternship;
        totalStudents = studentsWithoutInternship + studentsWithInternship;
    }

    chartContainer.innerHTML =
        `<div class="flex-container-stats">
        <p class="text-center">Année académique: ${selectedYear || 'Toutes les années'}</p>
        <p class="text-center">Nombre total d'étudiants: ${totalStudents}</p>
    </div>`
    ;
    if (totalStudents !== 0) {
        chartContainer.innerHTML += `<div class="p-3 mb-2 bg-white rounded shadow" style="animation: fadeInAnimation 1s;"><canvas id="myChart"></canvas></div>`
        const canvas = document.getElementById('myChart');
        new Chart(canvas, {
            type: 'pie',
            data: {
                labels: ['Avec Stage', 'Sans stage'],
                datasets: [{
                    label: 'Nombre d\'étudiants',
                    data: [studentsWithInternship, studentsWithoutInternship],
                    backgroundColor: ['#00609D', '#343a40'],
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
}
export default Dashboard;