package business;

import java.io.Serializable;

public class User implements Serializable {

    private int userID;
    private String userName;
    private String userPass;
    private String userRole;
    private boolean isActive;

    public User() {
        userID = 0;
        userName = "";
        userPass = "";
        userRole = "";
        isActive = false;
    }

    public User(String userName, String userPass, String userRole, boolean isActive) {
        this.userName = userName;
        this.userPass = userPass;
        this.userRole = userRole;
        this.isActive = isActive;
    }

    public User(int userID, String userName, String userPass, String userRole, boolean isActive) {
        this.userID = userID;
        this.userName = userName;
        this.userPass = userPass;
        this.userRole = userRole;
        this.isActive = isActive;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public String getUserRole() {
        return userRole;
    }

    public boolean getIsActive() {
        return isActive;
    }

}
