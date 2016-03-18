package business;

import java.io.Serializable;

public class EmailKey implements Serializable {

    private int keyID;
    private String customerEmail;

    public EmailKey() {
        keyID = 0;
        customerEmail = "";
    }

    public EmailKey(int keyID, String customerEmail) {
        this.keyID = keyID;
        this.customerEmail = customerEmail;
    }

    public void setKeyID(int keyID) {
        this.keyID = keyID;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public int getKeyID() {
        return keyID;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

}
