/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 *
 * @author megas
 */
public class Accounts implements Serializable{
    private String id;
    private String password;
    private String name;
    private double balance;
    //Format for balance
    private final DecimalFormat currency = new DecimalFormat ("VNĐ #,###,###");
    
    
    public Accounts() {
    }

    public Accounts(String id) {
        this.id = id;
    }

    public Accounts(String id, String password, String name, double balance) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.balance = balance;
    }

    public Accounts(String id, double balance) {
        this.id = id;
        this.balance = 0;
    }

    public Accounts(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public Accounts(String id, String password) {
        this.id = id;
        this.password = password;
    }

    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        String result = "";
        result ="\n---------------------------------------\n"+
                "ID: " + this.id +
                "\nName: " + this.name+
                "\nBalance: " + currency.format(balance)+
                "\n---------------------------------------\n";
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
    // finding the account as require from searchAccount (a method of accountList)
    @Override
    public boolean equals(Object obj) {
        Accounts another = (Accounts) obj;
        return this.id.equals(another.id);
    }
    
}
