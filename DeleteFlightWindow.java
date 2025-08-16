package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.DeleteFlight;
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

public class DeleteFlightWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField flightIdText = new JTextField();

    private JButton deleteBtn = new JButton("Delete");
    private JButton closeBtn = new JButton("Close");

    public DeleteFlightWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setTitle("Delete a Flight");

        setSize(350, 150);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));
        topPanel.add(new JLabel("Flight ID: "));
        topPanel.add(flightIdText);

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
            deleteFlight();
        } else if (ae.getSource() == closeBtn) {
            this.setVisible(false);
        }
    }

    private void deleteFlight() {
        try {
            int flightId = Integer.parseInt(flightIdText.getText());

            // Create and execute the DeleteFlight Command
            Command deleteFlight = new DeleteFlight(flightId);
            deleteFlight.execute(mw.getFlightBookingSystem());

            // Refresh the view with the list of flights
            mw.displayFlights(mw.getFlightBookingSystem().getFlights()); // Pass the full list of flights

            // Hide (close) the DeleteFlightWindow
            this.setVisible(false);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Flight ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}