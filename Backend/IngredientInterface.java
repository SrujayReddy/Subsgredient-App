/**
 * Represents an individual ingredient with associated properties. Provides methods for retrieving
 * and modifying ingredient details.
 */
public interface IngredientInterface extends Comparable<IngredientInterface> {

  /**
   * Retrieves the name of the ingredient.
   *
   * @return Ingredient name.
   */
  public String getName();

  /**
   * Retrieves the category of the ingredient.
   *
   * @return Ingredient category.
   */
  public String getCategory();

  /**
   * Retrieves the caloric value of the ingredient.
   *
   * @return Calories.
   */
  public int getCalories();

  /**
   * Sets the name for the ingredient.
   *
   * @param name New ingredient name.
   */
  public void setName(String name);

  /**
   * Sets the category for the ingredient.
   *
   * @param category New ingredient category.
   */
  public void setCategory(String category);

  /**
   * Sets the caloric value for the ingredient.
   *
   * @param calories New caloric value.
   */
  public void setCalories(int calories);
}
