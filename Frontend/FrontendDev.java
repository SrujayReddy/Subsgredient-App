import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains the methods to implement the frontend interface
 */

public class FrontendDev implements FrontendInterface {

  BackendInterface backend;
  Scanner scanner;

  public FrontendDev(BackendInterface backend, Scanner scanner) {
    this.backend = backend;
    this.scanner = scanner;
  }

  /**
   * Loads a specific data file
   */
  public void dataFile() throws FileNotFoundException {
    // obtain the file path we are searching for
    System.out.println("Enter the file path for the data set: ");
    String filePath = scanner.nextLine();

    if (backend.loadData(filePath)) {
      System.out.println("File path read successfully");
    } else {
      System.out.println("File path failed to find");
    }
  }

  /**
   * Lists the replacement for the ingredient selected
   *
   * @throws NullPointerException
   */

  public void listReplacements() throws NullPointerException {

    System.out.println("Enter the name of the ingredient: ");
    String ingredientName = scanner.nextLine();

    ArrayList<Ingredient> substitutes = backend.getNameSubstitutes(ingredientName);

    if (substitutes != null && !substitutes.isEmpty()) {
      System.out.println("Replacements for " + ingredientName + ":");
      for (int i = 0; i < substitutes.size(); i++) {
        Ingredient substitute = substitutes.get(i);
        System.out.println(
            "Replacement " + (i + 1) + ": " + substitute.getName() + " (" + substitute.getCategory() + ", " + substitute.getCalories() + " calories)");
      }
    } else {
      System.out.println("No replacements found for " + ingredientName);
    }
  }

  /**
   * Lists the number of ingredients and the number of categories
   */
  public void listNumberAndCategory() {
    int numOfCategories = backend.getCategoryCount();
    int numOfIngredients = backend.getIngredientCount();

    System.out.println(
        numOfIngredients + " ingredients, " + numOfCategories + " categories in the file");
  }

  /**
   * Command to exit the loop
   */
  public void exit() {
    System.out.println("Exited App");
    System.exit(0);
  }

  /**
   * Starts the main menu
   */
  public void startMain() {

    while (true) {
      startSubMenu();
      String userChoice = scanner.nextLine();
      switch (userChoice) {
        case "1" -> {
          try {
            dataFile();
          } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
          }
        }
        case "2" -> listReplacements();
        case "3" -> listNumberAndCategory();
        case "4" -> exit();
      }
    }
  }

  /**
   * Starts the submenu
   */
  public void startSubMenu() {
    System.out.println(
        "Welcome to the Subsgredient app. This app will return a substitute for an ingredint.");
    System.out.println("""
        Select an option:
        1. Upload data file\s
        2. List of ingredient replacements
        3. List the number of ingredients and categories\s
        4: Exit""");
  }
}
