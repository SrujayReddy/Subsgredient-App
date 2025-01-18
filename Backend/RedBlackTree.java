// --== CS400 Fall 2023 File Header Information ==--
// Name: SRUJAY REDDY JAKKIDI
// Email: jakkidi@wisc.edu
// Group: A23
// TA: CONNOR BAILEY
// Lecturer: GARY DAHL
// Notes to Grader: NONE

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * This class represents a Red-Black Tree with all its properties to ensure that the tree remains
 * balanced during insertions and deletions. As a result, the tree ensures O(log n) search time.
 *
 * @param <T> The type of elements held in this tree, which must be comparable.
 */
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

  protected static class RBTNode<T> extends Node<T> {
    public int blackHeight = 0;
    public RBTNode(T data) { super(data); }
    public RBTNode<T> getUp() { return (RBTNode<T>)this.up; }
    public RBTNode<T> getDownLeft() { return (RBTNode<T>)this.down[0]; }
    public RBTNode<T> getDownRight() { return (RBTNode<T>)this.down[1]; }
  }

  /**
   * Ensures the Red-Black Tree properties are maintained following the insertion of a new node.
   *
   * After a new node insertion, the Red-Black Tree properties may be violated. This method
   * recursively checks and resolves possible violations from the inserted node upwards through the
   * tree and ensuring that the tree remains valid. The properties preserved are:
   * - Each node is either red or black, indicated by blackHeight.
   * - The root node of the tree is always black.
   * - Every path from the root to any of the leaf nodes must have the same number of black nodes.
   * - No two red nodes can be adjacent, i.e., a red node cannot be the
   *   parent or the child of another red node.
   *
   * Violations are resolved through a series of rotations and re-coloring:
   * - Case 1: If the uncle of the new node is red, re-coloring is performed.
   * - Case 2 and 3: If the uncle is black or null, rotations may be needed.
   *
   * @param newNode The recently inserted node into the Red-Black Tree.
   */
  protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> newNode) {
    // Basic checks: if newNode is null or the root, no properties need to be enforced.
    if (newNode == null || newNode == root) {
      return;
    }

    // Retrieve parent and grandparent for easy reference.
    RBTNode<T> parent = newNode.getUp();
    if (parent == null) {
      // If parent is null, ensure the root is black and return.
      ((RBTNode<T>) root).blackHeight = 1;
      return;
    }
    RBTNode<T> grandparent = parent.getUp();
    if (grandparent == null) {
      // If grandparent is null, no further checks are needed.
      return;
    }

    // Check for violation: if parent is red, we might have a violation to handle.
    if (parent.blackHeight == 0) {
      // Determine uncle's color: null or black is treated the same way.
      RBTNode<T> uncle =
          parent.isRightChild() ? grandparent.getDownLeft() : grandparent.getDownRight();
      boolean uncleIsBlack = uncle == null || uncle.blackHeight == 1;

      // Handling violations based on uncle's color.
      if (uncleIsBlack) {
        // Case 2 and 3: Uncle is black or absent.
        if (isOnTheSameSide(newNode, parent)) {
          // Case 2: Node and parent are on the same side.
          rotate(parent, grandparent);
          // Swap colors of parent and grandparent after rotation.
          parent.blackHeight = 1;
          grandparent.blackHeight = 0;
        } else {
          // Case 3: Node and parent are on opposite sides.
          rotate(newNode, parent);
          rotate(newNode, grandparent);
          // Adjust color of new node and grandparent after rotation.
          newNode.blackHeight = 1;
          grandparent.blackHeight = 0;
        }
      } else {
        // Case 1: Uncle is red.
        parent.blackHeight = 1;
        uncle.blackHeight = 1;
        grandparent.blackHeight = 0;
        // Check for further violations up the tree.
        enforceRBTreePropertiesAfterInsert(grandparent);
      }
    }
    // Ensure the root is always black.
    ((RBTNode<T>) root).blackHeight = 1;
  }

  /**
   * This method is a helper method which checks whether the parent and the child are on the same
   * side.
   *
   * @param child  the child node being passed
   * @param parent parent of the child being passed
   * @return returns true if both the parent and child are on the same side, else returns false
   */
  public boolean isOnTheSameSide(RBTNode<T> child, RBTNode<T> parent) {
    if (child.isRightChild()) {
      return parent.isRightChild();
    } else {
      return !parent.isRightChild();
    }
  }

  /**
   * Inserts a new element into the Red-Black Tree while maintaining the properties of a binary
   * search tree and subsequently enforces the Red-Black Tree properties. If the data to be inserted
   * is null, a NullPointerException is thrown.
   *
   * @param data The data of the new node that is to be inserted.
   * @return true always in the current implementation, signaling that the insertion was successful.
   * @throws NullPointerException if the data to be inserted is null.
   */
  @Override
  public boolean insert(T data) throws NullPointerException {
    // Ensure the data is not null, throwing an exception if it is.
    if (data == null) {
      throw new NullPointerException();
    }
    // Create a new RBTNode with the provided data and insert it using helper.
    RBTNode<T> node = new RBTNode<>(data);
    insertHelper(node);
    // Enforce Red-Black Tree properties after insertion.
    enforceRBTreePropertiesAfterInsert(node);
    // Set the blackHeight for the new node accordingly.
    ((RBTNode<T>) root).blackHeight = 1;

    return true;
  }

  /**
   * Test case 1: Test to ensure that the root of the tree has the correct blackHeight after
   * insertion of nodes.
   */
  @Test
  public void testCase1() {
    RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
    RBTNode<Integer> node1 = new RBTNode<>(6);
    RBTNode<Integer> node2 = new RBTNode<>(9);
    // Insert nodes into the RedBlackTree
    redBlackTree.insert(node1.data);
    redBlackTree.insert(node2.data);

    // Assert that the blackHeight of the root is updated accurately
    Assertions.assertEquals(1, ((RBTNode<T>) redBlackTree.root).blackHeight);
  }

  /**
   * Test case 2: Test to ensure that the right child of the root has the correct colour after
   * insertion of nodes.
   */
  @Test
  public void testCase2() {
    RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
    RBTNode<Integer> node1 = new RBTNode<>(6);
    RBTNode<Integer> node2 = new RBTNode<>(9);
    // Insert nodes into the RedBlackTree
    redBlackTree.insert(node1.data);
    redBlackTree.insert(node2.data);

    // Assert that the right child of the root node has accurate colour
    Assertions.assertEquals(0, ((RBTNode<T>) redBlackTree.root).getDownRight().blackHeight);
  }

  /**
   * Test case 3: Test to ensure the blackHeight properties of various nodes in the tree are
   * accurate after several insertions.
   */
  @Test
  public void testCase3() {
    RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
    RBTNode<Integer> node1 = new RBTNode<>(15);
    RBTNode<Integer> node2 = new RBTNode<>(16);
    RBTNode<Integer> node3 = new RBTNode<>(17);
    redBlackTree.insert(node1.data);
    redBlackTree.insert(node2.data);
    redBlackTree.insert(node3.data);

    // Assert that both the root and its children have accurate blackHeight values
    // This checks if enforceRBTreePropertiesAfterInsert() method does its job by rerotating and
    // recolouring(This test should fail for now)
    Assertions.assertEquals(1, ((RBTNode<T>) redBlackTree.root).blackHeight);
    Assertions.assertEquals(0, ((RBTNode<T>) redBlackTree.root).getDownLeft().blackHeight);
    Assertions.assertEquals(0, ((RBTNode<T>) redBlackTree.root).getDownRight().blackHeight);
  }

  /**
   * Test case 4: Ensuring accurate blackHeight and color properties after several insertions.
   * Checks the blackHeight properties of various nodes in the tree.
   */
  @Test
  public void testCase4() {
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    RBTNode<Integer> node1 = new RBTNode<>(30);
    RBTNode<Integer> node2 = new RBTNode<>(25);
    RBTNode<Integer> node3 = new RBTNode<>(45);
    RBTNode<Integer> node4 = new RBTNode<>(55);
    RBTNode<Integer> node5 = new RBTNode<>(44);
    RBTNode<Integer> node6 = new RBTNode<>(50);
    tree.insert(node1.data);
    tree.insert(node2.data);
    tree.insert(node3.data);
    tree.insert(node4.data);
    tree.insert(node5.data);
    tree.insert(node6.data);

    // These assertions validate that the tree adheres to the Red-Black Tree properties,
    // ensuring it is balanced and sorted after each insertion
    Assertions.assertEquals(1, ((RBTNode<T>) tree.root).blackHeight);
    Assertions.assertEquals(0, ((RBTNode<T>) tree.root).getDownRight().blackHeight);
    Assertions.assertEquals(1, ((RBTNode<T>) tree.root).getDownLeft().blackHeight);
    Assertions.assertEquals(1, ((RBTNode<T>) tree.root).getDownRight().getDownRight().blackHeight);
    Assertions.assertEquals(1, ((RBTNode<T>) tree.root).getDownRight().getDownLeft().blackHeight);
    Assertions.assertEquals(0,
        ((RBTNode<T>) tree.root).getDownRight().getDownRight().getDownLeft().blackHeight);
  }

  /**
   * Test case 5: Checking blackHeight and color properties after multiple node insertions.
   * Ensures that all nodes have accurate blackHeight properties after several node insertions.
   */
  @Test
  public void testCase5() {
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    RBTNode<Integer> node1 = new RBTNode<>(30);
    RBTNode<Integer> node2 = new RBTNode<>(25);
    RBTNode<Integer> node3 = new RBTNode<>(45);
    RBTNode<Integer> node4 = new RBTNode<>(55);
    RBTNode<Integer> node5 = new RBTNode<>(44);
    RBTNode<Integer> node6 = new RBTNode<>(50);
    RBTNode<Integer> node7 = new RBTNode<>(60);
    RBTNode<Integer> node8 = new RBTNode<>(41);
    RBTNode<Integer> node9 = new RBTNode<>(49);
    RBTNode<Integer> node10 = new RBTNode<>(43);
    tree.insert(node1.data);
    tree.insert(node2.data);
    tree.insert(node3.data);
    tree.insert(node4.data);
    tree.insert(node5.data);
    tree.insert(node6.data);
    tree.insert(node7.data);
    tree.insert(node8.data);
    tree.insert(node9.data);
    tree.insert(node10.data);

    // These assertions validate that the tree adheres to the Red-Black Tree properties,
    // ensuring it is balanced and sorted after each insertion
    Assertions.assertEquals(1, ((RBTNode<T>) tree.root).blackHeight);
    Assertions.assertEquals(0, ((RBTNode<T>) tree.root).getDownRight().blackHeight);
    Assertions.assertEquals(1, ((RBTNode<T>) tree.root).getDownRight().getDownRight().blackHeight);
    Assertions.assertEquals(0,
        ((RBTNode<T>) tree.root).getDownRight().getDownLeft().getDownLeft().blackHeight);
    Assertions.assertEquals(1, ((RBTNode<T>) tree.root).getDownRight().getDownLeft().blackHeight);
    Assertions.assertEquals(0, ((RBTNode<T>) tree.root).getDownLeft().blackHeight);
    Assertions.assertEquals(1, ((RBTNode<T>) tree.root).getDownLeft().getDownLeft().blackHeight);
    Assertions.assertEquals(0,
        ((RBTNode<T>) tree.root).getDownLeft().getDownRight().getDownLeft().blackHeight);
    Assertions.assertEquals(0,
        ((RBTNode<T>) tree.root).getDownLeft().getDownRight().getDownRight().blackHeight);
  }
}
