import java.util.*;

/**
 * This class implements the IterableMultiKeySortedCollectionInterface and provides a collection
 * that can store and iterate over IngredientInterface objects using multiple keys, specifically, by
 * name and calorie count.
 */
public class IterablePlaceholder
    implements IterableMultiKeySortedCollectionInterface<IngredientInterface> {

  RedBlackTree<IngredientInterface> nameTree = new RedBlackTree<>();
  RedBlackTree<IngredientInterface> calorieTree = new RedBlackTree<>();
  boolean isCalorie = false;

  /**
   * Inserts value into tree that can store multiple objects per key by keeping lists of objects in
   * each node of the tree.
   *
   * @param key object to insert
   * @return true if obj was inserted.
   */
  @Override
  public boolean insertSingleKey(IngredientInterface key) {
    return true;
  }

  /**
   * @return the number of values in the tree.
   */
  @Override
  public int numKeys() {
    return 0;
  }

  /**
   * Returns an iterator that does an in-order iteration over the tree.
   */
  @Override
  public Iterator<IngredientInterface> iterator() {
    List<IngredientInterface> list = new ArrayList<>();
    if (isCalorie) {
      if (calorieTree.root != null) {
        Stack<BinarySearchTree.Node<IngredientInterface>> nodeStack = new Stack<>();
        BinarySearchTree.Node<IngredientInterface> current = calorieTree.root;
        while (!nodeStack.isEmpty() || current != null) {
          if (current == null) {
            BinarySearchTree.Node<IngredientInterface> popped = nodeStack.pop();
            list.add(popped.data);
            current = popped.down[1];
          } else {
            nodeStack.add(current);
            current = current.down[0];
          }
        }
      }
    } else {
      if (nameTree.root != null) {
        Stack<BinarySearchTree.Node<IngredientInterface>> nodeStack = new Stack<>();
        BinarySearchTree.Node<IngredientInterface> current = nameTree.root;
        while (!nodeStack.isEmpty() || current != null) {
          if (current == null) {
            BinarySearchTree.Node<IngredientInterface> popped = nodeStack.pop();
            list.add(popped.data);
            current = popped.down[1];
          } else {
            nodeStack.add(current);
            current = current.down[0];
          }
        }
      }
    }
    return list.iterator();
  }

  /**
   * Sets the starting point for iterations. Future iterations will start at the starting point or
   * the key closest to it in the tree. This setting is remembered until it is reset. Passing in
   * null disables the starting point.
   *
   * @param startPoint the start point to set for iterations
   */
  @Override
  public void setIterationStartPoint(Comparable<IngredientInterface> startPoint) {
  }

  @Override
  public boolean insert(KeyListInterface<IngredientInterface> data)
      throws NullPointerException, IllegalArgumentException {
    return false;
  }

  @Override
  public boolean contains(Comparable<KeyListInterface<IngredientInterface>> data) {
    return false;
  }

  @Override
  public int size() {
    return calorieTree.size() + nameTree.size();
  }

  @Override
  public boolean isEmpty() {
    return calorieTree.size() + nameTree.size() == 0;
  }

  @Override
  public void clear() {
    calorieTree.clear();
    nameTree.clear();
  }
}




