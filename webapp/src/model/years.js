import {
    getToken,
  } from '../utils/auths';

async function getAllAcademicYears(){
    let years = null;
    const token = getToken();
    if(token) {
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      const response = await fetch(`http://localhost:8080/years`, options);

      if (!response.ok) {
        const nonPresent = "Aucun stage n'est en cours"

        return nonPresent;
      }
      years = await response.json();
    }

    return years;
  }

  async function getCurrentAcademicYear(){
    let currentYear = null;
    const token = getToken();
    if(token) {
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      const response = await fetch(`http://localhost:8080/years/current`, options);

      if (!response.ok) {
        const nonPresent = "Aucune année académique actuelle n'est disponible"

        return nonPresent;
      }
      currentYear = await response.text(); // Supposons que l'API renvoie une chaîne de texte
    }

    return currentYear;
  }


  export {
    getAllAcademicYears,
    getCurrentAcademicYear
  };