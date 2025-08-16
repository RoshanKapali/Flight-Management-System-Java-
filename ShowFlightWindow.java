package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

public class ShowFlightWindow extends JFrame {

    public ShowFlightWindow(FlightBookingSystem fbs, int flightId) {
        try {
            // Get the flight by ID
            Flight flight = fbs.getFlightByID(flightId);

            // Create a text area to display flight details
            JTextArea textArea = new JTextArea(flight.getDetailsLong());
            textArea.setEditable(false);

            // Add the text area to a scroll pane
            JScrollPane scrollPane = new JScrollPane(textArea);

            // Set up the window
            setTitle("Flight Details");
            setSize(500, 400);
            add(scrollPane);
            setLocationRelativeTo(null); // Center the window
            setVisible(true);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}