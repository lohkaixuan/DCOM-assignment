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
public class User {
    String Firstname;
    String Lastname;
    String ICnumber;
    String Gmail;
    String Password;
    double tax;double hour;double basic;
    
    private static final Scanner scanner = new Scanner(System.in);

    public void register() {
        System.out.print("Enter First Name: ");
        this.Firstname = scanner.nextLine().trim();
        System.out.print("Enter Last Name: ");
        this.Lastname = scanner.nextLine().trim();
        System.out.print("Enter IC Number: ");
        this.ICnumber = scanner.nextLine().trim();
        scanner.close();
    }

    public void login() {//qi 
        System.out.print("Enter lol: ");
        this.Firstname = scanner.nextLine();
        System.out.println("Log in complete!");
    }

    public void settax() {
        System.out.print("Enter tax: ");
        this.tax = scanner.nextDouble();
        System.out.println("Tax set to: " + tax);
    }
    
    public void sethour() {
        System.out.print("Enter hour: ");
        this.hour = scanner.nextDouble();
        System.out.println("Working set to: " + hour);
    }
    
    public void setbasic() {
        System.out.print("Enter basic: ");
        this.basic = scanner.nextDouble();
        System.out.println("Basic set to: " + basic);
    }

    public static void closeScanner() {
        scanner.close();
    }
}
