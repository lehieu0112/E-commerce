package business;

import java.io.Serializable;

public class Customer implements Serializable {

    private int customerID;
    private String customerName;
    private String customerEmail;
    private String customerPassword;
    private String customerAddress;
    private String customerPhone;
    private String customerNote;
    private boolean isActive;
    private String avatarLink;

    public Customer() {
        customerID = 0;
        customerName = "";
        customerEmail = "";
        customerPassword = "";
        customerAddress = "";
        customerPhone = "";
        customerNote = "";
        avatarLink = "/images/avatar_profile/avatar.png";
        isActive = false;
    }

    public Customer(String customerName, String customerEmail, String customerPassword,
            String customerAddress, String customerPhone, String customerNote, String avatarLink, boolean isActive) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.customerNote = customerNote;
        this.avatarLink = avatarLink;
        this.isActive = isActive;
    }

    public Customer(String customerName, String customerEmail, String customerPassword,
            String customerAddress, String customerPhone, String customerNote, boolean isActive) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.customerNote = customerNote;
        this.avatarLink = "/images/avatar_profile/avatar.png";
        this.isActive = isActive;
    }
    
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setCustomerNote(String customerNote) {
        this.customerNote = customerNote;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

}
