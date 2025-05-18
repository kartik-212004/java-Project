import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class OnlineShopApp {
    // Main frame
    private JFrame frame;
    
    // Panels
    private JPanel productPanel;
    private JPanel cartPanel;
    
    // Components
    private JLabel totalLabel;
    private JButton checkoutButton;
    
    // Data
    private ArrayList<Product> products;
    private ArrayList<Product> cart;
    private double totalAmount = 0.0;
    
    // Colors
    private final Color AMAZON_BLUE = new Color(19, 25, 33);
    private final Color AMAZON_LIGHT_BLUE = new Color(35, 47, 62);
    private final Color AMAZON_YELLOW = new Color(254, 189, 105);
    private final Color AMAZON_ORANGE = new Color(240, 136, 4);
    private final Color BACKGROUND_COLOR = new Color(243, 243, 243);
    private final Color CARD_COLOR = Color.WHITE;
    
    public OnlineShopApp() {
        // Initialize data
        initializeData();
        
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and set up the window
        frame = new JFrame("Amazon-Style Shop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Create header
        createHeader();
        
        // Create product panel
        createProductPanel();
        
        // Create cart panel
        createCartPanel();
        
        // Add panels to frame
        // Create split pane for products and cart
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                                             new JScrollPane(productPanel), 
                                             new JScrollPane(cartPanel));
        splitPane.setDividerLocation(700);
        splitPane.setDividerSize(5);
        splitPane.setBorder(null);
        frame.add(splitPane, BorderLayout.CENTER);
        
        // Create footer
        createFooter();
        
        // Show the window
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AMAZON_BLUE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        // Logo/Title
        JLabel headerLabel = new JLabel("Amazon-Style Shop");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        
        // Search bar
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(AMAZON_ORANGE);
        searchButton.setForeground(Color.WHITE);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        
        // Navigation links
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navPanel.setOpaque(false);
        
        JLabel accountLabel = new JLabel("Hello, Sign in");
        accountLabel.setForeground(Color.WHITE);
        accountLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel ordersLabel = new JLabel("Returns & Orders");
        ordersLabel.setForeground(Color.WHITE);
        ordersLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        navPanel.add(accountLabel);
        navPanel.add(Box.createHorizontalStrut(20));
        navPanel.add(ordersLabel);
        
        // Add components to header
        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.CENTER);
        headerPanel.add(navPanel, BorderLayout.EAST);
        
        // Navigation bar
        JPanel navBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        navBarPanel.setBackground(AMAZON_LIGHT_BLUE);
        navBarPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        
        String[] categories = {"All", "Electronics", "Computers", "Smart Home", "Deals", "Customer Service"};
        for (String category : categories) {
            JLabel categoryLabel = new JLabel(category);
            categoryLabel.setForeground(Color.WHITE);
            categoryLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            navBarPanel.add(categoryLabel);
        }
        
        // Combine header and navbar
        JPanel fullHeaderPanel = new JPanel(new BorderLayout());
        fullHeaderPanel.add(headerPanel, BorderLayout.NORTH);
        fullHeaderPanel.add(navBarPanel, BorderLayout.CENTER);
        
        frame.add(fullHeaderPanel, BorderLayout.NORTH);
    }
    
    private void createFooter() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(AMAZON_BLUE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JLabel footerLabel = new JLabel("© 2023 Amazon-Style Shop. All rights reserved.");
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);
        
        frame.add(footerPanel, BorderLayout.SOUTH);
    }
    
    private void initializeData() {
        // Initialize product list with some sample products
        products = new ArrayList<>();
        products.add(new Product("Laptop", 899.99, "High-performance laptop with 16GB RAM", "images/laptop.jpg"));
        products.add(new Product("Smartphone", 499.99, "Latest model with 128GB storage", "images/smartphone.jpg"));
        products.add(new Product("Headphones", 89.99, "Noise-cancelling wireless headphones", "images/headphones.jpeg"));
        products.add(new Product("Tablet", 299.99, "10-inch tablet with HD display", "images/tablet.jpeg"));
        products.add(new Product("Bluetooth Speaker", 59.99, "Portable speaker with 10-hour battery life", "images/speaker.jpeg"));
        products.add(new Product("Smart Watch", 199.99, "Fitness tracker with heart rate monitor", null));
        products.add(new Product("Wireless Mouse", 29.99, "Ergonomic design with long battery life", null));
        products.add(new Product("External SSD", 119.99, "1TB high-speed portable storage", null));
        
        // Initialize empty cart
        cart = new ArrayList<>();
    }
    
    private void createProductPanel() {
        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        productPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        productPanel.setBackground(BACKGROUND_COLOR);
        
        // Title and subtitle
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Today's Deals");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(17, 17, 17));
        
        JLabel subtitleLabel = new JLabel("Great prices on popular products");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(86, 86, 86));
        
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        productPanel.add(titlePanel);
        productPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Grid layout for products
        JPanel productsGrid = new JPanel(new GridLayout(0, 2, 15, 15));
        productsGrid.setOpaque(false);
        
        // Add products to grid
        for (Product product : products) {
            JPanel productCard = createProductCard(product);
            productsGrid.add(productCard);
        }
        
        // Make grid scrollable
        productsGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        productPanel.add(productsGrid);
    }
    
    private JPanel createProductCard(Product product) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(222, 222, 222), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Product image
        if (product.getImagePath() != null) {
            try {
                BufferedImage originalImage = ImageIO.read(new File(product.getImagePath()));
                
                // Resize image to fit card
                int targetWidth = 150;
                int targetHeight = 150;
                
                // Calculate dimensions while maintaining aspect ratio
                double aspectRatio = (double) originalImage.getWidth() / originalImage.getHeight();
                int width = targetWidth;
                int height = (int) (targetWidth / aspectRatio);
                
                // If height is too large, scale based on height instead
                if (height > targetHeight) {
                    height = targetHeight;
                    width = (int) (targetHeight * aspectRatio);
                }
                
                // Create scaled image
                Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(scaledImage);
                
                // Create image panel
                JLabel imageLabel = new JLabel(imageIcon);
                imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                // Add to panel with padding
                JPanel imagePanel = new JPanel();
                imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
                imagePanel.setOpaque(false);
                imagePanel.add(Box.createVerticalGlue());
                imagePanel.add(imageLabel);
                imagePanel.add(Box.createVerticalGlue());
                imagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));
                imagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                
                card.add(imagePanel);
                card.add(Box.createRigidArea(new Dimension(0, 10)));
                
            } catch (IOException e) {
                System.err.println("Error loading image: " + product.getImagePath());
                e.printStackTrace();
            }
        }
        
        // Product name
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Price with discount styling
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pricePanel.setOpaque(false);
        
        JLabel priceLabel = new JLabel(String.format("₹%.2f", product.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        priceLabel.setForeground(new Color(177, 39, 4)); // Amazon red price
        
        JLabel originalPriceLabel = new JLabel(String.format("₹%.2f", product.getPrice() * 1.2));
        originalPriceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        originalPriceLabel.setForeground(Color.GRAY);
        
        // Strike through the original price
        StrikethroughLabel strikethroughLabel = new StrikethroughLabel(originalPriceLabel);
        
        pricePanel.add(priceLabel);
        pricePanel.add(strikethroughLabel);
        pricePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Discount badge
        JLabel discountLabel = new JLabel("Save 20%");
        discountLabel.setFont(new Font("Arial", Font.BOLD, 12));
        discountLabel.setOpaque(true);
        discountLabel.setBackground(new Color(204, 12, 57)); // Amazon deal badge color
        discountLabel.setForeground(Color.WHITE);
        discountLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        discountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Product description
        JLabel descLabel = new JLabel("<html><body width='200px'>" + product.getDescription() + "</body></html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Rating stars
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        ratingPanel.setOpaque(false);
        
        // Add 4 filled stars and 1 half star
        for (int i = 0; i < 4; i++) {
            JLabel starLabel = new JLabel("★");
            starLabel.setForeground(AMAZON_ORANGE);
            ratingPanel.add(starLabel);
        }
        JLabel halfStarLabel = new JLabel("☆");
        halfStarLabel.setForeground(AMAZON_ORANGE);
        ratingPanel.add(halfStarLabel);
        
        JLabel reviewsLabel = new JLabel("(127)");
        reviewsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        reviewsLabel.setForeground(new Color(0, 113, 133)); // Amazon link blue
        ratingPanel.add(reviewsLabel);
        
        ratingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Prime badge
        JLabel primeLabel = new JLabel("Prime");
        primeLabel.setFont(new Font("Arial", Font.BOLD, 11));
        primeLabel.setOpaque(true);
        primeLabel.setBackground(AMAZON_BLUE);
        primeLabel.setForeground(AMAZON_ORANGE);
        primeLabel.setBorder(BorderFactory.createEmptyBorder(1, 3, 1, 3));
        primeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Delivery info
        JLabel deliveryLabel = new JLabel("Get it by Tomorrow, FREE Delivery");
        deliveryLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        deliveryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Add to cart button
        JButton addButton = new JButton("Add to Cart");
        addButton.setBackground(AMAZON_YELLOW);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart(product);
            }
        });
        
        // Buy now button
        JButton buyButton = new JButton("Buy Now");
        buyButton.setBackground(AMAZON_ORANGE);
        buyButton.setForeground(Color.WHITE);
        buyButton.setFocusPainted(false);
        buyButton.setBorderPainted(false);
        buyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buyButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Add all components to card with spacing
        card.add(nameLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(ratingPanel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(pricePanel);
        card.add(Box.createRigidArea(new Dimension(0, 3)));
        card.add(discountLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(primeLabel);
        card.add(Box.createRigidArea(new Dimension(0, 3)));
        card.add(deliveryLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(descLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Button panel for layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonPanel.add(buyButton);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        card.add(buttonPanel);
        
        return card;
    }
    
    // Custom component for strikethrough text
    private class StrikethroughLabel extends JPanel {
        public StrikethroughLabel(JLabel label) {
            setLayout(new BorderLayout());
            setOpaque(false);
            add(label, BorderLayout.CENTER);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GRAY);
            int y = getHeight() / 2;
            g.drawLine(0, y, getWidth(), y);
        }
    }
    
    private void createCartPanel() {
        cartPanel = new JPanel();
        cartPanel.setLayout(new BorderLayout(10, 10));
        cartPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        cartPanel.setBackground(CARD_COLOR);
        
        // Cart header
        JPanel cartHeaderPanel = new JPanel(new BorderLayout());
        cartHeaderPanel.setOpaque(false);
        
        JLabel cartTitleLabel = new JLabel("Shopping Cart");
        cartTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        cartHeaderPanel.add(cartTitleLabel, BorderLayout.WEST);
        
        // Cart items container
        JPanel cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        cartItemsPanel.setOpaque(false);
        
        // Cart summary panel
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(1, 0, 0, 0, new Color(222, 222, 222)),
            BorderFactory.createEmptyBorder(15, 0, 0, 0)
        ));
        summaryPanel.setOpaque(false);
        
        // Order summary
        JLabel summaryLabel = new JLabel("Order Summary");
        summaryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        summaryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Price details
        JPanel priceDetailsPanel = new JPanel();
        priceDetailsPanel.setLayout(new GridLayout(3, 2, 5, 5));
        priceDetailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        priceDetailsPanel.setOpaque(false);
        
        priceDetailsPanel.add(new JLabel("Items:"));
        JLabel itemsValueLabel = new JLabel("₹0.00");
        priceDetailsPanel.add(itemsValueLabel);
        
        priceDetailsPanel.add(new JLabel("Shipping:"));
        priceDetailsPanel.add(new JLabel("FREE"));
        
        priceDetailsPanel.add(new JLabel("Total:"));
        totalLabel = new JLabel("₹0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(new Color(177, 39, 4)); // Amazon red price
        priceDetailsPanel.add(totalLabel);
        
        // Checkout button
        checkoutButton = new JButton("Proceed to Checkout");
        checkoutButton.setBackground(AMAZON_YELLOW);
        checkoutButton.setFocusPainted(false);
        checkoutButton.setBorderPainted(false);
        checkoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkoutButton.setEnabled(false);
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout();
            }
        });
        
        // Add components to summary panel
        summaryPanel.add(summaryLabel);
        summaryPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        summaryPanel.add(priceDetailsPanel);
        summaryPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        summaryPanel.add(checkoutButton);
        
        // Add all panels to cart panel
        cartPanel.add(cartHeaderPanel, BorderLayout.NORTH);
        cartPanel.add(new JScrollPane(cartItemsPanel), BorderLayout.CENTER);
        cartPanel.add(summaryPanel, BorderLayout.SOUTH);
    }
    
    private void updateCartPanel() {
        // Remove all components from cart panel except the title and bottom panel
        Component[] components = cartPanel.getComponents();
        JScrollPane scrollPane = (JScrollPane) components[1];
        JPanel cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        cartItemsPanel.setOpaque(false);
        
        // Add cart items
        if (cart.isEmpty()) {
            JPanel emptyCartPanel = new JPanel(new BorderLayout());
            emptyCartPanel.setOpaque(false);
            emptyCartPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
            
            JLabel emptyLabel = new JLabel("Your Amazon Cart is empty");
            emptyLabel.setFont(new Font("Arial", Font.BOLD, 16));
            emptyLabel.setHorizontalAlignment(JLabel.CENTER);
            
            JLabel suggestLabel = new JLabel("Shop today's deals");
            suggestLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            suggestLabel.setForeground(new Color(0, 113, 133)); // Amazon link blue
            suggestLabel.setHorizontalAlignment(JLabel.CENTER);
            suggestLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            emptyCartPanel.add(emptyLabel, BorderLayout.NORTH);
            emptyCartPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.CENTER);
            emptyCartPanel.add(suggestLabel, BorderLayout.SOUTH);
            
            cartItemsPanel.add(emptyCartPanel);
        } else {
            for (Product product : cart) {
                JPanel cartItemPanel = createCartItemPanel(product);
                cartItemsPanel.add(cartItemPanel);
                cartItemsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        
        // Update scroll pane
        scrollPane.setViewportView(cartItemsPanel);
        
        // Update total
        totalLabel.setText(String.format("₹%.2f", totalAmount));
        
        // Update price details
        Component[] summaryComponents = ((JPanel)components[2]).getComponents();
        JPanel priceDetailsPanel = (JPanel)summaryComponents[2];
        ((JLabel)priceDetailsPanel.getComponent(1)).setText(String.format("₹%.2f", totalAmount));
        
        // Enable checkout button if cart is not empty
        checkoutButton.setEnabled(!cart.isEmpty());
        
        // Refresh panel
        cartPanel.revalidate();
        cartPanel.repaint();
    }
    
    private JPanel createCartItemPanel(Product product) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, new Color(222, 222, 222)),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        // Product image (small thumbnail)
        if (product.getImagePath() != null) {
            try {
                BufferedImage originalImage = ImageIO.read(new File(product.getImagePath()));
                
                // Create scaled image for thumbnail
                Image scaledImage = originalImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(scaledImage);
                JLabel imageLabel = new JLabel(imageIcon);
                
                panel.add(imageLabel, BorderLayout.WEST);
                
            } catch (IOException e) {
                System.err.println("Error loading thumbnail image: " + product.getImagePath());
            }
        }
        
        // Product info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel priceLabel = new JLabel(String.format("₹%.2f", product.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setForeground(new Color(177, 39, 4)); // Amazon red price
        
        JLabel inStockLabel = new JLabel("In Stock");
        inStockLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        inStockLabel.setForeground(new Color(0, 118, 0)); // Amazon green
        
        JLabel eligibleLabel = new JLabel("Eligible for FREE Shipping");
        eligibleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        infoPanel.add(inStockLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        infoPanel.add(eligibleLabel);
        
        // Action panel
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setOpaque(false);
        
        JButton removeButton = new JButton("Delete");
        removeButton.setForeground(new Color(0, 113, 133)); // Amazon link blue
        removeButton.setBorderPainted(false);
        removeButton.setContentAreaFilled(false);
        removeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        removeButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeFromCart(product);
            }
        });
        
        actionPanel.add(removeButton);
        
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private void addToCart(Product product) {
        cart.add(product);
        totalAmount += product.getPrice();
        updateCartPanel();
        
        // Show confirmation
        JOptionPane.showMessageDialog(
            frame, 
            "Added to your cart: " + product.getName(), 
            "Added to Cart", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void removeFromCart(Product product) {
        cart.remove(product);
        totalAmount -= product.getPrice();
        updateCartPanel();
    }
    
    private void checkout() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Your cart is empty!", "Checkout", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder message = new StringBuilder();
        message.append("Thank you for your purchase!\n\n");
        message.append("Order Summary:\n");
        
        for (Product product : cart) {
            message.append(String.format("- %s: ₹%.2f\n", product.getName(), product.getPrice()));
        }
        
        message.append(String.format("\nTotal Amount: ₹%.2f", totalAmount));
        message.append("\n\nYour order will be delivered by tomorrow!");
        
        JOptionPane.showMessageDialog(frame, message.toString(), "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);
        
        // Clear cart after checkout
        cart.clear();
        totalAmount = 0.0;
        updateCartPanel();
    }
    
    public static void main(String[] args) {
        // Use Event Dispatch Thread for Swing applications
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OnlineShopApp();
            }
        });
    }
} 