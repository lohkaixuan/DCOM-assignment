package dcom.assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Firstpage extends JFrame implements ActionListener, KeyListener  {

    JLabel loginLabel;
    JLabel registerLabel;
    JButton exitButton;

    public Firstpage() {
        setTitle("First Page");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // Panel for labels
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(2, 1, 10, 10));
        loginLabel = new JLabel("Login", SwingConstants.CENTER);
        registerLabel = new JLabel("Register", SwingConstants.CENTER);
        labelPanel.add(loginLabel);
        labelPanel.add(registerLabel);

        // Exit button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);

        // Add components
        add(labelPanel, BorderLayout.CENTER);
        add(exitButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new Firstpage();
    }    

}
