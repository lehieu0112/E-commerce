
package business;

import java.io.Serializable;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Invoices implements Serializable{
    private int invoiceID;
    private Customer customer;   
    private ArrayList<LineItem> items;
    private Date invoiceDate;
    private String shippingProcess;
    private Date shippingDate;
    private boolean isPaid;
    private double invoiceTotal;
    
    public Invoices(){
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getShippingProcess() {
        return shippingProcess;
    }

    public void setShippingProcess(String shippingProcess) {
        this.shippingProcess = shippingProcess;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }      
    
    public double getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }
    
    public String getFormatInvoiceTotal(){
        NumberFormat c = NumberFormat.getNumberInstance();
        return c.format(this.invoiceTotal);
    }
    
    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<LineItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<LineItem> items) {
        this.items = items;
    }

}
