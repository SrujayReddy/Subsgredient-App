

/**

 * Represents an individual ingredient with associated properties. Provides methods for retrieving
 * and modifying ingredient details.
 */
public class Ingredient implements Comparable<Ingredient> {

  public String ingredientName;  //ingredient name
  public int ingredientCalories; //ingredient calories per 100g
  public String ingredientCategory; //ingredient category

  //create two classes, one that has a key of name and another, or we can use a method
  //that specifies the key to search with instead of two constructors
  public Ingredient(String category, String name, int calories) {
    this.ingredientName = name;
    this.ingredientCalories = calories;
    this.ingredientCategory = category;
  }

  /**
   * Retrieves the name of the ingredient.
   *
   * @return Ingredient name.
   */
  public String getName() {return this.ingredientName;}

  /**
   * Retrieves the category of the ingredient.
   *
   * @return Ingredient category.
   */

  public String getCategory() {return this.ingredientCategory;}

  /**
   * Retrieves the caloric value of the ingredient.
   *
   * @return Calories.
   */
  public int getCalories() {return this.ingredientCalories;}

  /**
   * Sets the name for the ingredient.
   *
   * @param name New ingredient name.
   */
  public void setName(String name) {this.ingredientName = name;}

  /**
   * Sets the category for the ingredient.
   *
   * @param category New ingredient category.
   */
  public void setCategory(String category) {this.ingredientCategory = category;}

  /**
   * Sets the caloric value for the ingredient.
   *
   * @param calories New caloric value.
   */
  public void setCalories(int calories) {this.ingredientCalories = calories;}

  /**
   * This method was required by implementation of Comparable interface
   *
   * @param ing
   * @return
   */
  @Override
  public int compareTo(Ingredient ing) {
    return -1;
  }




}
