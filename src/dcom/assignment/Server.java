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
            if (row.get(0) != null && row.get(0).toString().equalsIgnoreCase(Firstname)) {
                return "Registration rejected: First Name '" + Firstname + "' is already used.\n";
            }
        }

        ArrayList<Object> newData = new ArrayList<>(Arrays.asList(Firstname, Lastname, ICnumber));
        String message = neon.adddata(table , newData);
        if (message.contains("Inserted ")) {
            message = "Registration successful!\n" +
                    "Name: " + Firstname + " " + Lastname + "\n" +
                    "IC Number: " + ICnumber +"\n";
        } else {
            message = "Registration failed! " + message +"\n";
        }
        return message;
    }
}
