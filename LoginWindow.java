package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.User;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow extends JFrame implements ActionListener {

    private final FlightBookingSystem fbs;
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton loginBtn = new JButton("Login");
    private final JButton cancelBtn = new JButton("Cancel");

    public LoginWindow(FlightBookingSystem fbs) {
        this.fbs = fbs;
        initialize();
    }

    private void initialize() {
        setTitle("Login");

        setSize(350, 150);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 2));
        topPanel.add(new JLabel("Username: "));
        topPanel.add(usernameField);
        topPanel.add(new JLabel("Password: "));
        topPanel.add(passwordField);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.add(loginBtn);
        bottomPanel.add(cancelBtn);

        loginBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == loginBtn) {
            login();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Hardcoded users for demonstration
        User admin = new User("admin", "admin123", "admin");
        User customer = new User("customer", "customer123", "customer");

        if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
            // Open the admin window
            new MainWindow(fbs, admin);
            this.setVisible(false);
        } else if (username.equals(customer.getUsername()) && password.equals(customer.getPassword())) {
            // Open the customer window
            new MainWindow(fbs, customer);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}