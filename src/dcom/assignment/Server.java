/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dcom.assignment;

import java.rmi.*;
import java.util.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Server extends UnicastRemoteObject implements RMIinterface{
    public Server() throws RemoteException{
        super();
        qi lpasap
    }
    
    public static void main(String[] args) throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(1060);
        reg.rebind("sub", new Server());
        System.out.println("Server is running on port 1060...");   
    }
    
    public void register(String Firstname,String Lastname, String ICnumber) {
        Neon neon = new Neon();
        ArrayList<ArrayList<Object>> data = neon.read("employee");
          boolean exists = false;
          for (ArrayList<Object> row : data) {
              // Assuming the first column is the first name
              if (row.get(0) != null && row.get(0).toString().equalsIgnoreCase(Firstname)) {
                  exists = true;
                  break;
              }
          }

          if (exists) {
              System.out.println("Registration rejected: First Name '" + Firstname + "' is already used.");
          } else {
              System.out.println("Registration complete!");
              System.out.println("Name: " + Firstname + " " + Lastname);
              System.out.println("IC Number: " + ICnumber);

              // Now you can add the new data to the database if needed
              ArrayList<Object> newData = new ArrayList<>();
              newData.add(Firstname);
              newData.add(Lastname);
              newData.add(ICnumber);
              neon.adddata("employee", newData);
        }
    }        
}
      