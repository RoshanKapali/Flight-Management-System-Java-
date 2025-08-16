package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import javax.swing.*;
import java.awt.*;

public class ShowCustomerWindow extends JFrame {

    public ShowCustomerWindow(FlightBookingSystem fbs, int customerId) {
        try {
            // Get the customer by ID
            Customer customer = fbs.getCustomerByID(customerId);

            if (customer == null) {
                JOptionPane.showMessageDialog(this, "Customer ID " + customerId + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Build customer details
            StringBuilder details = new StringBuilder();
            details.append("Customer Details:\n");
            details.append("Name: ").append(customer.getName()).append("\n");
            details.append("Phone: ").append(customer.getPhone()).append("\n");
            details.append("Email: ").append(customer.getEmail()).append("\n");
            details.append("\n--- Bookings ---\n");

            // Iterate through customer's bookings and display both flights
            for (Booking booking : customer.getBookings()) {
                Flight outboundFlight = booking.getOutboundFlight();
                Flight returnFlight = booking.getReturnFlight();

                details.append("Outbound Flight: ").append(outboundFlight.getFlightNumber())
                        .append(" (").append(outboundFlight.getOrigin()).append(" → ").append(outboundFlight.getDestination()).append(")\n")
                        .append("Departure: ").append(outboundFlight.getDepartureDate()).append("\n");

                if (returnFlight != null) { // Check if return flight exists
                    details.append("Return Flight: ").append(returnFlight.getFlightNumber())
                            .append(" (").append(returnFlight.getOrigin()).append(" → ").append(returnFlight.getDestination()).append(")\n")
                            .append("Departure: ").append(returnFlight.getDepartureDate()).append("\n");
                }
                details.append("----------------------\n");
            }

            // Create text area to display details
            JTextArea textArea = new JTextArea(details.toString());
            textArea.setFont(new Font("Arial", Font.PLAIN, 14));
            textArea.setMargin(new Insets(10, 10, 10, 10)); // Padding
            textArea.setEditable(false);

            // Scroll pane for large text
            JScrollPane scrollPane = new JScrollPane(textArea);

            // Set up the window
            setTitle("Customer Details - ID: " + customerId);
            setSize(550, 350);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
            add(scrollPane);
            setLocationRelativeTo(null); // Center the window
            setVisible(true);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching customer details: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
