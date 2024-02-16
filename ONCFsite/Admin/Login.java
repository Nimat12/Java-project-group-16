package ONCFsite.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Connection connection;

    public Login() {
        setTitle("Login Page");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);
        setVisible(true);

        // Initialize database connection
        connection = establishConnection();
    }

    // Establish database connection
    private Connection establishConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "Nima13072002+-");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to the database", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }

    // Handle login button click event
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String Name = nameField.getText();
            String Email = emailField.getText();
            String Password = new String(passwordField.getPassword());

            if (Name.isEmpty() || Email.isEmpty() || Password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name, Email, and Password fields cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    // SQL query to check user credentials
                    String query = "SELECT * FROM users.userstable WHERE Name=? AND Email=? AND Password=?";

                    // Create a prepared statement
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setString(1, Name);
                    ps.setString(2, Email);
                    ps.setString(3, Password);

                    // Execute the query
                    ResultSet rs = ps.executeQuery();

                    // Check if the result set has any rows (i.e., if the credentials are valid)
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // Open Button class upon successful login
                        new Boutton().setVisible(true);
                        dispose(); // Close the login window
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Close the result set and prepared statement
                    rs.close();
                    ps.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Database error", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        // Run the login page
        SwingUtilities.invokeLater(() -> new Login());
    }
}

