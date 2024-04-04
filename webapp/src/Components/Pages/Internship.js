/* eslint-disable no-console */
import {
    getContact
  } from "../../model/contacts";
import getManagers from "../../model/managers";
import { insertInternship } from "../../model/internships";
import Navigate from '../Router/Navigate';

let managers = [];

const Internship = async () => {
    const main = document.querySelector('main');
    const contactId = sessionStorage.getItem('contactId');
    const contact = await getContact(contactId);
    console.log('contactId: ', contactId, ', contact: ', contact, ', companyId: ', contact.entreprise.id)
    managers = await getManagers(contact.entreprise.id);
    console.log('managers: ', managers);
    let managerOptions = [];
    
    managerOptions = managers.map(manager => `<option value="${manager.id}">${manager.prenom} ${manager.nom}</option>`).join('');
    console.log('manager options: ', managerOptions);


    main.innerHTML = `
    <form id="internshipForm">
        <label for="managerId">Manager ID:</label>
        <select id="managerId" name="managerId">
        ${managerOptions}
        </select>
        <label for="topic">Topic:</label>
        <input type="text" id="topic" name="topic">
        <label for="signatureDate">Signature Date:</label>
        <input type="date" id="signatureDate" name="signatureDate">
        <input type="submit" value="Submit">
    </form>
    `;

    document.getElementById('internshipForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const managerId = document.getElementById("managerId").value;
    const topic = document.getElementById("topic").value;
    const signatureDate = document.getElementById("signatureDate").value;

    console.log("managerId: ", managerId)
    await insertInternship(managerId, contact.utilisateur, contact, contact.entreprise, topic, signatureDate);
    console.log("contactId: ", contact.id, ", contactEntreprise: ", contact.entreprise,", contactEtudiant: ", contact.utilisateur, ", contactVersion: ", contact.version)
    console.log('Form submitted');
    Navigate('/')
    });

    // When the page is about to be unloaded
    window.addEventListener('beforeunload', () => {
    // Clear the contact ID from sessionStorage
    sessionStorage.removeItem('contactId');
    });
};

export default Internship;