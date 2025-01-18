import java.util.Iterator;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Provides backend functionalities for managing ingredients. Uses two sorted collections for
 * operations based on ingredient names and caloric values.
 */
public class BackendImplementation implements BackendInterface {

  // Data structures to store ingredients and categories.
  private static IterableMultiKeyRBT<Ingredient> ingredientTree;
  private static IterableMultiKeyRBT<String> categoryTree;

  public BackendImplementation() {
    this.categoryTree = new IterableMultiKeyRBT<>();
    this.ingredientTree = new IterableMultiKeyRBT<>();
  }


  /**
   * Reads ingredient data from a specified CSV file. This method was
   * developed collaboratively between backend developers in our group.
   *
   * @param filePath The path to the CSV file containing ingredient data.
   * @return true if data is loaded successfully, false otherwise.
   */
  @Override
  public boolean loadData(String filePath) throws FileNotFoundException {
    // Set up file input stream and scanner.
    FileInputStream csvFileInput;
    Scanner fileReader;

    try {
      csvFileInput = new FileInputStream(filePath); // Open the CSV file at the specified filePath.
      fileReader = new Scanner(csvFileInput);
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException();
    }

    fileReader.nextLine(); // Skip the header line.

    // Variables for storing ingredient data.
    String name;
    String category;
    String calsStr;
    int cals;

    while (fileReader.hasNextLine()) {

      String csvLine = fileReader.nextLine();
      char[] csvLineBuff = csvLine.toCharArray();

      name = "";
      boolean apostropheOn = false;
      int start_index = 0;
      int end_index = 0;
      while (csvLineBuff[end_index] != ',' || apostropheOn) {
        if (csvLineBuff[end_index] == '"') {
          apostropheOn = !apostropheOn;
        }
        end_index++;
      }
      category = csvLine.substring(start_index, end_index);

      start_index = end_index + 1;
      end_index = start_index;

      while (csvLineBuff[end_index] != ',' || apostropheOn) {
        if (csvLineBuff[end_index] == '\"') {
          apostropheOn = !apostropheOn;
        }
        end_index++;
      }
      name = csvLine.substring(start_index, end_index);

      start_index = end_index + 1;
      end_index = start_index;

      while (csvLineBuff[end_index] != ',' || apostropheOn) {
        if (csvLineBuff[end_index] == '\"') {
          apostropheOn = !apostropheOn;
        }
        end_index++;
      }

      start_index = end_index + 1;
      end_index = start_index;

      while (csvLineBuff[end_index] != ',' || apostropheOn) {
        if (csvLineBuff[end_index] == '\"') {
          apostropheOn = !apostropheOn;
        }
        end_index++;
      }
      calsStr = csvLine.substring(start_index, end_index);

      // Read the calorie count as a string and parse it into an integer.
      cals = Integer.parseInt(calsStr.substring(0, calsStr.length() - 4));

      // Insert ingredients into data structures (e.g., trees).
      insertIngredient(new Ingredient(category, name, cals));
    }

    // Data loading is complete, return true to indicate success.
    return true;
  }

  /**
   * Inserts an ingredient into the appropriate Red-Black Tree.(Since there are two trees)
   *
   * @param ingredient The ingredient object to be inserted.
   * @return true if the ingredient was successfully inserted, false otherwise.
   */
  @Override
  public boolean insertIngredient(Ingredient ingredient) throws NullPointerException{
    if (ingredient == null) {throw new NullPointerException();}

    ingredientTree.insertSingleKey(ingredient); //insert ingredient object into iterable tree
    categoryTree.insertSingleKey(ingredient.getCategory()); //insert category count into tree

    return true;
  }

  /**
   * Retrieves a list of up to three ingredients that can replace a given ingredient by name. The
   * replacements should have the same or slightly higher caloric value. If no substitutions
   * can be found, method will return null. Else, this method will return an ArrayList with as
   * any subgredients we can find in our whole data set. Up to frontend to decide
   * how to use them
   *
   * @param ingredientName The name of the ingredient to be replaced.
   * @return A list of potential replacement ingredients.
   */
  @Override
  public ArrayList<Ingredient> getNameSubstitutes(String ingredientName) {

    // Search for the specified ingredient.
    Iterator<Ingredient> iterator = ingredientTree.iterator();
    Ingredient holder;
    Ingredient originalIngredient = null;

    while (iterator.hasNext()) {
      holder = iterator.next();
      if (holder.getName().equals(ingredientName)) {
        originalIngredient = holder;
        break;
      }
    }

    if (originalIngredient == null) {
      System.out.println("Ingredient not found");
      return new ArrayList<>(); // Return empty list instead of null
    }

    // Now we iterate through the whole tree with the ingredient to find three substitutes
    ArrayList<Ingredient> substitutes = new ArrayList<>();
    ingredientTree.setIterationStartPoint(originalIngredient);
    Iterator<Ingredient> subIterator = ingredientTree.iterator();
    int threshold = originalIngredient.getCalories() + 30;
    int counter = 0;

    while (subIterator.hasNext()) {
      holder = subIterator.next();

      // Making sure it is not a duplicate ingredient
      if (!holder.getName().equals(originalIngredient.getName())) {
        // Making sure categories align
        if (holder.getCategory().equals(originalIngredient.getCategory())) {
          // Making sure calories are within threshold
          if (holder.getCalories() >= originalIngredient.getCalories()
              && holder.getCalories() <= threshold) {
            substitutes.add(holder);
            counter++;
          }
        }
      }
      if (counter == 3) { break; }
    }

    return substitutes; // Always return the ArrayList, whether it's empty or has items
  }


  /**
   * This method returns the calorie count for an ingredient specified
   * by name. The calories returned is the caloric count per 100g as
   * provided in the .csv file.
   *
   * @param ingredientName name of ingredient
   * @return ingredient calories, -1 if ingredient doesn't exist
   */
  @Override
  public int getCalorieCount(String ingredientName) {

    // Search for the ingredient's calorie count.
    Iterator<Ingredient> iterator = ingredientTree.iterator();
    Ingredient holder;
    Ingredient originalIngredient = null;

    while (iterator.hasNext()) {
      holder = iterator.next();
      if (holder.getName().equals(ingredientName)) {
        originalIngredient = holder;
        break;
      }
    }
    if (originalIngredient != null) {
      return originalIngredient.getCalories();
    }
    //return - 1 if ingredient cannot be found
    return -1;
  }

  /**
   * Gets the total number of ingredients in the dataset.
   *
   * @return The total count of ingredients.
   */
  @Override
  public int getIngredientCount() {
    // Retrieve the count of all ingredients.
    return categoryTree.numKeys();
  }

  /**
   * Gets the total number of unique categories in the dataset.
   *
   * @return The count of unique categories.
   */
  @Override
  public int getCategoryCount() {
    // Retrieve the count of unique categories.
    return categoryTree.size();
  }

  /**
   * Clears all data in both implemented trees
   */
  @Override
  public void clearData() {
    // Clear both data structures.
    categoryTree.clear();
    ingredientTree.clear();
  }

  /**
   * The main function that runs the app
   *
   * @param args
   */
  public static void main(String[] args) {
    // Initialize backend and frontend and start the frontend.
    BackendImplementation backend = new BackendImplementation();
    FrontendDev frontend = new FrontendDev(backend, new Scanner(System.in));
    frontend.startMain();
  }

}



