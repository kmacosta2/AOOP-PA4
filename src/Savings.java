import java.io.IOException;

/**
 * Savings class, one of threes account types
 */
public class Savings extends Account{
    /**
     * Savings Constructor
     * @throws IOException
     */
    public Savings() throws IOException {
    }

    /**
     * Alternate savings constructor
     * @param accNumIn
     * @param balanceIn
     */
    public Savings(int accNumIn, double balanceIn){
        super(accNumIn, balanceIn);
    }

}
