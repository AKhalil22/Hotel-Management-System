import java.util.*;

public class HotelManagementSystem {
    public static void main(String[] args) {
        // Store customers O(1) Lookup (Time Complexity)
        ArrayList<Customer> customers = new ArrayList<>();
        // Key-Value Pair | Key = Room (Object) | Value = Price (Integer)
        TreeMap<Room, Integer> map = new TreeMap<>();
        // Wait-list for booked rooms, Priority will be given to member holders
        Queue<Customer> waitlist = new PriorityQueue<>();
        // Store rooms, O(1) Search/Lookup & O(1) Inserting/Storage
        BinarySearchTree treeHouse = new BinarySearchTree();

        Room room1 = new Room(1, "Single");
        /*TODO: Integrate Binary Search Tree (BST)
           treeHouse.root = treeHouse.insert(treeHouse.root, room1);
        */
    }

}
