// --== CS400 Fall 2023 File Header Information ==--
// Name: SRUJAY REDDY JAKKIDI
// Email: jakkidi@wisc.edu
// Group: A23
// TA: CONNOR BAILEY
// Lecturer: GARY DAHL
// Notes to Grader: NONE

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * An extension of a RedBlackTree that allows multiple keys per node. Each node stores a list of
 * keys.
 *
 * @param <T> The type of the keys in the tree. Must be Comparable.
 */
public class IterableMultiKeyRBT<T extends Comparable<T>> extends RedBlackTree<KeyListInterface<T>>
    implements IterableMultiKeySortedCollectionInterface<T> {

  // The starting point for the iteration
  private T startPoint;
  // Count of total keys in the tree
  private int numKeys;

  /**
   * Inserts a value into the tree that can store multiple objects per key by keeping lists of
   * objects in each node of the tree.
   *
   * @param key object to insert
   * @return true if a new node was created for the key, false if the key was added to an existing
   * list.
   * @throws IllegalArgumentException if the provided key is null.
   */
  @Override
  public boolean insertSingleKey(T key) {
    if (key == null) {
      throw new IllegalArgumentException("Key cannot be null.");
    }

    KeyListInterface<T> list = new KeyList<T>(key);
    Node<KeyListInterface<T>> node = findNode(list);

    // If the node already exists, add the key to its list.
    if (node != null) {
      node.data.addKey(key);
      numKeys++;
      return false;
    } else {
      // Otherwise, insert a new node for the key.
      this.insert(list);
      numKeys++;
      return true;
    }
  }

  /**
   * @return the number of values in the tree.
   */
  @Override
  public int numKeys() {
    return this.numKeys;
  }

  /**
   * Returns a stack containing nodes for in-order traversal. The starting node is determined based
   * on whether a starting point has been set. If no starting point is set, the traversal begins
   * from the leftmost node of the tree. Otherwise, it starts from the closest node to the specified
   * startPoint.
   *
   * @return Stack containing nodes for in-order traversal.
   * @throws IllegalStateException If the tree is empty (i.e., the root is null).
   */
  protected Stack<Node<KeyListInterface<T>>> getStartStack() {
    Stack<Node<KeyListInterface<T>>> initialStack = new Stack<>();

    if (startPoint == null) {
      Node curr = root;
      // Traverse to the leftmost node and push nodes onto the stack for in-order traversal
      while (curr != null) {
        initialStack.push(curr);
        curr = curr.down[0];
      }
    } else {
      Node<KeyListInterface<T>> curr = root;
      // Traverse to the node closest to the set startPoint and push nodes onto the stack
      while (curr != null) {
        if (startPoint.compareTo(curr.data.iterator().next())< 0) {
          initialStack.push(curr);
          curr = curr.down[0];
        } else {
          curr = curr.down[1];
        }
      }
    }
    return initialStack;
  }

  /**
   * Returns an iterator for in-order traversal over the tree. If a starting point is set, the
   * traversal begins from the node closest to that point. Otherwise, it starts from the leftmost
   * node.
   *
   * @return Iterator for in-order traversal.
   */
  @Override
  public Iterator<T> iterator() {
    class MultiKeyRBTIterator implements Iterator<T> {
      // Stack to assist with in-order traversal. Initialized with the starting nodes for traversal.
      Stack<Node<KeyListInterface<T>>> iteratingStack = getStartStack();
      // Iterator for the current node's list. It will iterate over the keys in a node.
      Iterator<T> currListIterator = null;

      /**
       * Checks if there are more keys to be iterated over.
       *
       * @return true if there are more keys, false otherwise.
       */
      @Override
      public boolean hasNext() {
        return !iteratingStack.isEmpty() || (currListIterator != null && currListIterator.hasNext());
      }

      /**
       * Returns the next key in the in-order traversal.
       *
       * @return Next key in the traversal.
       * @throws NoSuchElementException if there are no more keys to return.
       */
      @Override
      public T next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }

        // If there are no more keys in the current list or it's the first call to next
        while (currListIterator == null || !currListIterator.hasNext()) {
          Node<KeyListInterface<T>> currNode = iteratingStack.pop();
          currListIterator = currNode.data.iterator();

          // If the current node has a right child, push its leftmost path to the stack for in-order traversal
          Node<KeyListInterface<T>> temp = currNode.down[1];
          while (temp != null) {
            iteratingStack.push(temp);
            temp = temp.down[0];
          }
        }
        return currListIterator.next();
      }
    }
    return new MultiKeyRBTIterator();
  }

  /**
   * Sets the starting point for iterations. Future iterations will start at the starting point or
   * the key closest to it in the tree. This setting is remembered until it is reset. Passing in
   * null disables the starting point.
   *
   * @param startPoint the start point to set for iterations
   */
  @Override
  public void setIterationStartPoint(Comparable<T> startPoint) {
    this.startPoint = (T) startPoint;
  }

  /**
   * Clears the tree by calling the parent class's clear method and resetting the key count.
   */
  @Override
  public void clear() {
    super.clear();
    numKeys = 0;
  }

  private IterableMultiKeyRBT<Integer> tree;

  @BeforeEach
  public void setUp() {
    tree = new IterableMultiKeyRBT<>();
  }

  /**
   * Test1: Verifying the behavior of the insertSingleKey method.
   *
   * This test validates that the insertSingleKey method is inserting elements into the tree. By
   * default, it returns false in the current implementation, signifying that the insertion was not
   * successful. The expected behavior (returning false) is being tested.
   */
  @Test
  public void testInsertSingleKey() {
    IterableMultiKeyRBT<String> tree = new IterableMultiKeyRBT<>();
    assertTrue(tree.insertSingleKey("Apple"));
    assertFalse(tree.insertSingleKey("Apple"));
  }

  /**
   * Test2: Checking the functionality of the numKeys method.
   *
   * This test verifies that the numKeys method returns the correct number of keys present in the
   * tree. With the current implementation, it always returns 0, which is what is being checked in
   * this test case.
   */
  @Test
  public void testNumKeys() {
    IterableMultiKeyRBT<String> tree = new IterableMultiKeyRBT<>();
    tree.insertSingleKey("Apple");
    tree.insertSingleKey("Apple");
    tree.insertSingleKey("Banana");
    assertEquals(3, tree.numKeys());
  }

  /**
   * Test3: Validating the behavior of the iterator method.
   *
   * This test checks that the iterator method returns a valid iterator for the tree. The current
   * implementation always returns null, and this test checks that this expected behavior is
   * consistent.
   */
  @Test
  public void testIterator() {
    IterableMultiKeyRBT<String> tree = new IterableMultiKeyRBT<>();

    // Insert items into the tree
    tree.insertSingleKey("Apple");
    tree.insertSingleKey("Banana");
    tree.insertSingleKey("Apple");
    // Extract iterator from the tree
    Iterator<String> iterator = tree.iterator();

    // Assert that iterator is not null
    assertNotNull(iterator, "Expected non-null iterator, but got null.");

    // Assert that the iterator can iterate over the items
    assertTrue(iterator.hasNext(), "Expected iterator to have next item, but it didn't.");
    assertEquals("Apple", iterator.next(), "Expected first element to be 'Apple'");

    assertTrue(iterator.hasNext(), "Expected iterator to have next item, but it didn't.");
    assertEquals("Apple", iterator.next(), "Expected first element to be 'Apple'");

    assertTrue(iterator.hasNext(), "Expected iterator to have next item, but it didn't.");
    assertEquals("Banana", iterator.next(), "Expected second element to be 'Banana'");

    // Assert that iterator doesn't have more items
    assertFalse(iterator.hasNext(), "Expected iterator to not have more items, but it did.");
  }

  /**
   * Tests if the iterator for an empty tree returns false for the hasNext() method.
   */
  @Test
  public void testEmptyTreeIterator() {
    Assertions.assertFalse(tree.iterator().hasNext());
  }

  /**
   * Tests the insertion of a single key and verifies the size and number of keys.
   */
  @Test
  public void testSingleInsert() {
    tree.insertSingleKey(5);
    Assertions.assertEquals(1, tree.size());
    Assertions.assertEquals(1, tree.numKeys());
  }

  /**
   * Tests the insertion of duplicate keys and verifies the size and number of keys.
   */
  @Test
  public void testDuplicateInsert() {
    tree.insertSingleKey(5);
    tree.insertSingleKey(5);
    Assertions.assertEquals(1, tree.size());
    Assertions.assertEquals(2, tree.numKeys());
  }

  /**
   * Tests the iterator for a tree with multiple keys, ensuring the correct order.
   */
  @Test
  public void testIteratorWithMultipleKeys() {
    tree.insertSingleKey(10);
    tree.insertSingleKey(5);
    tree.insertSingleKey(20);
    tree.insertSingleKey(5);

    Iterator<Integer> iter = tree.iterator();
    Assertions.assertEquals(5, iter.next());
    Assertions.assertEquals(5, iter.next());
    Assertions.assertEquals(10, iter.next());
    Assertions.assertEquals(20, iter.next());
    Assertions.assertFalse(iter.hasNext());
  }

  /**
   * Tests the iterator starting from a specified start point.
   */
  @Test
  public void testIterationStartPoint() {
    tree.insertSingleKey(10);
    tree.insertSingleKey(5);
    tree.insertSingleKey(20);

    tree.setIterationStartPoint(15);
    Iterator<Integer> iter = tree.iterator();
    Assertions.assertEquals(20, iter.next());
    Assertions.assertFalse(iter.hasNext());
  }

  /**
   * Tests the iterator behavior after clearing the tree.
   */
  @Test
  public void testIteratorAfterClear() {
    tree.insertSingleKey(5);
    tree.insertSingleKey(10);
    tree.clear();

    Assertions.assertFalse(tree.iterator().hasNext());
  }

  /**
   * Tests the iterator behavior after clearing the tree and resetting the start point.
   */
  @Test
  public void testIteratorNoStartPointAfterClear() {
    tree.insertSingleKey(10);
    tree.insertSingleKey(5);
    tree.insertSingleKey(20);
    tree.setIterationStartPoint(15);
    tree.clear();

    tree.setIterationStartPoint(null); // Resetting start point
    Assertions.assertFalse(tree.iterator().hasNext());
  }

  /**
   * Tests the iterator with edge cases for the start point.
   */
  @Test
  public void testEdgeCasesForIteratorStartPoint() {
    tree.insertSingleKey(10);
    tree.insertSingleKey(5);
    tree.insertSingleKey(20);

    // Set a start point that's smaller than any key in the tree
    tree.setIterationStartPoint(1);
    Iterator<Integer> iter = tree.iterator();
    Assertions.assertEquals(5, iter.next());

    // Set a start point that's larger than any key in the tree
    tree.setIterationStartPoint(100);
    iter = tree.iterator();
    Assertions.assertFalse(iter.hasNext());
  }

  /**
   * Tests the iterator for a tree with only duplicate keys.
   */
  @Test
  public void testIteratorWithOnlyDuplicates() {
    tree.insertSingleKey(10);
    tree.insertSingleKey(10);
    tree.insertSingleKey(10);

    Iterator<Integer> iter = tree.iterator();
    Assertions.assertEquals(10, iter.next());
    Assertions.assertEquals(10, iter.next());
    Assertions.assertEquals(10, iter.next());
    Assertions.assertFalse(iter.hasNext());
  }

  /**
   * Tests the iterator for a tree with only duplicate keys.
   */
  @Test
  public void testIteratorWithOnlyDuplicate() {
    tree.insertSingleKey(10);
    tree.insertSingleKey(10);
    tree.insertSingleKey(10);

    Iterator<Integer> iter = tree.iterator();
    Assertions.assertEquals(10, iter.next());
    Assertions.assertEquals(10, iter.next());
    Assertions.assertEquals(10, iter.next());
    Assertions.assertFalse(iter.hasNext());
  }

}




