package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AddBookingWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField customerIdText = new JTextField();
    private JTextField flightIdText = new JTextField();
    private JTextField returnFlightIdText = new JTextField(); // ✅ Added return flight input

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    public AddBookingWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setTitle("Add a New Booking");

        setSize(350, 200);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2)); 
        topPanel.add(new JLabel("Customer ID: "));
        topPanel.add(customerIdText);
        topPanel.add(new JLabel("Flight ID (Outbound): "));
        topPanel.add(flightIdText);
        // return flight input
        topPanel.add(new JLabel("Return Flight ID (Optional): ")); 
        topPanel.add(returnFlightIdText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addBooking();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    private void addBooking() {
        try {
            int customerId = Integer.parseInt(customerIdText.getText());
            int flightId = Integer.parseInt(flightIdText.getText());
            
            // Get return flight ID (optional)
            Integer returnFlightId = null;
            String returnFlightInput = returnFlightIdText.getText().trim();
            if (!returnFlightInput.isEmpty()) {
                returnFlightId = Integer.parseInt(returnFlightInput);
            }

            // Create and execute the AddBooking Command
            Command addBooking = new AddBooking(customerId, flightId, returnFlightId);
            addBooking.execute(mw.getFlightBookingSystem());

            // Refresh the view with the list of flights
            mw.displayFlights(mw.getFlightBookingSystem().getFlights());

            // Hide (close) the AddBookingWindow
            this.setVisible(false);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter valid numeric IDs.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
