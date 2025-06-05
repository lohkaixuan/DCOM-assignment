/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dcom.assignment;

/**
 *
 * @author Loh Kai Xuan
 */
import java.rmi.RemoteException;
import java.rmi.*;
import java.net.MalformedURLException;

public class Client {
    public static void main(String[] args)throws RemoteException,
            NotBoundException,MalformedURLException
            {
             menu();
            RMIinterface obj = (RMIinterface)Naming.lookup("rmi://192.168.207.199:1060/sub");
//            RMIInterface obj = (RMIInterface)Naming.lookup("rmi://10.101.107.1:1060/sub");
//        System.out.println("The number is "+obj.add(5,8));
        
    }
    public static void menu(){
        User user = new User();
        user.register();
    }
    
}
