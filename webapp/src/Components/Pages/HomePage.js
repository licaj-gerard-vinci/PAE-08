import { getContactsAllInfo, getUserData, insertContact } from "../../model/users";
import getEntreprises from "../../model/entreprises";
import logo from '../../img/HELOGO.png';

const HomePage = async () => {
  await renderHomePage();
};

async function renderHomePage(){
  const main = document.querySelector('main');
  const user = await getUserData();
  console.log(user);

  if(user.role === "A" || user.role === "P"){
    main.innerHTML = `
    <div style="display: flex; justify-content: center; align-items: center; height: 100vh;">
      <h1 style="font-size: 3em;">Welcome to the Home Page for professors and administratifs only!</h1>
    </div>
  `;


  } else if (user.role === "E") {
    const entreprises = await getEntreprises();
    const contacts = await getContactsAllInfo();

    if(!entreprises || entreprises.length === 0) {
      main.innerHTML = `
      <p>Aucune entreprise n'est disponible pour le moment.</p>
      `;
    } else {
    main.innerHTML = `
      <div class="container-fluid">
      <div class="row justify-content-center">
        <div class="col-10 col-md-8 col-lg-6">
        ${entreprises.map(entreprise => {
          const contactFound = contacts.find(contact => contact.entreprise === entreprise.id);
          let button;
          console.log(contactFound);
          if (contactFound) {
            button = `
            <div class="d-flex justify-content-center">
              <p>contact deja prise<p>
            </div>`;
          } else {
            button = `
            <div class="d-flex justify-content-center">
              <button type='button' class='btn btn-primary' onclick='${insertContact(entreprise.id, user.id, 'prise')}'>Contacter l'entreprise</button>
            </div>`;
          }
          return `
            <div class="border rounded p-3 d-flex flex-column justify-content-between" style="border-radius: 50px;">
                <div>
                    <div class="d-flex justify-content-between">
                        <div class="mr-auto text-left">
                            <h1 class="mb-auto">${entreprise.nom}</h1>
                            <ul class="small">
                                <li>${entreprise.appellation}</li>
                                <li>${entreprise.adresse}</li>
                                <li>${entreprise.numTel}</li>
                            </ul>
                        </div>
                        <img src="${logo}" alt="Logo" class="ml-3" style="width: 100px; height: auto;">
                    </div>
                </div>
                ${button}
            </div>
          `;
        }).join('')}
        </div> 
      </div> 
    </div>

  `;}
  } else {
    console.log("Unknown user role");
  }
  
}

export default HomePage;
