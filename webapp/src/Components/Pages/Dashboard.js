import {clearPage} from "../../utils/render";

const Dashboard = () => {
    clearPage()
    const main = document.querySelector('main');
    main.innerHTML = `
        <h1>Home Page Admin</h1>
        <p>Welcome to the admin dashboard</p>
    `;
}

export default Dashboard;