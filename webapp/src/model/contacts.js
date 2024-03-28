import {
    getAuthenticatedUser,
    getToken,
  } from '../utils/auths';


async function getContacts(){
    let contacts = null;
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
      const response = await fetch(`http://localhost:8080/contacts/${idUser}`, options);

    if (!response.ok) {
      return "Aucun contact n'as été passé";
    }

    contacts = await response.json();
  }

    return contacts;
  }





  async function insertContact(entrepriseObject, userObject, etat) {
    const token = getToken();
    if(token) {
      const options = {
        method: 'POST',
        body: JSON.stringify({
          entreprise : entrepriseObject,
          utilisateur: userObject,
          etatContact: etat
        }),
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };

      try {
        const response = await fetch(`http://localhost:8080/contacts/insert`, options);
        if (!response.ok) {
          throw new Error(`Error inserting contact: ${response.statusText}`);
        }
  
        const result = await response.json();
        console.log(result);
      } catch (error) {
        console.error('Error inserting contact');
      }
    }
  }


  async function updateContact(contactId, entrepriseObject, userObject, etat, refusalReason, meetingPlace, contactVersion) {
    const token = getToken();
    if(token) {
      console.log('entrepriseObject: ', entrepriseObject, ', userObject: ', userObject, ', etat:', etat)
      console.log('refusal reason: ', refusalReason, ', meeting place: ', meetingPlace)
      console.log('contact version: ', contactVersion, ', contact id: ', contactId)
      const options = {
        method: 'PUT',
        body: JSON.stringify({
          id: contactId,
          entreprise: entrepriseObject,
          utilisateur: userObject,
          etatContact: etat,
          raisonRefus: refusalReason,
          lieuxRencontre: meetingPlace,
          version: contactVersion
        }),
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      try {
        const response = await fetch(`http://localhost:8080/contacts/update`, options);
        console.log('response: ', response)
        if (!response.ok) {
          throw new Error(`Error updating contact: ${response.statusText}`);
        }
  
        const result = await response.json();
        console.log(result);
      } catch (error) {
        console.error('Error updating contact');
      }
    }
  }


  export {
    getContacts,
    insertContact,
    updateContact
  }