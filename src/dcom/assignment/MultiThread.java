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
            RMIinterface obj = (RMIinterface) Naming.lookup("rmi://localhost:1060/sub");

            Thread thread = new Thread(() -> {
                System.out.println("[Thread] Starting employee and payroll fetch...");

                try {
                    List<Employee> employees = obj.getAllEmployees();
                    if (employees != null) {
                        for (Employee emp : employees) {
                            System.out.println("[Thread 1] Employee: " + emp.getFirstName() + " " + emp.getLastName() + " (" + emp.getIC() + ")");

                            List<PayrollRecord> payrolls = obj.getPayrollForEmployee(emp.getIC());
                            if (payrolls != null && !payrolls.isEmpty()) {
                                for (PayrollRecord record : payrolls) {
                                    System.out.println("[Thread 2] Payroll for " + emp.getFirstName() + ": Hours=" + record.getHoursWorked() +
                                            ", Basic=" + record.getBasicSalary());
                                    ui.addPayrollRow(emp, record);
                                }
                            } else {
                                System.out.println("[Thread 2] No payroll records for " + emp.getFirstName());
                            }
                        }
                        System.out.println("[Thread 1] Finished processing all employees.");
                    } else {
                        System.out.println("[Thread 1] No employees found.");
                    }
                } catch (Exception e) {
                    System.out.println("[Thread] Error during processing.");
                    e.printStackTrace();
                }
            });

            thread.start();

        } catch (Exception e) {
            System.out.println("[Main] Error initializing thread.");
            e.printStackTrace();
        }
    }
}
