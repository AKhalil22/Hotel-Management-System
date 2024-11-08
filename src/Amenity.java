public enum Amenity {
    WIFI(0.0),
    BREAKFAST(0.0),
    POOL_ACCESS(0.0),
    GYM_ACCESS(0.0),
    PRIORITY_PARKING(75.0),
    ROOM_SERVICE(50.0), // 24-HOURS
    MINI_FRIDGE(0.0),
    BIKE_RENTAL(0.0),
    SPA(100.0),
    MEALCARD(250.0); // Access to free meals (local eateries)

    // Add any other amenities here

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
