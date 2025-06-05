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

        User user = new User();
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        System.out.println("Welcome to the Employee Management System");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Set Tax");   
        System.out.println("4. Set Hour");
        System.out.println("5. Set Basic Salary");
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
                String message = obj.register(user.Firstname, user.Lastname, user.ICnumber);
                System.out.println(message); // This will print the message returned from the server

                break;
            case 2:
                user.login();
                break;
            case 3:
                user.settax();
                break;
            case 4:
                user.sethour();
                break;
            case 5:
                user.setbasic();
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
