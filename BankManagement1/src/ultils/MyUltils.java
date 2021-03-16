/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ultils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author megas
 */
public class MyUltils {

    private static final DecimalFormat currency = new DecimalFormat("VNĐ #,###,###"); // format currency
    static Scanner sc = new Scanner(System.in);
    static String inputValue = "";

    //use for check format
    public static String checkStringFormat(String str, String error, String format) {
        while (true) {
            System.out.print(str);
            inputValue = sc.nextLine().trim();//trim to let input only string (After blankspace doesn't count)
            if (inputValue.isEmpty() || inputValue.matches(format) == false) {//check if inputed value is empty or match the right format.
                System.out.println(error);
            } else {
                break; // if correct formated, exit loop
            }
        }
        return inputValue;
    }

    public static double getDouble(String str, String error, double min, double max, int choice) {
        double inputAmount = 0;
        if (max < min) {
            double t = max;
            max = min;
            min = t;
        }
        switch (choice) {
            case 1: { // Litmited by min and max use for withdrawn
                while (true) {
                    try {
                        System.out.print(str);
                        inputAmount = Double.parseDouble(sc.nextLine());
                        if (inputAmount < min) {
                            System.out.println("Minimum transaction is: " + currency.format(min));
                        } else if (inputAmount > max) {
                            System.out.println("Can tranfers amounts more than your balance! Your balance is " + currency.format(max));
                        } else {
                            break;
                        }
                    } catch (Exception ex) {
                        System.out.println(error);
                    }
                }
                break;
            }
            case 2: { //Limited only min use for deposit
                while (true) {
                    try {
                        System.out.print(str);
                        inputAmount = Double.parseDouble(sc.nextLine().trim());
                        if (inputAmount < min) {
                            System.out.println("Minimum transaction is: " + currency.format(min));
                        } else {
                            break;
                        }
                    } catch (Exception ex) {
                        System.out.println(error);
                    }
                }
                break;
            }
        }
        return inputAmount;
    }

    //Get name
    public static String getName(String str, String error, String format) {
        while (true) {
            System.out.print(str);
            inputValue = sc.nextLine().trim().toLowerCase();
            if (inputValue.contains("admin")) {
                System.out.println("Cannot input name as admin!!!");
            } else if ((!inputValue.isEmpty() && inputValue.contains(" ")) == true) { // check if name is not empty and have blankspace then accpeted
                break;
            } else if (inputValue.isEmpty()) {
                System.out.println("The name can't be empty"); // check if name is empty
            } else {
                System.out.println(error);// Check if not full name
            }
        }
        //If all checked then go and formated name
        inputValue = checkNameFormat(str, inputValue, error, format);
        //If all valid then return name
        return inputValue;
    }

    //check and fix Name
    public static String checkNameFormat(String str, String name, String error, String format) {
        String rep = ""; // contain one word to using for Uppercase
        StringTokenizer temp = new StringTokenizer(name, " ");//Help split off words for rep to Uppercase words
        name = "";
        while (temp.hasMoreTokens()) {
            rep = temp.nextToken().trim();// Spliting off
            boolean match = rep.matches(format);// check if right format
            if (match == false) {
                name = name + " " + rep.substring(0, 1).toUpperCase() + rep.substring(1);//Upper case first character
                rep = ""; //reset to null for next word
            } else {
                System.out.println("Error Name! please input again!");//if not right format then input again
                name = getName(str, error, format);//Re-input
            }
        }
        //if all valid then return name
        return name.trim();
    }

    //Get Password
    public static String getPwd(String information) {
        boolean checking = true;
        String password = "";
        System.out.print(information);
        password = sc.nextLine();
        if (password.isEmpty()) {
            System.out.println("Password cannot empty!");
            return getPwd(information);
        }
        if (password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,}$")) {
            int length = password.length();
            int counterSign = 0, counterSpace = 0;
            for (int i = 0; i < length; i++) {
                char c = password.charAt(i);
                if (!Character.isWhitespace(c)) {//Check space, if there is no space then continues
                    if (!Character.isDigit(c) && !Character.isAlphabetic(c)) {
                        counterSign++;
                    }
                } else {//If there is space then count++ for checking error
                    counterSpace++;
                }
            }
            if (counterSign == 1 && counterSpace == 0) {
                return password;
            } else if (counterSign >= 2 && counterSpace > 0) {
                System.out.println("Must be no blankspace and can't contain more than 1 special sign.");
                return getPwd(information);
            } else if (counterSpace > 0) {
                System.out.println("Password can't contain blankspace");
                return getPwd(information);
            } else if (counterSign >= 2) {
                System.out.println("No more than 1 special sign!");
                return getPwd(information);
            } else if (counterSign == 0) {
                System.out.println("Must be at least 1 special sign!");
                return getPwd(information);
            }
        } else {
            System.out.println("You input wrong format!");
            return getPwd(information);
        }
        return password;
    }

    //check Yes No
    public static boolean checkYN(String str) {
        boolean check = false, flag = true;
        while (flag) {
            System.out.print(str);
            String choice = sc.nextLine();
            if ((choice.isEmpty() || !choice.matches("[Y|y|N|n]{1}"))) {
                System.out.println("Y/N Only!");
            } else {
                if (choice.equals("Y") || choice.equals("y")) {
                    check = true;
                }
                break;
            }
        }
        return check;
    }

    // encrypted as SHA256
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    // encrypted into HexString
    public static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
}
