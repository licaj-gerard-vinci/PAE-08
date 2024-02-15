const Login = () => {
    const main = document.querySelector('main');
    main.innerHTML = `
      <div class="login-container">

        <h2>Se connecter</h2>

        <form id="loginForm">
          <label for="email">Email</label>
          <input type="email" id="email" name="email" placeholder="email" required>

          <br>

          <label for="password">Mot de passe</label>
          <input type="password" id="password" name="password" placeholder="mot de passe" required>

          <br>

          <div class="checkbox-container">
          <input type="checkbox" id="rememberMe" name="remember">
          <label for="rememberMe">Se souvenir de moi</label>
        </div>

          <br>

          <button type="submit">Se connecter</button>
        </form>
        <p>Pas de compte ? <a href="#" id="registerLink">Enregistrez-vous !</a></p>
      </div>
    `;
    
    
    const loginForm = document.getElementById('loginForm');
    loginForm.addEventListener('submit', (e) => {
      e.preventDefault();
      const email = document.getElementById('email').value;
      const password = document.getElementById('password').value;
      
      
      console.log('Email:', email);
      console.log('Password:', password);
      
    });
    
    
    const registerLink = document.getElementById('registerLink');
    registerLink.addEventListener('click', (e) => {
      e.preventDefault();
      // envoyé vers la page d'inscription
     
      
    });

    const rememberMeCheckbox = document.getElementById('rememberMe');
  // eslint-disable-next-line no-unused-vars
  rememberMeCheckbox.addEventListener('change', (e) => {
   
    // Lorsque le type click sur 'se souvenir de moi' implementer la methode
  });

  };
  
  export default Login;
  