package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.EditBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditBookingWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField customerIdText = new JTextField();
    private JTextField oldFlightIdText = new JTextField();
    private JTextField newFlightIdText = new JTextField();

    private JButton editBtn = new JButton("Edit Booking");
    private JButton closeBtn = new JButton("Close");

    public EditBookingWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setTitle("Edit a Booking");

        setSize(400, 200);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2));
        topPanel.add(new JLabel("Customer ID: "));
        topPanel.add(customerIdText);
        topPanel.add(new JLabel("Old Flight ID: "));
        topPanel.add(oldFlightIdText);
        topPanel.add(new JLabel("New Flight ID: "));
        topPanel.add(newFlightIdText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(editBtn);
        bottomPanel.add(closeBtn);

        editBtn.addActionListener(this);
        closeBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == editBtn) {
            editBooking();
        } else if (ae.getSource() == closeBtn) {
            this.setVisible(false);
        }
    }

    private void editBooking() {
        try {
            int customerId = Integer.parseInt(customerIdText.getText());
            int oldFlightId = Integer.parseInt(oldFlightIdText.getText());
            int newFlightId = Integer.parseInt(newFlightIdText.getText());

            // Create and execute the EditBooking Command
            Command editBooking = new EditBooking(customerId, oldFlightId, newFlightId);
            editBooking.execute(mw.getFlightBookingSystem());

            // Refresh the view with the list of flights
            mw.displayFlights(mw.getFlightBookingSystem().getFlights()); // Refresh flight);

            // Hide (close) the EditBookingWindow
            this.setVisible(false);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid customer or flight ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}