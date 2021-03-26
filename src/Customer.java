import java.beans.Customizer;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This is the customer class, which serves to access all other account types more specifically.
 */
public class Customer extends Person{
    Checking checkingAcc;
    Savings savingsAcc;
    Credit creditAcc;
    ArrayList<String> activities = new ArrayList<>();

    /**
     * Constructor customer
     */
    public void Customer() {
    }

    /**Overriding Person Methods for Bank Manager
     *
     */
    @Override
    public void getEverything(){
        super.getEverything();
    }

    /**
     * getCheckingAcc
     * @return
     */
    public Checking getCheckingAcc() {
        return checkingAcc;
    }

    /**
     * setCheckingAcc
     * @param checkingAcc
     */
    public void setCheckingAcc(Checking checkingAcc) {
        this.checkingAcc = checkingAcc;
    }

    /**
     * getSavingsAcc
     * @return
     */
    public Savings getSavingsAcc() {
        return savingsAcc;
    }

    /**
     * setSavingsAcc
     * @param savingsAcc
     */
    public void setSavingsAcc(Savings savingsAcc) {
        this.savingsAcc = savingsAcc;
    }

    /**
     * getCreditAcc
     * @return
     */
    public Credit getCreditAcc() {
        return creditAcc;
    }

    /**
     * setCreditAcc
     * @param creditAcc
     */
    public void setCreditAcc(Credit creditAcc) {
        this.creditAcc = creditAcc;
    }

    public ArrayList getActivity(){
        return activities;
    }
    //the list only is to be stored in customer
    public void addActivities(String act){
        activities.add(act);
    }
    /*Customer custom
    public void logTransacReader() throws IOException {
        FileWriter tmpWrite = new FileWriter("loggedTransactionReader.txt", true);
        tmpWrite.write("500 W University Ave, El Paso, TX 79968\n\n");
        tmpWrite.write(getfName() + " " + getlName()+"\n");
        String addr = getAddress();
        tmpWrite.write(addr + "\n");
        tmpWrite.write("\tThank you for choosing our bank!"+"\n\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.write("Summary of your accounts\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.write("\t\tCHECKING-" + getCheckingAcc().getAccNumber() + "\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.write("BALANCE " + getCheckingAcc().getStartBalance() + "\n\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.write("\t\tSAVINGS-" + getSavingsAcc().getAccNumber() + "\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.write("BALANCE " + getCheckingAcc().getStartBalance() + "\n\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.write("\t\tCREDIT-" + getCreditAcc().getAccNumber() + "\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.write("BALANCE " + getCheckingAcc().getStartBalance() + "\n");
        tmpWrite.write("CREDIT MAX " + getCreditAcc().getMaxCred() + "\n");
        tmpWrite.write("---------------------------------------\n");
        tmpWrite.close();
    }

     */

}
