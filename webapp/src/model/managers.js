import {
    getToken
  } from '../utils/auths';

async function getManagers(companyId) {
    let managers = null;
    console.log('companyId in getManagers: ', companyId)
    const token = getToken();
    if(token) {
      const options = {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
      };
      const response = await fetch(`http://localhost:8080/managers/${companyId}`, options);
      console.log('response: ', response)

      if (!response.ok) {
        return "Aucun responsable n'as été passé";
      }

      managers = await response.json();
    }

    return managers;
}

async function addManager(manager) {
  const token = getToken();
  if(token) {
    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: token,
      },
      body: JSON.stringify(manager),
    };
    const response = await fetch('http://localhost:8080/managers/insert', options);
    if (!response.ok) {
      return null;
    }
  }
  return manager;
}

export { getManagers,
  addManager }