#calculate the salary
package dcom.assignment;

import java.util.Scanner;

public class Calculate {
    double netpay,netde, hours, basic, tax, grosspay, deduction;
   
    public void sethours(double hours){
        this.hours = hours;
    }
    public void setbasic(double basic){
        this.basic = basic;
    }
    public void settax(double tax){
        this.tax = tax;
    }
     
    public double getnetpay(){
        grosspay = hours * basic;
        return grosspay;
    }
     public double getdeduction(){
        deduction = grosspay * tax;
        return deduction;
    }
    
    public double netpay(){
        grosspay = getnetpay();
        netpay = grosspay * (1 - tax);
        return netpay;
    }
    /*
    public double netde(){
        grosspay = getnetpay();
        deduction = getdeduction();
        netde = grosspay - deduction;
        return netde;
    }*/
}