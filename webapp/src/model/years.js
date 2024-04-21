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

  export default getAllAcademicYears;