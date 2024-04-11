package be.vinci.pae.dal.year;

import be.vinci.pae.business.year.YearDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface YearDAO {

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
   */
  YearDTO rsToYear(ResultSet rs) throws SQLException;

}
