package dcom.assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.rmi.*;
import java.net.MalformedURLException;
import java.nio.file.Files;

import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;

public class AppTheme {
    // Global frame size
    public static final int FRAME_WIDTH = 600;
    public static final int FRAME_HEIGHT = 600;
    public static final int TEXTFIELD = 30;
    // public static final String RMI_HOST = "192.168.100.26"; // Change to your
    // RMI server host
    public static final String RMI_HOST = "localhost"; // Change to your RMI server host
    public static final int RMI_PORT = 1060; // Change to your RMI server port
    public static final String RMI_SERVICE = "sub"; // Change to your RMI service name

}
class Session {
    private Session() {}
    public static Employee currentUser;
} 

class LoginPage extends JFrame implements ActionListener {
    JTextField icField, passwordField;
    JButton loginBtn, backBtn;

    public LoginPage() {
        setTitle("Login Page");
        setSize(370, 250);
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

        JPanel label2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.add(new JLabel("Enter Password:"));
        passwordField = new JTextField(AppTheme.TEXTFIELD);
        label2.add(passwordField);

        mainPanel.add(label2);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        // Button row
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginBtn = new JButton("Login");
        loginBtn.addActionListener(this);
        buttonPanel.add(loginBtn);

        mainPanel.add(buttonPanel);
        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            String ic = icField.getText().trim();
            String password = passwordField.getText().trim();

            try {
                String url = String.format("rmi://%s:%d/%s", AppTheme.RMI_HOST, AppTheme.RMI_PORT,
                        AppTheme.RMI_SERVICE);
                        System.out.println("Connecting to RMI server at: " + url); // Debugging line
                RMIinterface obj = (RMIinterface) Naming.lookup(url);
                Employee emp = obj.login(ic, password);
                if (emp != null) {
                    Session.currentUser = emp; // Store the user globally
                    JOptionPane.showMessageDialog(this, "Welcome " + emp.getFirstName());
                    new MenuPage();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Login failed.");
                }
            } catch (RemoteException | NotBoundException | MalformedURLException ex) {
                ex.printStackTrace(); // Good for debugging
                JOptionPane.showMessageDialog(this, "Error connecting to server: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}

class MenuPage extends JFrame implements ActionListener {
    JButton profileBtn, payrollBtn, setpayrollBtn, registerBtn, showallBtn, logoutBtn;

    public MenuPage() {
        setTitle("Menu Page");
        setSize(370, 370);
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

        Employee emp = Session.currentUser; // Get the current user from the session
        if (emp != null && emp.getRole().equalsIgnoreCase("hr")) {
            registerBtn = new JButton("Register Employee");
            registerBtn.addActionListener(this);
            registerBtn.setPreferredSize(buttonSize);
            registerBtn.setMaximumSize(buttonSize);
            JPanel registerPanel = new JPanel();
            registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.X_AXIS));
            registerPanel.setOpaque(false); // no background
            registerPanel.add(Box.createHorizontalGlue());
            registerPanel.add(registerBtn);
            registerPanel.add(Box.createHorizontalGlue());
            panel.add(registerPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            // setpayroll
            setpayrollBtn = new JButton("Set Payroll Employee");
            setpayrollBtn.addActionListener(this);
            setpayrollBtn.setPreferredSize(buttonSize);
            setpayrollBtn.setMaximumSize(buttonSize);
            JPanel setpayrollPanel = new JPanel();
            setpayrollPanel.setLayout(new BoxLayout(setpayrollPanel, BoxLayout.X_AXIS));
            setpayrollPanel.setOpaque(false); // no background
            setpayrollPanel.add(Box.createHorizontalGlue());
            setpayrollPanel.add(setpayrollBtn);
            setpayrollPanel.add(Box.createHorizontalGlue());
            panel.add(setpayrollPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            // showall
            showallBtn = new JButton("Show all Payroll & Employee");
            showallBtn.addActionListener(this);
            showallBtn.setPreferredSize(buttonSize);
            showallBtn.setMaximumSize(buttonSize);
            JPanel showallPanel = new JPanel();
            showallPanel.setLayout(new BoxLayout(showallPanel, BoxLayout.X_AXIS));
            showallPanel.setOpaque(false); // no background
            showallPanel.add(Box.createHorizontalGlue());
            showallPanel.add(showallBtn);
            showallPanel.add(Box.createHorizontalGlue());
            panel.add(showallPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // Profile Button
        profileBtn = new JButton("Profile");
        profileBtn.addActionListener(this);
        profileBtn.setPreferredSize(buttonSize);
        profileBtn.setMaximumSize(buttonSize);
        JPanel profilepanel = new JPanel();
        profilepanel.setLayout(new BoxLayout(profilepanel, BoxLayout.X_AXIS));
        profilepanel.setOpaque(false); // no background
        profilepanel.add(Box.createHorizontalGlue());
        profilepanel.add(profileBtn);
        profilepanel.add(Box.createHorizontalGlue());
        panel.add(profilepanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Payroll Button
        payrollBtn = new JButton("Payroll");
        payrollBtn.addActionListener(this);
        payrollBtn.setPreferredSize(buttonSize);
        payrollBtn.setMaximumSize(buttonSize);
        JPanel payrollPanel = new JPanel();
        payrollPanel.setLayout(new BoxLayout(payrollPanel, BoxLayout.X_AXIS));
        payrollPanel.setOpaque(false);
        payrollPanel.add(Box.createHorizontalGlue());
        payrollPanel.add(payrollBtn);
        payrollPanel.add(Box.createHorizontalGlue());
        panel.add(payrollPanel);
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
        if (e.getSource() == profileBtn) {
            new ProfilePage();
            dispose();
        } else if (e.getSource() == payrollBtn) {
            new PayrollPage();
            dispose();
        } else if (e.getSource() == setpayrollBtn) {
            new SetPayrollForUser();
            dispose();
        } else if (e.getSource() == showallBtn) {
            new ShowallPage();
            dispose();
        } else if (e.getSource() == logoutBtn) { 
             dispose();
                new LoginPage();
            // System.exit(0);
        } else if (e.getSource() == registerBtn) {
            new RegisterPage();
            dispose();
        }
    }
}

class RegisterPage extends JFrame implements ActionListener {
    JTextField icField, passowrdField, firstNameField, lastNameField;
    JButton submitButton, backButton;
    JRadioButton hrRadio, employeeRadio;

    public RegisterPage() {
        setTitle("Register Page");
        setSize(370, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main vertical panel (like Flutter's Column)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around the panel

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

        // Passowrd Row
        JPanel passowrdRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passowrdRow.add(new JLabel("Password:"));
        passowrdField = new JTextField(AppTheme.TEXTFIELD);
        passowrdRow.add(passowrdField);

        // Role Row
        JPanel roleRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        roleRow.add(new JLabel("Role:"));

        hrRadio = new JRadioButton("hr");
        employeeRadio = new JRadioButton("employee");
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(hrRadio);
        roleGroup.add(employeeRadio);
        roleRow.add(hrRadio);
        roleRow.add(employeeRadio);

        // button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalStrut(20)); // 20 pixels of space
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        buttonPanel.add(submitButton);

        // Add all rows to main panel
        mainPanel.add(firstNameRow);
        mainPanel.add(lastNameRow);
        mainPanel.add(icRow);
        mainPanel.add(passowrdRow);
        mainPanel.add(roleRow);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // spacing
        mainPanel.add(buttonPanel);
        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String first = firstNameField.getText().trim();
            String last = lastNameField.getText().trim();
            String ic = icField.getText().trim();
            String password = passowrdField.getText().trim();
            String role = hrRadio.isSelected() ? "hr" : (employeeRadio.isSelected() ? "employee" : "");

            if (first.isEmpty() || last.isEmpty() || ic.isEmpty() || password.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            if (!role.equals("hr") && !role.equals("employee")) {
                JOptionPane.showMessageDialog(this, "Role must be either 'hr' or 'employee'.");
                return;
            }
            try {
                String url = String.format("rmi://%s:%d/%s", AppTheme.RMI_HOST, AppTheme.RMI_PORT,
                        AppTheme.RMI_SERVICE);
                RMIinterface obj = (RMIinterface) Naming.lookup(url);
                Employee emp = obj.addnewEmplyee(ic, password, first, last, role);
                if (emp != null) {
                    JOptionPane.showMessageDialog(this, "Registration successful!");
                    new MenuPage();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed.");
                }
            } catch (RemoteException | NotBoundException | MalformedURLException ex) {
                ex.printStackTrace(); // Good for debugging
                JOptionPane.showMessageDialog(this, "Error connecting to server: " + ex.getMessage());
            }
        } else if (e.getSource() == backButton) {
            dispose();
            new MenuPage(); // Go back to the menu page
        }
    }
}

class ProfilePage extends JFrame implements ActionListener {
    JTextField firstNameField, lastNameField, phoneField, passwordField; // JPasswordField passwordField;
    JLabel icLabel, roleLabel;
    JButton updateButton, backButton;

    public ProfilePage() {
        Employee emp = Session.currentUser; // Get the current user from the session

        setTitle("Profile Page");
        setSize(430, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // IC (non-editable)
        JPanel icRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        icRow.add(new JLabel("IC Number:"));
        icLabel = new JLabel(emp.getIC());
        icRow.add(icLabel);

        // First Name
        JPanel firstNameRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        firstNameRow.add(new JLabel("First Name:"));
        firstNameField = new JTextField(emp.getFirstName(), AppTheme.TEXTFIELD);
        firstNameRow.add(firstNameField);

        // Last Name
        JPanel lastNameRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lastNameRow.add(new JLabel("Last Name:"));
        lastNameField = new JTextField(emp.getLastName(), AppTheme.TEXTFIELD);
        lastNameRow.add(lastNameField);

        // Password
        JPanel passwordRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordRow.add(new JLabel("Password:"));
        passwordField = new JTextField(emp.getPassword(), AppTheme.TEXTFIELD);
        passwordRow.add(passwordField);

        // Role
        JPanel roleRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        roleRow.add(new JLabel("Role:"));
        roleLabel = new JLabel(emp.getRole());
        roleRow.add(roleLabel);

        // phone number
        JPanel phoneRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        phoneRow.add(new JLabel("Phone:"));
        phoneField = new JTextField(emp.getPhoneNumber(), AppTheme.TEXTFIELD);
        phoneRow.add(phoneField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalStrut(20)); // 20 pixels of space
        updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        buttonPanel.add(updateButton);

        // Add to main panel
        mainPanel.add(icRow);
        mainPanel.add(firstNameRow);
        mainPanel.add(lastNameRow);
        mainPanel.add(passwordRow);
        mainPanel.add(roleRow);
        mainPanel.add(phoneRow);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            String first = firstNameField.getText();
            String last = lastNameField.getText();
            String pass = passwordField.getText();
            String phone = phoneField.getText();

            if (!first.isEmpty() && !last.isEmpty() && !pass.isEmpty() && !phone.isEmpty()) {

                try {
                    String url = String.format("rmi://%s:%d/%s", AppTheme.RMI_HOST, AppTheme.RMI_PORT,
                            AppTheme.RMI_SERVICE);
                    RMIinterface obj = (RMIinterface) Naming.lookup(url);
                    Employee updatedEmployee = new Employee(
                            Session.currentUser.getIC(), // Use the current user's IC
                            pass, // Updated password
                            first, // Updated first name
                            last, // Updated last name
                            Session.currentUser.getRole(), // Keep the same role
                            phone // Updated phone number
                    );
                    Employee emp = obj.editEmployeeByIC(updatedEmployee);
                    if (emp != null) {
                        Session.currentUser = emp; // Store the user globally
                        JOptionPane.showMessageDialog(this, "Profile updated successfully!");
                        new MenuPage();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Login failed.");
                    }
                } catch (RemoteException | NotBoundException | MalformedURLException ex) {
                    ex.printStackTrace(); // Good for debugging
                    JOptionPane.showMessageDialog(this, "Error connecting to server: " + ex.getMessage());
                }

            } else {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
            }
        } else if (e.getSource() == backButton) {
            new MenuPage(); // or whatever role/page you want to go back to
            dispose();
        }
    }
}

class PayrollPage extends JFrame implements ActionListener {
    JButton backButton;
    JLabel monthYearLabel, hoursLabel, basicSalaryLabel, taxLabel, grossPayLabel, deductionLabel, netPayLabel;
    JList<String> payrollList;
    DefaultListModel<String> listModel;
    List<PayrollRecord> recordlist;

    public PayrollPage() {
        Employee emp = Session.currentUser; // Get the current user from the session
        setTitle("Payroll Page");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left: Payroll list
        listModel = new DefaultListModel<>();
        try {
            String url = String.format("rmi://%s:%d/%s", AppTheme.RMI_HOST, AppTheme.RMI_PORT,
                    AppTheme.RMI_SERVICE);
            RMIinterface obj = (RMIinterface) Naming.lookup(url);
            recordlist = obj.getPayrollForEmployee(emp.getIC());
            if (recordlist != null && !recordlist.isEmpty()) {
                for (PayrollRecord record : recordlist) {
                    listModel.addElement(record.getMonth() + " " + record.getYear());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Record Empty.");
            }
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + ex.getMessage());
        }
        payrollList = new JList<>(listModel);
        payrollList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroll = new JScrollPane(payrollList);
        listScroll.setPreferredSize(new Dimension(150, 300));
        mainPanel.add(listScroll, BorderLayout.WEST);

        // Right: Details panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Payroll Details"));

        monthYearLabel = new JLabel("Month / Year: ");
        hoursLabel = new JLabel("Hours: ");
        basicSalaryLabel = new JLabel("Basic Salary: ");
        taxLabel = new JLabel("Tax: ");
        grossPayLabel = new JLabel("Gross Pay: ");
        deductionLabel = new JLabel("Deduction: ");
        netPayLabel = new JLabel("Net Pay: ");

        detailsPanel.add(monthYearLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailsPanel.add(hoursLabel);
        detailsPanel.add(basicSalaryLabel);
        detailsPanel.add(taxLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailsPanel.add(grossPayLabel);
        detailsPanel.add(deductionLabel);
        detailsPanel.add(netPayLabel);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        // Bottom: Back and Clear buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        buttonPanel.add(backButton);

        // Add Clear button
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            payrollList.clearSelection();
            clearPayrollLabels();
        });
        buttonPanel.add(clearButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // List selection listener
        payrollList.addListSelectionListener(e -> {
            int idx = payrollList.getSelectedIndex();
            if (idx >= 0 && recordlist != null && idx < recordlist.size()) {
                PayrollRecord record = recordlist.get(idx);
                monthYearLabel.setText("Month / Year: " + record.getMonth() + " " + record.getYear());
                hoursLabel.setText("Hours: " + record.getHoursWorked());
                basicSalaryLabel.setText("Basic Salary: " + record.getBasicSalary());
                taxLabel.setText("Tax: " + record.getTaxAmount());
                grossPayLabel.setText("Gross Pay: " + record.getGrossPay());
                deductionLabel.setText("Deduction: " + record.getDeduction());
                netPayLabel.setText("Net Pay: " + record.getNetPay());
            }
        });

        add(mainPanel);
        setVisible(true);

        // Select the first record by default if available
        if (!listModel.isEmpty()) {
            payrollList.setSelectedIndex(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            new MenuPage();
            dispose();
        }
    }

    // Helper method to clear labels
    private void clearPayrollLabels() {
        monthYearLabel.setText("Month / Year: ");
        hoursLabel.setText("Hours: ");
        basicSalaryLabel.setText("Basic Salary: ");
        taxLabel.setText("Tax: ");
        grossPayLabel.setText("Gross Pay: ");
        deductionLabel.setText("Deduction: ");
        netPayLabel.setText("Net Pay: ");
    }
}

class SetPayrollForUser extends JFrame implements ActionListener {
    JList<String> employeeList;
    DefaultListModel<String> listModel;
    List<Employee> recordlist;
    JTextField taxField, hourField, basicField, yearField;
    JComboBox<String> monthCombo;
    JButton submitButton, backButton;

    public SetPayrollForUser() {
        setTitle("Set Payroll for User");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left: Employee list
        listModel = new DefaultListModel<>();
        try {
            String url = String.format("rmi://%s:%d/%s", AppTheme.RMI_HOST, AppTheme.RMI_PORT,
                    AppTheme.RMI_SERVICE);
            RMIinterface obj = (RMIinterface) Naming.lookup(url);
            recordlist = obj.getAllEmployees();
            if (recordlist != null && !recordlist.isEmpty()) {
                for (Employee record : recordlist) {
                    listModel.addElement(record.getFirstName() + " " + record.getLastName());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Record Empty.");
            }
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + ex.getMessage());
        }
        employeeList = new JList<>(listModel);
        employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroll = new JScrollPane(employeeList);
        listScroll.setPreferredSize(new Dimension(180, 250));
        mainPanel.add(listScroll, BorderLayout.WEST);

        // Right: Payroll input fields
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Set Payroll"));

        JPanel taxRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        taxRow.add(new JLabel("Tax:"));
        taxField = new JTextField(10);
        taxRow.add(taxField);

        JPanel hourRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        hourRow.add(new JLabel("Hour:"));
        hourField = new JTextField(10);
        hourRow.add(hourField);

        JPanel basicRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        basicRow.add(new JLabel("Basic:"));
        basicField = new JTextField(10);
        basicRow.add(basicField);

        // Month row (use JComboBox, not JTextField)
        JPanel monthRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        monthRow.add(new JLabel("Month:"));
        String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        monthCombo = new JComboBox<>(months);
        monthRow.add(monthCombo);

        JPanel yearRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        yearRow.add(new JLabel("Year:"));
        yearField = new JTextField(10);
        yearRow.add(yearField);

        detailsPanel.add(taxRow);
        detailsPanel.add(hourRow);
        detailsPanel.add(basicRow);
        detailsPanel.add(monthRow);
        detailsPanel.add(yearRow);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        // Bottom: Submit and Back buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        buttonPanel.add(submitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // List selection listener
        employeeList.addListSelectionListener(e -> {
            taxField.setText("");
            hourField.setText("");
            basicField.setText("");
            yearField.setText("");
        });
        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            new MenuPage();
            dispose();
        } else if (e.getSource() == submitButton) {
            int idx = employeeList.getSelectedIndex();
            if (idx < 0) {
                JOptionPane.showMessageDialog(this, "Please select an employee.");
                return;
            }
            String icNumber = recordlist.get(idx).getIC();
            String hourStr = hourField.getText().trim();
            String basicStr = basicField.getText().trim();
            String taxStr = taxField.getText().trim();
            String month = monthCombo.getSelectedItem().toString();
            String yearStr = yearField.getText().trim();

            if (hourStr.isEmpty() || basicStr.isEmpty() || taxStr.isEmpty() || month.isEmpty() || yearStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }
            try {
                double hoursWorked = Double.parseDouble(hourStr);
                double basicSalary = Double.parseDouble(basicStr);
                double taxAmount = Double.parseDouble(taxStr);
                int year = Integer.parseInt(yearStr);
                PayrollRecord payrollRecord = new PayrollRecord(icNumber, hoursWorked, basicSalary, taxAmount, month,
                        year);
                String url = String.format("rmi://%s:%d/%s", AppTheme.RMI_HOST, AppTheme.RMI_PORT,
                        AppTheme.RMI_SERVICE);
                RMIinterface obj = (RMIinterface) Naming.lookup(url); // Call your function to create payroll record
                String message = obj.setPayrollForUser(payrollRecord);
                if (message.contains("Failed")) {
                    JOptionPane.showMessageDialog(this, "Failed to create payroll record." + message);
                } else {
                    JOptionPane.showMessageDialog(this, "Successfully  create payroll record.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Hour, Basic, Tax, and Year must be numbers.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
}

class ShowallPage extends JFrame implements ActionListener {
    JTable payrollTable;
    DefaultTableModel tableModel;
    JButton backButton, startButton, exportButton, deserializeButton; // NEW

    public ShowallPage() {
        setTitle("Payroll Overview");
        setSize(900, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table columns
        String[] columnNames = {
                "Employee Name", "Hours", "Basic Salary", "Tax",
                "Gross Pay", "Deduction", "Net Pay", "Month", "Year"
        };

        // Table model and table
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        payrollTable = new JTable(tableModel);
        payrollTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScroll = new JScrollPane(payrollTable);

        // Panels & buttons
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(tableScroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButton       = new JButton("Back");
        startButton      = new JButton("Start");
        exportButton     = new JButton("Export Selected Row");
        deserializeButton = new JButton("Deserialize..."); // NEW

        backButton.addActionListener(this);
        startButton.addActionListener(this);
        exportButton.addActionListener(this);
        deserializeButton.addActionListener(this); // NEW
        exportButton.setEnabled(false);

        buttonPanel.add(backButton);
        buttonPanel.add(startButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(deserializeButton); // NEW

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Selection listener AFTER exportButton is created
        payrollTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    exportButton.setEnabled(payrollTable.getSelectedRow() != -1);
                }
            }
        });

        setVisible(true);
    }

    /** UI helper: add a row using Employee + PayrollRecord */
    public void addPayrollRow(Employee emp, PayrollRecord record) {
        SwingUtilities.invokeLater(() -> {
            tableModel.addRow(new Object[] {
                    emp.getFirstName() + " " + emp.getLastName(),
                    record.getHoursWorked(),
                    record.getBasicSalary(),
                    record.getTaxAmount(),
                    record.getGrossPay(),
                    record.getDeduction(),
                    record.getNetPay(),
                    record.getMonth(),
                    record.getYear()
            });
        });
    }

    /** Build a DTO from the currently selected row */
    private PayrollRowDTO buildDTOFromSelectedRow() {
        int r = payrollTable.getSelectedRow();
        if (r == -1) return null;

        String employeeName = String.valueOf(tableModel.getValueAt(r, 0));
        double hours       = parseDouble(tableModel.getValueAt(r, 1));
        double basic       = parseDouble(tableModel.getValueAt(r, 2));
        double tax         = parseDouble(tableModel.getValueAt(r, 3));
        double gross       = parseDouble(tableModel.getValueAt(r, 4));
        double deduction   = parseDouble(tableModel.getValueAt(r, 5));
        double net         = parseDouble(tableModel.getValueAt(r, 6));
        String month       = String.valueOf(tableModel.getValueAt(r, 7));
        String year        = String.valueOf(tableModel.getValueAt(r, 8));

        return new PayrollRowDTO(employeeName, hours, basic, tax, gross, deduction, net, month, year);
    }

    private double parseDouble(Object o) {
        if (o == null) return 0.0;
        try { return Double.parseDouble(String.valueOf(o)); }
        catch (NumberFormatException e) { return 0.0; }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == backButton) {
            new MenuPage();
            dispose();

        } else if (src == startButton) {
            tableModel.setRowCount(0);
            payrollTable.clearSelection();
            exportButton.setEnabled(false);
            MultiThread multiThread = new MultiThread(this);
            multiThread.getAllInfo();

        } else if (src == exportButton) {
            PayrollRowDTO dto = buildDTOFromSelectedRow();
            if (dto == null) {
                JOptionPane.showMessageDialog(this, "Please select a row to export.", "No Selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save Payroll Row as PDF");
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF Files (*.pdf)", "pdf"));

            String baseName = String.format(
                    "payroll_%s_%s_%s",
                    slug(dto.getEmployeeName()),
                    slug(dto.getMonth()),
                    slug(dto.getYear())
            );
            chooser.setSelectedFile(new File(baseName + ".pdf"));

            int result = chooser.showSaveDialog(this);
            if (result != JFileChooser.APPROVE_OPTION) return;

            File pdfFile = chooser.getSelectedFile();
            if (!pdfFile.getName().toLowerCase().endsWith(".pdf")) {
                pdfFile = new File(pdfFile.getParentFile(), pdfFile.getName() + ".pdf");
            }

            try {
                BasicPdfExporter.exportPayrollRowToPDF(dto, pdfFile);
                File serFile = new File(pdfFile.getParentFile(),
                        pdfFile.getName().replaceAll("(?i)\\.pdf$", "") + ".ser");
                Serialization.serialize(dto, serFile); // use your util class name
                JOptionPane.showMessageDialog(this,
                        "Exported:\n" + pdfFile.getAbsolutePath() +
                        "\nSerialized:\n" + serFile.getAbsolutePath(),
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Export failed: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (src == deserializeButton) { // NEW
            // If a row is selected, pass it as BEFORE; else null
            PayrollRowDTO before = buildDTOFromSelectedRow();
            new DeserializePage(before);
        }
    }

    /** Make a string safe for filenames: letters/numbers only, others -> '_' */
    private static String slug(String s) {
        if (s == null) return "";
        String t = s.trim().replaceAll("[^A-Za-z0-9]+", "_");
        return t.replaceAll("^_+|_+$", "");
    }
}

class DeserializePage extends JFrame implements ActionListener {

    private final JTextArea beforeArea;
    private final JTextArea serializedArea;   // NEW: shows .ser file bytes (hex/base64)
    private final JTextArea afterArea;

    private final JLabel filePathLabel;
    private final JComboBox<String> viewMode; // "Hex" or "Base64"
    private final JButton chooseBtn, backBtn, exitBtn;

    private final PayrollRowDTO beforeDto;
    private byte[] lastRaw = null;            // remembers last .ser bytes to re-render on mode change

    public DeserializePage(PayrollRowDTO beforeDto) {
        this.beforeDto = beforeDto;

        setTitle("Deserialize Payroll (.ser)");
        setSize(1100, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // --- Top: info + file path + view mode ---
        JPanel north = new JPanel(new BorderLayout(8, 8));
        JLabel info = new JLabel("Pick a .ser file. LEFT = BEFORE (table row), CENTER = Serialized (.ser), RIGHT = AFTER (deserialized).");
        info.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));

        JPanel pathAndMode = new JPanel(new BorderLayout(8, 8));
        filePathLabel = new JLabel("File: (none)");
        filePathLabel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));

        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        modePanel.add(new JLabel("View serialized as:"));
        viewMode = new JComboBox<>(new String[]{"Hex", "Base64"});
        viewMode.addActionListener(e -> renderSerializedAgain());
        modePanel.add(viewMode);

        pathAndMode.add(filePathLabel, BorderLayout.CENTER);
        pathAndMode.add(modePanel,     BorderLayout.EAST);

        north.add(info,         BorderLayout.NORTH);
        north.add(pathAndMode,  BorderLayout.SOUTH);

        // --- Center: three columns ---
        beforeArea     = buildMonospaceArea();
        serializedArea = buildMonospaceArea();
        afterArea      = buildMonospaceArea();

        JPanel leftPanel   = titledPanel("BEFORE (from selected row)", new JScrollPane(beforeArea));
        JPanel middlePanel = titledPanel("SERIALIZED BYTES (.ser)",     new JScrollPane(serializedArea));
        JPanel rightPanel  = titledPanel("AFTER (deserialized from .ser)", new JScrollPane(afterArea));

        JPanel center = new JPanel(new GridLayout(1, 3, 10, 10));
        center.add(leftPanel);
        center.add(middlePanel);
        center.add(rightPanel);

        // --- Bottom: buttons ---
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        chooseBtn = new JButton("Choose .ser File");
        backBtn   = new JButton("Back");
        exitBtn   = new JButton("Exit");
        for (JButton b : new JButton[]{chooseBtn, backBtn, exitBtn}) b.addActionListener(this);
        south.add(chooseBtn);
        south.add(backBtn);
        south.add(exitBtn);

        // --- Compose frame ---
        JPanel root = new JPanel(new BorderLayout(10,10));
        root.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        root.add(north,  BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
        root.add(south,  BorderLayout.SOUTH);
        setContentPane(root);

        // Preload BEFORE area
        beforeArea.setText(beforeDto != null ? formatDto(beforeDto) : "(No row selected in ShowallPage)");

        setVisible(true);
    }

    private static JTextArea buildMonospaceArea() {
        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        ta.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        return ta;
    }

    private static JPanel titledPanel(String title, JComponent content) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder(title));
        p.add(content, BorderLayout.CENTER);
        return p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == chooseBtn) {
            chooseAndDeserialize();
        } else if (src == backBtn) {
            dispose();  // close this window; ShowallPage remains
        } else if (src == exitBtn) {
            int ok = JOptionPane.showConfirmDialog(
                    this, "Exit the application?", "Confirm Exit",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (ok == JOptionPane.YES_OPTION) System.exit(0);
        }
    }

    private void chooseAndDeserialize() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open Serialized Payroll (*.ser)");
        chooser.setFileFilter(new FileNameExtensionFilter("Serialized Files (*.ser)", "ser"));

        int result = chooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;

        File ser = chooser.getSelectedFile();
        filePathLabel.setText("File: " + ser.getAbsolutePath());

        try {
            // Read raw bytes for center column
            lastRaw = Files.readAllBytes(ser.toPath());
            renderSerializedAgain(); // fill center area based on current mode

            // Deserialize for right column
            PayrollRowDTO after = Serialization.deserialize(ser, PayrollRowDTO.class);
            afterArea.setText(formatDto(after));

            // Optional: quick compare
            if (beforeDto != null) {
                boolean same = equalsDto(beforeDto, after);
                String msg = same ? "BEFORE and AFTER are identical." : "BEFORE and AFTER differ.";
                JOptionPane.showMessageDialog(this, msg, "Comparison", same
                        ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to deserialize: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Re-render center area based on selected view mode (Hex or Base64). */
    private void renderSerializedAgain() {
        if (lastRaw == null) {
            serializedArea.setText("(No file chosen)");
            return;
        }
        String mode = (String) viewMode.getSelectedItem();
        if ("Base64".equals(mode)) {
            serializedArea.setText(base64Block(lastRaw));
        } else {
            serializedArea.setText(hexDump(lastRaw));
        }
        serializedArea.setCaretPosition(0);
    }

    // ---------- Formatting helpers ----------

    private String formatDto(PayrollRowDTO d) {
        StringBuilder sb = new StringBuilder();
        sb.append("Employee Name : ").append(d.getEmployeeName()).append("\n");
        sb.append("Month / Year  : ").append(d.getMonth()).append(" ").append(d.getYear()).append("\n");
        sb.append("Hours         : ").append(d.getHoursWorked()).append("\n");
        sb.append("Basic Salary  : ").append(d.getBasicSalary()).append("\n");
        sb.append("Tax           : ").append(d.getTaxAmount()).append("\n");
        sb.append("Gross Pay     : ").append(d.getGrossPay()).append("\n");
        sb.append("Deduction     : ").append(d.getDeduction()).append("\n");
        sb.append("Net Pay       : ").append(d.getNetPay()).append("\n");
        return sb.toString();
    }

    /** Classic 16‑byte per line hex dump with offset and ASCII gutter. */
    private String hexDump(byte[] data) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Length: %d bytes\n", data.length));
        // Detect Java Serialization Stream Magic (AC ED 00 05)
        if (data.length >= 4 &&
            (data[0] & 0xFF) == 0xAC && (data[1] & 0xFF) == 0xED &&
            (data[2] & 0xFF) == 0x00 && (data[3] & 0xFF) == 0x05) {
            sb.append("Header: Java Serialization Stream (AC ED 00 05)\n\n");
        } else {
            sb.append('\n');
        }

        for (int i = 0; i < data.length; i += 16) {
            sb.append(String.format("%08X  ", i));
            // hex bytes
            for (int j = 0; j < 16; j++) {
                if (i + j < data.length) sb.append(String.format("%02X ", data[i + j] & 0xFF));
                else sb.append("   ");
            }
            sb.append(" ");
            // ascii gutter
            for (int j = 0; j < 16; j++) {
                if (i + j < data.length) {
                    int b = data[i + j] & 0xFF;
                    char ch = (b >= 32 && b < 127) ? (char) b : '.';
                    sb.append(ch);
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /** Base64 with line wrapping at 76 chars for readability. */
    private String base64Block(byte[] data) {
        String raw = Base64.getEncoder().encodeToString(data);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Length: %d bytes\n\n", data.length));
        for (int i = 0; i < raw.length(); i += 76) {
            sb.append(raw, i, Math.min(i + 76, raw.length())).append('\n');
        }
        return sb.toString();
    }

    // Field-by-field equality
    private boolean equalsDto(PayrollRowDTO a, PayrollRowDTO b) {
        if (a == null || b == null) return false;
        if (!safe(a.getEmployeeName()).equals(safe(b.getEmployeeName()))) return false;
        if (!safe(a.getMonth()).equals(safe(b.getMonth()))) return false;
        if (!safe(a.getYear()).equals(safe(b.getYear()))) return false;
        return Double.compare(a.getHoursWorked(), b.getHoursWorked()) == 0 &&
                Double.compare(a.getBasicSalary(), b.getBasicSalary()) == 0 &&
                Double.compare(a.getTaxAmount(),   b.getTaxAmount())   == 0 &&
                Double.compare(a.getGrossPay(),    b.getGrossPay())    == 0 &&
                Double.compare(a.getDeduction(),   b.getDeduction())   == 0 &&
                Double.compare(a.getNetPay(),      b.getNetPay())      == 0;
        }

    private String safe(String s) { return s == null ? "" : s; }
}
