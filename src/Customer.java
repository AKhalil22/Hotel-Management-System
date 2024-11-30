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

    public Customer(String name, String cardNumber, String phoneNumber, boolean loyaltyMember) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.phoneNumber = phoneNumber;
        this.loyaltyMember = loyaltyMember;
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

}
