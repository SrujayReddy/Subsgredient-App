import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides backend functionalities for managing ingredients. Uses two sorted collections for
 * operations based on ingredient names and caloric values.
 */
public class BackendPlaceholder implements BackendInterface {

  /**
   * Reads ingredient data from a specified CSV file.
   *
   * @param filePath The path to the CSV file containing ingredient data.
   * @return true if data is loaded successfully, false otherwise.
   * @throws FileNotFoundException if the file does not exist.
   */
  public boolean loadData(String filePath) throws FileNotFoundException {
    return true;
  }

  /**
   * Inserts an ingredient into the appropriate Red-Black Tree.(Since there are two trees)
   *
   * @param ingredient The ingredient object to be inserted.
   * @return true if the ingredient was successfully inserted, false otherwise.
   */
  @Override
  public boolean insertIngredient(Ingredient ingredient) {
    return true;
  }

  /**
   * Retrieves a list of up to three ingredients that can replace a given ingredient by name. The
   * replacements should have the same or slightly higher caloric value.
   *
   * @param ingredientName The name of the ingredient to be replaced.
   * @return A list of potential replacement ingredients.
   */
  @Override
  public ArrayList<Ingredient> getNameSubstitutes(String ingredientName) {
    ArrayList<Ingredient> substitutes = new ArrayList<>();
    substitutes.add(new Ingredient("Substitute1", "Category1", 100));
    substitutes.add(new Ingredient("Substitute2", "Category2",  150));
    substitutes.add(new Ingredient("Substitute3", "Category3",  200));
    return substitutes;
  }

  /**
   * This method returns the calorie count for an ingredient specified by name. The calories
   * returned is the caloric count per 100g as provided in the .csv file.
   *
   * @param ingredientName name of ingredient
   * @return ingredient calories, -1 if ingredient doesn't exist
   */
  @Override
  public int getCalorieCount(String ingredientName) {
    return 0;
  }

  /**
   * Gets the total number of ingredients in the dataset.
   *
   * @return The total count of ingredients.
   */
  public int getIngredientCount() {
    return 10;
  }

  /**
   * Gets the total number of unique categories in the dataset.
   *
   * @return The count of unique categories.
   */
  public int getCategoryCount() {
    return 5;
  }

  /**
   * Clears all data.
   */
  public void clearData() {
    // Placeholder
  }

}
