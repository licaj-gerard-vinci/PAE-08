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
            let button;
            if(contacts){
              const contactFound = contacts.find(contact => contact.entreprise === entreprise.id);
              console.log(contactFound);
              if(!contactFound){
                button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <button type='button' class='btn btn-primary' id='initiatedButton${entreprise.id}'>Contacter l'entreprise</button>
                  </div>
                  <div class="col"></div>
                </div>`;
              } else if (contactFound.etatContact === 'initiated') {
                button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <button type='button' class='btn btn-orange' id='stopFollowingButton${entreprise.id}'>ne plus suivre</button>
                  </div>
                  <div class="col d-flex justify-content-end">
                    <button type='button' class='btn btn-success' id='takenButton${entreprise.id}'>contact pris</button>
                  </div>
                </div>`;
              } else if (contactFound.etatContact === 'taken'){
                button = `
                <div class="row">
                  <div class="col d-flex justify-content-start">
                    <button type='button' class='btn btn-danger' id='refusedButton${entreprise.id}'>contact refusé</button>
                  </div>
                  <div class="col d-flex justify-content-center">
                    <button type='button' class='btn btn-orange' id='stopFollowingButton${entreprise.id}'>ne plus suivre</button>
                  </div>
                  <div class="col d-flex justify-content-end">
                    <button type='button' class='btn btn-success' id='acceptedButton${entreprise.id}'>stage accepté</button>
                  </div>
                </div>
                <div id='form${entreprise.id}' style='display: none;'>
                  <input type='text' id='textInput${entreprise.id}' placeholder='Entrez la raison du refus'>
                  <button type='button' id='saveButton${entreprise.id}'>Save</button>
                </div>`;
              } else if(contactFound.etatContact === 'accepted') {
                button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <p>Contact accepté</p>
                  </div>
                  <div class="col"></div>
                </div>`;
              } else if(contactFound.etatContact === 'refused'){
                button = `
                <div class="row">
                  <div class="col"></div>
                  <div class="col d-flex justify-content-center">
                    <p>Contact refusé</p>
                  </div>
                  <div class="col"></div>
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
              <div class="row">
                <div class="col"></div>
                <div class="col d-flex justify-content-center">
                  <button type='button' class='btn btn-primary' id='contactButton${entreprise.id}'>Contacter l'entreprise</button>
                </div>
                <div class="col"></div>
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

      entreprises.forEach(entreprise => {
        const initiatedButton = document.querySelector(`#initiatedButton${entreprise.id}`);
        const takenButton = document.querySelector(`#takenButton${entreprise.id}`);
        const acceptedButton = document.querySelector(`#acceptedButton${entreprise.id}`);
        const refusedButton = document.querySelector(`#refusedButton${entreprise.id}`);
        const stopFollowingButton = document.querySelector(`#stopFollowingButton${entreprise.id}`);


        if (initiatedButton) {
          console.log('initiatedButton: ', initiatedButton)
          initiatedButton.addEventListener('click', async () => {
            // to make sure the insertion isn't done twice
            initiatedButton.disabled = true;
            console.log('before insert informations: entrepriseId: ', entreprise.id, ', userId: ', user.id)
            await insertContact(entreprise.id, user.id, "initiated", null);
            console.log('after insert')
            await renderHomePage();
            initiatedButton.disabled = false;
          });
        }

        if (takenButton) {
          console.log('takenButton: ', takenButton)
          takenButton.addEventListener('click', async () => {
            takenButton.disabled = true;
            console.log('before update informations: entrepriseId: ', entreprise.id, ', userId: ', user.id)
            await updateContact(entreprise.id, user.id, "taken", null);
            console.log('after update')
            await renderHomePage();
            takenButton.disabled = false;
          });
        }

        if (acceptedButton) {
          console.log('acceptedButton: ', acceptedButton)
          acceptedButton.addEventListener('click', async () => {
            acceptedButton.disabled = true;
            console.log('before update informations: entrepriseId: ', entreprise.id, ', userId: ', user.id)
            await updateContact(entreprise.id, user.id, "accepted", null);
            console.log('after update')
            await renderHomePage();
            acceptedButton.disabled = false;
          });
        }

        if (stopFollowingButton) {
          console.log('stopFollowingButton: ', stopFollowingButton)
          stopFollowingButton.addEventListener('click', async () => {
            // to make sure the insertion isn't done twice
            stopFollowingButton.disabled = true;
            console.log('before update informations: entrepriseId: ', entreprise.id, ', userId: ', user.id)
            await updateContact(entreprise.id, user.id, "Unsupervised", null);
            console.log('after update')
            await renderHomePage();
            stopFollowingButton.disabled = false;
          });
        }

        if (refusedButton) {
          console.log('refusedButton: ', refusedButton)
          refusedButton.addEventListener('click', () => {
            document.querySelector(`#form${entreprise.id}`).style.display = 'block';
          });
        }
        
        if (document.querySelector(`#saveButton${entreprise.id}`)) {
          document.querySelector(`#saveButton${entreprise.id}`).addEventListener('click', async () => {
            const textInputValue = document.querySelector(`#textInput${entreprise.id}`).value;
            if(textInputValue){
              await updateContact(entreprise.id, user.id, "refused", textInputValue);
              await renderHomePage();
            }
          });
        }
      });
      
    }
  } else {
    console.log("Unknown user role");
  }
}

export default HomePage;
