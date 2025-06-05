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
    private String Firstname;
    private String Lastname;
    private String ICnumber;
    private String Gmail;
    private String Password;

    
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
    }
     public void login() {
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
    }
}
