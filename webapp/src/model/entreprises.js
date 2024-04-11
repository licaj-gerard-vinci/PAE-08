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

async function blackListEntreprise(entreprise, raisonBlacklist){
  let entrepriseToreturn = null;
  const token = getToken();
  const idEntreprise = entreprise.id;
  if(token) {
    const options = {
      method: 'PUT',
      body: JSON.stringify({
        id: idEntreprise,
        motivation: raisonBlacklist
      }),
      headers: {
        'Content-Type': 'application/json',
        Authorization: token,
      },
    };
    const response = await fetch(`http://localhost:8080/entreprise/${idEntreprise}/blacklist`, options);

    if (!response.ok) {
      const nonPresent = "sah pour l'instant jsp"

      return nonPresent;
    }
    entrepriseToreturn = await response.json();
  }

  return entrepriseToreturn;
}

export { getEntrepriseById, getEntreprises, blackListEntreprise,insertEntreprises };

