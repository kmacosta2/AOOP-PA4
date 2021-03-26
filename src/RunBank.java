import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
// Only a bank manager could add another customer to the system and is expected to provide the identification number.

/**
 * This is my main class, where all execution of the code gets accessed.
 * @version 3.0
 */
public class RunBank {//THIS IS UPDOATED!!!
    public static void main(String[] args) {
        try {
            //Taking care of emptying out the file.
            FileWriter tmpWriter = new FileWriter("loggedTransaction.txt");
            tmpWriter.close();
//            FileWriter tmpWrite = new FileWriter("loggedTransactionReader.txt");
//            tmpWriter.close();
            File clientInfoFile = new File("src/CS 3331 - Bank Users 3(6).csv");
            Scanner scanner = new Scanner(clientInfoFile);
            ArrayList<Customer> customer = new ArrayList<>();

            String currentLastID = " ";
            int currSavAccNum = -99;
            int currCheckAccNum = -99;
            int currCredAccNum = -99;

            String firstL = scanner.nextLine();   //Here i am storing the first row (header with fields) in order to indicate where everything is in this csv file
            String[] fLine = firstL.split(",");
            int fN, lN, svAccNum, iD, dob, chAccNum, credAccNum, phone, chStrBal, svStrBal, credMAX, credStrBal, addr;
            fN = columnFinder("First Name", fLine);
            lN = columnFinder("Last Name", fLine);
            svAccNum = columnFinder("Savings Account Number", fLine);
            iD = columnFinder("Identification Number", fLine);
            dob = columnFinder("Date of Birth", fLine);
            chAccNum = columnFinder("Checking Account Number", fLine);
            credAccNum = columnFinder("Credit Account Number", fLine);
            phone = columnFinder("Phone Number", fLine);
            chStrBal = columnFinder("Checking Starting Balance", fLine);
            svStrBal = columnFinder("Savings Starting Balance", fLine);
            credMAX = columnFinder("Credit Max", fLine);
            credStrBal = columnFinder("Credit Starting Balance", fLine);
            addr = columnFinder("Address", fLine);
            while (scanner.hasNext()) {
                String strin = scanner.nextLine();
                String[] clientInfoAt;
                clientInfoAt = strin.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);    //Should address the address being comma separated and all
                Customer temp = new Customer();
                temp.setfName(clientInfoAt[fN]);
                temp.setlName(clientInfoAt[lN]);
                Checking chk = new Checking(Integer.parseInt(clientInfoAt[chAccNum]), Double.parseDouble(clientInfoAt[chStrBal])); //creating obj of type checking
                temp.setCheckingAcc(chk);
                Savings save = new Savings(Integer.parseInt(clientInfoAt[svAccNum]), Double.parseDouble(clientInfoAt[svStrBal]));
                temp.setSavingsAcc(save);
                Credit cred = new Credit(Integer.parseInt(clientInfoAt[credAccNum]), Double.parseDouble(clientInfoAt[credStrBal]), Integer.parseInt(clientInfoAt[credMAX])); //Max Credit is now accounted for
                temp.setCreditAcc(cred);
                temp.setIdentNumber(clientInfoAt[iD]);
                currentLastID = clientInfoAt[iD];         //<---A placeholder for upcoming new users, takes in last Identification number
                currSavAccNum = Integer.parseInt(clientInfoAt[svAccNum]);
                currCheckAccNum = Integer.parseInt(clientInfoAt[chAccNum]);
                currCredAccNum = Integer.parseInt(clientInfoAt[credAccNum]);
                temp.setDOB(clientInfoAt[dob]);
                temp.setAddress(clientInfoAt[addr]);
                temp.setPhoneNumber(clientInfoAt[phone]);
                customer.add(temp);
            }
            scanner.close();

            Scanner user = new Scanner(System.in);
            mainStart(customer, currentLastID, currSavAccNum, currCheckAccNum, currCredAccNum, user);
            user.close();
            generateNewCSV(customer);

        } catch (IOException e) {
            System.out.println("File can't be found..");
        }
    }

    public static void mainStart(ArrayList<Customer> customer, String currLastID, int currSavAccNum, int currCheckAccNum, int currCredAccNum, Scanner user) throws IOException {
        String type = "";
        while(!type.equals("3")) {
            System.out.println("Are you an Individual(0), Manager(1) or would you like to Enter Transaction reader(2)? to quit(3)");

            try {
                type = user.nextLine();
                if (Double.parseDouble(type) == 0) {
                    boolean nameFound = false;
                    while (!nameFound) {
                        System.out.println("Please Enter account name: ");
                        String userPerhaps = user.nextLine();
                        String[] flNames = userPerhaps.split(" ");
                        //Traversing all of the client objects to see if user input is valid
                        for (Customer x : customer) {
                            if (x.getfName().equalsIgnoreCase(flNames[0]) && (x.getlName().equalsIgnoreCase(flNames[1]))) {
                                nameFound = indiviStartMenu(customer, x, flNames, nameFound, user);
                            }
                        }
                    }
                }
                else if (Double.parseDouble(type) == 1) {
                    managerMenu(customer, user, currLastID, currSavAccNum, currCheckAccNum, currCredAccNum);
                } else if (Double.parseDouble(type) == 2) {
                    transactionReader(customer);
                }
                else if (Double.parseDouble(type) != 3) {
                    System.out.println("Invalid, try again");
                }
            }
            catch(Exception e){
                System.out.println("Number Format Incorrect, try again.");
            }
        }
    }

    public static boolean indiviStartMenu(ArrayList<Customer> customer, Customer x, String[] flNames, boolean nameFound, Scanner user){
        System.out.println("Welcome " + flNames[0] + " " + flNames[1] + "!");
        int result = -99;
        try {
            while(!nameFound) {
                System.out.println("Please select account type:\nChecking(0)\nSaving(1)\nCredit(2)");
                result = user.nextInt();
                //user.nextLine();
                String message = "How may I help you " + flNames[0] + " " + flNames[1] + "?";
                String text = "Is there anything else you would like to do? (y or another button for no)";
                if (result == 0) {
                    System.out.println(message);
                    myMenu(x, x.getCheckingAcc(), flNames, customer, "Checking-" + x.getCheckingAcc().getAccNumber(), user);
                    System.out.println(text);

                    String ans = user.nextLine();
                    if (!ans.equalsIgnoreCase("y"))
                        nameFound = true;
                    else
                        ;
                } else if (result == 1) {
                    System.out.println(message);
                    myMenu(x, x.getSavingsAcc(), flNames, customer, "Savings-" + x.getSavingsAcc().getAccNumber(), user);
                    System.out.println(text);
                    String ans = user.nextLine();
                    if (!ans.equalsIgnoreCase("y"))
                        nameFound = true;
                    else
                        ;
                } else if (result == 2) {
                    System.out.println(message);
                    myMenu(x, x.getCreditAcc(), flNames, customer, "Credit-" + x.getCreditAcc().getAccNumber(), user);
                    System.out.println(text);
                    String ans = user.nextLine();
                    if (!ans.equalsIgnoreCase("y"))
                        nameFound = true;
                    else
                        ;
                }
                else
                    System.out.println("Wrong input TRY again");
            }
        }
                catch (Exception e) {
                    System.out.println("Not a valid option");
                    result = -99;
                }
        return nameFound;
    }

    /**
     * The main method used for every client, which includes handling almost all the activity available in this program.
     * @param stndrd
     * @param account
     * @param flNames
     * @param customer
     * @param accTypeSelfAndNum
     * @param user
     * @throws IOException
     */
    public static void myMenu(Customer stndrd, Account account, String[] flNames, ArrayList<Customer> customer, String accTypeSelfAndNum, Scanner user) throws IOException {
        int choice = -99;
        do {
            System.out.println();
            System.out.println("To inquire balance: 1\nTo pay someone: 2\nTo deposit : 3\nTo withdraw: 4\nTo transfer: 5\nTo quit, press 0");
            try {
                choice = user.nextInt();
            }
            catch (Exception e){ //this exception was taken care of below
            }
            user.nextLine();
            switch (choice) {
                case 1:
                    account.inquireBalance();
                    account.logTrans("inquire", flNames[0] + " " + flNames[1], "",0, accTypeSelfAndNum, account.getStartBalance(), "", 0.0);
                    stndrd.addActivities(flNames[0] + " " + flNames[1] + " inquired about" + accTypeSelfAndNum + " which was " + account.getStartBalance());
                    break;
                case 2: {
                    try {
                        System.out.println("To what account type, amount, and to who would you like to pay? \n(Expected layout-->type(checking:0, Savings:1, etc.) amount fName lName, separate by spaces)");
                        String input1 = user.nextLine();
                        String[] someoneInfo = input1.split(" "); //size 4 array
                        int type = Integer.parseInt(someoneInfo[0]);
                        double amount = Double.parseDouble(someoneInfo[1]);
                        if (account.paySomeone(amount, someoneInfo[2] + " " + someoneInfo[3])) { //While client can afford to lose the transfer amount
                            //then we search the client list for the other client (if they exist)
                            for (Customer y : customer) {
                                if (someoneInfo[2].equalsIgnoreCase(y.getfName()) && someoneInfo[3].equalsIgnoreCase(y.getlName())) {
                                    if (type == 0) {                     //Confirming Account type for other client
                                        y.getCheckingAcc().deposit(amount);
                                        System.out.println("Pay to " + y.getfName() + " " + y.getlName() + "'s Checking Successful. Checking balance: " + y.getCheckingAcc().getStartBalance());
                                        account.logTrans("paySomeone", flNames[0] + " " + flNames[1], someoneInfo[2] + " " + someoneInfo[3], amount, accTypeSelfAndNum, account.getStartBalance(), "Checking-" + y.getCheckingAcc().getAccNumber(), y.getCheckingAcc().getStartBalance());
                                        stndrd.addActivities(flNames[0] + " " + flNames[1] + " paid " + someoneInfo[2] + " " + someoneInfo[3] + " from their " + accTypeSelfAndNum + " $" + amount + " " + someoneInfo[2] + " " + someoneInfo[3] + "'s new balance $" + y.getCheckingAcc().getStartBalance());
                                    } else if (type == 1) {
                                        y.getSavingsAcc().deposit(amount);
                                        System.out.println("Pay to " + y.getfName() + " " + y.getlName() + "'s Savings Successful. Savings balance: " + y.getSavingsAcc().getStartBalance());
                                        account.logTrans("paySomeone", flNames[0] + " " + flNames[1], someoneInfo[2] + " " + someoneInfo[3], amount, accTypeSelfAndNum, account.getStartBalance(), "Saving-" + y.getSavingsAcc().getAccNumber(), y.getSavingsAcc().getStartBalance());
                                        stndrd.addActivities(flNames[0] + " " + flNames[1] + " paid " + someoneInfo[2] + " " + someoneInfo[3] + " from their " + accTypeSelfAndNum + " $" + amount + " " + someoneInfo[2] + " " + someoneInfo[3] + "'s new balance $" + y.getSavingsAcc().getStartBalance());
                                    } else {
                                        y.getCreditAcc().deposit(amount);
                                        System.out.println("Pay to " + y.getfName() + " " + y.getlName() + "'s Credit Successful. Credit balance: " + y.getCreditAcc().getStartBalance());
                                        account.logTrans("paySomeone", flNames[0] + " " + flNames[1], someoneInfo[2] + " " + someoneInfo[3], amount, accTypeSelfAndNum, account.getStartBalance(), "Credit-" + y.getCreditAcc().getAccNumber(), y.getCreditAcc().getStartBalance());
                                        stndrd.addActivities(flNames[0] + " " + flNames[1] + " paid " + someoneInfo[2] + " " + someoneInfo[3] + " from their " + accTypeSelfAndNum + " $" + amount + " " + someoneInfo[2] + " " + someoneInfo[3] + "'s new balance $" + y.getCreditAcc().getStartBalance());
                                    }
                                    break;
                                }
                            }
                        } else {
                            System.out.println(flNames[0] + " " + flNames[1] + " attempted a transaction, has insufficient funds/did not exist in system.");
                            account.logTrans("fPaySomeone", flNames[0] + " " + flNames[1], someoneInfo[0] + someoneInfo[1], amount, accTypeSelfAndNum, 0.0, "", 0.0);
                            stndrd.addActivities(flNames[0] + " " + flNames[1] + " attempted to paid " + someoneInfo[2] + " " + someoneInfo[3] + " $" + amount + " from their " + accTypeSelfAndNum);
                        }
                        break;
                    }
                    catch (Exception e){
                        System.out.println("There was an input mismatch, please try again\n\n");
                    }
                }

                case 3: {
                    System.out.println("Deposit selected, please enter an amount:");
                    double amount = Double.parseDouble(user.nextLine());
                    account.deposit(amount);
                    account.logTrans("deposit", flNames[0] + " " + flNames[1], "", amount, accTypeSelfAndNum, account.getStartBalance(), "",0.0);
                    stndrd.addActivities(flNames[0] + " " + flNames[1] + " deposited " + amount + " to " + accTypeSelfAndNum);
                    break;
                }
                case 4: {
                    System.out.println("Withdraw selected, please enter an amount: ");
                    double amount = Double.parseDouble(user.nextLine());
                    if(account.withdraw(amount)){
                        account.logTrans("withdraw", flNames[0] + " " + flNames[1], "", amount, accTypeSelfAndNum, account.getStartBalance(),"",0.0);
                        stndrd.addActivities(flNames[0] + " " + flNames[1] + " withdrew " + amount + " from " + accTypeSelfAndNum + " " + flNames[0] + " " + flNames[1] + "'s new balance $" + account.getStartBalance());
                    }
                    else {
                        account.logTrans("fWithdraw", flNames[0] + " " + flNames[1], "", amount, accTypeSelfAndNum, account.getStartBalance(), "", 0.0);
                        stndrd.addActivities(flNames[0] + " " + flNames[1] + " failed to withdraw $" + amount + " from " + accTypeSelfAndNum);
                    }
                    break;
                }
                case 5: { //Transfer Selected
                    System.out.println("What amount would you like to transfer and to what account?\nChecking(0) Saving(1) Credit(2) (Expected layout--> amount type, separate by spaces) ");
                    //user.nextLine();
                    String input = user.nextLine();
                    String[] tranInfo = input.split(" ");            // flNames has self's name
                    double amount1 = Double.parseDouble(tranInfo[0]);
                    int accType = Integer.parseInt(tranInfo[1]);
                                if(accType == 0) {
                                    if(account.transfer(amount1, accType, stndrd.getCreditAcc().getStartBalance(), stndrd, account)) {
                                        System.out.println(flNames[0] + " " + flNames[1] + "'s transfer to Checking Successful, new balance: " + stndrd.getCheckingAcc().getStartBalance());
                                        account.logTrans("transfer", flNames[0] + " " + flNames[1], "", amount1, accTypeSelfAndNum, account.getStartBalance(), "Checking-" + stndrd.getCheckingAcc().getAccNumber(), stndrd.getCheckingAcc().getStartBalance());
                                        stndrd.addActivities(flNames[0] + " " + flNames[1] + " transferred $" + amount1 + " from " + accTypeSelfAndNum + " " + flNames[0] + " " + flNames[1] + "'s new balance $" + account.getStartBalance());
                                    }
                                }
                                else if(accType == 1) {
                                    if(account.transfer(amount1, accType, stndrd.getCreditAcc().getStartBalance(), stndrd, account)) {
                                        System.out.println(flNames[0] + " " + flNames[1] + "'s transfer to Savings Successful new balance: " + stndrd.getSavingsAcc().getStartBalance());
                                        account.logTrans("transfer", flNames[0] + " " + flNames[1], "", amount1, accTypeSelfAndNum, account.getStartBalance(), "Savings-" + stndrd.getSavingsAcc().getAccNumber(), stndrd.getSavingsAcc().getStartBalance());
                                    }
                                }
                                else if(accType == 2) {
                                    if(account.transfer(amount1, accType, stndrd.getCreditAcc().getStartBalance(), stndrd, account)) {
                                        System.out.println(flNames[0] + " " + flNames[1] + "'s deposit to Credit Successful new balance: " + stndrd.getCreditAcc().getStartBalance());
                                        account.logTrans("transfer", flNames[0] + " " + flNames[1], "", amount1, accTypeSelfAndNum, account.getStartBalance(), "Credit-" + stndrd.getCreditAcc().getAccNumber(), stndrd.getCreditAcc().getStartBalance());
                                    }
                                }
                    else {
                        System.out.println(flNames[0] + " " + flNames[1] + " attempted a transaction.");
                        //account.logTrans("fTransfer", flNames[0] + " " + flNames[1], tranInfo[0] + tranInfo[1], amount1, accTypeSelfAndNum, account.getStartBalance(), "", 0.0);
                    }
                    break;
                }
                case 0: {
                    System.out.println("Thank you, Goodbye!\n");
                    break;
                }
                default:
                    System.out.println("Invalid input, please try again");

            }
        }
        while (choice != 0);
    }
    //Fields are expected to be in the same place: (3)Action, (0)From First Name,(1)From Last Name,(2)From Where,(4)To First Name,(5)To Last Name,(6)To Where,(7)Action Amount
    public static void transactionReader(ArrayList<Customer> customer) throws IOException{
        File transFile = new File("src/Transaction Actions(5).csv");
        Scanner scann = new Scanner(transFile);
        scann.nextLine(); //skipping the header line
        while (scann.hasNext()) {   //Performing an action, one line at a time.
            String currentLine = scann.nextLine();   //Here I am storing the first row (header with fields) in order to indicate where everything is in this csv file
            String[] fL = currentLine.split(",");

            if (fL[3].equalsIgnoreCase("pays")) {
                System.out.println("PAY Selected");
                //boolean wasFound = false;
                for (Customer x : customer) {       //From who's account?  ASSUMPTION: WON'T be coming from a credit account;
                    if (fL[0].equalsIgnoreCase(x.getfName()) && fL[1].equalsIgnoreCase(x.getlName())) { //If they exist then establish the account type that will be withdrawn from..
                        if (fL[2].equals("Checking")) {
                            x.getCheckingAcc().pay(Double.parseDouble(fL[7]), customer, x.getCheckingAcc(),fL);    //INstead of POLYMORPHTRANS
                        } else if (fL[2].equals("Savings")) {
                            x.getSavingsAcc().pay(Double.parseDouble(fL[7]), customer, x.getSavingsAcc(), fL);
                        } else {
                            System.out.println("Wrong field in \"From Where\" ");
                        }
                        break;
                    }
                }
                    System.out.println("PAY DONE");
                    System.out.println();
            }//(3)Action, (0)From First Name,(1)From Last Name,(2)From Where,(4)To First Name,(5)To Last Name,(6)To Where,(7)Action Amount
            else if (fL[3].equalsIgnoreCase("transfers")){ //Assumed that it can't come from Credit!
                System.out.println("TRANSFER Selected");
                for (Customer x : customer) {
                    if (fL[0].equalsIgnoreCase(x.getfName()) && fL[1].equalsIgnoreCase(x.getlName())) {//If they exist then establish the account type that will be withdrawn from..
                        if (fL[2].equalsIgnoreCase("Checking")) {
                            if (fL[6].equalsIgnoreCase("Savings")) {
                                //System.out.println(fL[0]+fL[1]+"'s checking balance before "+x.getCheckingAcc().getStartBalance());
                                tranReaderPolymorph(x.getCheckingAcc(), x.getSavingsAcc(), fL);
                            } else if (fL[6].equalsIgnoreCase("Credit")) {
                                //System.out.println(fL[0]+fL[1]+"'s checking balance before "+x.getCheckingAcc().getStartBalance());
                                tranReaderPolymorph(x.getCheckingAcc(), x.getCreditAcc(), fL);
                            }
                        }
                        else if (fL[2].equalsIgnoreCase("Savings")) {
                            if (fL[6].equalsIgnoreCase("Checking")) {
                                //System.out.println(fL[0]+fL[1]+"'s Savings balance before "+x.getSavingsAcc().getStartBalance());
                                tranReaderPolymorph(x.getSavingsAcc(), x.getCheckingAcc(), fL);
                            } else if (fL[6].equalsIgnoreCase("Credit")) {
                                //System.out.println(fL[0]+fL[1]+"'s Savings balance before "+x.getSavingsAcc().getStartBalance());
                                tranReaderPolymorph(x.getSavingsAcc(), x.getCreditAcc(), fL);
                            }
                        } else {
                            System.out.println("Wrong field in \"From Where\" ");
                        }
                        break;
                    }
                }
                System.out.println("TRANSFER DONE");
                System.out.println();
            }
            else if (fL[3].equalsIgnoreCase("inquires")) {
                for (Customer x : customer) {
                    System.out.println("full name: " + x.getfName() + " " + x.getlName());
                    if (x.getfName().equalsIgnoreCase(fL[0]) && (x.getlName().equalsIgnoreCase(fL[1]))) { //Checking for first and last names to be valid for an existing account
                        if (fL[2].equalsIgnoreCase("Checking")) {
                            System.out.print("INQUIRE Checking Selected--> ");
                            x.getCheckingAcc().inquireBalance();
                            x.getCheckingAcc().logTrans("inquire", fL[0] + " " + fL[1], "", 0, fL[2] + x.getCheckingAcc().getAccNumber(), x.getCheckingAcc().getStartBalance(), "", 0.0);
                            //break;
                        } else if (fL[2].equalsIgnoreCase("Savings")) {
                            System.out.print("INQUIRE Savings Selected--> ");
                            x.getSavingsAcc().inquireBalance();
                            x.getSavingsAcc().logTrans("inquire", fL[0] + " " + fL[1], "", 0, fL[2] + x.getSavingsAcc().getAccNumber(), x.getSavingsAcc().getStartBalance(), "", 0.0);
                            //break;
                        } else if (fL[2].equalsIgnoreCase("Credit")) {
                            System.out.print("INQUIRE Credit Selected--> ");
                            x.getCreditAcc().inquireBalance();
                            x.getCreditAcc().logTrans("inquire", fL[0] + " " + fL[1], "", 0, fL[2] + x.getCreditAcc().getAccNumber(), x.getCreditAcc().getStartBalance(), "", 0.0);
                            //break;
                        }
                        System.out.println("INQUIRE DONE");
                        System.out.println();
                        break;
                    }
                }
            }           //Assumption: there are NONE FROM the credit account.

            else if (fL[3].equalsIgnoreCase("withdraws")) {
                System.out.println("WITHDRAW Selected");
                for (Customer x : customer) {
                    //System.out.println("full name: " + x.getfName() + " " + x.getlName());
                    if (x.getfName().equalsIgnoreCase(fL[0]) && (x.getlName().equalsIgnoreCase(fL[1]))) {
                        if(fL[2].equalsIgnoreCase("Checking")) {
                            if (x.getCheckingAcc().withdraw(Double.parseDouble(fL[7]))) {
                                x.getCheckingAcc().logTrans("withdraw", fL[0] + " " + fL[1], "", Double.parseDouble(fL[7]), fL[2] + x.getCheckingAcc().getAccNumber(), x.getCheckingAcc().getStartBalance(), "", 0.0);
                                //break;
                            }
                        }
                        else if(fL[2].equalsIgnoreCase("Savings")) {
                            if (x.getSavingsAcc().withdraw(Double.parseDouble(fL[7]))) {
                                x.getSavingsAcc().logTrans("withdraw", fL[0] + " " + fL[1], "", Double.parseDouble(fL[7]), fL[2] + x.getSavingsAcc().getAccNumber(), x.getSavingsAcc().getStartBalance(), "", 0.0);
                                //break;
                            }
                        }
                        break;
                    }
                }
                System.out.println("WITHDRAW DONE");
                System.out.println();
            }

            else if (fL[3].equalsIgnoreCase("deposits")) {
                System.out.println("DEPOSIT Selected");
                boolean found = false;
                for (Customer x : customer) {
                    if (x.getfName().equalsIgnoreCase(fL[4]) && (x.getlName().equalsIgnoreCase(fL[5]))) {
                        if(fL[6].equalsIgnoreCase("Checking")) {
                            System.out.println(x.getfName() + " " + x.getlName() + "'s Checking balance before deposit "+x.getCheckingAcc().getStartBalance());
                            x.getCheckingAcc().deposit(Double.parseDouble(fL[7]));
                            System.out.println(x.getfName() + " " + x.getlName() + "'s Checking balance after deposit "+x.getCheckingAcc().getStartBalance());
                            x.getCheckingAcc().logTrans("deposit", fL[4] + " " + fL[5], "", Double.parseDouble(fL[7]), fL[6] + x.getCheckingAcc().getAccNumber(), x.getCheckingAcc().getStartBalance(), "", 0.0);
                            found = true;
                            break;
                        }
                        else if(fL[6].equalsIgnoreCase("Savings")) {
                            System.out.println(x.getfName() + " " + x.getlName() + "'s Savings balance before deposit "+x.getSavingsAcc().getStartBalance());
                            x.getSavingsAcc().deposit(Double.parseDouble(fL[7]));
                            System.out.println(x.getfName() + " " + x.getlName() + "'s Saving balance after deposit "+x.getSavingsAcc().getStartBalance());
                            x.getSavingsAcc().logTrans("deposit", fL[4] + " " + fL[5], "", Double.parseDouble(fL[7]), fL[6] + x.getSavingsAcc().getAccNumber(), x.getSavingsAcc().getStartBalance(), "", 0.0);
                            found = true;
                            break;
                        }
                        else if(fL[6].equalsIgnoreCase("Credit")) {
                            System.out.println(x.getfName() + " " + x.getlName() + "'s Credit balance before deposit "+x.getSavingsAcc().getStartBalance());
                            x.getCreditAcc().deposit(Double.parseDouble(fL[7]));
                            System.out.println(x.getfName() + " " + x.getlName() + "'s Credit balance after deposit "+x.getSavingsAcc().getStartBalance());
                            x.getCreditAcc().logTrans("deposit", fL[4] + " " + fL[5], "", Double.parseDouble(fL[7]), fL[6] + x.getCreditAcc().getAccNumber(), x.getCreditAcc().getStartBalance(), "", 0.0);
                            found = true;
                            break;
                        }
                    }
                }
                if (!found)
                    System.out.println("Name Never found");
                System.out.println("DEPOSIT DONE");
                System.out.println();
            }
            else
                System.out.println("Action: " + fL[6] + " not applied");
                }
        scann.close();
    }                //(3)Action, (0)From First Name,(1)From Last Name,(2)From Where,(4)To First Name,(5)To Last Name,(6)To Where,(7)Action Amount

    public static void tranReaderPolymorph(Account acc, Account acc2, String[] fl) throws IOException{
        //(3)Action, (0)From First Name,(1)From Last Name,(2)From Where,(4)To First Name,(5)To Last Name,(6)To Where,(7)Action Amount
        if (fl[3].equalsIgnoreCase("transfers")) {
            if (acc.transferPart1(Double.parseDouble(fl[7]), fl[6], acc2.getStartBalance())) { //While client can afford to lose the transfer amount
                //if this is returns true then go ahead and deposit into the other account of the SAME person.
                System.out.println(fl[0] + " " + fl[1] + "'s balance after: " + acc.getStartBalance());
                if (fl[6].equals("Checking")) {
                    System.out.println(fl[0] + " " + fl[1] + "'s (2) Checking balance before: " + acc2.getStartBalance());
                    acc2.transferPart2(Double.parseDouble(fl[7]));
                    System.out.println(fl[0] + " " + fl[1] + "'s transfer to Checking Successful, new balance: " + acc2.getStartBalance());
                    acc.logTrans("transfer", fl[0] + " " + fl[1], "", Double.parseDouble(fl[7]), fl[2] + acc.getAccNumber(), acc.getStartBalance(), "Checking-" + acc2.getAccNumber(), acc2.getStartBalance());
                } else if (fl[6].equals("Savings")) {
                    System.out.println(fl[0] + " " + fl[1] + "'s (2) Savings balance before: " + acc2.getStartBalance());
                    acc2.transferPart2(Double.parseDouble(fl[7]));
                    System.out.println(fl[0] + " " + fl[1] + "'s transfer to Savings Successful new balance: " + acc2.getStartBalance());
                    acc.logTrans("transfer", fl[0] + " " + fl[1], "", Double.parseDouble(fl[7]), fl[2] + acc.getAccNumber(), acc.getStartBalance(), "Savings-" + acc2.getAccNumber(), acc2.getStartBalance());

                } else if (fl[6].equals("Credit")) {
                    System.out.println(fl[0] + " " + fl[1] + "'s (2) Credit balance before: " + acc2.getStartBalance());
                    acc2.transferPart2(Double.parseDouble(fl[7]));                        //TRY WITH transferPART2 instead of deposit
                    System.out.println(fl[0] + " " + fl[1] + "'s deposit to Credit Successful new balance: " + acc2.getStartBalance());
                    acc.logTrans("transfer", fl[0] + " " + fl[1], "", Double.parseDouble(fl[7]), fl[2] + acc.getAccNumber(), acc.getStartBalance(), "Credit-" + acc2.getAccNumber(), acc2.getStartBalance());
                }
            } else {
                System.out.println(fl[0] + " " + fl[1] + " attempted a transaction");
                acc.logTrans("fTransfer", fl[0] + " " + fl[1], "", Double.parseDouble(fl[7]), fl[2] + acc.getAccNumber(), acc.getStartBalance(), "", 0.0);
            }
        }
    }
    /**
     * my exclusive menu for the bank manager
     * @param customer
     * @param user
     * @throws IOException
     */
    //now I'll need the right indexes for a particular field, they'll be provided in my parameters.
    //FROM THIS METHOD, do i need to return an updated customer arraylist in the event that Option C "add new user" is chosen? I decided yes.
    public static void managerMenu(ArrayList<Customer> customer, Scanner user, String lastID, int newSavAccNum, int newCheckAccNum, int newCreditNum) throws IOException {
        String option = " ";
        while (!option.equalsIgnoreCase("Q")) {
            System.out.println("A. Inquire account by name\nB. Inquire account by number\nC. Create new User\nQ. to quit");
            option = user.nextLine();

            if (option.equalsIgnoreCase("A")) {
                System.out.println("Who's account would you like to inquire about?");
                String inquire = user.nextLine();
                String[] inquired = inquire.split(" ");
                String ans;
                System.out.println("Would you like a statement? y or n");
                ans = user.nextLine();
                //Traversing all of the client objects to see if user input is valid
                for (Customer x : customer) {
                    if (x.getfName().equalsIgnoreCase(inquired[0]) && (x.getlName().equalsIgnoreCase(inquired[1]))) { //Checking for first and last names to be valid for an existing account
                        x.getEverything();
                        System.out.println("Checking account: " + x.getCheckingAcc().getAccNumber() + "\nChecking Balance: " + x.getCheckingAcc().getStartBalance());
                        System.out.println("Savings account: " + x.getSavingsAcc().getAccNumber() + "\nSavings Balance: " + x.getSavingsAcc().getStartBalance());
                        System.out.println("Credit account: " + x.getCreditAcc().getAccNumber() + "\nCredit Balance: " + x.getCreditAcc().getStartBalance());
                        System.out.println("Credit Max: " + x.getCreditAcc().getAccNumber());
                        System.out.println();
                        if (ans.equalsIgnoreCase("y"))
                            BankStatement.logTransacReader(x);
                        else
                            System.out.println("(No statement desired)");
                        break;
                    }
                }
            } else if (option.equalsIgnoreCase("B")) {
                System.out.println("What is the account number?");
                int accNum = user.nextInt();
                user.nextLine();
                String ans;
                System.out.println("Would you like a statement? y or n");
                ans = user.nextLine();
                for (Customer x : customer) {
                    if (x.getCheckingAcc().getAccNumber() == accNum || x.getSavingsAcc().getAccNumber() == accNum || x.getCreditAcc().getAccNumber() == accNum) { //Checking for first and last names to be valid for an existing account # Based off of TYPE
                        x.getEverything();
                        System.out.println("Checking Account: " + x.getCheckingAcc().getAccNumber() + "\nChecking Balance " + x.getCheckingAcc().getStartBalance());
                        System.out.println("Savings Account: " + x.getSavingsAcc().getAccNumber() + "\nSavings Balance " + x.getSavingsAcc().getStartBalance());
                        System.out.println("Credit Account: " + x.getCreditAcc().getAccNumber() + "\nCredit Balance " + x.getCreditAcc().getStartBalance());
                        System.out.println("Credit Max: " + x.getCreditAcc().getAccNumber());
                        System.out.println();
                        if (ans.equalsIgnoreCase("y"))
                            BankStatement.logTransacReader(x);
                        else if (ans.equalsIgnoreCase("n"))
                            System.out.println("No statement desired");
                        break;
                    }
                }
            } else if (option.equalsIgnoreCase("C")) {  //Remember indexes: fN, lN, svAccNum, iD, dob, chAccNum, credAccNum, phone, chStrBal, svStrBal, credMAX, credStrBal, addr
                int newCustomID = Integer.parseInt(lastID) + 1;
                String newCustomerID = "" + newCustomID + "";
                System.out.println("please provide(Separated by spaces):\nfName, lName, and balance\n(A savings account will be created by default)");
                String newCustomer = user.nextLine();
                String[] clientInfoAt;
                clientInfoAt = newCustomer.split(" ");
                Customer temp = new Customer();
                temp.setfName(clientInfoAt[0]);
                temp.setlName(clientInfoAt[1]);
                temp.setIdentNumber(newCustomerID);
                Savings sav = new Savings( (newSavAccNum+1), Double.parseDouble(clientInfoAt[2]));
                temp.setSavingsAcc(sav);
                System.out.println("Please Provide a DOB");
                temp.setDOB(user.nextLine());
                //System.out.println("DOB: "+temp.getDOB());
                System.out.println("Please Provide an address");
                temp.setAddress(user.nextLine());
                //System.out.println("address: "+ temp.getAddress());
                System.out.println("Please Provide a Phone number");
                temp.setPhoneNumber(user.nextLine());
                //System.out.println("phone number: "+temp.getPhoneNumber());

                //might have to add a user.nextLine if it isn't registering here
                System.out.println("Would you like to include a Checking account?\nno(0) If yes include: account balance");
                double info = user.nextDouble();
                //String[] checkInfo = info.split(" ");
                if (info != 0) {                //Making sure user didn't select no, otherwise dont do anything here
                    Checking check = new Checking( (newCheckAccNum + 1), info);
                    temp.setCheckingAcc(check);
                } else
                    temp.setCheckingAcc(new Checking());          //If chosen to not make a saving account then just create a default
                user.nextLine();
                System.out.println("Would you like to include a Credit account?\nno(0) If yes include: account balance and credit max");
                String inFO = user.nextLine();
                String[] credInfo = inFO.split(" ");
                if (!(credInfo[0].equals("0"))) {
                    Credit cred = new Credit( (newCreditNum + 1), Double.parseDouble(credInfo[0]), Integer.parseInt(credInfo[1]));
                    temp.setCreditAcc(cred);
                } else
                    temp.setCreditAcc(new Credit());            //If chosen to not make cred account then just create a default
                customer.add(temp);
            }
            else if (!option.equalsIgnoreCase("Q")) {
                System.out.println("Input was invalid, please try again\n");
            }
        }
        System.out.println("Goodbye");
    }
    public static int columnFinder(String whatToFind, String[] headers){
        int counter = 0;
        for (String a: headers){
            if(a.equals(whatToFind)) {
                return counter;
            }
            else
                counter ++;
        }
        System.out.println("Name was never found");
        return 0;
    }
    public static void generateNewCSV(ArrayList<Customer> customer) throws IOException {
        File clientInfoFile = new File("src/CS 3331 - Bank Users Updated.csv");
        FileWriter fWriter = new FileWriter("clientInfoFile.csv");
        fWriter.write("First Name,Last Name,Date of Birth,IdentificationNumber,Address,Phone Number,Checking Account Number,Savings Account Number,Credit Account Number,Checking Starting Balance,Savings Starting Balance,Credit Starting Balance,Credit Max\n");
        for (Customer x : customer) {
            fWriter.write(x.getfName() + ",");
            fWriter.write(x.getlName() + ",");
            fWriter.write("\"" + x.getDOB() + "\"" + ",");
            fWriter.write(x.getIdentNumber() + ",");
            fWriter.write( x.getAddress() + ",");
            fWriter.write(x.getPhoneNumber() + ",");
            fWriter.write(x.getCheckingAcc().getAccNumber() + ",");
            fWriter.write(x.getSavingsAcc().getAccNumber() + ",");
            fWriter.write(x.getCreditAcc().getAccNumber()+",");
            fWriter.write(x.getCheckingAcc().getStartBalance() + ",");
            fWriter.write(x.getSavingsAcc().getStartBalance() + ",");
            fWriter.write(x.getCreditAcc().getStartBalance() + ",");
            fWriter.write(x.getCreditAcc().getMaxCred() + "\n");
        }
        fWriter.close();
    }
}