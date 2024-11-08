import java.util.*;

public class HotelManagementSystem {
    // Store customers O(1) Lookup (Time Complexity)
    ArrayList<Customer> customers = new ArrayList<>();
    // Key-Value Pair | Key = Room (Object) | Value = Price (Integer)
    TreeMap<Room, Integer> map = new TreeMap<>();
    // Wait-list for booked rooms, Priority will be given to member holders
    Queue<Customer> waitlist = new PriorityQueue<>();

    // TODO: Binary Search Tree

    public static void main(String[] args) {

    }

}
