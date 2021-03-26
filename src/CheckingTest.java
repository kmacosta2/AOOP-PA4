
import java.io.IOException;

import static org.junit.Assert.*;

public class CheckingTest {

    Checking obj1 = new Checking(1000,890.56);

    public CheckingTest() throws IOException {
    }

    @org.junit.Test
    public void getAccNumber() {
    }

    @org.junit.Test
    public void testDeposit_checkTrueCond() throws IOException {
        boolean expected = true;
        assertEquals(expected, obj1.deposit(56));
    }

    @org.junit.Test
    public void testWithdraw_checkFalseCond() throws IOException {
        boolean expected = false;
        assertEquals(expected, obj1.withdraw(891));
    }

//    @org.junit.Test
//    public void testTransferPart1_checkFalseCond() throws IOException {
//        boolean expected = false;
//        assertEquals(expected, obj1.transferPart1(39,2,0.0));
//    }

    @org.junit.Test
    public void testTransferPart2_checkFalseCond() {
        boolean expected = true;
        assertEquals(expected, obj1.transferPart2(32));
    }
}
