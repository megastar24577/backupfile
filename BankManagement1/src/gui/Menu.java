/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import java.util.Scanner;
import ultils.MyUltils;

/**
 *
 * @author megas
 */
public class Menu extends ArrayList<String> {

    public Menu() {
        super();
    }

    public void addItem(String str) {
        this.add(str);
    }

    public void print() {
        for (String str : this) {
            System.out.println(str);
        }
    }

    public int getUserChoice(){
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        System.out.print("Your choice: ");
        return Integer.parseInt(sc.nextLine());
    }
    
    public void generateMenu() {
        Menu menu = new Menu();
        menu.add("-------------------------------------------");
        menu.add("\t\tMenu\n");
        menu.add("1-Create Account. ");
        menu.add("2-Login Account");
        menu.add("3-Withdrawn. ");
        menu.add("4-Deposit. ");
        menu.add("5-Transfer. ");
        menu.add("6-Remove Accounts!. ");
        menu.add("7-Show encrypted Password!");
        menu.add("8-Change Password!");
        menu.add("Others to exit!");
        menu.print();
    }

}
