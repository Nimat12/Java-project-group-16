package ONCFsite.Client;

import java.sql.*;

public class ReductionDAO {
    public static double getReduction(String categorie, String code) {
        double reduction = 0.0; // Par défaut, pas de réduction

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oncf", "root", "Nima13072002+-");
             PreparedStatement statement = connection.prepareStatement("SELECT taux_reduction FROM personnes_reduction WHERE type_reduction = ? AND code_reduction = ?")) {
            statement.setString(1, categorie);
            statement.setString(2, code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                reduction = resultSet.getDouble("taux_reduction");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur de connexion à la base de données
        }

        return reduction;
    }

	public static boolean verifierCodeReduction(String categorie, String code) {
		// TODO Auto-generated method stub
		return false;
	}
}