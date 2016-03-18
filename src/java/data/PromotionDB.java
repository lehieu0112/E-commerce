package data;

import business.Promotions;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PromotionDB {

    public boolean insertPromotion(Promotions promotion) {
        boolean insert = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "insert into Promotions"
                + "(promotionID,promotionName,promotionDescription,"
                + "startDay,endDay,pictureLink,promotionDiscount)"
                + " values(?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, promotion.getPromotionID());
            ps.setString(2, promotion.getPromotionName());
            ps.setString(3, promotion.getPromotionDescription());
            ps.setDate(4, promotion.getStartDay());
            ps.setDate(5, promotion.getEndDay());
            ps.setString(6, promotion.getPictureLink());
            ps.setInt(7, promotion.getPromotionDiscount());
            ps.executeUpdate();
            insert = true;

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return insert;
    }

    public boolean updatePromotion(Promotions promotion, int promotionID) {
        boolean update = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "update Promotions set promotionName=?,"
                + "promotionDescription=?,startDay=?,endDay=?,"
                + "pictureLink=?,promotionDiscount=? where promotionID=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setString(1, promotion.getPromotionName());
            ps.setString(2, promotion.getPromotionDescription());
            ps.setDate(3, promotion.getStartDay());
            ps.setDate(4, promotion.getEndDay());
            ps.setString(5, promotion.getPictureLink());
            ps.setInt(6, promotion.getPromotionDiscount());
            ps.setInt(7, promotionID);
            ps.executeUpdate();
            update = true;

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return update;
    }

    public boolean deletePromotion(int promotionID) {
        boolean delete = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "delete from Promotions where promotionID=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, promotionID);
            ps.executeUpdate();
            delete = true;

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return delete;
    }

    public ArrayList<Promotions> getPromotionList() {
        ArrayList<Promotions> promotionsList = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select * from Promotions";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sqlString);
            rs = ps.executeQuery();
            Promotions promotion;
            while (rs.next()) {
                promotion = new Promotions();
                promotion.setPromotionID(rs.getInt("promotionID"));
                promotion.setPromotionName(rs.getString("promotionName"));
                promotion.setPromotionDescription(rs.getString("promotionDescription"));
                promotion.setStartDay(rs.getDate("startDay"));
                promotion.setEndDay(rs.getDate("endDay"));
                promotion.setPictureLink(rs.getString("pictureLink"));
                promotion.setPromotionDiscount(rs.getInt("promotionDiscount"));
                promotionsList.add(promotion);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(con);
        }
        return promotionsList;
    }
    
    public Promotions getPromotion(int promotionID) {
        Promotions promotion = new Promotions();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select * from Promotions where promotionID=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, promotionID);
            rs = ps.executeQuery();
            while (rs.next()) {
                promotion.setPromotionID(rs.getInt("promotionID"));
                promotion.setPromotionName(rs.getString("promotionName"));
                promotion.setPromotionDescription(rs.getString("promotionDescription"));
                promotion.setStartDay(rs.getDate("startDay"));
                promotion.setEndDay(rs.getDate("endDay"));
                promotion.setPictureLink(rs.getString("pictureLink"));
                promotion.setPromotionDiscount(rs.getInt("promotionDiscount"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(con);
        }
        return promotion;
    }
}
