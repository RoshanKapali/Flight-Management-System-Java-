package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

public class MainWindow extends JFrame implements ActionListener {

    private static MainWindow instance;  // Singleton instance

    private final FlightBookingSystem fbs;
    private final User user;

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu flightsMenu;
    private JMenu bookingsMenu;
    private JMenu customersMenu;

    private JMenuItem adminExit;
    private JMenuItem flightsView;
    private JMenuItem flightsAdd;
    private JMenuItem flightsDel;
    private JMenuItem flightsSearch;
    private JMenuItem bookingsIssue;
    private JMenuItem bookingsCancel;
    private JMenuItem bookingsUpdate;
    private JMenuItem custList;
    private JMenuItem custView;
    private JMenuItem custAdd;
    private JMenuItem custDel;

    public MainWindow(FlightBookingSystem fbs, User user) {
        this.fbs = fbs;
        this.user = user;
        instance = this; // Assign Singleton instance
        initialize();
    }

    public static MainWindow getInstance() {
        return instance;
    }

    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }

    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Ignore
        }

        setTitle("Flight Booking Management System - " + user.getRole().toUpperCase());

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        if (user.isAdmin()) {
            adminMenu = new JMenu("Admin");
            menuBar.add(adminMenu);

            adminExit = new JMenuItem("Exit");
            adminMenu.add(adminExit);
            adminExit.addActionListener(this);
        }

        flightsMenu = new JMenu("Flights");
        menuBar.add(flightsMenu);

        flightsView = new JMenuItem("View");
        flightsAdd = new JMenuItem("Add");
        flightsDel = new JMenuItem("Delete");
        flightsSearch = new JMenuItem("Search");

        flightsMenu.add(flightsView);
        if (user.isAdmin()) {
            flightsMenu.add(flightsAdd);
            flightsMenu.add(flightsDel);
        }
        flightsMenu.add(flightsSearch);

        flightsView.addActionListener(this);
        if (user.isAdmin()) {
            flightsAdd.addActionListener(this);
            flightsDel.addActionListener(this);
        }
        flightsSearch.addActionListener(this);

        bookingsMenu = new JMenu("Bookings");
        menuBar.add(bookingsMenu);

        bookingsIssue = new JMenuItem("Issue");
        bookingsCancel = new JMenuItem("Cancel");
        bookingsUpdate = new JMenuItem("Update");

        bookingsMenu.add(bookingsIssue);
        bookingsMenu.add(bookingsCancel);
        bookingsMenu.add(bookingsUpdate);

        bookingsIssue.addActionListener(this);
        bookingsCancel.addActionListener(this);
        bookingsUpdate.addActionListener(this);

        if (user.isAdmin()) {
            customersMenu = new JMenu("Customers");
            menuBar.add(customersMenu);

            custList = new JMenuItem("List");
            custView = new JMenuItem("View");
            custAdd = new JMenuItem("Add");
            custDel = new JMenuItem("Delete");

            customersMenu.add(custList);
            customersMenu.add(custView);
            customersMenu.add(custAdd);
            customersMenu.add(custDel);

            custList.addActionListener(this);
            custView.addActionListener(this);
            custAdd.addActionListener(this);
            custDel.addActionListener(this);
        }

        setSize(800, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == adminExit) {
            try {
                FlightBookingSystemData.store(fbs);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        } else if (ae.getSource() == flightsView) {
            String flightIdStr = JOptionPane.showInputDialog(this, "Enter Flight ID:");
            if (flightIdStr != null && !flightIdStr.isEmpty()) {
                try {
                    int flightId = Integer.parseInt(flightIdStr);
                    new ShowFlightWindow(fbs, flightId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Flight ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (ae.getSource() == flightsAdd) {
            new AddFlightWindow(this);
        } else if (ae.getSource() == flightsDel) {
            new DeleteFlightWindow(this);
        } else if (ae.getSource() == flightsSearch) {
            new FlightSearchWindow(this);
        } else if (ae.getSource() == bookingsIssue) {
            new AddBookingWindow(this);
        } else if (ae.getSource() == bookingsCancel) {
            new CancelBookingWindow(this);
        } else if (ae.getSource() == bookingsUpdate) {
            new EditBookingWindow(this);
        } else if (ae.getSource() == custList) {
            new ListCustomerWindow(this);
        } else if (ae.getSource() == custView) {
            String customerIdStr = JOptionPane.showInputDialog(this, "Enter Customer ID:");
            if (customerIdStr != null && !customerIdStr.isEmpty()) {
                try {
                    int customerId = Integer.parseInt(customerIdStr);
                    new ShowCustomerWindow(fbs, customerId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Customer ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (ae.getSource() == custAdd) {
            new AddCustomerWindow(this);
        } else if (ae.getSource() == custDel) {
            new DeleteCustomerWindow(this);
        }

        // Refresh flight list when booking-related actions occur
        if (ae.getSource() == bookingsIssue || ae.getSource() == bookingsCancel || ae.getSource() == bookingsUpdate) {
            displayFlights(fbs.getFlights());
            displayCustomers(); // Ensure customer details also update
        }
    }

    public void displayFlights(List<Flight> flightsList) {
        LocalDate systemDate = fbs.getSystemDate();

        List<Flight> futureFlights = flightsList.stream()
            .filter(flight -> flight.getDepartureDate().isAfter(systemDate))
            .collect(Collectors.toList());

        String[] columns = new String[]{"Flight No", "Origin", "Destination", "Departure Date", "Price ($)", "Capacity", "Booked Seats"};

        Object[][] data = new Object[futureFlights.size()][7];
        for (int i = 0; i < futureFlights.size(); i++) {
            Flight flight = futureFlights.get(i);
            data[i][0] = flight.getFlightNumber();
            data[i][1] = flight.getOrigin();
            data[i][2] = flight.getDestination();
            data[i][3] = flight.getDepartureDate();
            data[i][4] = flight.getPrice();
            data[i][5] = flight.getCapacity();
            data[i][6] = flight.getPassengers().size();
        }

        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }

    public void displayCustomers() {
        List<Customer> customersList = fbs.getCustomers();

        String[] columns = new String[]{"ID", "Name", "Phone", "Email", "Number of Bookings"};
        Object[][] data = new Object[customersList.size()][5];

        for (int i = 0; i < customersList.size(); i++) {
            Customer customer = customersList.get(i);
            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getPhone();
            data[i][3] = customer.getEmail();
            data[i][4] = customer.getBookings().size();
        }

        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }
}
