import java.util.Comparator;

public class CustomerComparator implements Comparator<Customer> {
    @Override
    public int compare(Customer c1, Customer c2) {
        // compare customers based on their loyalty status
        if (c1.getLoyaltyMember() && !c2.getLoyaltyMember()) {
            return -1;
        } else if (!c1.getLoyaltyMember() && c2.getLoyaltyMember()) {
            return 1;
        } else {
            // if both customers have the same loyalty status, compare them based on their names
            return c1.getName().compareTo(c2.getName());
        }
    }
}
