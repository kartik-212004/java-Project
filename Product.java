public class Product {
    private String name;
    private double price;
    private String description;
    private String imagePath;
    private double rating;
    private int reviewCount;
    
    public Product(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = null;
        this.rating = 0.0;
        this.reviewCount = 0;
    }
    
    public Product(String name, double price, String description, String imagePath) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.rating = 0.0;
        this.reviewCount = 0;
    }
    
    public Product(String name, double price, String description, String imagePath, double rating, int reviewCount) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public double getRating() {
        return rating;
    }
    
    public int getReviewCount() {
        return reviewCount;
    }
    
    @Override
    public String toString() {
        return name + " - ₹" + price;
    }
} 