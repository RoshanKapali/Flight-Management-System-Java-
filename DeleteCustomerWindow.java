package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.DeleteCustomer;
import bcu.cmp5332.bookingsystem.data.CustomerDataManager;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DeleteCustomerWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField customerIdText = new JTextField();

    private JButton deleteBtn = new JButton("Delete");
    private JButton closeBtn = new JButton("Close");

    public DeleteCustomerWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setTitle("Delete a Customer");

        setSize(350, 150);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));
        topPanel.add(new JLabel("Customer ID: "));
        topPanel.add(customerIdText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(deleteBtn);
        bottomPanel.add(closeBtn);

        deleteBtn.addActionListener(this);
        closeBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == deleteBtn) {
            deleteCustomer();
        } else if (ae.getSource() == closeBtn) {
            this.setVisible(false);
        }
    }

    private void deleteCustomer() {
        try {
            int customerId = Integer.parseInt(customerIdText.getText());

            // Create and execute the DeleteCustomer Command
            Command deleteCustomer = new DeleteCustomer(customerId);
            deleteCustomer.execute(mw.getFlightBookingSystem());

            // Save the updated data to customers.txt
            CustomerDataManager customerDataManager = new CustomerDataManager();
            customerDataManager.storeData(mw.getFlightBookingSystem()); // Save changes to file

            // Refresh the view with the list of customers
            mw.displayCustomers();

            // Hide (close) the DeleteCustomerWindow
            this.setVisible(false);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to save customer data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Customer ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}