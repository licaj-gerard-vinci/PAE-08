import {
    getToken,
  } from '../utils/auths';


async function getContacts() {
    let contacts = null;
    const token = getToken();
    if(token) {
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      const response = await fetch(`http://localhost:8080/contacts`, options);

    if (!response.ok) {
      return "Aucun contact n'as été passé";
    }

    contacts = await response.json();
  }

    return contacts;
  }

async function getContactsById(idUser) {
  let contacts = null;
  const token = getToken();
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


  async function getContact(contactId){
    let contact = null;
    const token = getToken();
    if(token) {
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      const response = await fetch(`http://localhost:8080/contacts/getOne/${contactId}`, options);

    if (!response.ok) {
      return "Le contact n'a pas été trouvé";
    }
    contact = await response.json();
  }
    return contact;
  }


  async function insertContact(entrepriseObject, userObject, etat) {
    const token = getToken();
    if(token) {
      const options = {
        method: 'POST',
        body: JSON.stringify({
          company : entrepriseObject,
          student: userObject,
          contactStatus: etat
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

        await response.json();

      } catch (error) {
        console.error('Error inserting contact');
      }
    }
  }


  async function updateContact(idContact, entrepriseObject, userObject, etat, refusalReason, meetingPlace, contactVersion) {
    const token = getToken();
    if(token) {

      const options = {
        method: 'PUT',
        body: JSON.stringify({
          id: idContact,
          company: entrepriseObject,
          student: userObject,
          contactStatus: etat,
          refusalReason,
          meetingPlace,
          version: contactVersion
        }),
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      try {
        const response = await fetch(`http://localhost:8080/contacts/update`, options);
        if (!response.ok) {
          throw new Error(`Error updating contact: ${response.statusText}`);
        }
  
        await response.json();
      } catch (error) {
        console.error('Error updating contact');
      }
    }
  }

  async function getContactByCompanyId(idCompany){
    let contact = null;
    const token = getToken();
    if(token) {
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      const response = await fetch(`http://localhost:8080/contacts/${idCompany}/company`, options);

      if (!response.ok) {
        return "Aucun contact n'as été passé";
      }

      contact = await response.json();
    }

    return contact;
  }


  export {
    getContact,
    getContacts,
    insertContact,
    updateContact,
    getContactByCompanyId,
    getContactsById
  }