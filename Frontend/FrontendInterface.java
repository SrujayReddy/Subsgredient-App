import java.io.File;
import java.io.FileNotFoundException;

public interface FrontendInterface {

  // public FrontendInterface(Backend backend, Scanner scanner);

  // Specifies and loads a data file
  //
  public void dataFile() throws FileNotFoundException;

  // Lists the replacement for the ingredient selected
  //@return provides the information for users regarding replacements for ingredients
  public void listReplacements();

  // Lists up to 3 replacements with the same or minimally higher caloric value than the original ingredient
  public void listNumberAndCategory();

  // Command to exit the app
  public void exit();

  /**
   * Starts the main menu
   */
  public void startMain();

  /**
   * Starts the submenu
   */
  public void startSubMenu();


}
