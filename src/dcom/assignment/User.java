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

    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter First Name: ");
        this.Firstname = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        this.Lastname = scanner.nextLine();
        System.out.print("Enter IC Number: ");
        this.ICnumber = scanner.nextLine();

        System.out.println("Registration complete!");
        System.out.println("Name: " + Firstname + " " + Lastname);
        System.out.println("IC Number: " + ICnumber);
        scanner.close();
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter lol: ");
        this.Firstname = scanner.nextLine();

        System.out.println("Log in complete!");
        scanner.close();
    }

    public void settax() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter tax: ");
        Double tax = scanner.nextDouble();
        System.out.println("Tax set to: " + tax);
        scanner.close();
    }
    
    public void sethour() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter hour: ");
        Double hour = scanner.nextDouble();
        System.out.println("Working set to: " + hour);
        scanner.close();
    }
    
    public void setbasic() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter basic: ");
        Double basic = scanner.nextDouble();
        System.out.println("Basic set to: " + basic);
        scanner.close();
    }

}
