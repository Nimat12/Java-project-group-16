package ONCFsite.Client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;


public class PageChoixTrajet extends JFrame {
    public PageChoixTrajet() {
        setTitle("Choix du Trajet");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        afficherPageChoixTrajet();
    }

    private void afficherPageChoixTrajet() {
        getContentPane().removeAll();

        JLabel departLabel = new JLabel("Gare de départ:");
        JComboBox<String> departBox = new JComboBox<>();
        departBox.addItem("Casablanca");
        departBox.addItem("Rabat");
        departBox.addItem("Marrakech");

        JPanel departPanel = new JPanel();
        departPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        departPanel.add(departLabel);
        departPanel.add(departBox);

        JLabel arriveeLabel = new JLabel("Gare d'arrivée:");
        JComboBox<String> arriveeBox = new JComboBox<>();
        arriveeBox.addItem("Casablanca");
        arriveeBox.addItem("Rabat");
        arriveeBox.addItem("Marrakech");

        JPanel arriveePanel = new JPanel();
        arriveePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        arriveePanel.add(arriveeLabel);
        arriveePanel.add(arriveeBox);

        JLabel heureDepartLabel = new JLabel("Date de départ:");
        JTextField heureDepartField = new JTextField(10);

        JPanel heureDepartPanel = new JPanel();
        heureDepartPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        heureDepartPanel.add(heureDepartLabel);
        heureDepartPanel.add(heureDepartField);

        JLabel heureArriveeLabel = new JLabel("Date d'arrivée:");
        JTextField heureArriveeField = new JTextField(10);

        JPanel heureArriveePanel = new JPanel();
        heureArriveePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        heureArriveePanel.add(heureArriveeLabel);
        heureArriveePanel.add(heureArriveeField);

        JLabel classeLabel = new JLabel("Classe:");
        JRadioButton premiereClasseRadio = new JRadioButton("1ère classe");
        JRadioButton deuxiemeClasseRadio = new JRadioButton("2ème classe");
        JRadioButton confortRadio = new JRadioButton("Confort");
        ButtonGroup classeGroup = new ButtonGroup();
        classeGroup.add(premiereClasseRadio);
        classeGroup.add(deuxiemeClasseRadio);
        classeGroup.add(confortRadio);

        JPanel classePanel = new JPanel();
        classePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        classePanel.add(premiereClasseRadio);
        classePanel.add(deuxiemeClasseRadio);
        classePanel.add(confortRadio);

        JButton rechercherButton = new JButton("Rechercher");
        rechercherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oncf", "root", "Nima13072002+-");
                    PageHorairesTrains pageHorairesTrains = new PageHorairesTrains(connection);
                    pageHorairesTrains.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(departPanel);
        mainPanel.add(arriveePanel);
        mainPanel.add(heureDepartPanel);
        mainPanel.add(heureArriveePanel);
        mainPanel.add(classeLabel);
        mainPanel.add(classePanel);
        mainPanel.add(rechercherButton);

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PageChoixTrajet());
    }
}