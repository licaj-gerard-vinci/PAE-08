package be.vinci.pae.dal;

import java.sql.PreparedStatement;

/**
 * Provides a data access layer service for creating prepared statements.
 */
public interface DALService {

  /**
   * Create a PS.
   *
   * @param query the query to be executed.
   * @return the prepared statement.
   */
  PreparedStatement preparedStatement(String query);
}
