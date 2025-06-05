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

public interface RMIinterface extends Remote{
    public String register(String Firstname,String LastnameString, String ICnumber)throws RemoteException;
    
}   
    