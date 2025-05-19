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
    
    // Current category
    private String currentCategory = "All Electronics";
    
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
        frame = new JFrame(" TechTrove ");
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
        JLabel headerLabel = new JLabel(" TechTrove ");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        headerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showCategory("All Electronics");
            }
        });
        
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
        
        String[] categories = {"All Electronics", "Smartphones", "Laptops", "Audio", "Headphones", "Speakers", "Cameras", "Wearables", "Gaming"};
        for (String category : categories) {
            JLabel categoryLabel = new JLabel(category);
            categoryLabel.setForeground(Color.WHITE);
            categoryLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Add hover effect
            categoryLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    categoryLabel.setForeground(AMAZON_YELLOW);
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    if (!category.equals(currentCategory)) {
                        categoryLabel.setForeground(Color.WHITE);
                    }
                }
                
                @Override
                public void mouseClicked(MouseEvent e) {
                    showCategory(category);
                }
            });
            
            navBarPanel.add(categoryLabel);
        }
        
        // Combine header and navbar
        JPanel fullHeaderPanel = new JPanel(new BorderLayout());
        fullHeaderPanel.add(headerPanel, BorderLayout.NORTH);
        fullHeaderPanel.add(navBarPanel, BorderLayout.CENTER);
        
        frame.add(fullHeaderPanel, BorderLayout.NORTH);
    }
    
    private void showCategory(String category) {
        currentCategory = category;
        refreshProductPanel();
        
        // Update title based on category
        frame.setTitle(" TechTrove - " + category + " ");
    }
    
    private void refreshProductPanel() {
        // Remove the current product panel from the frame
        Component[] components = frame.getContentPane().getComponents();
        for (Component component : components) {
            if (component instanceof JSplitPane) {
                JSplitPane splitPane = (JSplitPane) component;
                JScrollPane scrollPane = (JScrollPane) splitPane.getLeftComponent();
                
                // Create a new product panel
                createProductPanel();
                
                // Update the scroll pane with the new product panel
                scrollPane.setViewportView(productPanel);
                scrollPane.revalidate();
                scrollPane.repaint();
                break;
            }
        }
    }
    
    private void createFooter() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(AMAZON_BLUE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JLabel footerLabel = new JLabel("© 2025 TechTrove. All rights reserved.");
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);
        
        frame.add(footerPanel, BorderLayout.SOUTH);
    }
    
    private void initializeData() {
        // Initialize product list with electronics products
        products = new ArrayList<>();
        products.add(new Product("Premium Wireless Headphones", 12999.99, "Experience immersive sound with our noise-cancelling wireless headphones featuring 30-hour battery life, premium sound quality, and comfortable over-ear design. Perfect for music lovers and travelers.", "images/headphones.jpeg", 4.7, 328));
        products.add(new Product("Ultra HD Smart TV 55\"", 49999.99, "Transform your living room with this 55-inch 4K Ultra HD Smart TV featuring HDR, Dolby Vision, built-in voice assistant and seamless streaming. Experience cinema-quality viewing with vibrant colors and crystal-clear clarity.", "images/tablet.jpeg", 4.5, 156));
        products.add(new Product("Professional Gaming Laptop", 78999.99, "Dominate the competition with this high-performance gaming laptop featuring 16GB RAM, 512GB SSD, dedicated RTX graphics card and 15.6\" 144Hz display. Perfect for serious gamers and content creators.", "images/laptop.jpg", 4.8, 412));
        products.add(new Product("Smart Fitness Tracker", 8499.99, "Monitor your health 24/7 with this water-resistant fitness tracker featuring heart rate monitor, sleep tracking, GPS, and 7-day battery life. Stay fit and connected with smartphone notifications.", "images/smartphone.jpg", 4.3, 275));
        products.add(new Product("Waterproof Bluetooth Speaker", 5999.99, "Take your music anywhere with this waterproof portable speaker featuring 24-hour battery life, deep bass, and 360° sound. Perfect for outdoor adventures, beach trips, and pool parties.", "images/speaker.jpeg", 4.6, 189));
        products.add(new Product("Digital Drawing Tablet", 15999.99, "Unleash your creativity with this professional drawing tablet featuring pressure-sensitive pen, customizable shortcuts, and large active area. Perfect for digital artists, designers, and photographers.", "images/drawing table.jpeg", 4.4, 97));
        products.add(new Product("4K Security Camera System", 24999.99, "Protect your home with this advanced security camera system featuring 4K resolution, night vision, motion detection, two-way audio, and cloud storage. Monitor your property from anywhere.", "images/camera.jpeg", 4.2, 143));
        products.add(new Product("Wireless Charging Pad", 2499.99, "Eliminate cable clutter with this fast wireless charging pad compatible with all Qi-enabled devices. Features LED indicators and overheating protection for safe, efficient charging.", "images/charging-pod.jpeg", 4.5, 211));
        products.add(new Product("Smart Home Hub", 7999.99, "Control your entire smart home ecosystem with this central hub featuring voice control, automation capabilities, and compatibility with thousands of smart devices from leading brands.", "images/alexa.jpeg", 4.3, 112));
        products.add(new Product("True Wireless Earbuds", 9999.99, "Experience freedom with these true wireless earbuds featuring active noise cancellation, sweat resistance, touch controls, and 24-hour battery life with premium charging case.", "images/earbuds.jpeg", 4.6, 247));
        products.add(new Product("Gaming Console Pro", 44999.99, "Level up your gaming experience with this next-gen console featuring 1TB storage, 4K gaming at 120fps, ray tracing, fast loading times, and an exclusive game library.", "images/Gaming Console Pro.jpg", 4.9, 321));
        products.add(new Product("Professional DSLR Camera", 89999.99, "Capture perfect moments with this 24.1MP DSLR camera featuring 4K video recording, interchangeable lenses, advanced autofocus system, and professional-grade image quality.", "images/dslr.jpeg", 4.8, 68));
        products.add(new Product("Mechanical Gaming Keyboard", 8999.99, "Gain a competitive edge with this mechanical gaming keyboard featuring customizable RGB lighting, programmable macro keys, anti-ghosting technology, and durable construction.", "images/keyboard.png", 4.7, 184));
        products.add(new Product("Wireless Gaming Mouse", 6499.99, "Achieve precision control with this wireless gaming mouse featuring adjustable DPI settings, programmable buttons, ergonomic design, and ultra-low latency for competitive gaming.", "images/mouse.jpeg", 4.5, 94));
        
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
        
        JLabel titleLabel = new JLabel(currentCategory);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(17, 17, 17));
        
        String subtitleText = "Top-rated tech products at amazing prices";
        if (!currentCategory.equals("All Electronics")) {
            subtitleText = "Browse our selection of premium " + currentCategory.toLowerCase();
        }
        
        JLabel subtitleLabel = new JLabel(subtitleText);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(86, 86, 86));
        
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        productPanel.add(titlePanel);
        productPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Add category banner if not showing all products
        if (!currentCategory.equals("All Electronics")) {
            JPanel bannerPanel = createCategoryBanner(currentCategory);
            productPanel.add(bannerPanel);
            productPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }
        
        // Grid layout for products
        JPanel productsGrid = new JPanel(new GridLayout(0, 2, 15, 15));
        productsGrid.setOpaque(false);
        
        // Filter products by category
        ArrayList<Product> filteredProducts = filterProductsByCategory(currentCategory);
        
        // Add products to grid
        for (Product product : filteredProducts) {
            JPanel productCard = createProductCard(product);
            productsGrid.add(productCard);
        }
        
        // Make grid scrollable
        productsGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        productPanel.add(productsGrid);
    }
    
    private JPanel createCategoryBanner(String category) {
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setBackground(new Color(232, 242, 255)); // Light blue background
        bannerPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(204, 222, 255), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        bannerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        bannerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String bannerText = "";
        String imageIconPath = null;
        
        switch (category) {
            case "Headphones":
                bannerText = "Premium Headphones - Immerse yourself in superior sound quality with our selection of top-rated headphones.";
                imageIconPath = "images/headphones.jpeg";
                break;
            case "Smartphones":
                bannerText = "Latest Smartphones - Stay connected with cutting-edge technology and innovative features.";
                imageIconPath = "images/smartphone.jpg";
                break;
            case "Speakers":
                bannerText = "Powerful Speakers - Experience room-filling sound with our collection of high-quality speakers.";
                imageIconPath = "images/speaker.jpeg";
                break;
            case "Laptops":
                bannerText = "High-Performance Laptops - Power through your tasks with our range of premium laptops.";
                imageIconPath = "images/laptop.jpg";
                break;
            case "Cameras":
                bannerText = "Professional Cameras - Capture life's moments with exceptional clarity and detail.";
                imageIconPath = "images/camera.jpeg";
                break;
            case "Gaming":
                bannerText = "Gaming Gear - Level up your gaming experience with our selection of gaming products.";
                imageIconPath = "images/Gaming Console Pro.jpg";
                break;
            default:
                bannerText = "Explore our collection of premium " + category + " products.";
                break;
        }
        
        JLabel textLabel = new JLabel("<html><body style='width: 400px'>" + bannerText + "</body></html>");
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        bannerPanel.add(textLabel, BorderLayout.CENTER);
        
        // Add category image if available
        if (imageIconPath != null) {
            try {
                BufferedImage originalImage = ImageIO.read(new File(imageIconPath));
                
                // Resize image
                int targetHeight = 70;
                double aspectRatio = (double) originalImage.getWidth() / originalImage.getHeight();
                int width = (int) (targetHeight * aspectRatio);
                
                Image scaledImage = originalImage.getScaledInstance(width, targetHeight, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(scaledImage);
                
                JLabel imageLabel = new JLabel(imageIcon);
                imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
                bannerPanel.add(imageLabel, BorderLayout.WEST);
                
            } catch (IOException e) {
                System.err.println("Error loading banner image: " + imageIconPath);
            }
        }
        
        return bannerPanel;
    }
    
    private ArrayList<Product> filterProductsByCategory(String category) {
        if (category.equals("All Electronics")) {
            return products;
        }
        
        ArrayList<Product> filteredProducts = new ArrayList<>();
        
        for (Product product : products) {
            String name = product.getName().toLowerCase();
            String desc = product.getDescription().toLowerCase();
            category = category.toLowerCase();
            
            if (category.equals("headphones") && (name.contains("headphones") || name.contains("earbuds") || desc.contains("headphones") || desc.contains("earbuds"))) {
                filteredProducts.add(product);
            } else if (category.equals("smartphones") && (name.contains("smartphone") || name.contains("phone") || desc.contains("smartphone"))) {
                filteredProducts.add(product);
            } else if (category.equals("speakers") && (name.contains("speaker") || desc.contains("speaker"))) {
                filteredProducts.add(product);
            } else if (category.equals("laptops") && (name.contains("laptop") || desc.contains("laptop"))) {
                filteredProducts.add(product);
            } else if (category.equals("cameras") && (name.contains("camera") || name.contains("dslr") || desc.contains("camera"))) {
                filteredProducts.add(product);
            } else if (category.equals("wearables") && (name.contains("fitness") || name.contains("tracker") || name.contains("watch") || desc.contains("fitness tracker"))) {
                filteredProducts.add(product);
            } else if (category.equals("gaming") && (name.contains("gaming") || name.contains("console") || desc.contains("gaming"))) {
                filteredProducts.add(product);
            } else if (category.equals("audio") && (name.contains("headphones") || name.contains("earbuds") || name.contains("speaker") || desc.contains("audio"))) {
                filteredProducts.add(product);
            }
        }
        
        return filteredProducts;
    }
    
    private JPanel createProductCard(Product product) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(222, 222, 222), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Make the entire card clickable
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showProductDetails(product);
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(AMAZON_ORANGE, 2, true),
                    BorderFactory.createEmptyBorder(14, 14, 14, 14)
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(222, 222, 222), 1, true),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
            }
        });
        
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
        
        // Rating stars
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        ratingPanel.setOpaque(false);
        
        // Add stars based on rating
        double rating = product.getRating();
        for (int i = 1; i <= 5; i++) {
            JLabel starLabel;
            if (i <= Math.floor(rating)) {
                starLabel = new JLabel("★"); // Full star
            } else if (i - rating < 1 && i - rating > 0) {
                starLabel = new JLabel("☆"); // Half star
            } else {
                starLabel = new JLabel("☆"); // Empty star
            }
            starLabel.setForeground(AMAZON_ORANGE);
            ratingPanel.add(starLabel);
        }
        
        JLabel reviewsLabel = new JLabel("(" + product.getReviewCount() + ")");
        reviewsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        reviewsLabel.setForeground(new Color(0, 113, 133)); // Amazon link blue
        ratingPanel.add(reviewsLabel);
        
        ratingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
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
        
        // Tech specs badge
        JLabel techSpecsLabel = new JLabel("Tech Specs");
        techSpecsLabel.setFont(new Font("Arial", Font.BOLD, 11));
        techSpecsLabel.setOpaque(true);
        techSpecsLabel.setBackground(new Color(0, 113, 133));
        techSpecsLabel.setForeground(Color.WHITE);
        techSpecsLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        techSpecsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Product description
        JLabel descLabel = new JLabel("<html><body width='200px'>" + product.getDescription() + "</body></html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
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
        
        // Add tech specs badge
        JPanel badgePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        badgePanel.setOpaque(false);
        badgePanel.add(primeLabel);
        badgePanel.add(techSpecsLabel);
        badgePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(badgePanel);
        
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
            
            JLabel emptyLabel = new JLabel("Your TechTrove Cart is empty");
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
    
    private void showProductDetails(Product product) {
        // Create a new dialog for product details
        JDialog detailDialog = new JDialog(frame, product.getName(), true);
        detailDialog.setSize(800, 600);
        detailDialog.setLocationRelativeTo(frame);
        detailDialog.setLayout(new BorderLayout(20, 20));
        detailDialog.getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(BACKGROUND_COLOR);
        
        // Left panel for image
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setBorder(new LineBorder(new Color(222, 222, 222), 1, true));
        
        // Product image
        if (product.getImagePath() != null) {
            try {
                BufferedImage originalImage = ImageIO.read(new File(product.getImagePath()));
                
                // Resize image
                int targetWidth = 300;
                int targetHeight = 300;
                
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
                
                JLabel imageLabel = new JLabel(imageIcon);
                imageLabel.setHorizontalAlignment(JLabel.CENTER);
                imagePanel.add(imageLabel, BorderLayout.CENTER);
                
            } catch (IOException e) {
                System.err.println("Error loading image: " + product.getImagePath());
                e.printStackTrace();
            }
        }
        
        // Right panel for details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(222, 222, 222), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Product name
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Rating panel
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        ratingPanel.setOpaque(false);
        
        // Add stars based on rating
        double rating = product.getRating();
        for (int i = 1; i <= 5; i++) {
            JLabel starLabel;
            if (i <= Math.floor(rating)) {
                starLabel = new JLabel("★"); // Full star
            } else if (i - rating < 1 && i - rating > 0) {
                starLabel = new JLabel("☆"); // Half star
            } else {
                starLabel = new JLabel("☆"); // Empty star
            }
            starLabel.setForeground(AMAZON_ORANGE);
            starLabel.setFont(new Font("Arial", Font.BOLD, 16));
            ratingPanel.add(starLabel);
        }
        
        JLabel ratingValueLabel = new JLabel(String.format(" %.1f", product.getRating()));
        ratingValueLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ratingPanel.add(ratingValueLabel);
        
        JLabel reviewsLabel = new JLabel(" (" + product.getReviewCount() + " reviews)");
        reviewsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        reviewsLabel.setForeground(new Color(0, 113, 133)); // Amazon link blue
        ratingPanel.add(reviewsLabel);
        
        ratingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Separator
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Price with discount styling
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pricePanel.setOpaque(false);
        
        JLabel priceLabel = new JLabel(String.format("₹%.2f", product.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        priceLabel.setForeground(new Color(177, 39, 4)); // Amazon red price
        
        JLabel originalPriceLabel = new JLabel(String.format("₹%.2f", product.getPrice() * 1.2));
        originalPriceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        originalPriceLabel.setForeground(Color.GRAY);
        
        // Strike through the original price
        StrikethroughLabel strikethroughLabel = new StrikethroughLabel(originalPriceLabel);
        
        JLabel discountLabel = new JLabel(" (20% off)");
        discountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        discountLabel.setForeground(new Color(0, 128, 0)); // Green
        
        pricePanel.add(priceLabel);
        pricePanel.add(strikethroughLabel);
        pricePanel.add(discountLabel);
        pricePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Availability
        JLabel availabilityLabel = new JLabel("In Stock");
        availabilityLabel.setFont(new Font("Arial", Font.BOLD, 16));
        availabilityLabel.setForeground(new Color(0, 128, 0)); // Green
        availabilityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Delivery info
        JLabel deliveryLabel = new JLabel("FREE Delivery by Tomorrow");
        deliveryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        deliveryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Description title
        JLabel descTitleLabel = new JLabel("About this item:");
        descTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        descTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Description
        JTextArea descTextArea = new JTextArea(product.getDescription());
        descTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descTextArea.setLineWrap(true);
        descTextArea.setWrapStyleWord(true);
        descTextArea.setEditable(false);
        descTextArea.setBackground(null);
        descTextArea.setBorder(null);
        descTextArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Features section
        JLabel featuresLabel = new JLabel("Key Features:");
        featuresLabel.setFont(new Font("Arial", Font.BOLD, 16));
        featuresLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Generate some dummy features based on product type
        String[] features = generateFeatures(product);
        JPanel featuresPanel = new JPanel();
        featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.Y_AXIS));
        featuresPanel.setOpaque(false);
        
        for (String feature : features) {
            JLabel featureLabel = new JLabel("• " + feature);
            featureLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            featureLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            featuresPanel.add(featureLabel);
            featuresPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        
        featuresPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setBackground(AMAZON_YELLOW);
        addToCartButton.setFocusPainted(false);
        addToCartButton.setPreferredSize(new Dimension(150, 40));
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart(product);
                detailDialog.dispose();
            }
        });
        
        JButton buyNowButton = new JButton("Buy Now");
        buyNowButton.setBackground(AMAZON_ORANGE);
        buyNowButton.setForeground(Color.WHITE);
        buyNowButton.setFocusPainted(false);
        buyNowButton.setPreferredSize(new Dimension(150, 40));
        
        buttonPanel.add(addToCartButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(buyNowButton);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Add all components to details panel with spacing
        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailsPanel.add(ratingPanel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailsPanel.add(separator);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailsPanel.add(pricePanel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailsPanel.add(availabilityLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(deliveryLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        detailsPanel.add(descTitleLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(descTextArea);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        detailsPanel.add(featuresLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(featuresPanel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        detailsPanel.add(buttonPanel);
        
        // Add panels to content panel
        contentPanel.add(imagePanel, BorderLayout.WEST);
        contentPanel.add(detailsPanel, BorderLayout.CENTER);
        
        // Add content to dialog
        detailDialog.add(contentPanel, BorderLayout.CENTER);
        
        // Show dialog
        detailDialog.setVisible(true);
    }
    
    private String[] generateFeatures(Product product) {
        String name = product.getName().toLowerCase();
        
        if (name.contains("headphones")) {
            return new String[] {
                "Active noise cancellation with adaptive technology",
                "Bluetooth 5.2 connectivity with multipoint pairing",
                "30-hour battery life with quick charging",
                "Premium 40mm drivers for studio-quality sound",
                "Memory foam ear cushions for all-day comfort"
            };
        } else if (name.contains("earbuds")) {
            return new String[] {
                "Active noise cancellation with transparency mode",
                "IPX7 water and sweat resistance",
                "Touch controls for music and calls",
                "24-hour total battery life with charging case",
                "Custom EQ settings via companion app"
            };
        } else if (name.contains("laptop")) {
            return new String[] {
                "Intel Core i7-12700H processor (14 cores, up to 4.7GHz)",
                "16GB DDR5 RAM (expandable to 64GB)",
                "1TB NVMe PCIe 4.0 SSD",
                "NVIDIA GeForce RTX 3070 Ti graphics (8GB GDDR6)",
                "15.6\" QHD 165Hz display with 100% DCI-P3 color gamut"
            };
        } else if (name.contains("smartphone")) {
            return new String[] {
                "6.7\" Dynamic AMOLED display with 120Hz refresh rate",
                "Triple camera system with 108MP main, 12MP ultrawide, and 10MP telephoto",
                "5G connectivity with Wi-Fi 6E support",
                "4500mAh battery with 45W fast charging and 15W wireless charging",
                "IP68 water and dust resistance rating"
            };
        } else if (name.contains("speaker")) {
            return new String[] {
                "360° omnidirectional sound with dual passive radiators",
                "IPX7 waterproof rating for complete submersion",
                "24-hour battery life at moderate volume",
                "Built-in microphone with noise cancellation for calls",
                "Bluetooth 5.2 with 100ft wireless range"
            };
        } else if (name.contains("tv")) {
            return new String[] {
                "4K Ultra HD resolution (3840 x 2160) with HDR10+",
                "Dolby Vision and Dolby Atmos support",
                "Smart TV with voice control and AI-powered recommendations",
                "4 HDMI 2.1 ports with eARC and VRR support",
                "Quantum dot technology for 100% color volume"
            };
        } else if (name.contains("tracker") || name.contains("fitness")) {
            return new String[] {
                "24/7 heart rate and SpO2 monitoring",
                "Built-in GPS with GLONASS support",
                "Advanced sleep tracking with sleep stages analysis",
                "Water resistant up to 50m for swimming",
                "7-day battery life with always-on display"
            };
        } else if (name.contains("camera")) {
            return new String[] {
                "24.1MP APS-C CMOS sensor with DIGIC X processor",
                "4K video recording at 60fps with 10-bit color",
                "45-point all cross-type autofocus system",
                "3.0\" vari-angle touchscreen with 1.04M dots",
                "Built-in Wi-Fi, Bluetooth, and NFC connectivity"
            };
        } else if (name.contains("tablet")) {
            return new String[] {
                "10.9\" Liquid Retina display with P3 wide color",
                "A14 Bionic chip with 6-core CPU and 4-core GPU",
                "12MP wide back camera, 12MP ultra-wide front camera",
                "Up to 10 hours of battery life on Wi-Fi",
                "Support for Apple Pencil (2nd generation)"
            };
        } else if (name.contains("gaming")) {
            return new String[] {
                "AMD Ryzen 9 5900X processor (12 cores, up to 4.8GHz)",
                "NVIDIA GeForce RTX 3080 graphics with 10GB GDDR6X",
                "1TB NVMe SSD with 3500MB/s read speeds",
                "32GB DDR4-3600MHz RAM with RGB lighting",
                "Advanced cooling system with liquid cooling"
            };
        } else if (name.contains("keyboard")) {
            return new String[] {
                "Mechanical switches with 50 million keystroke lifespan",
                "Per-key RGB lighting with 16.8 million colors",
                "N-key rollover and 100% anti-ghosting",
                "Programmable macro keys with onboard memory",
                "Aircraft-grade aluminum frame for durability"
            };
        } else if (name.contains("mouse")) {
            return new String[] {
                "25,600 DPI optical sensor with 1ms response time",
                "8 programmable buttons with onboard memory",
                "Lightweight design (82g) with PTFE feet",
                "Up to 70 hours battery life with fast charging",
                "Ergonomic right-handed design with textured grips"
            };
        } else if (name.contains("charging")) {
            return new String[] {
                "15W fast wireless charging for compatible devices",
                "Qi certification for universal compatibility",
                "Foreign object detection for safety",
                "LED indicator for charging status",
                "Anti-slip surface with premium fabric finish"
            };
        } else if (name.contains("hub") || name.contains("smart home")) {
            return new String[] {
                "Compatible with over 10,000 smart home devices",
                "Voice control with multiple assistant support",
                "Advanced automation with customizable routines",
                "Secure local processing for privacy protection",
                "Zigbee, Z-Wave, and Bluetooth mesh connectivity"
            };
        } else if (name.contains("security")) {
            return new String[] {
                "4K Ultra HD video with HDR support",
                "160° field of view with night vision up to 30ft",
                "AI-powered person, vehicle, and package detection",
                "Two-way audio with noise cancellation",
                "Weatherproof design with IP66 rating"
            };
        } else {
            return new String[] {
                "Premium quality materials and construction",
                "Energy efficient design with low power consumption",
                "Smart features with app connectivity",
                "Sleek, modern design that complements any space",
                "2-year manufacturer warranty with extended options"
            };
        }
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