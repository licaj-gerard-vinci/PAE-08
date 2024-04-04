import {
    getToken,
  } from '../utils/auths';

async function getEntreprises(){
    let entreprise = null;
    const token = getToken();
    if(token) {
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      const response = await fetch(`http://localhost:8080/entreprise`, options);

      if (!response.ok) {
        const nonPresent = "Aucun stage n'est en cours"

        return nonPresent;
      }
      entreprise = await response.json();
    }

    return entreprise;
  }
  async function insertEntreprises(entreprise){
    let response = null;
    const token = getToken();
    if(token) {
      const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
        body: JSON.stringify(entreprise),
      };
      response = await fetch(`http://localhost:8080/entreprise`, options);
    }
    return response;
  }



export { getEntreprises, insertEntreprises };

