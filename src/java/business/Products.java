package business;

import data.PromotionDB;
import java.io.Serializable;
import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;

public class Products implements Serializable {

    private int productID;
    private int categoryID;
    private int manufactureID;
    private String productName;
    private String productDescription;
    private double productPrice;
    private int modelYear;
    private int quantityInStore;
    private int productWarranty;
    private int promotionID;
    private String pictureLink;

    public Products() {

    }

    public Products(int categoryID, int manufactureID, String productName,
            String productDescription, double productPrice, int modelYear,
            int quantityInStore, int productWarranty, int promotionID,
            String pictureLink) {
        this.categoryID = categoryID;
        this.manufactureID = manufactureID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.modelYear = modelYear;
        this.quantityInStore = quantityInStore;
        this.productWarranty = productWarranty;
        this.promotionID = promotionID;
        this.pictureLink = pictureLink;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getManufactureID() {
        return manufactureID;
    }

    public void setManufactureID(int manufactureID) {
        this.manufactureID = manufactureID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public double getPromotionPrice() {
        double promotionPrice = this.productPrice;
        PromotionDB proDB = new PromotionDB();
        LocalDate currentDate = LocalDate.now();
        Date date = Date.valueOf(currentDate);
        Promotions promotion = proDB.getPromotion(this.promotionID);
        if ((date.before(promotion.getEndDay()))&&(date.after(promotion.getStartDay())) ){
            promotionPrice = this.productPrice - ((this.productPrice * promotion.getPromotionDiscount()) / 100);
        }
        return promotionPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public String getFormatProductPrice() {
        NumberFormat c = NumberFormat.getNumberInstance();
        return c.format(this.productPrice);
    }

    public String getFormatPromotionPrice() {
        NumberFormat c = NumberFormat.getNumberInstance();
        return c.format(getPromotionPrice());
    }

    public int getQuantityInStore() {
        return quantityInStore;
    }

    public void setQuantityInStore(int quantityInStore) {
        this.quantityInStore = quantityInStore;
    }

    public int getProductWarranty() {
        return productWarranty;
    }

    public void setProductWarranty(int productWarranty) {
        this.productWarranty = productWarranty;
    }

    public int getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(int promotionID) {
        this.promotionID = promotionID;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

}
