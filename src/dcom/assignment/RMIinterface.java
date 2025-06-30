/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dcom.assignment;

/**
 *
 * @author Loh Kai Xuan
 */
import java.rmi.*;
import java.util.List;

public interface RMIinterface extends Remote {
    public Employee addnewEmplyee(String ic, String Password, String Firstname, String Lastname, String role) throws RemoteException;

    public Employee getinfoemployee(String ic) throws RemoteException;

    public  Employee editEmployeeByIC(Employee updatedEmployee) throws RemoteException;

    public void deleteEmployeeByIC(Employee employee) throws RemoteException;

    public Employee login(String ic, String password) throws RemoteException;

    public String setPayrollForUser(PayrollRecord payrollRecord) throws RemoteException;

    public List<PayrollRecord> getPayrollForEmployee(String icNumber) throws RemoteException;

    public List<Employee> getAllEmployees() throws RemoteException;
}
