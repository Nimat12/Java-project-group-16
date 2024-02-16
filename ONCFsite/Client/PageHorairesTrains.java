package ONCFsite.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PageHorairesTrains extends JFrame {
    private Connection connection;

    public PageHorairesTrains(Connection connection) {
        this.connection = connection;
        setTitle("Horaires des Trains");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        afficherHoraires();
    }

    private void afficherHoraires() {
        getContentPane().removeAll();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1));

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT gare_depart, gare_arrivee, date_depart, date_arrivee, prix FROM train");
            while (resultSet.next()) {
                JPanel trajetPanel = new JPanel(new BorderLayout());
                String details = "De " + resultSet.getString("gare_depart") + " à " + resultSet.getString("gare_arrivee") +
                        ", Départ: " + resultSet.getString("date_depart") + ", Arrivée: " + resultSet.getString("date_arrivee") +
                        ", Prix: " + resultSet.getDouble("prix") + " DH";

                JLabel detailsLabel = new JLabel(details);
                double prixDuTrajet = resultSet.getDouble("prix");
                JButton reserverButton = new JButton("Réserver");

                final String gareDepart = resultSet.getString("gare_depart");
                final String gareArrivee = resultSet.getString("gare_arrivee");

                reserverButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        PageReduction pageReduction = null;
                        pageReduction = new PageReduction(connection, prixDuTrajet, gareDepart, gareArrivee);
                        pageReduction.setVisible(true);
                        dispose(); // Fermer la page actuelle après avoir ouvert la page de réduction
                    }
                });

                trajetPanel.add(detailsLabel, BorderLayout.CENTER);
                trajetPanel.add(reserverButton, BorderLayout.SOUTH);
                mainPanel.add(trajetPanel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        getContentPane().add(new JScrollPane(mainPanel), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oncf", "root", "Nima13072002+-");
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    PageHorairesTrains page = new PageHorairesTrains(connection);
                    page.setVisible(true);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}