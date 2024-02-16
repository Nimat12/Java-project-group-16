package ONCFsite.Admin;

import javax.swing.*;
import java.awt.*;

public class Rapport extends JFrame {
    private JLabel labelTitre;
    private JLabel labelTicketsVendus;
    private JLabel labelTrajetsReserves;
    private JLabel labelReductionsUtilisees;

    public Rapport() {
        setTitle("Rapport");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the window without stopping the main application

        // Create labels for displaying report data
        labelTitre = new JLabel("Rapport de vente et de réservation");
        labelTicketsVendus = new JLabel("Nombre de tickets vendus : ");
        labelTrajetsReserves = new JLabel("Nombre de trajets réservés : ");
        labelReductionsUtilisees = new JLabel("Nombre de réductions utilisées : ");

        // Set title label to be centered
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);

        // Create a panel to hold the labels
        JPanel panelLabels = new JPanel();
        panelLabels.setLayout(new GridLayout(4, 1));

        // Add labels to the panel
        panelLabels.add(labelTicketsVendus);
        panelLabels.add(labelTrajetsReserves);
        panelLabels.add(labelReductionsUtilisees);

        // Set up the layout for the frame
        setLayout(new BorderLayout());

        // Add the title label and label panel to the frame
        add(labelTitre, BorderLayout.NORTH);
        add(panelLabels, BorderLayout.CENTER);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Make the frame visible
        setVisible(true);
    }

    // Method to update the report data
    public void updateReport(int ticketsSold, int tripsReserved, int discountsUsed) {
        labelTicketsVendus.setText("Nombre de tickets vendus : " + ticketsSold);
        labelTrajetsReserves.setText("Nombre de trajets réservés : " + tripsReserved);
        labelReductionsUtilisees.setText("Nombre de réductions utilisées : " + discountsUsed);
    }

    public static void main(String[] args) {
        // Example of using the Rapport class
        SwingUtilities.invokeLater(() -> {
            Rapport rapport = new Rapport();
            rapport.updateReport(100, 50, 20); // Example data
        });
    }
}