import java.io.IOException;

/**
 * Credit class, one of three account types
 */
public class Credit extends Account{

    double maxCred; // Newly introduced, im simply going to add it here, via constructor

    /**
     * Credit constructor
     * @throws IOException
     */
    public Credit() throws IOException{
    }

    /**
     * Alternate Constructor
     * @param credAccNumIn
     * @param credStartBalanceIn
     */
    public Credit(int credAccNumIn, double credStartBalanceIn, int maxCreditIn){
        super(credAccNumIn, credStartBalanceIn);
        this.maxCred = maxCreditIn;
    }

    public double getMaxCred() {
        return maxCred;
    }

    public void setMaxCred(double maxCred) {
        this.maxCred = maxCred;
    }

    @Override
    public void inquireBalance(){
        System.out.println("Balance: " + getStartBalance() + "\nMax Credit: " + getMaxCred());
    }
}
