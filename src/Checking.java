import java.io.IOException;

/**
 * Checking class, one of three account types
 */
public class Checking extends Account{
    /**
     * Checking constructor
     * @throws IOException
     */
    public Checking() throws IOException {
    }

    /**
     * Secondary Checking Constructor
     * @param accNum
     * @param balance
     */
    public Checking(int accNum, double balance){
        super(accNum, balance);
    }


}
