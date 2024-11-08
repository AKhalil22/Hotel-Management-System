public class Customer {
    // Customer Parameters/Variables
    private String name;
    private Integer cardNumber;
    private Boolean loyaltyMember;

    public Customer(String name, Integer cardNumber, Boolean loyaltyMember) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.loyaltyMember = loyaltyMember;
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
}
