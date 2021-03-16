/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Accounts;
import impl.repo.ImplBankRepository;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Scanner;
import ultils.MyUltils;

/**
 *
 * @author megas
 */
public class Services {

    ImplBankRepository repo = new ImplBankRepository();
    private final DecimalFormat currency = new DecimalFormat("VNĐ #,###,###"); // format currency
    Scanner sc = new Scanner(System.in);

    public int search(int choice, String element) {
        int pos = -1;
        if (repo.isEmpty()) {
            System.out.println("There are no accounts! Create one!");
        } else {
            switch (choice) {
                case 1: {// search pos by ID
                    pos = repo.indexOf(new Accounts(element));
                    break;
                }
                case 2: {// search pos by password for checking!
                    for (int i = 0; i < repo.size(); i++) {
                        if (repo.get(i).getPassword().equals(element)) {
                            pos = i;
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return pos;
    }

    public String enterValue(int choice, String information) {
        double minAmount = 10000;
        String value = "";
        switch (choice) {
            case 1: {//get ID
                value = MyUltils.checkStringFormat(information, "Wrong format!", "^\\d{1,}$");
                break;
            }
            case 2: {//get Name
                value = MyUltils.getName(information, "Please enter FULL NAME with NO number or special sign", "^.*(?=.*[^A-Za-z]){2,}.*$");
                break;
            }
            case 3: {//get or check password
                value = MyUltils.getPwd(information);
                break;
            }
            case 4: {//for withdrawn
                int position = repo.getPosition();
                double max = repo.get(position).getBalance();
                value += MyUltils.getDouble(information, "Number only!", minAmount, max, 1);
                break;
            }
            case 5: {//for deposit
                value += MyUltils.getDouble(information, "Number Only!", minAmount, 0, 2);
                break;
            }
        }
        return value;
    }

    public void createAccount() throws NoSuchAlgorithmException {
        String accID = "", accPass = "", accName = "", comfirmPass = "";
        Accounts acc = null;
        int posAcc = 0;
        System.out.println("\t============Creating Account============\n");
        do {//get ID
            accID = enterValue(1, "Input ID(Only Number): ");
            posAcc = search(1, accID);
            if (posAcc > -1) {
                System.out.println("ID Existed! please use another!");
            }
        } while (posAcc > -1);
        accName = enterValue(2, "Input Full Name: ");//get name 
        System.out.println("------------------------------------------------\n"
                + "Password should at least 6 character including\n"
                + "Capitals, lower characters and special signs\n"
                + "The special signs must have one of these: .,#?!@$%^&*-_\n"
                + "------------------------------------------------");
        accPass = enterValue(3, "Input Password: ");// get password
        do {//Comfirm password to create
            comfirmPass = enterValue(3, "Comfirm Password: ");
            if (!(comfirmPass.equals(accPass))) {
                System.out.println("Password not match, Try again!");
            }
        } while (!(comfirmPass.equals(accPass)));
        acc = new Accounts(accID, MyUltils.toHexString(MyUltils.getSHA(accPass)), accName, 1); // Add to list and encrypted Password
        repo.create(acc);
        System.out.println("\n\t Accounts created successfully!\n\n");
        System.out.println(acc.toString());
        repo.saveToFile();
    }

    public boolean loginAccount() throws NoSuchAlgorithmException {
        String idLogin = "", pwdLogin = "";
        int idPos = -1, pwdPos = -1;
        boolean checkingValue = true;
        if (repo.isEmpty()) {
            System.out.println("There're no account! Please create one.");
        } else {
            System.out.println("\n----------------------------------------------\n"
                    + "\tLogin Account\n");
            while (checkingValue) {
                idLogin = enterValue(1, "Input ID(Number Only): "); //get ID
                System.out.println("------------------------------------------------\n"
                        + "Password should at least 6 character including\n"
                        + "Capitals, lower characters and special signs\n"
                        + "The special signs must have one of these: .,#?!@$%^&*-_\n"
                        + "------------------------------------------------");
                pwdLogin = enterValue(3, "Input password: ");// get Password
                idPos = search(1, idLogin); // get position of ID
                pwdPos = search(2, MyUltils.toHexString(MyUltils.getSHA(pwdLogin)));// get position of Password
                if (idPos > -1 && pwdPos > -1) { //If ID or Password matched then loggin success
                    repo.setId(idLogin);
                    repo.setPosition(idPos);
                    System.out.println("\t ***Login Succesfully*** \n");
                    int position = repo.getPosition();
                    System.out.println(repo.get(position).toString());
                    break;
                } else {//If not matched then check what error
                    if (idPos == -1) {
                        System.out.println("ID not Existed!");
                        boolean check = MyUltils.checkYN("Do you wanted to create new? (Y/N): ");
                        if (check == true) {
                            createAccount();
                            break;
                        }
                    } else if (idPos > -1 && pwdPos == -1) {
                        System.out.println("Wrong Password");
                    }
                    checkingValue = MyUltils.checkYN("Do you want to login again?(Y/N): ");
                }
            }
        }
        return checkingValue;
    }

    public void changePassword() throws NoSuchAlgorithmException {
        if (checkLogin()) {
            String accPass = "", comfirmPass = "", oldPass = "", id = "";
            id = repo.getId();
            int oldPwdPos = -1;
            do {
                oldPass = enterValue(3, "Input old password: ");
                oldPwdPos = search(2, MyUltils.toHexString(MyUltils.getSHA(oldPass)));
                if (oldPwdPos > -1) {
                    System.out.println("------------------------------------------------\n"
                            + "Password should at least 6 character including\n"
                            + "Capitals, lower characters and special signs\n"
                            + "The special signs must have one of these: .,#?!@$%^&*-_\n"
                            + "------------------------------------------------");
                    accPass = enterValue(3, "Input new Password: ");// get password
                    do {//Comfirm password to create new
                        comfirmPass = enterValue(3, "Comfirm Password: ");
                        if (!(comfirmPass.equals(accPass))) {
                            System.out.println("Password not match, Try again!");
                        }
                    } while (!(comfirmPass.equals(accPass)));
                    repo.setPassword(accPass);
                } else {
                    System.out.println("Old password not match!");
                }
            } while (oldPwdPos == -1);
        }
        repo.setId("");
        repo.setPosition(0);
        System.out.println("\n\tPassword change successfully! Logged out!");
        repo.saveToFile();
    }

    public void withdrawn() throws NoSuchAlgorithmException {
        if (checkLogin()) {
            int position = repo.getPosition();
            boolean checkingValue = true;
            double withdrawnAmount;
            System.out.println("------------------------------------------------\n"
                    + "\tWithDrawal\n");
            System.out.println("Your balance: " + currency.format(repo.get(position).getBalance()));
            while (checkingValue) {
                withdrawnAmount = Double.parseDouble(enterValue(4, "Input amounts: "));
                checkingValue = updateBalance(withdrawnAmount, "withdrawn");
                if (checkingValue == false) {//Money larger than balance
                    checkingValue = MyUltils.checkYN("Do you want to make another Transaction?(Y/N): ");
                    if (checkingValue == false) {
                        System.out.println("Transaction Canceled!");
                    }
                } else {
                    repo.saveToFile();
                    break;
                }
            }

        }
    }

    public boolean updateBalance(double Amounts, String kind) {
        int i = repo.getPosition();
        double fee = 10000;
        double balanceAmount = repo.get(i).getBalance();// get balance amount
        if (balanceAmount >= Amounts) {
            balanceAmount -= Amounts;
            balanceAmount -= fee;
            if (balanceAmount >= 0) {
                repo.get(i).setBalance(balanceAmount);//Update balance
                System.out.println("\n\t**Transaction Succesfully**\n"
                        + currency.format(Amounts) + " has been " + kind + "\n"
                        + "\t\nTransaction fee is: " + currency.format(fee)
                        + "\n----------------------------------------------\n"
                        + repo.get(i).toString()
                        + "\n----------------------------------------------\n");
                return true;
            } else {
                System.out.println("Your balance after fee are negative! Transaction Failed");
                return false;
            }
        } else {
            System.out.println("Money " + kind + " is larger than your balance! Your Balance: " + currency.format(balanceAmount));
        }
        return false;
    }

    public void deposit() throws NoSuchAlgorithmException {
        if (checkLogin()) {
            int position = repo.getPosition();
            boolean checkingValue = true;
            double depositAmounts;
            double fee = 10000;
            System.out.println("------------------------------------------------\n"
                    + "\tDeposit\n");
            System.out.println("Your balance: " + currency.format(repo.get(position).getBalance()));
            while (checkingValue) {
                depositAmounts = Double.parseDouble(enterValue(5, "Input Amounts: "));
                checkingValue = MyUltils.checkYN("Are you sure wanted to deposit " + currency.format(depositAmounts) + " to your account? (Y/N): ");
                if (checkingValue == true) {
                    double accountBalance = repo.get(position).getBalance();
                    accountBalance += depositAmounts;
                    accountBalance -= fee;
                    if (accountBalance >= 0) {
                        repo.get(position).setBalance(accountBalance);
                        System.out.println("\t Deposit Successfully! Your balance is: " + currency.format(repo.get(position).getBalance())
                                + "\n\tTransaction fee is: " + currency.format(fee));
                        break;
                    } else {
                        System.out.println("Your balance after fee are negative! Transaction Failed");
                    }
                } else if (checkingValue == false) {
                    System.out.println("\t Transaction Canceled! \n");
                }
            }
            repo.saveToFile();
        }
    }

    public void transfer() throws NoSuchAlgorithmException {
        if (checkLogin()) {
            int pos = repo.getPosition();
            String receiver = "";
            double transferAmount, balance, receiverBalance;
            balance = repo.get(pos).getBalance();
            int receiverPos = -1;
            boolean checkingValue = true;
            System.out.println("------------------------------------------------\n"
                    + "\tTransfer\n");
            System.out.println("Your balance: " + currency.format(repo.get(pos).getBalance()));
            if (balance >= 1) {
                while (checkingValue) {
                    receiver = enterValue(1, "Input ID wanted to transfer: ");
                    receiverPos = search(1, receiver); // Search for receiver ID
                    if (receiverPos == repo.getPosition()) { // Check if receiver is himself
                        System.out.println("You can't tranfer to yourself!");
                    } else { // If not then countinues
                        if (receiverPos == -1) { // If receiver not existed
                            checkingValue = MyUltils.checkYN("The ID you wanted to tranfers not exist! Do you want make another tranfers?(Y/N): ");
                            if (checkingValue == false) {
                                System.out.println("\t Transaction Canceled! \n");
                            }
                        } else {// If existed then continues
                            System.out.println("\n\tReceiver Information: " + repo.get(receiverPos).toString());
                            transferAmount = Double.parseDouble(enterValue(4, "Input amounts you wanted to tranfer: "));
                            //Confirm wanted to tranfer or not?
                            checkingValue = MyUltils.checkYN("Are you sure wanted to tranfers " + currency.format(transferAmount) + " to ID: " + receiver + " ?(Y/N): ");
                            if (checkingValue == true) { //If choose yes then starting transfer
                                updateBalance(transferAmount, "transfer");
                                receiverBalance = repo.get(receiverPos).getBalance();
                                receiverBalance += transferAmount;
                                repo.get(receiverPos).setBalance(receiverBalance);
                                break;
                            } else {// Else error
                                System.out.println("\t Transaction Canceled! \n");
                            }
                        }
                    }
                }
            } else {
                System.out.println("Your balance need to be at least 1 VND to transfer! Transaction canceled!");
            }
            repo.saveToFile();
        }
    }

    public void removeAccount() throws NoSuchAlgorithmException {
        boolean checkingValue = true;
        if (checkLogin()) {
            int pos = repo.getPosition();
            double balance = repo.get(pos).getBalance();
            if (balance >= 1) {
                checkingValue = MyUltils.checkYN("Are you sure wanted to DELETE ACOUNTS! All your information will be LOST!(Y/N): ");
                if (checkingValue == true) {
                    repo.removeAccount(repo.getId());
                    repo.setId("");
                    repo.setPosition(-1);
                    System.out.println("\tAccount Deleted!!!\n");
                } else {
                    System.out.println("\tDelete Canceled\n");
                }
            } else {
                System.out.println("Your balance need to be at least 1 VND to REMOVE! REMOVE FAILED!");
            }
        }
        repo.saveToFile();
    }

    public boolean checkLogin() throws NoSuchAlgorithmException {
        if (repo.getId().isEmpty()) { // If not login yet then login else return comfirm that logged
            System.out.println("\tLogin Required\n");
            return loginAccount();
        }
        return true;
    }

    public void showPassword() throws NoSuchAlgorithmException {
        if (checkLogin()) {
            int pos = repo.getPosition();
            System.out.println("Your password has been encrypted as: " + repo.get(pos).getPassword());
        }
    }

    public void display() {
        for (Accounts acc : repo) {
            System.out.println(acc);
        }
    }
}
