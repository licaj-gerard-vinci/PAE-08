async function getEntreprises(){
    let entreprises = null;

    try {
        const options = {
            method: 'GET',
            headers: {
                'content-type': 'application/json'
            }
        }

        const response = await fetch(`http://localhost:8080/entreprise`, options);

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