
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * @author Kevin Acosta
 * @version 2.0
 * @since February 12 2021
 *
 * This is an abstract account object which gets re-instantiated several times for the uses of other classes
 */
/**
 *
 *Honesty Statement:I confirm that the work of this
 *     assignment is completely my own. By turning in this assignment, I declare that I did
 *     not receive unauthorized assistance. Moreover, all deliverables including, but not limited to the
 *     source code, lab report and output files were written and produced by me alone.
 */
public abstract class Account {
        private int accNumber;
        private double startBalance;

    /**
     * Constructor which creates an instance of the account class.
     *
     * @throws IOException
     */
    public Account() throws IOException {
        }
        public Account(int AccNumIn, double startBalanceIn){
            this.accNumber = AccNumIn;
            this.startBalance = startBalanceIn;
        }

    /**
     *
     * @return the account number
     */
    public int getAccNumber() {
            return accNumber;
        }


        public void setAccNumber(int accNumber) {
            this.accNumber = accNumber;
        }

    /**
     * Sets the starting balance.
     * @return the start balance
     */
        public double getStartBalance() {
            return startBalance;
        }

    /**
     * The alternative option to start balance
     * @param startBalance
     */
        public void setStartBalance(double startBalance) {
            this.startBalance = startBalance;
        }

    /**
     * Inquires the current balance
     */
        public void inquireBalance(){
            System.out.println("Balance: " + startBalance);
        }

    /**
     *
     * @param amount
     * @param customer
     * @return
     * @throws IOException
     */
    //HERE take care of the successful/failed messages of conditions!!
    public boolean pay(double amount, ArrayList<Customer> customer, Account acc, String[] fl) throws IOException {        //Returns true if client can afford money loss
        //FIRST THING, Check for receiver's existence before the giver gives up the money!!
        //then we search the client list for the other client (if they exist)
        boolean isfound = false;
        if (amount > 0 && startBalance >= amount) { //While client can afford to lose the transfer amount, then go search for receiver
            for (Customer y : customer) {
                if (fl[4].equalsIgnoreCase(y.getfName()) && fl[5].equalsIgnoreCase(y.getlName())) {
                    if (fl[6].equalsIgnoreCase("Checking")) {                     //Confirming Account type for other client
                        //System.out.println(y.getfName() + y.getlName() + "'s original Checking balance before paySomeone " + y.getCheckingAcc().getStartBalance());
                        /*The change happened HERE*/

                        y.getCheckingAcc().setStartBalance(y.getCheckingAcc().getStartBalance()+Double.parseDouble(fl[7]));      //paySomeoneP2(Double.parseDouble(fl[7]));
                        //System.out.println("Pay to " + y.getfName() + " " + y.getlName() + "'s Checking Successful. Checking balance: " + y.getCheckingAcc().getStartBalance());
                        startBalance -= amount;
                        acc.logTrans("paySomeone", fl[0] + " " + fl[1], fl[4] + " " + fl[5], Double.parseDouble(fl[7]), fl[2] + acc.getAccNumber(), acc.getStartBalance(), "Checking-" + y.getCheckingAcc().getAccNumber(), y.getCheckingAcc().getStartBalance());
                        //System.out.println(y.getfName() + y.getlName() + "'s new balance from paySomeone " + y.getCheckingAcc().getStartBalance());
                        isfound = true;
                    } else if (fl[6].equalsIgnoreCase("Savings")) {
                        //System.out.println(y.getfName() + y.getlName() + "'s original Savings balance before paySomeone " + y.getCheckingAcc().getStartBalance());
                        y.getSavingsAcc().setStartBalance(y.getSavingsAcc().getStartBalance()+Double.parseDouble(fl[7]));                          //paySomeoneP2(Double.parseDouble(fl[7]));
                        //System.out.println("Pay to " + y.getfName() + " " + y.getlName() + "'s Savings Successful. Savings balance: " + y.getSavingsAcc().getStartBalance());
                        startBalance -= amount;
                        acc.logTrans("paySomeone", fl[0] + " " + fl[1], fl[4] + " " + fl[5], Double.parseDouble(fl[7]), fl[2] + acc.getAccNumber(), acc.getStartBalance(), "Saving-" + y.getSavingsAcc().getAccNumber(), y.getSavingsAcc().getStartBalance());
                        //System.out.println(y.getfName() + y.getlName() + "'s new balance from paySomeone " + y.getSavingsAcc().getStartBalance());
                        isfound = true;
                    } else if (fl[6].equalsIgnoreCase("Credit")) {
                        //System.out.println(y.getfName() + y.getlName() + "'s original Credit balance from paySomeone " + y.getCreditAcc().getStartBalance());
                        y.getCreditAcc().setStartBalance(y.getCreditAcc().getStartBalance()+Double.parseDouble(fl[7]));                        //paySomeoneP2(Double.parseDouble(fl[7]));
                        //System.out.println("Pay to " + y.getfName() + " " + y.getlName() + "'s Credit Successful. Credit balance: " + y.getCreditAcc().getStartBalance());
                        startBalance -= amount;
                        acc.logTrans("paySomeone", fl[0] + " " + fl[1], fl[4] + " " + fl[5], Double.parseDouble(fl[7]), fl[2] + acc.getAccNumber(), acc.getStartBalance(), "Saving-" + y.getSavingsAcc().getAccNumber(), y.getSavingsAcc().getStartBalance());
                        //System.out.println(y.getfName() + y.getlName() + "'s new balance from paySomeone " + y.getCreditAcc().getStartBalance());
                        isfound = true;
                    }
                    break;
                }
            }
        } else {
            System.out.println(fl[0] + " " + fl[1] + " attempted to pay, has insufficient funds");
            acc.logTrans("fPaySomeone", fl[0] + " " + fl[1], fl[4] + fl[5], Double.parseDouble(fl[7]), fl[2] + acc.getAccNumber(), 0.0, "", 0.0);
        }
            return true;
    }

    /**
     *
     * @param amount
     * @param who
     * @return boolean
     * @throws IOException
     */
        public boolean paySomeone(double amount, String who) throws IOException {        //Returns true if client can afford money loss
            //FIRST THING, Check for receiver's existence before the giver gives up the money!!

            if(amount > 0 && startBalance >= amount){ //lastly, we check if the giver has sufficient funds
                startBalance -= amount;
                //System.out.println("Pay of $" + amount + " to " + who + " successful");
                return true;
            }
            else {
                System.out.println("Pay of $" + amount + " to " + who + " failed due to amount");
                return false;
            }
        }

    /**
     *
     * @param amount
     * @return
     * @throws IOException
     */
        public boolean paySomeoneP2(double amount) throws IOException {        //simply adds to the account of the person that's on the receiving end of the pay
            if (amount >= 0){
                startBalance += amount;
                return true;
            }
            return false;
        }

    /**
     * the method can update the balance
     * @param amount
     * @return whether or not it was successful (true) or not (false)
     * @throws IOException
     */
        public boolean deposit(double amount) throws IOException {
            if (amount >= 0){
                startBalance += amount;
                System.out.println("Deposit of $" + amount + " successful");
                return true;
            }
            else {
                System.out.println("Deposit $" + amount + " Failed");
                return false;
            }
        }

    /**
     * This method withdraws from balance.
     * @param amount
     * @return
     * @throws IOException
     */
        public boolean withdraw(double amount) throws IOException {    //Returns true if successful
            if(amount > 0 && startBalance >= amount){
                startBalance -= amount;
                System.out.println("Withdraw of $" + amount + " successful");
                return true;
            }
            else{
                System.out.println("Withdraw of $" + amount + " failed ");
                return false;
            }
        }

    /**
     * transfer
     * @param amount
     * @param type
     * @param CreditBalance
     * @return
     * @throws IOException
     */
        public boolean transfer(double amount, int type, double CreditBalance, Customer cust, Account acc) throws IOException {       //p1 simply withdraws from the original account
            if (type == 2) {
                //System.out.println("hello type credit is acknowledged in TransferPrt1");
                if (amount > (CreditBalance * -1)) {
                    System.out.println("Failed Credit-Balance Transfer Requirements");
                    return false;
                }
            }
            switch (type) {
                case 0:
                    if (cust.getCheckingAcc().equals(acc)) {  //Ensuring they aren't the same type
                        System.out.println("Cannot transfer to same account type (Checking)");
                        logTrans("fTransfer", cust.getfName() + " " + cust.getlName(), "", amount, "Checking-" + acc.getAccNumber(), acc.getStartBalance(), "Checking-" + acc.getAccNumber(), 0.0);
                        return false;
                    } else if (amount > 0 && startBalance >= amount) {
                        startBalance -= amount;
                        cust.getCheckingAcc().setStartBalance(cust.getCheckingAcc().getStartBalance() + amount);
                        System.out.println("Transfer of $" + amount + " successful");
                        return true;
                    } else {
                        System.out.println("Transfer of $" + amount + " failed");
                        return false;
                    }

                case 1:
                    if (cust.getSavingsAcc().equals(acc)) {  //Ensuring they aren't the same type
                        System.out.println("Cannot transfer to same account type, (Savings)");
                        logTrans("fTransfer", cust.getfName() + " " + cust.getlName(), "", amount, "Savings-" + acc.getAccNumber(), acc.getStartBalance(), "Savings-" + acc.getAccNumber(), 0.0);
                        return false;
                    } else if (amount > 0 && startBalance >= amount) {
                        startBalance -= amount;
                        cust.getSavingsAcc().setStartBalance(cust.getSavingsAcc().getStartBalance() + amount);
                        System.out.println("Transfer of $" + amount + " successful");
                        return true;
                    } else {
                    System.out.println("Transfer of $" + amount + " failed");
                    return false;
                }

                case 2:
                    if (cust.getCreditAcc().equals(acc)) {  //Ensuring they aren't the same type
                        System.out.println("Cannot transfer to same account type, (Credit)");
                        logTrans("fTransfer", cust.getfName() + " " + cust.getlName(), "", amount, "Credit-" + acc.getAccNumber(), acc.getStartBalance(), "Credit-" + acc.getAccNumber(), 0.0);
                        return false;
                    } else if (amount > 0 && startBalance >= amount) {
                        startBalance -= amount;
                        cust.getCreditAcc().setStartBalance(cust.getCreditAcc().getStartBalance() + amount);
                        System.out.println("Transfer of $" + amount + " successful");
                        return true;
                    } else {
                        System.out.println("Transfer of $" + amount + " failed");
                        return false;
                    }
            }
            System.out.println("No Appropriate account type was selected");
            return false;
        }

    /**
     * TransferPart1(Overload)
     * @param amount
     * @param type
     * @param CreditBalance
     * @return
     * @throws IOException
     */
    public boolean transferPart1(double amount, String type, double CreditBalance) throws IOException {       //p1 simply withdraws from the original account
        if(type.equalsIgnoreCase("Credit")){
            //System.out.println("hello type credit is acknowledged in TransferPrt1");
            if (amount > (CreditBalance * -1) ){
                System.out.println("Failed Credit-Balance Transfer Requirements");
                return false;
            }
        }
        if(amount > 0 && startBalance >= amount){
            startBalance -= amount;
            System.out.println("Transfer of $" + amount + " successful");
            return true;
        }
        else
            System.out.println("Transfer of $" + amount + " failed");
        return false;
    }

    /**
     * TransferPart2
     * @param amount
     * @return
     */
    public boolean transferPart2(double amount){
            startBalance += amount;
            return true;
        }

    /**
     * This method logs all activity the gets executed during the current runtime.
     * @param subj
     * @param flNames
     * @param otherPerson
     * @param amount
     * @param selfTypeAndAccNumb
     * @param selfBalanceNew
     * @param otherTypeAndAccNum
     * @param otherBalanceNew
     * @throws IOException
     */
    public void logTrans(String subj, String flNames, String otherPerson, double amount, String selfTypeAndAccNumb, double selfBalanceNew, String otherTypeAndAccNum, double otherBalanceNew) throws IOException {
        FileWriter tmpWriter = new FileWriter("loggedTransaction.txt", true);
        if(subj == "paySomeone"){
            tmpWriter.write(flNames + " paid "+ otherPerson + " $" + amount + " " + " from " + selfTypeAndAccNumb + ". " + flNames + "'s new balance for " + selfTypeAndAccNumb + ": $"+ selfBalanceNew  +"\n");
            tmpWriter.write(otherPerson + " received "+ " $" + amount + " from "+ flNames+". "+otherPerson+"'s new balance for " + otherTypeAndAccNum + ": $" + otherBalanceNew  +"\n");
            tmpWriter.close();
        }
        else if(subj == "fPaySomeone"){
            tmpWriter.write( " failed to pay someone " + otherPerson);
            tmpWriter.close();
        }
        else if(subj == "deposit"){
            tmpWriter.write(flNames + " Deposited $" + amount + " in "+ selfTypeAndAccNumb + "\n");
            tmpWriter.close();
        }
        else if(subj == "withdraw"){
            tmpWriter.write(flNames + " did a withdraw of $" + amount + " in " + selfTypeAndAccNumb + "\n");
            tmpWriter.close();
        }
        else if(subj == "fWithdraw"){
            tmpWriter.write(flNames + " failed to withdraw $" + amount + "\n");
            tmpWriter.close();
        }
        else if(subj == "transfer"){
            tmpWriter.write(flNames + " transferred $" + amount +" from "+selfTypeAndAccNumb+" to "+ otherTypeAndAccNum+". "+flNames+"'s balance for "+ selfTypeAndAccNumb+": $"+selfBalanceNew+" "+flNames+"'s balance for "+otherTypeAndAccNum+": $"+otherBalanceNew + "\n");
            tmpWriter.close();
        }
        else if(subj == "fTransfer"){
            tmpWriter.write(flNames + " failed to transfer $" + amount + " from "+ selfTypeAndAccNumb + " to " + otherTypeAndAccNum + "\n");
            tmpWriter.close();
        }
        else if(subj == "inquire"){
            tmpWriter.write(flNames + " made a balance inquiry on " + selfTypeAndAccNumb + ". " + flNames + "'s balance for " + selfTypeAndAccNumb + " " + selfBalanceNew +"\n");
            tmpWriter.close();
        }
        else{
            System.out.println("logging failed");
        }
        tmpWriter.close();
    }
}
