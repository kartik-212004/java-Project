public class Product {
    private String name;
    private double price;
    private String description;
    private String imagePath;
    
    public Product(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = null;
    }
    
    public Product(String name, double price, String description, String imagePath) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
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
    
    @Override
    public String toString() {
        return name + " - ₹" + price;
    }
} 