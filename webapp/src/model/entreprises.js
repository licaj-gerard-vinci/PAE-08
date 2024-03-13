async function getEntreprises(){
    let entreprises = null;

    try {
        const response = await fetch(`http://localhost:8080/ent/all`);

        if (!response.ok) {
            throw new Error(`Error fetchng entreprises data`);
        
        }

        entreprises = await response.json();
    } catch (error) {
        console.log('Error fetching entreprise data');
    }

    return entreprises;
}

export default getEntreprises;