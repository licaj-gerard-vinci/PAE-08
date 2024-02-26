import { setAuthenticatedUser } from '../utils/auths';

import Navigate from '../Components/Router/Navigate';

import Navbar from '../Components/Navbar/Navbar';


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
  
    Navbar();
  
    Navigate('/');
  }

  export default loginUser;
