package dcom.assignment;


import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Server  {
    public static void main(String[] args) {
        try {
            ServerFunction serverFunction = new ServerFunction();
            Registry reg = LocateRegistry.createRegistry(1060);
            reg.rebind("sub", serverFunction);
            System.out.println("Server is running on port 1060...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
        
    }


}