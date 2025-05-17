# Simple Online Shopping Desktop Application

A minimal Java Swing application that simulates an online shopping experience with product listings, shopping cart, and checkout functionality.

## Features

- View a list of products with names, prices, and descriptions
- Add products to your shopping cart
- Remove products from your cart
- View the total cost of items in your cart
- Complete a simulated checkout process

## Requirements

- Java Development Kit (JDK) 8 or higher

## How to Compile and Run

### Using Command Line

1. Compile the Java files:
```
javac Product.java OnlineShopApp.java
```

2. Run the application:
```
java OnlineShopApp
```

### Using an IDE

1. Import the project into your favorite IDE (Eclipse, IntelliJ IDEA, NetBeans, etc.)
2. Build the project
3. Run the `OnlineShopApp` class

## Usage Instructions

1. The application window is divided into two main sections:
   - Left side: Product listings
   - Right side: Shopping cart

2. To add a product to your cart, click the "Add to Cart" button next to the product.

3. To remove a product from your cart, click the "Remove" button next to the product in your cart.

4. When you're ready to check out, click the "Checkout" button at the bottom of the cart panel.

5. A confirmation dialog will display your order summary and total amount.

## Project Structure

- `OnlineShopApp.java` - Main application class with UI components and logic
- `Product.java` - Class representing a product with properties and methods 