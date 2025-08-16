package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.FlightSearch;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FlightSearchWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField flightNumberText = new JTextField();
    private JTextField originText = new JTextField();
    private JTextField destinationText = new JTextField();
    private JTextField departureDateText = new JTextField();
    private JTextField availableSeatsText = new JTextField();

    private JButton searchBtn = new JButton("Search");
    private JButton closeBtn = new JButton("Close");

    public FlightSearchWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setTitle("Search Flights");

        setSize(400, 250); // Increased height to accommodate additional fields
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(6, 2)); // Increased rows for additional fields
        topPanel.add(new JLabel("Flight Number: "));
        topPanel.add(flightNumberText);
        topPanel.add(new JLabel("Origin: "));
        topPanel.add(originText);
        topPanel.add(new JLabel("Destination: "));
        topPanel.add(destinationText);
        topPanel.add(new JLabel("Departure Date (YYYY-MM-DD): "));
        topPanel.add(departureDateText);
        topPanel.add(new JLabel("Available Seats: "));
        topPanel.add(availableSeatsText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(searchBtn);
        bottomPanel.add(closeBtn);

        searchBtn.addActionListener(this);
        closeBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == searchBtn) {
            searchFlights();
        } else if (ae.getSource() == closeBtn) {
            this.setVisible(false);
        }
    }

    private void searchFlights() {
        try {
            String flightNumber = flightNumberText.getText().trim();
            String origin = originText.getText().trim();
            String destination = destinationText.getText().trim();
            String departureDate = departureDateText.getText().trim();
            int availableSeats = availableSeatsText.getText().trim().isEmpty() ? -1 : Integer.parseInt(availableSeatsText.getText().trim());

            // Create and execute the FlightSearch Command
            Command flightSearch = new FlightSearch(flightNumber, origin, destination, departureDate, availableSeats);
            flightSearch.execute(mw.getFlightBookingSystem());

            // Get the filtered list of flights
            List<Flight> filteredFlights = mw.getFlightBookingSystem().getFilteredFlights();

            // Refresh the view with the filtered list of flights
            mw.displayFlights(filteredFlights);

            // Hide (close) the FlightSearchWindow
            this.setVisible(false);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Available Seats must be a valid number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}