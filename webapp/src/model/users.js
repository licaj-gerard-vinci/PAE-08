import {
  setAuthenticatedUser,
  getToken,
  clearAuthenticatedUser
} from '../utils/auths';

import Navigate from '../Components/Router/Navigate';

  async function loginUser(email, password) {

    const options = {
      method: 'POST',
      body: JSON.stringify({
        email,
        password,
      }),
      headers: {
        'Content-Type': 'application/json',
      },
    };


    const response = await fetch(`http://localhost:8080/auth/login`, options);

    if (!response.ok) throw new Error('Invalid username or password');

    const authenticatedUser = await response.json();



    setAuthenticatedUser(authenticatedUser);
    Navigate('/');
  }

  async function refreshUser(){
    let authenticatedUser = null;
    const token = getToken();
    if(token) {
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      const response = await fetch(`http://localhost:8080/user`, options);

      if (!response.ok) {
        clearAuthenticatedUser();
      }
      authenticatedUser = await response.json();
    }

    return authenticatedUser;
  }

  async function getStagePresent(){
    let stagePresent = null;
    const token = getToken();
    if(token) {
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      const response = await fetch(`http://localhost:8080/stage`, options);

      if (!response.ok) {
        const nonPresent = "Aucun stage n'est en cours"

        return nonPresent;
      }
      stagePresent = await response.json();
    }

    return stagePresent;
  }

  async function getContacts(){
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
      const response = await fetch(`http://localhost:8080/auth/contact`, options);

    if (!response.ok) {
      return "Aucun contact n'as été passé";
    }
    contacts = await response.json();
  }

    return contacts;
  }


  async function getUserData() {
    let user = null;
    const token = getToken();

    if (token) {
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };

      try {
        const response = await fetch(`http://localhost:8080/user`, options);

        if (!response.ok) {
          throw new Error(`Error fetching user data: ${response.statusText}`);
        }

        user = await response.json();
      } catch (error) {
        console.error('Error fetching user data');
      }
    }

    return user;
  }

  async function getContactsAllInfo(){
    let contacts = [];
    const token = getToken();
    if(token) {
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };

      try {
        const response = await fetch(`http://localhost:8080/contact`, options);
  
        if (!response.ok) {
          throw new Error(`Error fetching contacts data: ${response.statusText}`);
        }
  
        contacts = await response.json();
      } catch (error) {
        console.error('Error fetching contacts data');
      }
    }

    return contacts;
  }

  async function insertContact(entrepriseId, userId, etat) {
    const token = getToken();
    if(token) {
      console.log('entrepriseId: ', entrepriseId, ', userId: ', userId, ', etat:', etat)
      const options = {
        method: 'POST',
        body: JSON.stringify({
          entreprise: entrepriseId,
          utilisateur: userId,
          etatContact: etat
        }),
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };

      try {
        const response = await fetch(`http://localhost:8080/contact/insert`, options);
        console.log('response: ', response)
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

  async function updateContact(entrepriseId, userId, etat) {
    const token = getToken();
    if(token) {
      console.log('entrepriseId: ', entrepriseId, ', userId: ', userId, ', etat:', etat)
      const options = {
        method: 'PUT',
        body: JSON.stringify({
          entreprise: entrepriseId,
          utilisateur: userId,
          etatContact: etat
        }),
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      try {
        const response = await fetch(`http://localhost:8080/contact/`, options);
        console.log('response: ', response)
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

  async function updateContactRefused(entrepriseId, userId, etat, refusalReason) {
    const token = getToken();
    if(token) {
      console.log('entrepriseId: ', entrepriseId, ', userId: ', userId, ', etat:', etat, ', refusal reason: ', refusalReason)
      const options = {
        method: 'PUT',
        body: JSON.stringify({
          entreprise: entrepriseId,
          utilisateur: userId,
          etatContact: etat,
          raisonRefus: refusalReason
        }),
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      try {
        const response = await fetch(`http://localhost:8080/contact/`, options);
        console.log('response: ', response)
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

  async function updateContactTaken(entrepriseId, userId, etat, meetingPlace) {
    const token = getToken();
    if(token) {
      console.log('entrepriseId: ', entrepriseId, ', userId: ', userId, ', etat:', etat, ', meeting place: ', meetingPlace)
      const options = {
        method: 'PUT',
        body: JSON.stringify({
          entreprise: entrepriseId,
          utilisateur: userId,
          etatContact: etat,
          lieuxRencontre: meetingPlace
        }),
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      try {
        const response = await fetch(`http://localhost:8080/contact/`, options);
        console.log('response: ', response)
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

  async function getAllUsers() {
    let users = null;
    const token = getToken();

    if (token) {
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };

      try {
        const response = await fetch(`http://localhost:8080/auth/users`, options);

        if (!response.ok) {
          throw new Error(`Error fetching users: ${response.statusText}`);
        }

        users = await response.json();
      } catch (error) {
        console.error('Error fetching users');
      }
    }

    return users;
  }

async function registerUser(user){
  const options = {
    method: 'POST',
    body: JSON.stringify({
      lastname: user.lastname,
      firstname: user.firstname,
      password: user.password,
      email: user.email,
      phone: user.phone,
      role: user.role
    }),
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = await fetch(`http://localhost:8080/auth/register`, options);

  if (!response.ok) throw new Error('Error');

  const authenticatedUser = await response.json();

  setAuthenticatedUser(authenticatedUser);
}


export {loginUser,
  refreshUser,
  getStagePresent,
  getContacts,
  getUserData,
  getAllUsers,
  registerUser,
  getContactsAllInfo,
  insertContact,
  updateContact,
  updateContactRefused,
  updateContactTaken
};
