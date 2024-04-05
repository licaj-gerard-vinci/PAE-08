import {
    getToken,
  } from '../utils/auths';

const getEntreprises = async () => {
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
const insertEntreprises = async (entreprise) => {
  let response = null;
  console.log('Inserting entreprise:', entreprise.name, entreprise.adresse, entreprise.phone, entreprise.email, entreprise.appelation);
  const token = getToken();
  if(token) {
    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: token,
      },
      body: JSON.stringify({
        nom: entreprise.name,
        adresse: entreprise.adresse,
        numTel: entreprise.phone,
        email: entreprise.email,
        appellation: entreprise.appelation,
        city: entreprise.city,
        motivation: '',
      }),
    };
    console.log('Sending request with options:', options); // Ajoutez cette ligne pour afficher les options de la requête
    response = await fetch(`http://localhost:8080/entreprise`, options);
    if (!response.ok) {
      console.log('Received error response:', response); // Ajoutez cette ligne pour afficher la réponse en cas d'erreur
      throw new Error(`Error inserting entreprise: ${response.statusText}`);
    }
  }
  return response;
}

  export {  getEntreprises, insertEntreprises };


