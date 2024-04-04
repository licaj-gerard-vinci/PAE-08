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

async function getEntrepriseById(id){
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
    const response = await fetch(`http://localhost:8080/entreprise/${id}`, options);

    if (!response.ok) {
      const nonPresent = "Aucun stage n'est en cours"

      return nonPresent;
    }
    entreprise = await response.json();
  }

  return entreprise;
}

export { getEntrepriseById, getEntreprises };

