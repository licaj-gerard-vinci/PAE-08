package be.vinci.pae.dal.year;

import be.vinci.pae.business.year.YearDTO;
import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.dal.utils.DALBackServiceUtils;
import be.vinci.pae.exceptions.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The YearDAOImpl class implements the YearDAO interface and provides methods to retrieve a single
 * year by its unique identifier or year.
 */
public class YearDAOImpl implements YearDAO {

  @Inject
  private DALBackService dalBackService;

  @Inject
  private DALBackServiceUtils dalBackServiceUtils;

  /**
   * Retrieves a single year by its unique identifier.
   *
   * @param id The unique identifier of the year.
   * @return The {@link YearDTO} instance, or {@code null} if not found.
   */
  @Override
  public YearDTO getOneById(int id) {
    String query = "SELECT * FROM pae.school_years WHERE school_year_id = ?";

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setInt(1, id);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return rsToYear(rs);
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return null;
  }

  /**
   * Retrieves a single year by its year.
   *
   * @param year The year of the year.
   * @return The {@link YearDTO} instance, or {@code null} if not found.
   */
  @Override
  public YearDTO getOneByYear(String year) {
    String query = "SELECT * FROM pae.school_years WHERE year = ?";

    try (PreparedStatement statement = dalBackService.preparedStatement(query)) {
      statement.setString(1, year);
      try (ResultSet rs = statement.executeQuery()) {
        if (rs.next()) {
          return rsToYear(rs);
        }
      }
    } catch (SQLException e) {
      throw new FatalException(e);
    }
    return null;
  }

  /**
   * Converts a ResultSet to a YearDTO.
   *
   * @param rs  the ResultSet to convert.
   * @return the YearDTO.
   */
  @Override
  public YearDTO rsToYear(ResultSet rs) throws SQLException {
    return dalBackServiceUtils.fillYearDTO(rs);
  }
}
