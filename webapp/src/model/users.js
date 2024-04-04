/* eslint-disable import/no-cycle */
/* eslint-disable no-console */
import {
  getAuthenticatedUser,
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
    const response = await fetch(`http://localhost:8080/auth`, options);

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
      const response = await fetch(`http://localhost:8080/users`, options);

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


async function updateUser(user){
  const id = getAuthenticatedUser();
  const idUser = id.user.id;
  const token = getToken();
  const options = {
    method: 'PUT',
    body: JSON.stringify({
      lastname: user.lastname,
      firstname: user.firstname,
      password: user.newPassword,
      email: user.email,
      phone: user.phone,
      role: user.role
    }),
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  };

  try {
    const response = await fetch(`http://localhost:8080/users/${idUser}`, options);
    console.log("responnse",response);
    if (!response.ok) {
      throw new Error(`Error updating user: ${response.statusText}`);
    }

    // Obtenir la réponse mise à jour de l'utilisateur et préserver le token
    const updatedUserResponse = await response.json();
    const currentUser = getAuthenticatedUser();

    // Mettre à jour les informations de l'utilisateur actuel tout en préservant le token
    const updatedUser = {
      ...currentUser, // Cela préserve toutes les informations existantes
      user: updatedUserResponse // Cela met à jour les informations de l'utilisateur
    };

    // Mettre à jour le stockage local
    setAuthenticatedUser(updatedUser);

  } catch (error) {
    console.error('Error updating user', error);
  }
}

async function checkPassword(user){
  const id = getAuthenticatedUser();
  const idUser = id.user.id;
  const token = getToken();
  const options = {
    method: 'POST',
    body: JSON.stringify({
      Password: user.oldPassword,
    }),
    headers: {
      'Content-Type': 'application/json',
      Authorization: token,
    },
  };

  try {
    const response = await fetch(`http://localhost:8080/users/${idUser}/verify`, options);
    console.log("responnse",response);
    if (!response.ok) {
      return "Les mots de passe ne correspondent pas"
    }
    console.log("response.ok",response.ok);
   if(response.ok === true){
     return true;
    }
      return false;
    
    


  } catch (error) {
    console.error('Error updating user', error);
    return false;
  }
}




export {loginUser,
  refreshUser,
  getStagePresent,
  getAllUsers,
  registerUser,
  updateUser,
  checkPassword,
};
