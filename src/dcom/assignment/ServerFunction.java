package dcom.assignment;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerFunction extends UnicastRemoteObject implements RMIinterface {

    // Final static constants for table and column names
    private static final String EMPLOYEE_TABLE = "employee";
    private static final String PAYROLL_TABLE = "payroll";
    private static final ArrayList<String> EMPLOYEE_COLUMNS = new ArrayList<>(
            Arrays.asList("firstname", "lastname", "icnumber", "password", "role"));

    protected ServerFunction() throws RemoteException {
        super();
    }

    // 🧩 1. Add Employee
    public Employee addnewEmplyee(String ic, String Password, String Firstname, String Lastname, String role) {
        Neon neon = new Neon();

        ArrayList<ArrayList<Object>> data = neon.read(EMPLOYEE_TABLE);

        for (ArrayList<Object> row : data) {

            if (row.get(0) != null && row.get(1).toString().equalsIgnoreCase(Firstname)) {
                return null; // First name already used
            }
            if (row.get(0) != null && row.get(3).toString().equalsIgnoreCase(ic)) {
                return null; // IC number already used
            }
        }
        ArrayList<Object> newData = new ArrayList<>(Arrays.asList(Firstname, Lastname, ic, Password, role));
        String message = neon.addData(EMPLOYEE_TABLE, EMPLOYEE_COLUMNS, newData);
        if (message.contains("Inserted")) {
            // Return the newly added employee data with all fields
            return new Employee(ic, Password, Firstname, Lastname, role);
        } else {
            System.out.println("Registration failed: " + message);
            return null; // Registration failed
        }
    }

    // 🧩 2. Read Employee Info
    public Employee getinfoemployee(String ic) {
        Neon neon = new Neon();
        ArrayList<ArrayList<Object>> data = neon.read(EMPLOYEE_TABLE);
        for (ArrayList<Object> row : data) {
            if (row.size() > 2 && ic.equals(row.get(2))) { // Assuming column 3 is IC
                // Assuming column order: id, firstName, lastName, ic, password, role,
                // phoneNumber
                String firstName = (String) row.get(1);
                String lastName = (String) row.get(2);
                String icNumber = (String) row.get(3);
                String password = (String) row.get(4);
                String role = (String) row.get(5);
                String phoneNumber = row.size() > 6 ? (String) row.get(6) : null;
                return new Employee(icNumber, password, firstName, lastName, role, phoneNumber);
            }
        }
        return null; // No match found
    }

    // 🧩 3. Edit Employee Info
    public Employee editEmployeeByIC(Employee updatedEmployee) {
        Neon neon = new Neon();
        ArrayList<String> columns = new ArrayList<>(Arrays.asList(
                "firstname", "lastname", "password", "role", "phone_number"));
        ArrayList<Object> values = new ArrayList<>(Arrays.asList(
                updatedEmployee.getFirstName(),
                updatedEmployee.getLastName(),
                updatedEmployee.getPassword(),
                updatedEmployee.getRole(),
                updatedEmployee.getPhoneNumber()));
        String message = neon.updateData(EMPLOYEE_TABLE, columns, values, "icnumber", updatedEmployee.getIC());
        if (message.contains("Updated")) {
            // Return the newly added employee data with all fields
            return new Employee( updatedEmployee.getIC(),  updatedEmployee.getPassword(), updatedEmployee.getFirstName(), updatedEmployee.getLastName(), updatedEmployee.getRole(), updatedEmployee.getPhoneNumber());
        } else {
            return null; // Registration failed
        }
    }

    // 🧩 4. Delete Employee Info
    public void deleteEmployeeByIC(Employee employee) {
        Neon neon = new Neon();
        String icNumber = employee.getIC(); // Get IC from the object
        String result = neon.deleteData(EMPLOYEE_TABLE, "icnumber", icNumber);
        System.out.println(result);
    }

    // 🧩 5. Login
    public Employee login(String ic, String password) {
        Neon neon = new Neon();
        ArrayList<ArrayList<Object>> data = neon.read(EMPLOYEE_TABLE);
        for (ArrayList<Object> row : data) {
            String storedIC = row.get(3).toString();
            String storedPassword = row.get(4).toString();

            if (storedIC.equals(ic) && storedPassword.equals(password)) {
                String firstName = (String) row.get(1);
                String lastName = (String) row.get(2);
                String role = (String) row.get(5);
                String phoneNumber = row.size() > 6 ? (String) row.get(6) : null;
                return new Employee(ic, password, firstName, lastName, role,phoneNumber);
            }
        }
        return null; // Login failed
    }

    // 🧩 6. sestpayroll
    public String setPayrollForUser(PayrollRecord payrollRecord) {
        Neon neon = new Neon();
        String icNumber = payrollRecord.getIcNumber();
        double hours = payrollRecord.getHoursWorked();
        double basic = payrollRecord.getBasicSalary();
        double tax = payrollRecord.getTaxAmount();
        String month = payrollRecord.getMonth();
        int year = payrollRecord.getYear();

        // Find employee by IC
        ArrayList<ArrayList<Object>> employees = neon.read(EMPLOYEE_TABLE);
        Object userId = null;

        for (ArrayList<Object> emp : employees) {
            if (emp.size() > 3 && icNumber.equals(emp.get(3))) {
                userId = emp.get(0);
                break;
            }
        }

        if (userId == null) {
            return "User not found with IC: " + icNumber;
        }

        double grossPay = payrollRecord.getGrossPay();
        double deduction = payrollRecord.getDeduction();
        double netPay = payrollRecord.getNetPay();

        // Insert into payroll table
        ArrayList<String> columns = new ArrayList<>(Arrays.asList(
            "icnumber", "hours", "basic", "tax", "month", "year", "netpay", "grosspay", "deduction"
        ));
        ArrayList<Object> values = new ArrayList<>(Arrays.asList(
            icNumber, hours, basic, tax, month, year, netPay, grossPay, deduction
        ));

        String result = neon.addData(PAYROLL_TABLE, columns, values);
        System.out.println(result);

        if (result.contains("Inserted")) {
            return ("Payroll data added for " + icNumber + " (UserID: " + userId + ")");
        }   else {
            return ("Failed to add payroll data for " + icNumber);
        }
    }

    //🧩 7 . get all payroll data
    public List<PayrollRecord> getPayrollForEmployee(String icNumber) {
        Neon neon = new Neon();
        ArrayList<ArrayList<Object>> data = neon.read(PAYROLL_TABLE);
        List<PayrollRecord> payrollRecords = new ArrayList<>();

        for (ArrayList<Object> row : data) {
            if (row.size() > 6 && icNumber.equals(row.get(1))) { // IC is at index 1
                double hours = Double.parseDouble(row.get(2).toString());
                double basic = Double.parseDouble(row.get(3).toString());
                double tax = Double.parseDouble(row.get(4).toString());
                String month = row.get(5).toString();
                int year = Integer.parseInt(row.get(6).toString());
                double grossPay = Double.parseDouble(row.get(7).toString());
                double deduction = Double.parseDouble(row.get(8).toString());       
                double netPay = Double.parseDouble(row.get(9).toString());

                PayrollRecord record = new PayrollRecord(icNumber, hours, basic, tax, month, year, grossPay, deduction, netPay);
                payrollRecords.add(record);
            }
        }

        return payrollRecords;
    }

     //🧩 8. get all payroll data
    public List<Employee> getAllEmployees() {
        Neon neon = new Neon();
        ArrayList<ArrayList<Object>> data = neon.read(EMPLOYEE_TABLE);
        List<Employee> employees = new ArrayList<>();

        for (ArrayList<Object> row : data) {
            if (row.size() >= 6) { // id + 5 fields
                // row.get(0) is id, skip it
                String firstName = row.get(1) != null ? row.get(1).toString() : "";
                String lastName = row.get(2) != null ? row.get(2).toString() : "";
                String icNumber = row.get(3) != null ? row.get(3).toString() : "";
                String password = row.get(4) != null ? row.get(4).toString() : "";
                String role = row.get(5) != null ? row.get(5).toString() : "";
                String phoneNumber = row.size() > 6 && row.get(6) != null ? row.get(6).toString() : null;

                System.out.println("Adding employee: " + firstName + " " + lastName + " with IC: " + icNumber);
                employees.add(new Employee(icNumber, password, firstName, lastName, role, phoneNumber));
            }
        }

        return employees;
    }
}
