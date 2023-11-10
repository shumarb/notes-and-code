// Reference: https://www.youtube.com/watch?v=TbvhGcf6UJU&ab_channel=nptelhrd, https://www.geeksforgeeks.org/insertion-in-an-avl-tree/
import java.util.HashSet;
import java.util.Random;
  
public class AVLTree { 
  
    private int numberOfElements;
    private Node root; 
    private Random myRandom = new Random();

    // Gets height of a node
    // Precon: Nil
    // Postcon: Nil
    private int getsHeight(Node node) { 
        return (node == null) ? -1 : node.getsHeight();
    } 
  
    // A utility function to get maximum of two integers 
    private int getsMaximum(int a, int b) { 
        return (a > b) ? a : b; 
    } 
  
    // Get Balance factor of Node node 
    private int computesBalanceFactor(Node node) { 
        return (node == null) ? 0 : getsHeight(node.getsLeftChild()) - getsHeight(node.getsRightChild()); 
    } 

    // Displays Line
    // Precon: Nil
    // Postcon: Nil
    private void displaysLine() {
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    // Displays Message
    // Precon: Nil
    // Postcon: Nil
    private void displaysMessage(String sentence) {
        System.out.println(sentence);
    }

    // Displays New Line
    // Precon: Nil
    // Postcon: Nil
    private void displaysNewLine() {
        System.out.println();
    }

    // Displays two new Line
    // Precon: Nil
    // Postcon: Nil
    private void displaysTwoNewLines() {
        System.out.println();
        System.out.println();
    }

    // Displays element information
    // Precon: All elements inserted into AVL Tree
    // Postcon: Nil
    private void displaysElementInformation(Node node) {
        System.out.print("[" + node.getsData()
                        + ", h: " + node.getsHeight()
                        + "]  "
        );
    }

    // Displays AVL Tree
    // Precon: AVL Tree is formed
    // Postcon: Nil
    private void displaysAVLTree() {
        displaysMessage("======= Traversal =======");
        displaysMessage("Displaying AVL Tree of " + numberOfElements + ":");
        displaysNewLine();
        System.out.print(" * In-order:\t");
        displaysAVLTree(root, "in-order");
        displaysTwoNewLines();
        System.out.print(" * Pre-order:\t");
        displaysAVLTree(root, "pre-order");
        displaysTwoNewLines();
        System.out.print(" * Post-order:\t");
        displaysAVLTree(root, "post-order");
        displaysTwoNewLines();
        displaysLine();
    }

    // Updates height of node
    // Precon: Node inserted into AVL Tree
    // Postcon: Rotate AVL Tree if required
    private void updatesHeight(Node node, String operation) {
        if (node == null) {
            return;
        }

        if (operation.equals("increase")) {
            node.setsHeight(1 + getsMaximum(getsHeight(node.getsLeftChild()), getsHeight(node.getsRightChild())));

            
            // If an element's child has a different height value than it's parent,
            // update the height value of all it's elements in the subtrees
            if (node.hasLeftChild() && (node.getsLeftChild().getsHeight() != node.getsHeight() - 1)) {
                updatesHeight(node.getsLeftChild(), "updates height of elements in left and right subtrees");
            }
            if (node.hasRightChild() && (node.getsRightChild().getsHeight() != node.getsHeight() - 1)) {
                updatesHeight(node.getsRightChild(), "updates height of elements in left and right subtrees");
            }
        }

        if (operation.equals("updates height of elements in left and right subtrees")) {
            if (node.hasLeftChild() && (node.getsLeftChild().getsHeight() != node.getsHeight() - 1)) {
                node.getsLeftChild().setsHeight(node.getsHeight() - 1);
                updatesHeight(node.getsLeftChild(), "updates height of elements in left and right subtrees");
            }
            if (node.hasRightChild() && (node.getsRightChild().getsHeight() != node.getsHeight() - 1)) {
                node.getsRightChild().setsHeight(node.getsHeight() - 1);
                updatesHeight(node.getsRightChild(), "updates height of elements in left and right subtrees");
            }
        }
    }

    // Forms data set to insert into AVL Tree
    // Precon: Nil
    // Postcon: Nil
    private HashSet <Integer> formsSet() {
        HashSet <Integer> set = new HashSet <> ();
        int data;
        numberOfElements = myRandom.nextInt(5, 12);
        int order = myRandom.nextInt(1, 3);
        if (order < 3) {
            data = myRandom.nextInt(-10, 10);
            // Ascending order of number to be inserted into AVL tree
            for (int i = 0; i < numberOfElements; i++) {
                if (order == 1) {
                    // Ascending order of number to be inserted into AVL tree
                    set.add(data++);
                } else {
                    // Descending order of number to be inserted into AVL tree
                    set.add(data--);
                }
            }
        } else {
            for (int i = 0; i < numberOfElements; i++) {
                data = myRandom.nextInt(-10, 10);
                while (set.contains(data)) {
                    data = myRandom.nextInt(-10, 10);
                }
                set.add(data);
            }
        }
        return set;
    }

    private void formsAVLTree() {
        displaysLine();
        displaysMessage("======= Insertion =======");
        HashSet <Integer> set = formsSet();
        System.out.println("Forming AVL Tree with " + set.size() + " elements:");
        displaysNewLine();
        for (int data: set) {
            System.out.println(" * inserting " + data);
            displaysNewLine();
            root = insertion(root, data);
        }
        // AVL Tree formed but value of each element's height may not be 1 less
        // than it's parent, so update to ensure it is correct.
        updatesHeight(root, "updates subtree height");
        displaysLine();
    }

    // Insert an element into AVL Tree
    // Precon: AVL Tree with specified number of elements not formed
    // Postcon: AVL Tree with specified number of elements formed
    private Node insertion(Node node, int data) { 
        if (node == null) {
            return new Node(data);
        }
        if (data < node.getsData()) {
            node.setsLeftChild(insertion(node.getsLeftChild(), data));
        } 
        if (data > node.getsData()) {
            node.setsRightChild(insertion(node.getsRightChild(), data)); 
        } 
        return rebalancesAVLTree(node, data);
    } 
    
    // Rebalances AVL Tree
    // Precon: Element inserted into AVL Tree
    // Postcon: Insert next element into AVL Tree
    private Node rebalancesAVLTree(Node node, int data) {
        // 1. Update both node and it's children (if applicable) heights
        updatesHeight(node, "increase");

        // 2. Rotate node to rebalance AVL Tree if required
        int balanceFactor = computesBalanceFactor(node); 
  
        // 3. There are 4 situation when current tree is imbalanced (hence it is not AVL tree so far)
        // 3.1. Left-Left insertion ==> Right rotation
        if (balanceFactor > 1 && data < node.getsLeftChild().getsData()) {
            return rightRotation(node); 
        }
  
        // 3.2. Right-Right insertion ==> Left rotation
        if (balanceFactor < -1 && data > node.getsRightChild().getsData()) {
            return leftRotation(node);
        }
  
        // 3.3. Left-Right insertion ==> Left-Right,
        // This means Left rotation on node's left child, then Right rotation on node
        if (balanceFactor > 1 && data > node.getsLeftChild().getsData()) { 
            node.setsLeftChild(leftRotation(node.getsLeftChild())); 
            return rightRotation(node); 
        } 
  
        // 3.4. Right-Left insertion ==> Right-Left rotation
        // This means Right rotation on node's right child, then Left rotation on node
        if (balanceFactor < -1 && data < node.getsRightChild().getsData()) { 
            node.setsRightChild(rightRotation(node.getsRightChild())); 
            return leftRotation(node); 
        } 

        // Node is balanced, so no rotations required
        return node;
    }

    // Right Rotation
    // Precon: Balance factor of node is < -1
    // Postcon: Nil
    private Node rightRotation(Node node) { 
        // 1. Obtain node's predecessor
        // Predecessor is the largest element smaller than the node
        Node leftChild = node.getsLeftChild(); 
        Node predecessor = leftChild.getsRightChild(); 
  
        // 2. Right rotation
        // Note that node > leftChild > predecessor
        leftChild.setsRightChild(node);
        node.setsLeftChild(predecessor);
  
        // 3. Update heights 
        updatesHeight(node, "increase");
        updatesHeight(leftChild, "increase");
        
        // 4. Return new root
        return leftChild; 
    } 
  
    // Left Rotation
    // Precon: Balance factor of node is > 1
    // Postcon: Nil
    private Node leftRotation(Node node) { 
        // 1. Obtain node's successor
        // Successor is the smallest element greater than the node
        Node rightChild = node.getsRightChild(); 
        Node successor = rightChild.getsLeftChild(); 
  
        // 2. Left rotation 
        // Note that node < rightChild < successor
        rightChild.setsLeftChild(node);
        node.setsRightChild(successor);
  
        // 3. Update heights 
        updatesHeight(node, "increase");
        updatesHeight(rightChild, "increase");
  
        // 4. Return new root
        return rightChild; 
    } 

    // Displays AVL Tree
    // Precon: All elements inserted into AVL Tree
    // Postcon: Nil
    private void displaysAVLTree(Node node, String traversalOrder) {
        if (node == null) {
            return;
        }
        
        if (traversalOrder.equals("in-order")) {
            displaysAVLTree(node.getsLeftChild(), traversalOrder);
            displaysElementInformation(node);
            displaysAVLTree(node.getsRightChild(), traversalOrder);
        }

        if (traversalOrder.equals("pre-order")) {
            displaysElementInformation(node);
            displaysAVLTree(node.getsLeftChild(), traversalOrder);
            displaysAVLTree(node.getsRightChild(), traversalOrder);
        }

        if (traversalOrder.equals("post-order")) {
            displaysAVLTree(node.getsLeftChild(), traversalOrder);
            displaysAVLTree(node.getsRightChild(), traversalOrder);
            displaysElementInformation(node);
        }
    }

    // Searches for element in AVL Tree
    // Precon: AVL Tree formed
    // Postcon: Nil
    private void searchesAVLTree() {
        displaysMessage("======= Search =======");
        int key = myRandom.nextInt(-10, 10);
        System.out.println("Search for " + key + " | Starting from the root of the AVL Tree...");
        search(root, key);
        displaysNewLine();
        displaysLine();
        displaysMessage("======= Search Minimum =======");
        displaysMessage("Search for minimum element | Starting from the root of the AVL Tree...");
        search(root, "searches minimum");
        displaysMessage("======= Search Maximum =======");
        displaysMessage("Search for maximum element | Starting from the root of the AVL Tree...");
        search(root, "searches maximum");
    }

    // Searches for maximum or minimum element
    // Precon: AVL Tree formed
    // Postcon: Nil
    private void search(Node node, String searchOperation) {
        displaysNewLine();
        if (searchOperation.equals("searches minimum")) {
            System.out.print(" * current element: " + node.getsData());
            if (node.hasLeftChild()) {
                System.out.println(" | going to left child of " + node.getsData());
                search(node.getsLeftChild(), searchOperation);
            } else {
                System.out.println(" | " + node.getsData() + " has no left child, so the search ends here");
                displaysNewLine();
                System.out.println("Minimum element: " + node.getsData());
                displaysLine();
            }
        }
        if (searchOperation.equals("searches maximum")) {
            System.out.print(" * current element: " + node.getsData());
            if (node.hasRightChild()) {
                System.out.println(" | going to right child of " + node.getsData());
                search(node.getsRightChild(), searchOperation);
            } else {
                System.out.println(" | " + node.getsData() + " has no right child, so the search ends here");
                displaysNewLine();
                System.out.println("Maximum element: " + node.getsData());
                displaysLine();
            }
        }
    }

    // Searches for element in AVL Tree
    // Precon: AVL Tree formed
    // Postcon: Searches for maximum or minimum element
    private void search(Node node, int key) {
        displaysNewLine();
        System.out.print(" * current element: " + node.getsData());
        if (key == node.getsData()) {
            System.out.print(" | Found!");
            displaysTwoNewLines();
            System.out.println(key + " is in the AVL Tree");
        }

        if (key > node.getsData()) {
            if (node.hasRightChild()) {
                System.out.println(" | " + key + " > " + node.getsData() + ", so go to right child of " + node.getsData());
                search(node.getsRightChild(), key);
            } else {
                System.out.println(" | " + node.getsData() + " has no right child, so the search ends here");
                displaysNewLine();
                System.out.println(key + " is not in the AVL Tree");
            }
        }

        if (key < node.getsData()) {
            if (node.hasLeftChild()) {
                System.out.println(" | " + key + " < " + node.getsData() + ", so go to left child of " + node.getsData());
                search(node.getsLeftChild(), key);
            } else {
                System.out.println(" | " + node.getsData() + " has no left child, so the search ends here");
                displaysNewLine();
                System.out.println(key + " is not in the AVL Tree");
            }
        }
    }
    
    private void run() {
        formsAVLTree();
        displaysAVLTree();
        searchesAVLTree();
    }

    public static void main(String[] args) { 
        AVLTree tree = new AVLTree();
        tree.run();
    } 
} 

class Node { 
    private int data;
    private int height;
    private Node leftChild;
    private Node rightChild; 
  
    public Node(int data) { 
        this.data = data; 
    }

    public boolean hasLeftChild() {
        return this.leftChild != null;
    }
    
    public boolean hasRightChild() {
        return this.rightChild != null;
    }

    public int getsData() {
        return this.data;
    }

    public int getsHeight() {
        return this.height;
    }
    
    public Node getsLeftChild() {
        return this.leftChild;
    }

    public Node getsRightChild() {
        return this.rightChild;
    }

    public void setsHeight(int height) {
        this.height = height;
    }

    public void setsLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public void setsRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }
} 