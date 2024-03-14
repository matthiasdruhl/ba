import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Collection;
import java.util.NoSuchElementException;


/**
 * Your implementation of a BST.
 *
 * @author Matthias Druhl
 * @version 1.0
 * @userid mdruhl3
 * @GTID 903886853
 *
 * Collaborators: none
 *
 * Resources: none
 */
public class BST<T extends Comparable<? super T>> {


    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;


    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }


    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data into a binary search tree");
        }
        data.forEach(this::add);
    }


    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data into the tree");
        }


        if (size == 0) {
            root = new BSTNode<>(data);
            size++;
            return;
        }


        addHelp(data, root);
    }


    /**
     * A recursive helper method to find the place to add a piece of data and add it.
     * @param data the data to add
     * @param currentNode the node we're traversing from
     */
    private void addHelp(T data, BSTNode<T> currentNode) {
        if (data.compareTo(currentNode.getData()) > 0) {
            if (currentNode.getRight() == null) {
                currentNode.setRight(new BSTNode<>(data));
                size++;
            } else {
                addHelp(data, currentNode.getRight());
            }
        } else if (data.compareTo(currentNode.getData()) < 0) {
            if (currentNode.getLeft() == null) {
                currentNode.setLeft(new BSTNode<>(data));
                size++;
            } else {
                addHelp(data, currentNode.getLeft());
            }
        }
    }




    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data. You MUST use recursion to find and remove the
     * predecessor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (size == 0) {
            throw new NoSuchElementException("The tree is empty, so the data was not found.");
        }


        T returnData = removeHelper(data, root);
        if (returnData == null) {
            throw new NoSuchElementException("The data is null");
        }
        return returnData;
    }


    /**
     * A recursive helper method to remove an element from the tree.
     * @param data the data being removed
     * @param currentNode the node we're traversing from
     * @return the data that was removed
     * @throws NoSuchElementException if the data is not in the tree
     */
    private T removeHelper(T data, BSTNode<T> currentNode) {
        T returnData;
        BSTNode<T> parent;
        if (data.compareTo(currentNode.getData()) == 0) {
            returnData = currentNode.getData();
            if (currentNode.getLeft() == null && currentNode.getRight() == null) {
                if (currentNode == root) {
                    root = null;
                }
                else {
                    parent = findParent(currentNode, root);
                    if (currentNode.getData().compareTo(parent.getData()) < 0) {
                        parent.setLeft(null);
                    } else {
                        parent.setRight(null);
                    }
                }


            } else if (currentNode.getLeft() != null && currentNode.getRight() != null) {
                BSTNode<T> replace = replacement(currentNode.getLeft());
                if (replace == null) {
                    replace = currentNode.getLeft();
                    replace.setRight(currentNode.getRight());
                } else {
                    if (replace.getLeft() != null) {
                        System.out.println(replace.getLeft().getData());

                    }
                    replace.setLeft(currentNode.getLeft());
                    replace.setRight(currentNode.getRight());
                }
                if (currentNode == root) {
                    root = replace;
                } else {
                    parent = findParent(currentNode, root);
                    if (parent.getData().compareTo(currentNode.getData()) < 0) {
                        parent.setRight(replace);
                    } else {
                        parent.setLeft(replace);
                    }
                }

            } else {
                if (currentNode == root) {
                    if (currentNode.getLeft() != null) {
                        root = currentNode.getLeft();
                    } else {
                        root = currentNode.getRight();
                    }
                } else {
                    parent = findParent(currentNode, root);
                    if (currentNode.getLeft() != null) {
                        if (currentNode.getData().compareTo(parent.getData()) < 0) {
                            parent.setLeft(currentNode.getLeft());
                        } else {
                            parent.setRight(currentNode.getLeft());
                        }
                    } else {
                        if (currentNode.getData().compareTo(parent.getData()) > 0) {
                            parent.setRight(currentNode.getRight());
                        } else {
                            parent.setLeft(currentNode.getRight());
                        }

                    }
                }

            }
        } else if (data.compareTo(currentNode.getData()) < 0) {
            if (currentNode.getLeft() == null) {
                return null;
            } else {
                return removeHelper(data, currentNode.getLeft());
            }
        } else {
            if (currentNode.getRight() == null) {
                return null;
            } else {
                return removeHelper(data, currentNode.getRight());
            }
        }
        size --;
        return returnData;

    }

    private BSTNode<T> replacement(BSTNode<T> currentNode) {
        if (currentNode.getRight() == null) {
                return null;

        } else {
            if (currentNode.getRight().getRight()  != null) {
                return replacement(currentNode.getRight());
            } else {
                BSTNode<T> returnNode = currentNode.getRight();
                currentNode.setRight(null);
                return returnNode;
            }
        }

    }

    private BSTNode<T> findParent(BSTNode<T> findNode, BSTNode<T> currentNode) {
        if (findNode.getData().compareTo(currentNode.getData()) > 0) {
            if (currentNode.getRight()
                    .getData().
                    compareTo(findNode
                            .getData()) == 0) {
                return currentNode;
            } else {
                return findParent(findNode, currentNode.getRight());
            }
        } else {
            if (currentNode.getLeft().getData().compareTo(findNode.getData()) == 0) {
                return currentNode;
            } else {
                return findParent(findNode, currentNode.getLeft());
            }
        }
    }


    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get null data from this tree");
        }
        return getHelp(data, root);
    }


    /**
     * A recursive helper method to find the data in the BST
     * @param data the data we're checking for
     * @param currentNode the node we're traversing from
     * @return whether the data is contained
     * @throws NoSuchElementException if data is not found in the tree
     */
    private T getHelp(T data, BSTNode<T> currentNode) {
        if (currentNode == null) {
            throw new NoSuchElementException("The data was not in the tree");
        }
        if (data.compareTo(currentNode.getData()) > 0) {
            return getHelp(data, currentNode.getRight());
        } else if (data.compareTo(currentNode.getData()) < 0) {
            return getHelp(data, currentNode.getLeft());
        } else {
            return currentNode.getData();
        }
    }


    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The binary search tree cannot contain null data");
        }
        return containsHelp(data, root);
    }


    /**
     * A recursive helper method to check if the data exists in the BST
     * @param data the data we're checking for
     * @param currentNode the node we're traversing from
     * @return whether the data is contained
     */
    private boolean containsHelp(T data, BSTNode<T> currentNode) {
        if (currentNode == null) {
            return false;
        } else if (data.compareTo(currentNode.getData()) > 0) {
            return containsHelp(data, currentNode.getRight());
        } else if (data.compareTo(currentNode.getData()) < 0) {
            return containsHelp(data, currentNode.getLeft());
        } else {
            return true;
        }
    }


    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new LinkedList<>();
        preorderHelp(list, root);
        return list;
    }


    /**
     * A recursive helper method that uses preorder traversal
     * @param list the list recording the traversal
     * @param curr the current node to traverse from
     */
    private void preorderHelp(List<T> list, BSTNode<T> curr) {
        if (curr == null) {
            return;
        }
        list.add(curr.getData());
        if (curr.getLeft() != null) {
            preorderHelp(list, curr.getLeft());
        }
        if (curr.getRight() != null) {
            preorderHelp(list, curr.getRight());
        }
    }


    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new LinkedList<>();
        inorderHelp(list, root);
        return list;
    }


    /**
     * A recursive helper method that uses inorder traversal
     * @param list the list recording the traversal
     * @param curr the current node to traverse from
     */
    private void inorderHelp(List<T> list, BSTNode<T> curr) {
        if (curr == null) {
            return;
        }
        if (curr.getLeft() != null) {
            inorderHelp(list, curr.getLeft());
        }
        list.add(curr.getData());
        if (curr.getRight() != null) {
            inorderHelp(list, curr.getRight());
        }
    }


    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new LinkedList<>();
        postorderHelp(list, root);
        return list;
    }


    /**
     * A recursive helper method that uses postorder traversal
     * @param list the list recording the traversal
     * @param curr the current node to traverse from
     */
    private void postorderHelp(List<T> list, BSTNode<T> curr) {
        if (curr == null) {
            return;
        }
        if (curr.getLeft() != null) {
            postorderHelp(list, curr.getLeft());
        }
        if (curr.getRight() != null) {
            postorderHelp(list, curr.getRight());
        }
        list.add(curr.getData());
    }


    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> q = new LinkedList<>();
        List<T> list = new LinkedList<>();
        q.add(root);
        BSTNode<T> curr;
        while (!q.isEmpty() && root != null) {
            curr = q.remove();
            list.add(curr.getData());
            if (curr.getLeft() != null) {
                q.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                q.add(curr.getRight());
            }
        }
        return list;
    }


    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return heightHelp(root, 0);
    }


    /**
     * A recursive helper method to find the height of a node
     * @param curr the node we're measuring the height of
     * @param count the height of the nodes above
     * @return the height of curr
     */
    private int heightHelp(BSTNode<T> curr, int count) {
        if (curr.getLeft() == null && curr.getRight() == null) {
            return count;
        }
        int leftH = 0;
        int rightH = 0;
        if (curr.getLeft() != null) {
            leftH = heightHelp(curr.getLeft(), count + 1);
        }
        if (curr.getRight() != null) {
            rightH = heightHelp(curr.getRight(), count + 1);
        }
        return Math.max(leftH, rightH);
    }


    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }


    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     *
     * This must be done recursively.
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   11   15   40
     *  /
     * 10
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 10] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        List<T> list = new LinkedList<>();
        return maxDataPerLevelHelp(root, list, 0);
    }


    /**
     * a recursive helper method to find the max values per level in the BST.
     * @param node current node we're checking for.
     * @param result current list.
     * @param level current level of the BST at the traversal.
     * @return list containing max values per level.
     */
    private List<T> maxDataPerLevelHelp(BSTNode<T> node, List<T> result, int level) {
        if (node == null) {
            return result;
        }


        // If the current level is equal to the size of the result list, add the data to the list
        if (level == result.size()) {
            result.add(node.getData());
        } else {
            // If the current level is greater than the size of the result list,
            // update the data at the corresponding level
            result.set(level, node.getData());
        }


        // Recur for the left and right subtrees with increased level
        maxDataPerLevelHelp(node.getLeft(), result, level + 1);
        maxDataPerLevelHelp(node.getRight(), result, level + 1);
        return result;
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }


    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}


