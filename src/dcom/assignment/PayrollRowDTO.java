package dcom.assignment;

import java.io.Serializable;

public class PayrollRowDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String employeeName;
    private double hoursWorked;
    private double basicSalary;
    private double taxAmount;
    private double grossPay;
    private double deduction;
    private double netPay;
    private String month;
    private String year;

    public PayrollRowDTO(String employeeName, double hoursWorked, double basicSalary, double taxAmount,
                double grossPay, double deduction, double netPay, String month, String year) {
        this.employeeName = employeeName;
        this.hoursWorked = hoursWorked;
        this.basicSalary = basicSalary;
        this.taxAmount = taxAmount;
        this.grossPay = grossPay;
        this.deduction = deduction;
        this.netPay = netPay;
        this.month = month;
        this.year = year;
    }

    public String getEmployeeName() { return employeeName; }
    public double getHoursWorked() { return hoursWorked; }
    public double getBasicSalary() { return basicSalary; }
    public double getTaxAmount() { return taxAmount; }
    public double getGrossPay() { return grossPay; }
    public double getDeduction() { return deduction; }
    public double getNetPay() { return netPay; }
    public String getMonth() { return month; }
    public String getYear() { return year; }
}
