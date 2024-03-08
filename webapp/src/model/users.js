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
      const response = await fetch(`http://localhost:8080/auth/user`, options);

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
      const response = await fetch(`http://localhost:8080/auth/stage`, options);

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
        const nonPresent = "Aucun contact n'as été passé"
        
        return nonPresent;
      }
      contacts = await response.json();
    }

    return contacts;
  }




  export {loginUser, refreshUser, getStagePresent, getContacts};
