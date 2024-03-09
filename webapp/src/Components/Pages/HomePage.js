import { getUserData } from "../../model/users";

const HomePage = async () => {
  await renderHomePage();
};

async function renderHomePage(){
  const main = document.querySelector('main');
  const user = await getUserData();
  console.log(user);

  if(user.role === "A" || user.role === "P"){
    main.innerHTML = `
    <div style="display: flex; justify-content: center; align-items: center; height: 100vh;">
      <h1 style="font-size: 3em;">Welcome to the Home Page for professors and administratifs only!</h1>
    </div>
  `;




  } else if (user.role === "E") {
    main.innerHTML = `
    <div style="display: flex; justify-content: center; align-items: center; height: 100vh;">
      <h1 style="font-size: 3em;">Welcome to the Home Page for users only!</h1>
    </div>
  `;
  } else {
    console.log("Non existant user role or unknown user role");
  }
  
}

export default HomePage;
