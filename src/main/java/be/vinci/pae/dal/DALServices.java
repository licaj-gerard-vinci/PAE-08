package be.vinci.pae.dal;

/**
 * The {@code DALServices} interface represents a Data Access Layer (DAL) service within the
 * application. It defines methods to start, commit, and rollback transactions.
 */
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

  /**
   * Closes the connection.
   */
  void close();

  /**
   * Opens a connection.
   */
  void openConnection();

}
