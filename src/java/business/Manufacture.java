
package business;

import java.io.Serializable;

public class Manufacture implements Serializable {
    private int manufactureID;
    private String manufactureName;
    
    public Manufacture(){
        
    }

    public int getManufactureID() {
        return manufactureID;
    }

    public void setManufactureID(int manufactureID) {
        this.manufactureID = manufactureID;
    }

    public String getManufactureName() {
        return manufactureName;
    }

    public void setManufactureName(String manufactureName) {
        this.manufactureName = manufactureName;
    }
    
}
