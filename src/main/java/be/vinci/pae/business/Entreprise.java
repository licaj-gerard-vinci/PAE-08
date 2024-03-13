package be.vinci.pae.business;

/**
 * The Entreprise interface represents a business entity.
 * It extends the EntrepriseDTO interface and provides methods to get and set the properties of an entreprise.
 */
public interface Entreprise extends EntrepriseDTO {
    /**
     * Gets the ID of the entreprise.
     *
     * @return the ID of the entreprise.
     */
    int getId();

    /**
     * Sets the ID of the entreprise.
     *
     * @param id the new ID for the entreprise.
     */
    void setId(int id);

    /**
     * Gets the name of the entreprise.
     *
     * @return the name of the entreprise.
     */
    String getNom();

    /**
     * Sets the name of the entreprise.
     *
     * @param nom the new name for the entreprise.
     */
    void setNom(String nom);

    /**
     * Gets the apellation of the entreprise.
     *
     * @return the apellation of the entreprise.
     */
    String getAppellation();

    /**
     * Sets the apellation of the entreprise.
     *
     * @param appellation the new apellation for the entreprise.
     */
    void setAppellation(String appellation);

    /**
     * Gets the adresse of the entreprise.
     *
     * @return the adresse of the entreprise.
     */
    String getAdresse();

    /**
     * Sets the adresse of the entreprise.
     *
     * @param adresse the new adresse for the entreprise.
     */
    void setAdresse(String adresse);

    /**
     * Gets the numTel of the entreprise.
     *
     * @return the numTel of the entreprise.
     */
    String getNumTel();

    /**
     * Sets the numTel of the entreprise.
     *
     * @param numTel the new numTel for the entreprise.
     */
    void setNumTel(String numTel);

    /**
     * Gets the email of the entreprise.
     *
     * @return the email of the entreprise.
     */
    String getEmail();

    /**
     * Sets the email of the entreprise.
     *
     * @param email the new email for the entreprise.
     */
    void setEmail(String email);

    /**
     * Gets the blackListed of the entreprise.
     *
     * @return the blackListed of the entreprise.
     */
    boolean isBlackListed();

    /**
     * Sets the blackListed of the entreprise.
     *
     * @param blackListed the new blackListed for the entreprise.
     */
    void setBlackListed(boolean blackListed);

    /**
     * Gets the motivation_blacklist of the entreprise.
     *
     * @return the motivation_blacklist of the entreprise.
     */
    String getMotivation_blacklist();

    /**
     * Sets the motivation_blacklist of the entreprise.
     *
     * @param motivation the new motivation_blacklist for the entreprise.
     */
    void setMotivation_blacklist(String motivation);
}