import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Customer {
    // Customer Parameters/Variables
    private String name;
    private String cardNumber;
    private String phoneNumber;
    private Boolean loyaltyMember;
    private String startDate;
    private String endDate;
    private Set<Amenity> amenities;

    public Customer(String name, String cardNumber, String phoneNumber, boolean loyaltyMember) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.phoneNumber = phoneNumber;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getLoyaltyMember() {
        return loyaltyMember;
    }

    public void setLoyaltyMember(Boolean loyaltyMember) {
        this.loyaltyMember = loyaltyMember;
    }

    public String getStartDate(){
        return startDate;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public String getEndDate(){
        return endDate;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    // Method to add an amenity
    public void addAmenity(Amenity amenity) {
        amenities.add(amenity);
    }
    // Method to remove an amenity
    public void removeAmenity(Amenity amenity) {
        amenities.remove(amenity);
    }
    // Method to verify if a room has a specific amenity
    public boolean hasAmenity(Amenity amenity) {
        return amenities.contains(amenity);
    }

}
