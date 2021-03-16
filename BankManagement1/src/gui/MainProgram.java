/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.security.NoSuchAlgorithmException;
import services.Services;

/**
 *
 * @author megas
 */
public class MainProgram {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {       
        Services ser = new Services();
        Menu menu = new Menu();
        int choice = 0;
        boolean flag = true;
        do {
            System.out.println("");
            menu.generateMenu();
            choice = menu.getUserChoice();
            switch (choice) {
                case 1: { //ADD
                    ser.createAccount();
                    break;
                }
                case 2: { //Login
                    ser.loginAccount();
                    break;
                }
                case 3: { //Withdrawn
                    ser.withdrawn();
                    break;
                }
                case 4: { //Deposit
                    ser.deposit();
                    break;
                }
                case 5: { //transfer
                    ser.transfer();
                    break;
                }
                case 6: {//remove accounts
                    ser.removeAccount();
                    break;
                }
                case 7:{
                    ser.showPassword();
                    break;
                }
                case 8:{
                    ser.changePassword();
                    break;
                }
                default: {
                    System.out.println("Thank you");
                    flag = false;
                    break;
                }
            }
        } while (flag == true);

    }
}
