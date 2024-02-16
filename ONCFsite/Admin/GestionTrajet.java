package ONCFsite.Admin;

import javax.swing.*;

import java.awt.GridLayout;
import java.sql.*;

public class GestionTrajet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/oncf";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Nima13072002+-";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestionTrajet::createAndShowGUI);
    }

    static void createAndShowGUI() {
        JFrame frame = new JFrame("Gestion des Trajets");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton ajouterTrajetButton = new JButton("Ajouter Trajet");
        JButton afficherTrajetsButton = new JButton("Afficher Trajets");

        JPanel panel = new JPanel();
        panel.add(ajouterTrajetButton);
        panel.add(afficherTrajetsButton);

        ajouterTrajetButton.addActionListener(e -> {
            ajouterTrajet();
        });

        afficherTrajetsButton.addActionListener(e -> {
            afficherPageTrajets();
        });

        frame.getContentPane().add(panel);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void ajouterTrajet() {
        SwingUtilities.invokeLater(() -> {
            JFrame ajouterTrajetFrame = new JFrame("Ajouter un Trajet");
            ajouterTrajetFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel(new GridLayout(7, 2));
            JTextField numeroTrainField = new JTextField();
            JTextField dateDepartField = new JTextField();
            JTextField dateArriveeField = new JTextField();
            JTextField gareDepartField = new JTextField();
            JTextField gareArriveeField = new JTextField();
            JTextField prixField = new JTextField();

            panel.add(new JLabel("Numero Train:"));
            panel.add(numeroTrainField);
            panel.add(new JLabel("Date Depart:"));
            panel.add(dateDepartField);
            panel.add(new JLabel("Date Arrivee:"));
            panel.add(dateArriveeField);
            panel.add(new JLabel("Gare Depart:"));
            panel.add(gareDepartField);
            panel.add(new JLabel("Gare Arrivee:"));
            panel.add(gareArriveeField);
            panel.add(new JLabel("Prix:"));
            panel.add(prixField);

            JButton ajouterButton = new JButton("Ajouter");
            ajouterButton.addActionListener(e -> {
                String numeroTrain = numeroTrainField.getText();
                String dateDepart = dateDepartField.getText();
                String dateArrivee = dateArriveeField.getText();
                String gareDepart = gareDepartField.getText();
                String gareArrivee = gareArriveeField.getText();
                String prix = prixField.getText();

                ajouterTrajetDansBaseDeDonnees(numeroTrain, dateDepart, dateArrivee, gareDepart, gareArrivee, prix);
             // Fermer la fenêtre après l'ajout
                ajouterTrajetFrame.dispose();
            });

            panel.add(ajouterButton);

            ajouterTrajetFrame.getContentPane().add(panel);
            ajouterTrajetFrame.setSize(400, 300);
            ajouterTrajetFrame.setLocationRelativeTo(null);
            ajouterTrajetFrame.setVisible(true);
        });
    }


    private static void afficherPageTrajets() {
        SwingUtilities.invokeLater(() -> {
            JFrame trajetsFrame = new JFrame("Liste des Trajets");
            trajetsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Code to retrieve trips from the database
            Object[][] data = recupererTrajetsDepuisBaseDeDonnees();

            // Create a JTable to display the data (same as before)
            String[] columnNames = {"Numero Train", "Date Depart", "Date Arrivee", "Gare Depart", "Gare Arrivee", "Prix"};
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);

            trajetsFrame.getContentPane().add(scrollPane);
            trajetsFrame.setSize(500, 300);
            trajetsFrame.setLocationRelativeTo(null);
            trajetsFrame.setVisible(true);
        });
    }


    private static Object[][] recupererTrajetsDepuisBaseDeDonnees() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = statement.executeQuery("SELECT * FROM oncf.train")) {

            // Compter le nombre de lignes dans le ResultSet
            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.beforeFirst();

            Object[][] data = new Object[rowCount][7];
            int index = 0;
            while (resultSet.next()) {
                // Remplir le tableau data (la même logique que vous aviez auparavant)
                data[index][0] = resultSet.getString("numero_train");
                data[index][1] = resultSet.getString("date_depart");
                data[index][2] = resultSet.getString("date_arrivee");
                data[index][3] = resultSet.getString("gare_depart");
                data[index][4] = resultSet.getString("gare_arrivee");
                data[index][5] = resultSet.getString("prix");
                // Ajoutez d'autres colonnes au besoin

                index++;
            }

            return data;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving trips from database: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return new Object[0][0];
    }


    private static void ajouterTrajetDansBaseDeDonnees(String numero_train, String date_depart, String date_arrivee, String gare_depart, String gare_arrivee, String prix) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO oncf.train(numero_train, date_depart, date_arrivee, gare_depart, gare_arrivee, prix) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, numero_train);
                preparedStatement.setString(2, date_depart);
                preparedStatement.setString(3, date_arrivee);
                preparedStatement.setString(4, gare_depart);
                preparedStatement.setString(5, gare_arrivee);
                preparedStatement.setString(6, prix);

                preparedStatement.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Trajet ajouté avec succès !");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout du trajet dans la base de données: " + ex.getMessage(),
                    "Erreur de la base de données", JOptionPane.ERROR_MESSAGE);
        }
    }

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
}