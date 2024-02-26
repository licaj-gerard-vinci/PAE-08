package be.vinci.pae.views;

/**
 * The Views class provides nested classes representing different JSON views for serialization
 * and deserialization.
 */
public class Views {

  /**
   * The Public class represents a public JSON view.
   * Fields marked with this view will be included in public JSON responses.
   */
  public static class Public {
  }

  /**
   * The Internal class represents an internal JSON view.
   * Fields marked with this view will be included in internal JSON responses,
   * extending the Public view.
   * */
  public static class Internal extends Public {
  }

}
