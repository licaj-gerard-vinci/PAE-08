import {
    getToken,
    getAuthenticatedUser
  } from '../utils/auths';

  async function getStagePresent(){
    let stagePresent = null;
    const token = getToken();
    const id = getAuthenticatedUser();
    const idUser = id.user.id;
    if(token) {
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      const response = await fetch(`http://localhost:8080/stages/${idUser}`, options);
  
      if (!response.ok) {
        const nonPresent = "Aucun stage n'est en cours"
  
        return nonPresent;
      }
      stagePresent = await response.json();
    }
  
    return stagePresent;
  }

async function insertInternship(managerId, student, contactObject, company, topic, signatureDate) {
    let internship = null;
    const token = getToken();
    if(token) {
      const options = {
        method: 'POST',
        body: JSON.stringify({
            idResponsable: managerId,
            etudiant: student,
            contact: contactObject,
            entreprise: company,
            sujet: topic,
            dateSignature: signatureDate
          }),
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      const response = await fetch(`http://localhost:8080/contacts/getOne/${contactObject.id}`, options);

    if (!response.ok) {
      return "Le contact n'a pas été trouvé";
    }
    internship = await response.json();
  }
    return internship;
  }

  export {
    getStagePresent,
    insertInternship
  }