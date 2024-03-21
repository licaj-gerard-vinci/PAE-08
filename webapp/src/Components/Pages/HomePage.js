import { getContactsAllInfo, getUserData, insertContact, updateContact } from "../../model/users";
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
    let  searchResult = entreprises;

    if(!entreprises || entreprises.length === 0) {
      main.innerHTML = `
      <p>Aucune entreprise n'est disponible pour le moment.</p>
      `;
    } else {
      main.innerHTML = `       
      <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="col-10 col-md-8 col-lg-6">
                <div class="input-group mb-3">
                    <input type="text" class="form-control" id="searchInput" placeholder="Rechercher une entreprise" aria-label="Rechercher une entreprise" aria-describedby="button-addon2">
                    <button class="btn btn-primary" type="button" id="button-addon2">Rechercher</button>
                </div>
            </div>
        </div>
    </div>
    
        <div class="container-fluid">
        <div class="row justify-content-center">
          <div class="col-10 col-md-8 col-lg-6">
          ${searchResult.map(entreprise => {
            let button;
            
            if(contacts){
              const contactFound = contacts.find(contact => contact.entreprise === entreprise.id);
              console.log(contactFound);
              if(!contactFound){
                button = `
                <div class="d-flex justify-content-center">
                  <button type='button' class='btn btn-primary' id='initiatedButton${entreprise.id}'>Contacter l'entreprise</button>
                </div>`;
              } else if (contactFound.etatContact === 'initiated') {
                button = `
                <div class="d-flex justify-content-between">
                  <button type='button' class='btn btn-orange' id='stopFollowingButton${entreprise.id}'>ne plus suivre</button>
                  <button type='button' class='btn btn-success' id='takenButton${entreprise.id}'>contact prise</button>
                </div>`;
              } else if (contactFound.etatContact === 'taken'){
                button = `
                <div class="d-flex justify-content-between">
                  <button type='button' class='btn btn-danger' id='refusedButton${entreprise.id}'>refuser contact</button>
                  <button type='button' class='btn btn-orange' id='stopFollowingButton${entreprise.id}'>ne plus suivre</button>
                  <button type='button' class='btn btn-success' id='acceptedButton${entreprise.id}'>contact accepté</button>                
                </div>`;
              } else if(contactFound.etatContact === 'accepted') {
                button = `
                <div class="d-flex justify-content-center">
                  <p>Contact accepté</p>
                </div>`;
              }
              else if (contactFound.etatContact === 'Unsupervised'){
                button = `
                <div class="d-flex justify-content-center">
                  <p>Contact n'est plu suivi</p>
                </div>`;
              }
            } else {
              button = `
              <div class="d-flex justify-content-center">
                <button type='button' class='btn btn-primary' id='contactButton${entreprise.id}'>Contacter l'entreprise</button>
              </div>`;
            }
            return `
              <div class="border rounded p-3 d-flex flex-column justify-content-between" style="border-radius: 50px;">
                  <div>
                      <div class="d-flex justify-content-between">
                          <div class="mr-auto text-left">
                              <h1 class="mb-auto">${entreprise.nom}</h1>
                              <ul class="list-unstyled">
                                  <li>Appellation: ${entreprise.appellation}</li>
                                  <li>Adresse: ${entreprise.adresse}</li>
                                  <li>Téléphone: ${entreprise.numTel}</li>
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
      `;


        const searchButton = document.getElementById('button-addon2');
        searchButton.addEventListener('click', async () => {
          const searchInput = document.getElementById('searchInput').value.trim().toLowerCase();
          console.log('searchInput: ', searchInput);
          if (searchInput !== '') {
             searchResult = entreprises.filter(entreprise =>
                entreprise.nom.toLowerCase().includes(searchInput)
             );
        }
        await renderHomePage();
        });



      entreprises.forEach(entreprise => {
        const initiatedButton = document.querySelector(`#initiatedButton${entreprise.id}`);
        const takenButton = document.querySelector(`#takenButton${entreprise.id}`);
        const acceptedButton = document.querySelector(`#acceptedButton${entreprise.id}`);
        const stopFollowingButton = document.querySelector(`#stopFollowingButton${entreprise.id}`);

        if (initiatedButton) {
          console.log('initiatedButton: ', initiatedButton)
          initiatedButton.addEventListener('click', async () => {
            // to make sure the insertion isn't done twice
            initiatedButton.disabled = true;
            console.log('before insert informations: entrepriseId: ', entreprise.id, ', userId: ', user.id)
            await insertContact(entreprise.id, user.id, "initiated");
            console.log('after insert')
            await renderHomePage();
            initiatedButton.disabled = false;
          });
        }
        if (takenButton) {
          console.log('takenButton: ', takenButton)
          takenButton.addEventListener('click', async () => {
            // to make sure the insertion isn't done twice
            takenButton.disabled = true;
            console.log('before update informations: entrepriseId: ', entreprise.id, ', userId: ', user.id)
            await updateContact(entreprise.id, user.id, "taken");
            console.log('after update')
            await renderHomePage();
            takenButton.disabled = false;
          });
        }

        if (stopFollowingButton) {
          console.log('stopFollowingButton: ', stopFollowingButton)
          stopFollowingButton.addEventListener('click', async () => {
            // to make sure the insertion isn't done twice
            stopFollowingButton.disabled = true;
            console.log('before update informations: entrepriseId: ', entreprise.id, ', userId: ', user.id)
            await updateContact(entreprise.id, user.id, "Unsupervised");
            console.log('after update')
            await renderHomePage();
            takenButton.disabled = false;
          });
        }
        if (acceptedButton) {
          console.log('acceptedButton: ', acceptedButton)
          acceptedButton.addEventListener('click', async () => {
            // to make sure the insertion isn't done twice
            acceptedButton.disabled = true;
            console.log('before update informations: entrepriseId: ', entreprise.id, ', userId: ', user.id)
            await updateContact(entreprise.id, user.id, "accepted");
            console.log('after update')
            await renderHomePage();
            acceptedButton.disabled = false;
          });
        }
      });

    }
  } else {
    console.log("Unknown user role");
  }
}



export default HomePage;
