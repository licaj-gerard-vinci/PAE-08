import Navigate from "../Router/Navigate";

const StudentInfoPage = (user) => {
  // Retrieve the user data from localStorage
  const main = document.querySelector('main');
  if (!user) {
    Navigate('/users');
  }
  else {
  main.innerHTML = `
      <h1 class="text-center">Informations sur l'étudiant</h1>
      <div> ${user.lastname} ${user.firstname}</div>`;
  }
};

export default StudentInfoPage;