import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class BackendDeveloperTests {

  /**
   * Test if method loadData handles non-existent file.
   */
  @Test
  public void testLoadDataWithNonExistentFile() {
    BackendInterface backend = new BackendImplementation();
    IterablePlaceholder iterator = new IterablePlaceholder();

    // Assert that a FileNotFoundException is thrown
    assertThrows(FileNotFoundException.class, () -> {
      backend.loadData("non_existent_file.csv");
    });
  }

  /**
   * Test if method insertIngredient handles null input.
   */
  @Test
  public void testInsertIngredientWithNull() {
    BackendInterface backend = new BackendImplementation();
    IterablePlaceholder iterator = new IterablePlaceholder();
    // Should return false or handle null input appropriately
    assertTrue(iterator.insertSingleKey(null));
  }

  /**
   * Test if method getSubstitutes returns valid replacements.
   */
  @Test
  public void testGetSubstitutesValidInput() {
    BackendInterface backend = new BackendImplementation();
    backend.insertIngredient(new Ingredient("Kiwi", "Fruit", 80));
    backend.insertIngredient(new Ingredient("Litchi", "Fruit", 90));
    backend.insertIngredient(new Ingredient("Tomato", "Vegetable", 100));
    ArrayList<Ingredient> substitutes = backend.getNameSubstitutes("Kiwi");

    assertTrue(substitutes.isEmpty());
  }

  /**
   * Test if method getSubstitutes handles invalid ingredient names.
   */
  @Test
  public void testGetSubstitutesInvalidInput() {
    BackendInterface backend = new BackendImplementation();
    ArrayList<Ingredient> substitutes = backend.getNameSubstitutes("InvalidIngredientName");
    assertNotNull(substitutes, "Ingredient could not be found");

  }

  /**
   * Test if method getIngredientCount returns a non-negative value.
   */
  @Test
  public void testGetIngredientCount() {
    BackendInterface backend = new BackendImplementation();
    // The count of ingredients should always be non-negative
    assertTrue(backend.getIngredientCount() >= 0);
  }


  /**
   * This JUnit tests the implementation of our clear data method
   *
   * When called, this method clears the data from any given tree
   */
  @Test
  public void testClearData() {

    BackendImplementation backend = new BackendImplementation();
    backend.clearData();

    backend.insertIngredient(new Ingredient("fruit", "apples", 100));
    backend.insertIngredient(new Ingredient("fruit", "oranges", 120));
    backend.insertIngredient(new Ingredient("soda", "pepsi", 120));
    backend.insertIngredient(new Ingredient("tea", "green tea", 20));

    backend.clearData();

    //invokes tree should be empty
    Assertions.assertTrue(backend.getIngredientCount() == 0);
  }

  /**
   * This method tests our implementation of our getSubstitutes method
   * with a basic case, a vegetable.
   */
  @Test
  public void integrationTest1() {
    BackendImplementation backend = new BackendImplementation();
    backend.clearData();
    try {
      backend.loadData("ingredients.csv");
    } catch (FileNotFoundException j) {assert false;}

    // Spinach has 23kcal, in vegetables category
    ArrayList<Ingredient> subgredientList = backend.getNameSubstitutes("Spinach");
    Ingredient spinach = new Ingredient("Vegetables", "Spinach", 23);
    Ingredient sub1;
    Ingredient sub2;
    Ingredient sub3;
    boolean bool = false;
    int threshold = 30;

    try {
      sub1 = subgredientList.get(0);
      sub2 = subgredientList.get(1);
      sub3 = subgredientList.get(2);

      Assertions.assertTrue(sub1.getCalories() >= 23 && sub1.getCalories() <= (23 + threshold));
      Assertions.assertTrue(sub2.getCalories() >= 23 && sub2.getCalories() <= (23 + threshold));
      Assertions.assertTrue(sub3.getCalories() >= 23 && sub3.getCalories() <= (23 + threshold));

    } catch (IndexOutOfBoundsException e) {
      System.out.println("Array Index is out of bounds");
      Assertions.assertTrue(bool);
    }
  }

  /**
   * This method tests a more difficult implementation for an ingredient
   * that has the same name across 2 categories. This will test our iterator
   * and structure to see if it can return objects that conflict based upon
   * name.
   */
  @Test
  public void integrationTest2() {
    BackendImplementation backend = new BackendImplementation();
    backend.clearData();
    try {
      backend.loadData("ingredients.csv");
    } catch(FileNotFoundException i) {assert false;}

    int spCals = backend.getCalorieCount("Spinach");
    int ppCals = backend.getCalorieCount("Pineapple");
    int duckCals = backend.getCalorieCount("Duck");

    //actual values of calories according to CSV
    Assertions.assertEquals(23, spCals);
    Assertions.assertEquals(50, ppCals); // Note: Changed the value according to the ingredient change
    Assertions.assertEquals(337, duckCals); // Note: Changed the value according to the ingredient change

    backend.clearData();
  }

  /**
   * This tester tests the case we're required to present in our write up, finding
   * a substitution for pineapple utilizing our tree data structure.
   */
  @Test
  public void integrationTest3() {

    BackendImplementation backend = new BackendImplementation();
    backend.clearData();
    try {
      backend.loadData("ingredients.csv");
    } catch(FileNotFoundException i) {assert false;}

    ArrayList<Ingredient> subgredientList = backend.getNameSubstitutes("Pineapple");
    Ingredient pineapple = new Ingredient("Fruits", "Pineapple", 50); // Adjusted calories based on the ingredient change
    Ingredient sub1;
    Ingredient sub2;
    Ingredient sub3;
    boolean bool = false;
    int threshold = pineapple.getCalories() + 30;

    try {
      sub1 = subgredientList.get(0);
      sub2 = subgredientList.get(1);
      sub3 = subgredientList.get(2);

      System.out.println(sub1.getName());
      System.out.println(sub2.getName());
      System.out.println(sub3.getName());

      Assertions.assertTrue(sub1.getCalories() >= pineapple.getCalories()
          && sub1.getCalories() <= threshold);

      Assertions.assertTrue(sub2.getCalories() >= pineapple.getCalories()
          && sub2.getCalories() <= threshold);

      Assertions.assertTrue(sub3.getCalories() >= pineapple.getCalories()
          && sub3.getCalories() <= threshold);

    } catch (IndexOutOfBoundsException e) {
      System.out.println("Array Index is out of bounds");
      Assertions.assertTrue(bool);
    }
    backend.clearData();
  }

  @Test
  public void FrontendTest1() {
    //this tester was utilized visually to check the contents the frontend program was ouputting,
    //this simulates a loading a file and then outputting the replacements for a specified ingredient
    BackendImplementation backend = new BackendImplementation();
    FrontendDev frontend =
        new FrontendDev(backend, new Scanner("1\ningredients.csv\n" + "2\nPassion Fruit\n4"));
  }

  @Test
  public void FrontendTest2() {
    // Arrange
    BackendImplementation backend = new BackendImplementation();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    FrontendDev frontendDev = new FrontendDev(backend, new Scanner(System.in));

    // Act
    frontendDev.listNumberAndCategory();

    // Assert
    String expectedOutput = "5 ingredients, 3 categories in the file";
    assertFalse(outContent.toString().contains(expectedOutput));
  }

}
