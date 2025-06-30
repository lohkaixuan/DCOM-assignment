package dcom.assignment;

import java.io.Serializable;

/**
 * Employee class representing an employee record.
 * Includes fields for IC, password, name, role, and optional phone number.
 * 
 * @author Loh Kai Xuan
 */
public class Employee implements Serializable {
    private String ic;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private String phoneNumber; // Optional field

    // Constructor with all fields
    public Employee( String ic, String password, String firstName, String lastName, String role, String phoneNumber) {
        this.ic = ic;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }

    // Constructor without phone number
    public Employee( String ic, String password, String firstName, String lastName, String role) {
        this( ic, password, firstName, lastName, role, null);
    }

    // Getters
    public String getIC() { return ic; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getRole() { return role; }
    public String getPhoneNumber() { return phoneNumber; }

    // Setters
    public void setIC(String ic) { this.ic = ic; }
    public void setPassword(String password) { this.password = password; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setRole(String role) { this.role = role; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }


    public Employee getEmployee() {
        return this;
    }

}
