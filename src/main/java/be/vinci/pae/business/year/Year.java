package be.vinci.pae.business.year;

public interface Year extends YearDTO{

    /**
     * Retrieves the current year.
     *
     * @return The {@link YearDTO} instance representing the current year.
     */
    String renderCurrentYear();
}
