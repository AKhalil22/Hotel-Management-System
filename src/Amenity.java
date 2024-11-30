public enum Amenity {
    PRIORITY_PARKING(75.0),
    BREAKFAST_BUFFET(50.0), // 24-HOURS
    SPA(100.0),
    MEAL_CARD(250.0); // Access to free meals (local eateries)

    double price; // Optionally make "final" to prevent price changes

    // Setter (Enum)
    Amenity(double price) {
        this.price = price;
    }

    // Getter (Enum)
    public double getPrice() {
        return price;
    }

}
