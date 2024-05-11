import {
    getToken,
  } from '../utils/auths';

  async function getInternshipPresent(idUser) {
    let internshipPresent = null;
    const token = getToken();
    if(token) {
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      const response = await fetch(`http://localhost:8080/internships/${idUser}`, options);
  
      if (!response.ok) {
        const nonPresent = "Aucun stage n'est en cours"
  
        return nonPresent;
      }
      internshipPresent = await response.json();
    }
  
    return internshipPresent;
  }

  async function insertInternship(managerId, student, contactObject, company, topic, signatureDate) {
    const token = getToken();
    if(token) {

      const options = {
        method: 'POST',
        body: JSON.stringify({
            idManager: managerId,
            student,
            contact: contactObject,
            company,
            topic,
            signatureDate
          }),
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      
      try {
        const response = await fetch(`http://localhost:8080/internships/insert`, options);
        if (!response.ok) {
          throw new Error(`Error inserting contact: ${response.statusText}`);
        }
      } catch (error) {
        console.error('Error inserting internship');
      }
    }
  }

  async function getAllInternships() {
    let internships = null;
    const token = getToken();
    if(token) {
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      const response = await fetch('http://localhost:8080/internships', options);

      if (!response.ok) {
        return internships;
      }
      internships = await response.json();
    }
    return internships;
  }

async function updateInternship(internship) {
  const token = getToken();

  if(token) {
    const options = {
      method: 'PUT',
      body: JSON.stringify(internship),
      headers: {
        'Content-Type': 'application/json',
        Authorization: token,
      },
    };
    const response = await fetch(`http://localhost:8080/internships/update/${internship.id}`, options);
    if (!response.ok) {
      throw new Error(`Error updating internship: ${response.statusText}`);
    }
  }
}


  export {
    getInternshipPresent,
    insertInternship,
    getAllInternships,
    updateInternship,
  }