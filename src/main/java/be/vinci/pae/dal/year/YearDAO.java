package be.vinci.pae.dal.year;

import be.vinci.pae.business.year.YearDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The Interface YearDAO.
 */
public interface YearDAO {

  /**
   * Retrieves all years.
   *
   * @return A list of all years.
   */
  List<YearDTO> getAll();

  /**
   * Retrieves a single year by its unique identifier.
   *
   * @param id The unique identifier of the year.
   * @return The {@link YearDTO} instance, or {@code null} if not found.
   */
  YearDTO getOneById(int id);

  /**
   * Retrieves a single year by its year.
   *
   * @param year The year of the year.
   * @return The {@link YearDTO} instance, or {@code null} if not found.
   */
  YearDTO getOneByYear(String year);

  /**
   * Converts a ResultSet to a YearDTO.
   *
   * @param rs the ResultSet to convert.
   * @return the YearDTO.
   * @throws SQLException if there is an issue accessing the ResultSet data.
   */
  YearDTO rsToYear(ResultSet rs) throws SQLException;

}
