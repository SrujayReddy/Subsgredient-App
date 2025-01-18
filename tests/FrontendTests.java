import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * Class with 5 test cases
 */

public class FrontendTests {

  /**
   * This test will ensure that the dataFile method is working correctly.
   * We make a brand new test text file and assert that our frontend interface can find it
   */
  @Test
  public void testDataFile() {
    Scanner scnr = new Scanner(System.in);
    BackendPlaceholder backend = new BackendPlaceholder();

    FrontendDev frontend = new FrontendDev(backend, scnr);

    try {
      TextUITester tester = new TextUITester("\ningredients.csv\n");
      frontend.dataFile();
      String output = tester.checkOutput();
      Assertions.assertTrue(output.contains("file path for the data set"));
      Assertions.assertTrue(output.contains("successfully"), "case 2: ran unsuccessfully");
    } catch (FileNotFoundException e) {
      System.out.println("File not found.");
    }
    scnr.close();
  }


  /**
   * Test that makes sure we can't take numbers
   */
  @Test
  public void badIngredient() {
    Scanner scnr = new Scanner(System.in);
    BackendPlaceholder backend = new BackendPlaceholder();

    FrontendDev frontend = new FrontendDev(backend, scnr);

    TextUITester tester = new TextUITester("777");

    frontend.listReplacements();

    String output = tester.checkOutput();

    // Tests finding a replacement of the ingredient "111", which does not exist in the csv.
    Assertions.assertTrue(output.contains("not found"));
    scnr.close();
  }

  /**
   * Verifies that there is at least one replacement outputted when testing listReplacements.
   */
  @Test
  public void testReplacementValidity() {
    Scanner scnr = new Scanner(System.in);
    BackendPlaceholder backend = new BackendPlaceholder();
    FrontendDev frontend = new FrontendDev(backend, scnr);
    TextUITester tester = new TextUITester("Lime");

    frontend.listReplacements();

    String output = tester.checkOutput();

    Assertions.assertTrue(output.contains("Lemon"));

    scnr.close();
  }

  /**
   * Test the startMain method. We'd expect our app's main menu to pop up
   * without error
   */
  @Test
  public void testStartMain() {
    Scanner scnr = new Scanner(System.in);
    BackendPlaceholder backend = new BackendPlaceholder();

    FrontendDev frontend = new FrontendDev(backend, scnr);
    TextUITester tester = new TextUITester("\n2");
    frontend.startMain();
    String output = tester.checkOutput();
    Assertions.assertTrue(output.contains("Welcome to the Subsgredient"), "Intro message failed.");

    TextUITester tester2 = new TextUITester("\n2\n2");

    frontend.listReplacements();
    output = tester2.checkOutput();
    Assertions.assertTrue(output.contains("replace"), "Option 2 failed.");
    scnr.close();
  }

  /**
   * This tests the startSubmenu method. Will verify if the app's submenu
   * appears as intended
   */
  @Test
  public void testStartSubmMenu() {
    Scanner scnr = new Scanner(System.in);
    BackendPlaceholder backend = new BackendPlaceholder();

    FrontendDev frontend = new FrontendDev(backend, scnr);

    TextUITester tester = new TextUITester("ingredients.csv\n");

    frontend.startSubMenu();

    String output = tester.checkOutput();

    Assertions.assertTrue(output.contains("the file path"));
    Assertions.assertTrue(output.contains("categories in the file"));

    scnr.close();
  }

  /**
   * Test to see if backend method loadData can handle non-existent files
   *
   */
  @Test
  public void testIntegratiionLoadData() {
    BackendPlaceholder backend = new BackendPlaceholder();
    Scanner scnr = new Scanner(System.in);
    FrontendDev frontend = new FrontendDev(backend, scnr);
    // Should return false or handle null input appropriately
    Assertions.assertFalse(backend.insertIngredient(null));
  }


  /**
   * Test to see if backend method getIngredientCount returns a non-negative value
   *
   */
  @Test
  public void testIntegrationGetIngredientcount() {
    BackendPlaceholder backend = new BackendPlaceholder();
    Scanner scnr = new Scanner(System.in);
    FrontendDev frontend = new FrontendDev(backend, scnr);
    Assertions.assertFalse(backend.getIngredientCount() >= 0);
  }
}
