package dcom.assignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;

public class Session {
    String choice ="";

    public static Employee currentUser;

    public static void main(String[] args) {
    // Initialize the session
    // Initialize currentUser with a default Employee object
    currentUser = new Employee("123456789", "password123", "John", "Doe", "Manager", "0123456789");
    Session session = new Session();
    session.choice = "Serial";
    Serial serial = session.new Serial();
    serial.serialize();

    session.choice = "Deserial";
    Deserial deserial = session.new Deserial();
    deserial.deserialize();
    }

    public class Deserial {
        public void deserialize() {
            if (choice.equals("Deserial")) {
                try {
                    //Creating stream to read the object
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream("test.txt"));
                    //Reading the object
                    currentUser = (Employee) in.readObject();
                    System.out.println("Deserialized Employee...");
                    System.out.println("IC: " + currentUser.getIC());
                    System.out.println("Name: " + currentUser.getFirstName() + " " + currentUser.getLastName());
                    System.out.println("Role: " + currentUser.getRole());
                    in.close();
                } catch (Exception e) {
                    System.out.println("Deserialization failed: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }


    public class Serial{
        public void serialize(){
            if (choice.equals("Serial")) {
                try {
                    //Creating stream to read the object
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("test.txt"));
                    //Reading the object
                    out.writeObject(currentUser);
                    System.out.println("Serialized Employee...");
                    System.out.println("IC: " + currentUser.getIC());
                    System.out.println("Name: " + currentUser.getFirstName() + " " + currentUser.getLastName());
                    System.out.println("Role: " + currentUser.getRole());
                    out.close();
                } catch (Exception e) {
                    System.out.println("Serialization failed: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}


