package dcom.assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.rmi.*;
import java.net.MalformedURLException;

public class AppTheme {
    // Global frame size
    public static final int FRAME_WIDTH = 400;
    public static final int FRAME_HEIGHT = 300;
    public static final int TEXTFIELD = 30;

}
// This class serves as the main entry point for the application
class Firstpage extends JFrame implements ActionListener {
    JButton loginButton, registerButton, exitButton;

    public Firstpage() {
        setTitle("Start Page");
        setSize(AppTheme.FRAME_WIDTH, AppTheme.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Optional padding
        

        JLabel label = new JLabel("Welcome! Choose an option:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // 20px vertical space
        panel.add(label);

        Dimension buttonSize = new Dimension(200, 30); // Width: 200px, Height: 30px

        loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setPreferredSize(buttonSize);
        loginButton.setMaximumSize(buttonSize);
        loginButton.addActionListener(this);
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        registerButton = new JButton("Register");
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setPreferredSize(buttonSize);
        registerButton.setMaximumSize(buttonSize);
        registerButton.addActionListener(this);
        panel.add(registerButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setPreferredSize(buttonSize);
        exitButton.setMaximumSize(buttonSize);
        exitButton.addActionListener(this);
        panel.add(exitButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            new LoginPage();
            dispose();
        } else if (e.getSource() == registerButton) {
            new RegisterPage();
            dispose();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {    
        new Firstpage();
    }
}

class RegisterPage extends JFrame implements ActionListener {
    JTextField firstNameField, lastNameField, icField;
    JButton submitButton, backButton;

    public RegisterPage() {
        setTitle("Register Page");
        setSize(AppTheme.FRAME_WIDTH, AppTheme.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main vertical panel (like Flutter's Column)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // First Name Row
        JPanel firstNameRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        firstNameRow.add(new JLabel("First Name:"));
        firstNameField = new JTextField(AppTheme.TEXTFIELD);
        firstNameRow.add(firstNameField);

        // Last Name Row
        JPanel lastNameRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lastNameRow.add(new JLabel("Last Name:"));
        lastNameField = new JTextField(AppTheme.TEXTFIELD);
        lastNameRow.add(lastNameField);

        // IC Number Row
        JPanel icRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        icRow.add(new JLabel("IC Number:"));
        icField = new JTextField(AppTheme.TEXTFIELD);
        icRow.add(icField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        buttonPanel.add(backButton);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        buttonPanel.add(submitButton);

        // Add all rows to main panel
        mainPanel.add(firstNameRow);
        mainPanel.add(lastNameRow);
        mainPanel.add(icRow);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // spacing
        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String first = firstNameField.getText();
            String last = lastNameField.getText();
            String ic = icField.getText();
            if (!first.isEmpty() && !last.isEmpty() && !ic.isEmpty()) {
                // users.put(ic, new String[]{first, last});
                JOptionPane.showMessageDialog(this, "Registration successful!");
                new Firstpage(); // Go back to first page
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
            }
        } else if (e.getSource() == backButton) {
            new Firstpage(); // Return to start page
            dispose();  
        }
    }
}

class LoginPage extends JFrame implements ActionListener {
    JTextField icField;
    JButton loginBtn, backBtn;

    public LoginPage() {
        setTitle("Login Page");
        setSize(AppTheme.FRAME_WIDTH, AppTheme.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with vertical layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel label = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.add(new JLabel("Enter IC Number:"));
        icField = new JTextField(AppTheme.TEXTFIELD);
        label.add(icField);

        mainPanel.add(label);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    
        // Button row
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        loginBtn = new JButton("Login");
        loginBtn.addActionListener(this);
        buttonPanel.add(loginBtn);

        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        buttonPanel.add(backBtn);

        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            String ic = icField.getText().trim();
            try {
                RMIinterface obj = (RMIinterface) Naming.lookup("rmi://localhost:1060/sub");
                // Assuming your RMI interface has a method like `login(String ic)`
                String message = obj.login(ic);

                if (message.contains("success")) {
                    JOptionPane.showMessageDialog(this, "Welcome, " + message + " ");
                    new MenuPage(); // Navigate to menu
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "IC not found. Please register first.");
                }

            } catch (RemoteException | NotBoundException | MalformedURLException ex) {
                ex.printStackTrace(); // Good for debugging
                JOptionPane.showMessageDialog(this, "Error connecting to server: " + ex.getMessage());
            }
        } else if (e.getSource() == backBtn) {
            new Firstpage(); // Return to start page
            dispose();
        }
    }
}

    class MenuPage extends JFrame implements ActionListener {
        JButton function1Btn, function2Btn, logoutBtn;

        public MenuPage() {
            setTitle("Menu Page");
            setSize(AppTheme.FRAME_WIDTH, AppTheme.FRAME_HEIGHT);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            Dimension buttonSize = new Dimension(200, 30); // Width: 200px, Height: 30px

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            // Label centered
            JLabel label = new JLabel("Choose a function:");
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);
            panel.add(Box.createRigidArea(new Dimension(0, 20))); // spacing

            // Function 1 Button
            function1Btn = new JButton("Function 1");
            function1Btn.addActionListener(this);
            function1Btn.setPreferredSize(buttonSize);
            function1Btn.setMaximumSize(buttonSize);
            JPanel f1Panel = new JPanel();
            f1Panel.setLayout(new BoxLayout(f1Panel, BoxLayout.X_AXIS));
            f1Panel.setOpaque(false); // no background
            f1Panel.add(Box.createHorizontalGlue());
            f1Panel.add(function1Btn);
            f1Panel.add(Box.createHorizontalGlue());
            panel.add(f1Panel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Function 2 Button
            function2Btn = new JButton("Function 2");
            function2Btn.addActionListener(this);
            function2Btn.setPreferredSize(buttonSize);
            function2Btn.setMaximumSize(buttonSize);
            JPanel f2Panel = new JPanel();
            f2Panel.setLayout(new BoxLayout(f2Panel, BoxLayout.X_AXIS));
            f2Panel.setOpaque(false);
            f2Panel.add(Box.createHorizontalGlue());
            f2Panel.add(function2Btn);
            f2Panel.add(Box.createHorizontalGlue());
            panel.add(f2Panel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Logout Button
            logoutBtn = new JButton("Logout");
            logoutBtn.addActionListener(this);
            logoutBtn.setPreferredSize(buttonSize);
            logoutBtn.setMaximumSize(buttonSize);
            JPanel logoutPanel = new JPanel();
            logoutPanel.setLayout(new BoxLayout(logoutPanel, BoxLayout.X_AXIS));
            logoutPanel.setOpaque(false);
            logoutPanel.add(Box.createHorizontalGlue());
            logoutPanel.add(logoutBtn);
            logoutPanel.add(Box.createHorizontalGlue());
            panel.add(logoutPanel);

            add(panel);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == function1Btn) {
                JOptionPane.showMessageDialog(this, "Function 1 selected.");
                // new Function1Page(); // or your custom page

            } else if (e.getSource() == function2Btn) {
                JOptionPane.showMessageDialog(this, "Function 2 selected.");
                // new Function2Page();

            } else if (e.getSource() == logoutBtn) {
                new Firstpage(); // Return to start page
                dispose();
            }
        }
    }
