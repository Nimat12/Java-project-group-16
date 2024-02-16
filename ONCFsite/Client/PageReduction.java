package ONCFsite.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PageReduction extends JFrame {
    private Connection connection;
    private double prixDuTrajet;
    private String gareDepart;
    private String gareArrivee;
	protected double prixDuTrajetApresReduction;

    public PageReduction(Connection connection, double prixDuTrajet, String gareDepart, String gareArrivee) {
        this.connection = connection;
        this.prixDuTrajet = prixDuTrajet;
        this.gareDepart = gareDepart;
        this.gareArrivee = gareArrivee;

        setTitle("Page de Réduction");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JLabel label = new JLabel("Sélectionnez une catégorie de réduction :");
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"None", "Militaire", "Étudiant", "Senior", "Enfant", "Famille nombreuse", "Carte fidélité"});

        JLabel codeLabel = new JLabel("Entrez le code de réduction (optionnel) :");
        JTextField codeField = new JTextField(20);

        // Ajout des champs pour les informations du client
        JLabel nomLabel = new JLabel("Nom:");
        JTextField nomField = new JTextField(20);
        JLabel prenomLabel = new JLabel("Prénom:");
        JTextField prenomField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        JLabel telLabel = new JLabel("Téléphone:");
        JTextField telField = new JTextField(20);

        JButton validerButton = new JButton("Valider");
        JButton continuerButton = new JButton("Continuer");

        validerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String categorie = (String) comboBox.getSelectedItem();
                String code = codeField.getText().trim();

                double reduction = ReductionDAO.getReduction(categorie, code);
                if (reduction > 0) {
                	prixDuTrajetApresReduction = prixDuTrajet * (1 - reduction);
                    String message = "Prix avec réduction pour la catégorie " + categorie + " : " + prixDuTrajetApresReduction + " DH";
                    JOptionPane.showMessageDialog(PageReduction.this, message, "Prix avec Réduction", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String message = "Aucun code de réduction valide trouvé pour la catégorie : " + categorie;
                    JOptionPane.showMessageDialog(PageReduction.this, message, "Réduction Invalide", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        continuerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Récupérez les informations du client saisies dans les champs
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String email = emailField.getText();
                String tel = telField.getText();

                // Insérer les informations du client dans la base de données
                insererInfoVoy(nom, prenom, email, tel);

                // Ouvrir la prochaine page avec les informations du client et le prix après réduction
                PageTicket pageTicket = new PageTicket(nom, prenom, email, tel, prixDuTrajet, prixDuTrajetApresReduction, gareDepart, gareArrivee);
                pageTicket.setVisible(true);

                // Fermer la page de réduction
                dispose();
            }
        });

        panel.add(label);
        panel.add(comboBox);
        panel.add(codeLabel);
        panel.add(codeField);

        // Ajout des champs pour les informations du client
        panel.add(nomLabel);
        panel.add(nomField);
        panel.add(prenomLabel);
        panel.add(prenomField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(telLabel);
        panel.add(telField);

        panel.add(validerButton);
        panel.add(continuerButton);

        add(panel);

        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
    }

    private void insererInfoVoy(String nom, String prenom, String email, String tel) {
        String sql = "INSERT INTO info_voy (nom, prenom, email, tel) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, email);
            statement.setString(4, tel);

            int lignesModifiees = statement.executeUpdate();

            if (lignesModifiees > 0) {
                System.out.println("Les informations du client ont été insérées avec succès dans la table info_voy.");
            } else {
                System.out.println("Échec de l'insertion des informations du client dans la table info_voy.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}