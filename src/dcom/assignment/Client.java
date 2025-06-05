/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dcom.assignment;

import java.util.Scanner;
/**
 *
 * @author Loh Kai Xuan
 */
import java.rmi.*;
import java.net.MalformedURLException;

public class Client {
    public static void main(String[] args) throws RemoteException,NotBoundException, MalformedURLException {
        menu();
        // RMIinterface obj = (RMIinterface) Naming.lookup("rmi://192.168.207.199:1060/sub");
        
        // System.out.println("The number is "+obj.add(5,8));

    }

    public static void menu() throws RemoteException,NotBoundException, MalformedURLException {
        RMIinterface obj = (RMIinterface)Naming.lookup("rmi://localhost:1060/sub");
        String message = "";
        User user = new User();
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        System.out.println("Welcome to the Employee Management System");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Set Tax, Hour, and Basic Salary");   
        System.out.println("0. Exit");
        System.out.print("Please select an option: ");
        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
        } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // consume invalid input
            return;
        }
        switch (choice) {
            case 1:
                user.register();
                message = obj.register(user.Firstname, user.Lastname, user.ICnumber);
                System.out.println(message); 

                break;
            case 2:
                user.login();
                break;
            case 3:
                user.sethour();
                user.settax();
                user.setbasic();
                message = obj.getnetpay(user.tax, user.hour, user.basic);
                System.out.println(message); 
                break;
            case 0:
                System.out.println("Exiting the system. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        scanner.close();
    }

}
