import java.util.ArrayList;

/**
 * My person class which contains all common attributes
 */
public abstract class Person {
    public String fName;
    public String lName;

    private String DOB;
    private String identNumber;
    private String Address;
    private String phoneNumber;

    /**
     * Person Constructor
     */
    public Person(){
    }

    /**
     * get Name
     * @return
     */
    public String getfName() {
        return fName;
    }

    /**
     *setfName
     * @param fNameIn
     */
    public void setfName(String fNameIn) {
        this.fName = fNameIn;
    }

    /**
     *getlName
     * @return
     */
    public String getlName() {
        return lName;
    }

    /**
     *setlName
     * @param lNameIn
     */
    public void setlName(String lNameIn) {
        this.lName = lNameIn;
    }

    /**
     *getDOB
     * @return
     */
    public String getDOB() {
        return DOB;
    }

    /**
     *setDOB
     * @param DOB
     */
    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    /**
     *getIdentNumber
     * @return
     */
    public String getIdentNumber() {
        return identNumber;
    }

    /**
     *setIdentNumber
     * @param identNumber
     */
    public void setIdentNumber(String identNumber) {
        this.identNumber = identNumber;
    }

    /**
     * getAddress
     * @return
     */
    public String getAddress() {
        return Address;
    }

    /**
     * getPhoneNUmber
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * setPhoneNUmber
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        Address = address;
    }

    /**
     * The manager's added privilege of access
     */
    public void getEverything(){
        System.out.println("Full Name: " + getfName() + " " + getlName() + "\nDate of Birth: " + getDOB() + "\nIdentification Number: " + getIdentNumber() +
                "\nAddress: " + getAddress() + "\nPhone Number: " + getPhoneNumber());
    }
}
