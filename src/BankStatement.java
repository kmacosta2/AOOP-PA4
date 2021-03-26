import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

//extends Customer
public class BankStatement {
    //Needs to store customer's personal info, Account(s) information, all transactions for this customer
    double startingBalance;
    //at the end/the time requested
    double endingBalance;
    String dateOfTransaction;
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    //System.out.println(formatter.format(date));
    //ex. 05-02-2021 10:12:46


//, String flName, int accNum
    public static void logTransacReader(Customer cust) throws IOException { //somehow if inside do an if else in order to exclude when they dont have a checking/credit account

        String fullName = cust.getfName() + " " + cust.getlName();
        FileWriter tmpWrite = new FileWriter(fullName+"Statement.txt", true);
        tmpWrite.write("500 W University Ave, El Paso, TX 79968\n\n");
        tmpWrite.write(cust.getfName() + " " + cust.getlName()+"\n");
        String addr = cust.getAddress();
        tmpWrite.write(addr + "\n");
        tmpWrite.write("\tThank you for choosing our bank!"+"\n\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.write("Summary of your accounts\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.write("\t\tCHECKING-" + cust.getCheckingAcc().getAccNumber() + "\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.write("BALANCE " + cust.getCheckingAcc().getStartBalance() + "\n\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.write("\t\tSAVINGS-" + cust.getSavingsAcc().getAccNumber() + "\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.write("BALANCE " + cust.getCheckingAcc().getStartBalance() + "\n\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.write("\t\tCREDIT-" + cust.getCreditAcc().getAccNumber() + "\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.write("BALANCE " + cust.getCheckingAcc().getStartBalance() + "\n");
        tmpWrite.write("CREDIT MAX " + cust.getCreditAcc().getMaxCred() + "\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.write("\t\tRecent activity\n");
        ArrayList<String> actions = cust.getActivity();
        for (String acts : actions) {
            tmpWrite.write(acts + "\n");
        }
        tmpWrite.close();
    }
}
