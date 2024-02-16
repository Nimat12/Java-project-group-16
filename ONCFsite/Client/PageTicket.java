package ONCFsite.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PageTicket extends JFrame {
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private double prixDuTrajet;
    private double prixDuTrajetApresReduction;
    private String gareDepart; 
    private String gareArrivee;

    public PageTicket(String nom, String prenom, String email, String tel, double prixDuTrajet, double prixDuTrajetApresReduction, String gareDepart, String gareArrivee) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.prixDuTrajet = prixDuTrajet;
        this.prixDuTrajetApresReduction = prixDuTrajetApresReduction;
        this.gareDepart = gareDepart;
        this.gareArrivee = gareArrivee;

        setTitle("Page de Ticket");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setBackground(Color.WHITE); // Définir le fond de la page en blanc

        JLabel titleLabel = new JLabel("Votre Ticket");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nomLabel = new JLabel("Nom: " + nom);
        JLabel prenomLabel = new JLabel("Prénom: " + prenom);
        JLabel emailLabel = new JLabel("Email: " + email);
        JLabel telLabel = new JLabel("Téléphone: " + tel);
        JLabel prixLabel = new JLabel("Prix du Trajet après réduction: " + prixDuTrajetApresReduction + " DH");
        JLabel trajetLabel = new JLabel("Trajet: De " + gareDepart + " à " + gareArrivee);

        JButton confirmerButton = new JButton("Imprimer Ticket");
        confirmerButton.setBackground(Color.ORANGE); // Définir la couleur de fond du bouton en orange
        confirmerButton.setForeground(Color.WHITE); // Définir la couleur du texte du bouton en blanc
        confirmerButton.setFont(new Font("Arial", Font.BOLD, 14)); // Mettre le texte du bouton en gras et plus grand

        confirmerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ici vous pouvez ajouter le code pour enregistrer le ticket dans la base de données
                // Par exemple :
                // TicketDAO.enregistrerTicket(nom, prenom, email, tel, prixDuTrajetApresReduction);
                JOptionPane.showMessageDialog(PageTicket.this, "Ticket enregistré avec succès!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(titleLabel);
        panel.add(nomLabel);
        panel.add(prenomLabel);
        panel.add(emailLabel);
        panel.add(telLabel);
        panel.add(prixLabel);
        panel.add(trajetLabel);
        panel.add(confirmerButton);

        getContentPane().setBackground(Color.WHITE); // Définir le fond de la fenêtre en blanc
        add(panel);

        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Exemple d'utilisation de la classe PageTicket
                PageTicket pageTicket = new PageTicket("John", "Doe", "john.doe@example.com", "123456789", 100.0, 90.0, "Gare A", "Gare B"); // Prix après réduction et détails du trajet ajoutés
                pageTicket.setVisible(true);
            }
        });
    }
}