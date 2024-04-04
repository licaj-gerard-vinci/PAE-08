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

export default getManagers;