import java.util.ArrayList;
import java.util.List;

class RoomNode {
    int roomKey; // Room Key = Room Number
    String roomType;
    double roomPrice;
    boolean isAvailable;
    RoomNode left, right;

    public RoomNode(int roomKey, String roomType, double roomPrice, boolean isAvailable) {
        this.roomKey = roomKey;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isAvailable = isAvailable;
        this.left = this.right = null;
    }
}

public class BinarySearchTree {
    RoomNode root;

    // Constructor (set the root node to null whenever we create a new Tree)
    public BinarySearchTree() {
        root = null;
    }

    // ** INSERTING NODES
    RoomNode insert(RoomNode node, int roomKey, String roomType, double roomPrice, boolean isAvailable) {
        // If the tree is empty, return a new node
        if (root == null) {
            root = new RoomNode(roomKey, roomType, roomPrice, isAvailable);
            return root;
        } else if (node == null) {
            node = new RoomNode(roomKey, roomType, roomPrice, isAvailable);
            return node;
        }

        // Otherwise, recur down the tree
        if (roomKey < node.roomKey)
            node.left = insert(node.left, roomKey, roomType, roomPrice, isAvailable);
        else if (roomKey > node.roomKey)
            node.right = insert(node.right, roomKey, roomType, roomPrice, isAvailable);

        // Return the (unchanged) node pointer
        return node;
    }

    // ** SEARCHING FOR A NODE
    RoomNode search(RoomNode root, int roomKey) {
        // Base Cases: root is null or key is present at root
        if (root == null || root.roomKey == roomKey)
            return root;

        // Key is greater than root's key
        if (root.roomKey < roomKey)
            return search(root.right, roomKey);

        // Key is smaller than root's key
        return search(root.left, roomKey);
    }

    // ** DELETING NODES
    RoomNode deleteRoom(RoomNode root, int roomKey) {
        // Base case
        if (root == null)
            return root;

        // Recursive calls for ancestors of nodes to be deleted
        if (root.roomKey > roomKey) {
            root.left = deleteRoom(root.left, roomKey);
            return root;
        } else if (root.roomKey < roomKey) {
            root.right = deleteRoom(root.right, roomKey);
            return root;
        }

        // We reach here when root is the node to be deleted.
        // If one of the children is empty
        if (root.left == null) {
            RoomNode temp = root.right;
            return temp;
        } else if (root.right == null) {
            RoomNode temp = root.left;
            return temp;
        }

        // If both children exist
        else {

            RoomNode succParent = root;

            // Find successor
            RoomNode succ = root.right;
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
            root.roomKey = succ.roomKey;

            // Delete Successor and return root
            return root;
        }
    }

    // ** TRAVERSING (in-order):
    List<Integer> inorder(RoomNode root, List<Integer> result) {
        if (result == null) result = new ArrayList<>();
        if (root != null) {
            inorder(root.left, result);
            result.add(root.roomKey);
            inorder(root.right, result);
        }
        return result;
    }

}

// Credits: this code is a modified version of what was found on geeksforgeeks.com. I modified it to make it clearer.