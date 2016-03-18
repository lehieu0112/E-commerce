
package business;

import data.PromotionDB;
import java.io.Serializable;
import java.text.NumberFormat;

public class LineItem implements Serializable{
    private int invoiceID;
    private Products product;
    private int quantity;
    private double lineItemAmount;
    
    public LineItem(){
    }
    
    public int getPromotionDiscount(){
        int promotionDiscount = 0;
        PromotionDB proDB = new PromotionDB();
        Promotions promotion = proDB.getPromotion(this.product.getPromotionID());
        promotionDiscount = promotion.getPromotionDiscount();
        return promotionDiscount;
    }
        
    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }
    
    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getLineItemAmount() {
        return lineItemAmount;
    }

    public void setLineItemAmount(double lineItemAmount) {
        this.lineItemAmount = lineItemAmount;
    }
    
    public String getFormatLineItemAmount(){
        NumberFormat c = NumberFormat.getNumberInstance();
        return c.format(this.lineItemAmount);
    }
    
}
