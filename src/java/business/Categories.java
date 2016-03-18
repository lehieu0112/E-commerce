
package business;

import java.io.Serializable;

public class Categories implements Serializable{
    private int categoryID;
    private String categoryName;
    
    public Categories(){
        
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
