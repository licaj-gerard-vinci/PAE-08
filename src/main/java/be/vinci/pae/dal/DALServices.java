package be.vinci.pae.dal;

public interface DALServices {

    /**
     * Starts a transaction.
     */
    void startTransaction();

    /**
     * Commits a transaction.
     */
    void commitTransaction();

    /**
     * Rolls back a transaction.
     */
    void rollbackTransaction();

}
