package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListCustomerWindow extends JFrame implements ActionListener {
    
    private MainWindow mw;
    private JTextArea customerListArea = new JTextArea();
    private JButton closeButton = new JButton("Close");
    
    public ListCustomerWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }
    
    private void initialize() {
        setTitle("Customer List");
        setSize(500, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(mw);

        customerListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(customerListArea);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(closeButton);
        add(bottomPanel, BorderLayout.SOUTH);

        closeButton.addActionListener(this);
        
        loadCustomerList();
        setVisible(true);
    }
    
    private void loadCustomerList() {
        List<Customer> customers = mw.getFlightBookingSystem().getCustomers();
        StringBuilder sb = new StringBuilder();
        if (customers.isEmpty()) {
            sb.append("No customers found.\n");
        } else {
            for (Customer customer : customers) {
                sb.append("ID: ").append(customer.getId())
                  .append(", Name: ").append(customer.getName())
                  .append(", Phone: ").append(customer.getPhone())
                  .append(", Email: ").append(customer.getEmail())
                  .append("\n");
            }
        }
        customerListArea.setText(sb.toString());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton) {
            this.setVisible(false);
        }
    }
}