package business;

import java.io.Serializable;
import java.sql.Date;

public class Promotions implements Serializable {

    private int promotionID;
    private String promotionName;
    private String promotionDescription;
    private int promotionDiscount;
    private Date startDay;
    private Date endDay;
    private String pictureLink;

    public Promotions() {

    }

    public Promotions(String promotionName, String promotionDescription,
            Date startDay, Date endDay, String pictureLink, int promotionDiscount) {
        this.promotionName = promotionName;
        this.promotionDescription = promotionDescription;
        this.startDay = startDay;
        this.endDay = endDay;
        this.pictureLink = pictureLink;
        this.promotionDiscount = promotionDiscount;
        this.promotionID = 0;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public void setPromotionDiscount(int promotionDiscount) {
        this.promotionDiscount = promotionDiscount;
    }

    public int getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(int promotionID) {
        this.promotionID = promotionID;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionDescription() {
        return promotionDescription;
    }

    public void setPromotionDescription(String promotionDescription) {
        this.promotionDescription = promotionDescription;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }
}
