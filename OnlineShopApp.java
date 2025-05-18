import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
    
    public OnlineShopApp() {
        // Initialize data
        initializeData();
        
        // Create and set up the window
        frame = new JFrame("Simple Online Shop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));
        
        // Create header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel headerLabel = new JLabel("Simple Online Shop");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        
        // Create product panel
        createProductPanel();
        
        // Create cart panel
        createCartPanel();
        
        // Add panels to frame
        frame.add(headerPanel, BorderLayout.NORTH);
        
        // Create split pane for products and cart
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                                             new JScrollPane(productPanel), 
                                             new JScrollPane(cartPanel));
        splitPane.setDividerLocation(500);
        frame.add(splitPane, BorderLayout.CENTER);
        
        // Show the window
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void initializeData() {
        // Initialize product list with some sample products
        products = new ArrayList<>();
        products.add(new Product("Laptop", 899.99, "High-performance laptop with 16GB RAM"));
        products.add(new Product("Smartphone", 499.99, "Latest model with 128GB storage"));
        products.add(new Product("Headphones", 89.99, "Noise-cancelling wireless headphones"));
        products.add(new Product("Tablet", 299.99, "10-inch tablet with HD display"));
        products.add(new Product("Smart Watch", 199.99, "Fitness tracker with heart rate monitor"));
        products.add(new Product("Bluetooth Speaker", 59.99, "Portable speaker with 10-hour battery life"));
        
        // Initialize empty cart
        cart = new ArrayList<>();
    }
    
    private void createProductPanel() {
        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Available Products");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        productPanel.add(titleLabel);
        productPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Add products to panel
        for (Product product : products) {
            JPanel productItemPanel = createProductItemPanel(product);
            productPanel.add(productItemPanel);
            productPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }
    
    private JPanel createProductItemPanel(Product product) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 5));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        // Product info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel priceLabel = new JLabel(String.format("₹%.2f", product.getPrice()));
        
        JLabel descLabel = new JLabel(product.getDescription());
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(descLabel);
        
        // Add to cart button
        JButton addButton = new JButton("Add to Cart");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart(product);
            }
        });
        
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.EAST);
        
        return panel;
    }
    
    private void createCartPanel() {
        cartPanel = new JPanel();
        cartPanel.setLayout(new BorderLayout(10, 10));
        cartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel cartTitleLabel = new JLabel("Shopping Cart");
        cartTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JPanel cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        
        totalLabel = new JLabel("Total: ₹0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        checkoutButton = new JButton("Checkout");
        checkoutButton.setEnabled(false);
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout();
            }
        });
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(totalLabel, BorderLayout.WEST);
        bottomPanel.add(checkoutButton, BorderLayout.EAST);
        
        cartPanel.add(cartTitleLabel, BorderLayout.NORTH);
        cartPanel.add(new JScrollPane(cartItemsPanel), BorderLayout.CENTER);
        cartPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void updateCartPanel() {
        // Remove all components from cart panel except the title and bottom panel
        Component[] components = cartPanel.getComponents();
        JScrollPane scrollPane = (JScrollPane) components[1];
        JPanel cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        
        // Add cart items
        for (Product product : cart) {
            JPanel cartItemPanel = createCartItemPanel(product);
            cartItemsPanel.add(cartItemPanel);
            cartItemsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        
        // Update scroll pane
        scrollPane.setViewportView(cartItemsPanel);
        
        // Update total
        totalLabel.setText(String.format("Total: ₹%.2f", totalAmount));
        
        // Enable checkout button if cart is not empty
        checkoutButton.setEnabled(!cart.isEmpty());
        
        // Refresh panel
        cartPanel.revalidate();
        cartPanel.repaint();
    }
    
    private JPanel createCartItemPanel(Product product) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 0));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        JLabel nameLabel = new JLabel(product.getName());
        JLabel priceLabel = new JLabel(String.format("₹%.2f", product.getPrice()));
        
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeFromCart(product);
            }
        });
        
        panel.add(nameLabel, BorderLayout.WEST);
        panel.add(priceLabel, BorderLayout.CENTER);
        panel.add(removeButton, BorderLayout.EAST);
        
        return panel;
    }
    
    private void addToCart(Product product) {
        cart.add(product);
        totalAmount += product.getPrice();
        updateCartPanel();
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
        
        JOptionPane.showMessageDialog(frame, message.toString(), "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);
        
        // Clear cart after checkout
        cart.clear();
        totalAmount = 0.0;
        updateCartPanel();
    }
    
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Start application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OnlineShopApp();
            }
        });
    }
} 