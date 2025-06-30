package dcom.assignment;

import java.io.Serializable;

public class PayrollRecord implements Serializable {
    private String icNumber;
    private double hoursWorked;
    private double basicSalary;
    private double taxAmount;
    private String month;
    private int year;
    private double grossPay;
    private double deduction;
    private double netpay;

    public PayrollRecord(String icNumber, double hoursWorked, double basicSalary, double taxAmount, String month, int year) {
        this.icNumber = icNumber;
        this.hoursWorked = hoursWorked;
        this.basicSalary = basicSalary;
        this.taxAmount = taxAmount;
        this.month = month;
        this.year = year;
    }
    public PayrollRecord(String icNumber, double hoursWorked, double basicSalary, double taxAmount, String month, int year, double grossPay, double deduction, double netpay) {
        this.icNumber = icNumber;
        this.hoursWorked = hoursWorked;
        this.basicSalary = basicSalary;
        this.taxAmount = taxAmount;
        this.month = month;
        this.year = year;
        this.grossPay = grossPay;
        this.deduction = deduction; 
        this.netpay = netpay;
    }

    // Getters
    public String getIcNumber() { return icNumber; }
    public double getHoursWorked() { return hoursWorked; }
    public double getBasicSalary() { return basicSalary; }
    public double getTaxAmount() { return taxAmount; }
    public String getMonth() { return month; }
    public int getYear() { return year; }
    public double getGrossPayDB() { return grossPay; }
    public double getDeductionDB() { return deduction; } 
    public double getNetPayDB() { return netpay; }

    // Setters
    public void setHoursWorked(double hoursWorked) { this.hoursWorked = hoursWorked; }
    public void setBasicSalary(double basicSalary) { this.basicSalary = basicSalary; }
    public void setTaxAmount(double taxAmount) { this.taxAmount = taxAmount; }
    public void setMonth(String month) { this.month = month; }
    public void setYear(int year) { this.year = year; }

    // Calculation Methods
    public double getGrossPay() {
        return hoursWorked * basicSalary;
    }

    public double getDeduction() {
        return getGrossPay() * taxAmount;
    }

    public double getNetPay() {
        return getGrossPay() - getDeduction();
    }

    @Override
    public String toString() {
        return "PayrollRecord{" +
                "IC='" + icNumber + '\'' +
                ", Hours=" + hoursWorked +
                ", Basic=" + basicSalary +
                ", Tax=" + taxAmount +
                ", Month='" + month + '\'' +
                ", Year=" + year +
                ", GrossPay=" + getGrossPay() +
                ", Deduction=" + getDeduction() +
                ", NetPay=" + getNetPay() +
                '}';
    }

    public PayrollRecord getPayrollRecord() {
        return this;
    }
}
