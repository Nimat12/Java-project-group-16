package ONCFsite.First;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ONCFsite.Admin.Login;
import ONCFsite.Client.PageChoixTrajet;

public class First extends JFrame {
    private JButton userButton;
    private JButton adminButton;
    private Image backgroundImage;

    public First() {
        setTitle("First page");
        setSize(500, 400); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        backgroundImage = new ImageIcon("C:\\Users\\khald\\train.jpg").getImage();

       
        setLayout(new BorderLayout());

        
        JPanel centerPanel = new JPanel(new GridBagLayout());

        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding around the buttons

        
        userButton = new JButton("User");
        adminButton = new JButton("Admin");

        
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);
        userButton.setFont(buttonFont);
        adminButton.setFont(buttonFont);

        
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Ferme la fenêtre actuelle
                SwingUtilities.invokeLater(() -> new PageChoixTrajet().setVisible(true));
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                new Login(); 
            }
        });

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(userButton, gbc);

        gbc.gridy = 1;
        centerPanel.add(adminButton, gbc);


        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public static void main(String[] args) {
        new First();
    }
}

