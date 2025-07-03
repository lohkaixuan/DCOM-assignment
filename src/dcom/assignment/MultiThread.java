package dcom.assignment;

import java.rmi.Naming;
import java.util.List;

public class MultiThread {
    private ShowallPage ui;

    public MultiThread(ShowallPage ui) {
        this.ui = ui;
    }

    public void getAllInfo() {
        try {
            String url = String.format("rmi://%s:%d/%s", AppTheme.RMI_HOST, AppTheme.RMI_PORT,
                    AppTheme.RMI_SERVICE);
            System.out.println("Connecting to RMI server at: " + url); // Debugging line
            RMIinterface obj = (RMIinterface) Naming.lookup(url);
            Thread employeeThread = new Thread(() -> {
                System.out.println("[Thread 1] Starting employee fetch...");

                try {
                    List<Employee> employees = obj.getAllEmployees();
                    if (employees != null) {
                        for (Employee emp : employees) {
                            System.out.println("[Thread 1] Employee: " + emp.getFirstName() + " " + emp.getLastName()
                                    + " (" + emp.getIC() + ")");
                            Thread payrollThread = new Thread(() -> {
                                try {
                                    List<PayrollRecord> payrolls = obj.getPayrollForEmployee(emp.getIC());
                                    if (payrolls != null && !payrolls.isEmpty()) {
                                        for (PayrollRecord record : payrolls) {
                                            System.out.println("[Thread 2] Payroll for " + emp.getFirstName()
                                                    + ": Hours=" + record.getHoursWorked() +
                                                    ", Basic=" + record.getBasicSalary());
                                            ui.addPayrollRow(emp, record);
                                        }
                                    } else {
                                        System.out.println("[Thread 2] No payroll records for " + emp.getFirstName());
                                    }
                                } catch (Exception e) {
                                    System.out.println("[Thread 2] Error fetching payroll for " + emp.getIC());
                                    e.printStackTrace();
                                }
                            });
                            payrollThread.start();
                            payrollThread.join(); // Wait for payroll thread to finish before continuing
                        }
                        System.out.println("[Thread 1] Finished processing all employees.");
                    } else {
                        System.out.println("[Thread 1] No employees found.");
                    }
                } catch (Exception e) {
                    System.out.println("[Thread 1] Error fetching employees.");
                    e.printStackTrace();
                }
            });
            employeeThread.start();
        } catch (Exception e) {
            System.out.println("[Main] Error initializing thread.");
            e.printStackTrace();
        }
    }
}
