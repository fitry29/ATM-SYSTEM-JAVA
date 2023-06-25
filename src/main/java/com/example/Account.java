package com.example;

public class Account {
    private String aName;
    private String pass;
    private String accNum;
    private double balance;

    Account(String name, String pass, String accNum, double balance){
        this.aName = name;
        this.pass = pass;
        this.accNum = accNum;
        this.balance = balance;
    }

    public String getName(){
        return aName;
    }
    public String getPassword(){
        return pass;
    }
    public String getAccountNumber(){
        return accNum;
    }
    public double getBalance(){
        return balance;
    }

    public void setName(String newName){
         this.aName = newName;
    }
    public void setPassword(String newPass){
        this.pass = newPass;
    }
    public void setBalance(double newBalance){
        this.balance = newBalance;
    }

    public double withdrawSaving(double amountWithdraw){
        double latestBalance = 0;
        
        if(amountWithdraw > balance){
            System.out.println("\n----NOT ENOUGH BALANCE----");
        }
        else{
            latestBalance = balance - amountWithdraw;
        }
        return latestBalance;
    }
    public double depositSaving(double depositAmount){
        double latestBalance = 0;
        
        latestBalance = balance + depositAmount;
        
        return latestBalance;
    }
    public double transfer(double transferAmount){
        double latestBalance = 0;
        
        latestBalance = balance - transferAmount;
        
        return latestBalance;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return getName() + ", " + getPassword() + ", " + getAccountNumber() + ", " + getBalance();
    }
}
