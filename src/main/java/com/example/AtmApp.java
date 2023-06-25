package com.example;

import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AtmApp {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) throws IOException, ParseException {

        Scanner in = new Scanner(System.in);

        JSONParser jsonParser = new JSONParser();

        FileReader reader = new FileReader(".\\json\\account.json");

        Object obj = jsonParser.parse(reader);

        JSONObject accountObj = (JSONObject) obj;

        JSONArray accountJSONArray = (JSONArray) accountObj.get("account");

        Account accArr[] = new Account[accountJSONArray.size()];

        for (int i = 0; i < accountJSONArray.size(); i++) {
            JSONObject accountJSON = (JSONObject) accountJSONArray.get(i);

            String name = (String) accountJSON.get("name");
            String pass = (String) accountJSON.get("pass");
            String accNumber = (String) accountJSON.get("accNumber");
            String balance = (String) accountJSON.get("balance");

            double dbalance = Double.parseDouble(balance);

            // System.out.println();
            // System.out.println("------ACCOUNT " + (i + 1) + " DETAILS------");
            // System.out.println("Name :" + name);
            // System.out.println("Password :" + pass);
            // System.out.println("Account Number :" + accNumber);
            // System.out.println("Balance :" + balance + "\n");

            accArr[i] = new Account(name, pass, accNumber, dbalance);
        }
        // for (int i = 0; i < accArr.length; i++) {
        //     System.out.println(accArr[i].toString());
        // }
        System.out.println("\n---Hi, Welcome To Fyt ATM---");
        System.out.println("----------------------------");

        int menuNumber = 1;
        int menuLoginNumber = 1;
        String inputName;
        String inputPassword;
        String transferAcc;
        double transferAmount = 0;
        boolean resultTransfer = false, resultAccount = false;

        while (menuNumber != 2) {
            System.out.println("\n--ENTER NUMBER FOR ACTION---");
            System.out.println("\n--[1]-LOGIN-----------------");
            System.out.println("--[2]-QUIT-ATM--------------");

            System.out.print("\nUser Response: ");
            menuNumber = in.nextInt();

            if (menuNumber == 1) {
                System.out.println("\n--ENTER YOUR CREDENTIALS----");
                System.out.print("\n--Username: ");
                inputName = in.next();
                System.out.print("--Password: ");
                inputPassword = in.next();

                for (int i = 0; i < accArr.length; i++) {
                    if (accArr[i].getName().contains(inputName)) {
                        resultAccount = true;
                        break;
                    }
                }

                if(resultAccount == true){
                    for (int i = 0; i < accArr.length; i++) {
                        if (inputName.equalsIgnoreCase(accArr[i].getName())
                                && inputPassword.equals(accArr[i].getPassword())) {
                            System.out.println("\n---------SUCCESFULL---------");
                            System.out.println("\n--WELCOME--" + inputName.toUpperCase() + "------------");

                            while (menuLoginNumber != 7) {
                                menuAccount();

                                System.out.print("\nUser Response: ");
                                menuLoginNumber = in.nextInt();
 
                                if (menuLoginNumber == 1) {

                                    printCurrentBalance(accArr, i);

                                } else if (menuLoginNumber == 2) {

                                    withdrawFunction(in, accArr, i);

                                } else if (menuLoginNumber == 3) {

                                    depositAccount(in, accArr, i);

                                } else if (menuLoginNumber == 4) {
                                    System.out.print("\nAccount number: ");
                                    transferAcc = in.next();
                                    System.out.print("\nTransfer Amount: RM ");
                                    transferAmount = in.nextDouble();

                                    int countTransferIndex = 0;
                                    for (int j = 0; j < accArr.length; j++) {
                                        if (accArr[j].getAccountNumber().contains(transferAcc)) {
                                            resultTransfer = true;
                                            countTransferIndex = j;
                                        }
                                    }
                                    System.out.println("\nIndex of acc to transfer is " + countTransferIndex);
                                    if (resultTransfer == true) {
                                        for (int j = 0; j < accArr.length; j++) {
                                            if (j == countTransferIndex) {
                                                System.out.println("\n--RECEIVER ACCOUNT INFO-------");
                                                System.out.println("\n-------------------------------------------");
                                                System.out
                                                        .println("---Name: " + accArr[j].getName() + "-----");
                                                System.out.println(
                                                        "---Account Number: " + accArr[j].getAccountNumber()
                                                                + "-----");
                                                System.out.println("-------------------------------------------");
                                            }

                                        }

                                        int transferConfirmation = 0;

                                        System.out.println("\n--APPROVAL CONFIRMATION---");
                                        System.out.println("--[1] CONFIRM TRANSFER---");
                                        System.out.println("--[2] CANCEL TRANSFER---");
                                        System.out.print("\nARE YOUR CONFIRM? : ");
                                        transferConfirmation = in.nextInt();

                                        if (transferConfirmation == 1) {
                                            accArr[i].setBalance(accArr[i].transfer(transferAmount));

                                            for (int j = 0; j < accArr.length; j++) {
                                                if (j == countTransferIndex) {
                                                    accArr[j]
                                                            .setBalance(
                                                                    accArr[j].depositSaving(transferAmount));
                                                }
                                            }

                                            System.out.println("\n--TRANSFER SUCCESFULL-------");
                                            printCurrentBalance(accArr, i);
                                        } else {
                                            System.out.println("\n----TRANSFER HAS BEEN CANCELLED----");
                                        }

                                    } else {
                                        System.out.println("\nAccount not existed");
                                    }
                                } else if (menuLoginNumber == 5) {
                                    accountInfo(accArr, i);
                                } else if(menuLoginNumber == 6){
                                    menuLoginNumber = logout(in, menuLoginNumber);
                                    
                                }
                            }
                            
                        }
                    }
                }
                else{
                    System.out.println("\nAccount not existed");
                }
                menuLoginNumber = 0;
            }

        }
        System.out.println("----------------------------");
        System.out.println();
        in.close();
    }

    private static void menuAccount() {
        System.out.println("\n--CHOOSE OUR SERVICES-------");
        System.out.println("\n--[1]-CHECK BALANCE---------");
        System.out.println("--[2]-WITHDRAW SAVING--------");
        System.out.println("--[3]-DEPOSIT INTO ACCOUNT---");
        System.out.println("--[4]-TRANSFER---------------");
        System.out.println("--[5]-ACCOUNT INFORMATION----");
        System.out.println("--[6]-LOGOUT-----------------");
    }

    private static int logout(Scanner in, int menuLoginNumber) {
        int logoutConfirm = 0;
        System.out.print("\n---LOGOUT CONFIRMATION---[1]--CONFIRM LOGOUT---: ");
        logoutConfirm = in.nextInt();

        if(logoutConfirm == 1){
            menuLoginNumber = 7;
        }
        return menuLoginNumber;
    }

    private static void accountInfo(Account[] accArr, int i) {
        System.out.println("\n--YOUR ACCOUNT-------");
        System.out.println("\n-------------------------------------------");
        System.out.println("---Your Name: " + accArr[i].getName() + "-----");
        System.out.println(
                "---Your Account Number: " + accArr[i].getAccountNumber() + "-----");
        System.out.println("-------------------------------------------");
    }

    private static void depositAccount(Scanner in, Account[] accArr, int i) {
        double depositAmount;
        System.out.print("\nDeposit Amount: RM ");
        depositAmount = in.nextDouble();
        accArr[i].setBalance(accArr[i].depositSaving(depositAmount));

        System.out.println("\n--DEPOSIT SUCCESFULL-------");
        printCurrentBalance(accArr, i);
    }

    private static void withdrawFunction(Scanner in, Account[] accArr, int i) {
        double withdrawAmount;
        System.out.print("\nAmount: RM ");
        withdrawAmount = in.nextDouble();
        accArr[i].setBalance(accArr[i].withdrawSaving(withdrawAmount));

        System.out.println("\n--WITHDRAW SUCCESFULL-------");
        printCurrentBalance(accArr, i);
    }

    private static void printCurrentBalance(Account[] accArr, int i) {
        System.out.println("\n-------------------------------------------");
        System.out.println("--CURRENT BALANCE: RM " + df.format(accArr[i].getBalance()));
        System.out.println("-------------------------------------------");
    }
}
