import java.util.HashSet;
import java.util.Set;

public class Customer {
    // Customer Parameters/Variables
    private String name;
    private Integer cardNumber;
    private Boolean loyaltyMember;
    private Set<Amenity> amenities;

    public Customer(String name, Integer cardNumber, Boolean loyaltyMember) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.loyaltyMember = loyaltyMember;
        this.amenities = new HashSet<>(); // Initialize an empty Hashset of amenities
    }

    // Getter & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Boolean getLoyaltyMember() {
        return loyaltyMember;
    }

    public void setLoyaltyMember(Boolean loyaltyMember) {
        this.loyaltyMember = loyaltyMember;
    }

    // Method to add an amenity
    public void addAmenity(Amenity amenity) {
        amenities.add(amenity);
    }

    // Method to remove an amenity
    public void removeAmenity(Amenity amenity) {
        amenities.remove(amenity);
    }

    // Method to check if a room has a specific amenity
    public boolean hasAmenity(Amenity amenity) {
        return amenities.contains(amenity);
    }

}
