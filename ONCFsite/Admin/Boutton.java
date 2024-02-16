package ONCFsite.Admin;

import java.awt.*;
import java.awt.event.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ONCFsite.Admin.GererCarteRed;
import ONCFsite.Admin.Rapport;


public class Boutton extends JFrame {
    private JButton bouttonPage1;
    private JButton bouttonPage2;
    private JButton bouttonPage3;

    private JPanel mainPanel;
    private int spacing = 10; // Spacing between buttons

    public Boutton() {
        setTitle("Page Principale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        bouttonPage1 = new JButton("Gestion des trajets");
        bouttonPage2 = new JButton("Gestion de reductions");
        bouttonPage3 = new JButton("Rapport");

        bouttonPage1.setAlignmentX(Component.CENTER_ALIGNMENT);
        bouttonPage2.setAlignmentX(Component.CENTER_ALIGNMENT);
        bouttonPage3.setAlignmentX(Component.CENTER_ALIGNMENT);

        bouttonPage1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GestionTrajet.createAndShowGUI();
            }
        });


        bouttonPage2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Action to perform when bouttonPage2 is clicked
                // For example:
                GererCarteRed gererCarteRed = new GererCarteRed();
                gererCarteRed.setVisible(true);
            }
        });

        bouttonPage3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Action to perform when bouttonPage3 is clicked
                // For example:
                Rapport rapport = new Rapport();
                rapport.setVisible(true);
            }
        });

        bouttonPage1.setPreferredSize(new Dimension(200, 50)); // Set preferred size for button 1
        bouttonPage2.setPreferredSize(new Dimension(200, 50)); // Set preferred size for button 2
        bouttonPage3.setPreferredSize(new Dimension(200, 50)); // Set preferred size for button 3

        mainPanel.add(Box.createVerticalStrut(spacing));
        mainPanel.add(bouttonPage1);
        mainPanel.add(Box.createVerticalStrut(spacing));
        mainPanel.add(bouttonPage2);
        mainPanel.add(Box.createVerticalStrut(spacing));
        mainPanel.add(bouttonPage3);

        add(mainPanel);
        pack(); // Packs the components tightly
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Boutton();
        });
    }
}