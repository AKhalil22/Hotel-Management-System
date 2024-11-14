class Node {
    int key;
    Node left, right;

    public Node(int item) {
        key = item;
        left = right = null;
    }
}

public class BinarySearchTree {
    public Node root;

    // Constructor (set the root node to null whenever we create a new Tree)
    public BinarySearchTree() {
        root = null;
    }

    // ** INSERTING NODES
    Node insert(Node node, int key) {
        // If the tree is empty, return a new node
        if (node == null) {
            node = new Node(key);
            return node;
        }

        // Otherwise, recur down the tree
        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);

        // Return the (unchanged) node pointer
        return node;
    }

    // ** SEARCHING FOR A NODE
    Node search(Node root, int key) {
        // Base Cases: root is null or key is present at root
        if (root == null || root.key == key)
            return root;

        // Key is greater than root's key
        if (root.key < key)
            return search(root.right, key);

        // Key is smaller than root's key
        return search(root.left, key);
    }

    // ** DELETING NODES
    Node deleteNode(Node root, int key) {
        // Base case
        if (root == null)
            return root;

        // Recursive calls for ancestors of
        // node to be deleted
        if (root.key > key) {
            root.left = deleteNode(root.left, key);
            return root;
        } else if (root.key < key) {
            root.right = deleteNode(root.right, key);
            return root;
        }

        // We reach here when root is the node
        // to be deleted.

        // If one of the children is empty
        if (root.left == null) {
            Node temp = root.right;
            return temp;
        } else if (root.right == null) {
            Node temp = root.left;
            return temp;
        }

        // If both children exist
        else {

            Node succParent = root;

            // Find successor
            Node succ = root.right;
            while (succ.left != null) {
                succParent = succ;
                succ = succ.left;
            }

            // Delete successor.  Since successor
            // is always left child of its parent
            // we can safely make successor's right
            // right child as left of its parent.
            // If there is no succ, then assign
            // succ.right to succParent.right
            if (succParent != root)
                succParent.left = succ.right;
            else
                succParent.right = succ.right;

            // Copy Successor Data to root
            root.key = succ.key;

            // Delete Successor and return root
            return root;
        }
    }

    // ** TRAVERSING (in-order):
    void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.key + " ");
            inorder(root.right);
        }
    }

}

// Credits: this code is a modified version of what was found on geeksforgeeks.com. I modified it to make it clearer.