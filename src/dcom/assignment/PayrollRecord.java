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

    public PayrollRecord(String icNumber, double hoursWorked, double basicSalary, double taxAmount, String month,
            int year, double grossPay, double deduction, double netpay) {
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

    public PayrollRecord(String icNumber, double hoursWorked, double basicSalary, double taxAmount, String month,
            int year) {
        this.icNumber = icNumber;
        setHoursWorked(hoursWorked);
        setBasicSalary(basicSalary);
        setTaxAmount(taxAmount);
        this.month = month;
        setYear(year);
    }

    // Getters
    public String getIcNumber() {
        return icNumber;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public double getGrossPayDB() {
        return grossPay;
    }

    public double getDeductionDB() {
        return deduction;
    }

    public double getNetPayDB() {
        return netpay;
    }

    // Setters

    public void setMonth(String month) {
        this.month = month;
    }

    // Setters
    public void setHoursWorked(double hoursWorked) {
        if (hoursWorked < 0)
            throw new IllegalArgumentException("Hours worked must be positive.");
        this.hoursWorked = hoursWorked;
    }

    public void setBasicSalary(double basicSalary) {
        if (basicSalary <= 0)
            throw new IllegalArgumentException("Basic salary must be greater than zero.");
        this.basicSalary = basicSalary;
    }

    public void setTaxAmount(double taxAmount) {
        if (taxAmount < 0 || taxAmount > 1) {
            throw new IllegalArgumentException("Tax rate must be between 0 and 1 (e.g., 0.14 for 14%).");
        }
        this.taxAmount = taxAmount;
    }

    public void setGrossPay(double grossPay) {
        if (grossPay <= 0)
            throw new IllegalArgumentException("Gross pay must be positive.");
        this.grossPay = grossPay;
    }

    public void setDeduction(double deduction) {
        if (deduction < 0)
            throw new IllegalArgumentException("Deduction cannot be negative.");
        this.deduction = deduction;
    }

    public void setNetPay(double netpay) {
        if (netpay < 0)
            throw new IllegalArgumentException("Net pay cannot be negative.");
        this.netpay = netpay;
    }

    public void setYear(int year) {
        if (year < 2000 || year > 2100)
            throw new IllegalArgumentException("Must be a valid year.");
        this.year = year;
    }

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
