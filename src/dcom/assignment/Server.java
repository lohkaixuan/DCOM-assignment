/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dcom.assignment;

import java.util.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Server extends UnicastRemoteObject implements RMIinterface {
    public Server() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(1060);
        reg.rebind("sub", new Server());
        System.out.println("Server is running on port 1060...");
    }

    public String register(String Firstname, String Lastname, String ICnumber) {
        Neon neon = new Neon();
        String table = "employee";
        ArrayList<ArrayList<Object>> data = neon.read(table);

        for (ArrayList<Object> row : data) {
            System.out.println(row);
            if (row.get(0) != null && row.get(1).toString().equalsIgnoreCase(Firstname)) {
                return "\n\nRegistration rejected: First Name '" + Firstname + "' is already used.\n";
            }//check first name is it used
            if (row.get(0) != null && row.get(3).toString().equalsIgnoreCase(ICnumber)) {
                return "\n\nRegistration rejected: IC number'" + ICnumber + "' is already used.\n";
            }//check ic number is it used
        }

        ArrayList<Object> newData = new ArrayList<>(Arrays.asList(Firstname, Lastname, ICnumber));
        String message = neon.adddata(table , newData);
        if (message.contains("Inserted ")) {
            message = 
            "\n\nRegistration successful!\n" +
            "Name: " + Firstname + " " + Lastname + "\n" +
            "IC Number: " + ICnumber +"\n";
        } else {
            message = "\n\n" + "Registration failed! " + message +"\n";
        }
        return message;
    }

    public String getnetpay( double tax, double hours, double basic) {
        Calculate calculate = new Calculate();
        calculate.sethours(hours);
        calculate.settax(tax);
        calculate.setbasic(basic);
        double grosspay = calculate.getgrosspayy();
        double deduction = calculate.getdeduction();
        double netPay = calculate.netpay();

        String message =
        "\n\n" +
        "       Pay Slip" + "\n" +
        "Hours        : " + hours + "hours" + "\n" +
        "Tax          : " + tax * 100 + " % " + "\n" +
        "Basic Salary : RM " + String.format("%.2f", basic) + "\n" +
        "Gross Pay    : RM " + String.format("%.2f", grosspay) + "\n" +
        "Deduction    : RM " + String.format("%.2f", deduction) + "\n" +
        "Net Pay      : RM " + String.format("%.2f", netPay) + "\n";
        return message;
    }

}
