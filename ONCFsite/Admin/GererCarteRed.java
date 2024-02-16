package ONCFsite.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GererCarteRed extends JFrame {

    private JTable reductionTable;
    private Connection connection;
    private JTextField nomRedField;
    private JTextField pourcentageField;
    private JButton addButton;
    private JButton deleteButton;

    public GererCarteRed() {
        setTitle("Liste des Réductions");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);


        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oncf", "root", "Nima13072002+-");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }


        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5)); 
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 

        
        inputPanel.add(new JLabel("Nom de la Réduction:"));
        nomRedField = new JTextField(10);
        inputPanel.add(nomRedField);
        inputPanel.add(new JLabel("Pourcentage de Réduction:"));
        pourcentageField = new JTextField(5);
        inputPanel.add(pourcentageField);
        addButton = new JButton("Ajouter");
        inputPanel.add(addButton);
        deleteButton = new JButton("Supprimer");
        inputPanel.add(deleteButton);

        
        addButton.addActionListener(e -> {
            String nomRed = nomRedField.getText();
            String pourcentage = pourcentageField.getText();
            if (!nomRed.isEmpty() && !pourcentage.isEmpty()) {
                addReductionToDatabase(nomRed, pourcentage);
                refreshTable(); 
                nomRedField.setText("");
                pourcentageField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        deleteButton.addActionListener(e -> {
            int selectedRow = reductionTable.getSelectedRow();
            if (selectedRow != -1) { 
                String idRed = reductionTable.getValueAt(selectedRow, 0).toString();
                deleteReductionFromDatabase(idRed); 
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une réduction à supprimer", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        refreshTable();

        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new JScrollPane(reductionTable), BorderLayout.CENTER);
        getContentPane().add(inputPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    
    private void refreshTable() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM oncf.reduction");

            reductionTable = new JTable(buildTableModel(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des données depuis la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public static DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();

        
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }

        
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        while (resultSet.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = resultSet.getObject(i);
            }
            tableModel.addRow(rowData);
        }

        return tableModel;
    }

    
    private void addReductionToDatabase(String nomRed, String pourcentage) {
        try {
            String query = "INSERT INTO oncf.reduction (NomReduction, Pourcentage) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nomRed);
            statement.setString(2, pourcentage);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Réduction ajoutée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la réduction", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteReductionFromDatabase(String idRed) {
        try {
            String query = "DELETE FROM oncf.reductions WHERE idreductions = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, idRed);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Réduction supprimée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de la réduction", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GererCarteRed();
        });
    }
}

