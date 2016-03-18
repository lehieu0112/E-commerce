package data;

import util.DBUtil;
import util.SqlUtil;
import business.Products;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

public class ProductDB {

    public int getListPages(int productsPerPage, int choose) {
        int pages = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select count(*)/" + productsPerPage + " as pages,"
                + "count(*)%" + productsPerPage + " as lastPage"
                + " from Products";
        String sqlString1 = "select count(*)/" + productsPerPage + " as pages,"
                + "count(*)%" + productsPerPage + " as lastPage"
                + " from Products join"
                + " (select productID, sum(lineItemTotal)"
                + " as productSelling from LineItem group by productID)"
                + " as SellingTable on Products.productID = SellingTable.productID"
                + " order by SellingTable.productSelling desc";
        if (choose == 1) {
            sqlString = sqlString1;
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sqlString);
            rs = ps.executeQuery();
            while (rs.next()) {
                pages = rs.getInt("pages");
                int lastPage = rs.getInt("lastPage");
                if (lastPage != 0) {
                    pages = pages + 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(con);
        }
        if (pages != 0) {
            pages = pages - 1;
        }
        return pages;
    }

    public ArrayList<Products> getProductsList(int page, int productsPerPage, int choose) {
        ArrayList<Products> productsList = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String offset = " offset " + (page * productsPerPage) + " rows"
                + " fetch next " + productsPerPage + " rows only";
        String sqlString = "select * from Products order by productPrice asc";
        String sqlString1 = "select * from Products order by productPrice desc";
        String sqlString2 = "select * from Products order by modelYear desc";
        String sqlString3 = "select Products.* from Products join"
                + " (select productID, sum(lineItemTotal)"
                + " as productSelling from LineItem group by productID)"
                + " as SellingTable on Products.productID = SellingTable.productID"
                + " order by SellingTable.productSelling desc";
        String sqlString4 = "select Products.* from Products join"
                + " (select promotionID, startDay"
                + " from Promotions) as PromotionTable"
                + " on Products.promotionID = PromotionTable.promotionID"
                + " order by PromotionTable.startDay desc";
        switch (choose) {
            case 1:
                sqlString = sqlString1;
                break;
            case 2:
                sqlString = sqlString2;
                break;
            case 3:
                sqlString = sqlString3;
                break;
            case 4:
                sqlString = sqlString4;
                break;
            default:
                break;
        }
        sqlString = sqlString + offset;

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sqlString);
            rs = ps.executeQuery();
            Products product;
            while (rs.next()) {
                product = new Products();
                product.setProductID(rs.getInt("productID"));
                product.setCategoryID(rs.getInt("categoryID"));
                product.setManufactureID(rs.getInt("manufactureID"));
                product.setProductName(rs.getString("productName"));
                product.setProductDescription(rs.getString("productDescription"));
                product.setProductPrice(rs.getDouble("productPrice"));
                product.setModelYear(rs.getInt("modelYear"));
                product.setQuantityInStore(rs.getInt("quantityInStore"));
                product.setProductWarranty(rs.getInt("productWarranty"));
                product.setPromotionID(rs.getInt("promotionID"));
                product.setPictureLink(rs.getString("pictureLink"));

                productsList.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(con);
        }
        return productsList;
    }

    public Products getProduct(int productID) {
        Products product = new Products();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select * from Products where productID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, productID);
            rs = ps.executeQuery();
            while (rs.next()) {
                product.setProductID(rs.getInt("productID"));
                product.setCategoryID(rs.getInt("categoryID"));
                product.setManufactureID(rs.getInt("manufactureID"));
                product.setProductName(rs.getString("productName"));
                product.setProductDescription(rs.getString("productDescription"));
                product.setProductPrice(rs.getDouble("productPrice"));
                product.setModelYear(rs.getInt("modelYear"));
                product.setQuantityInStore(rs.getInt("quantityInStore"));
                product.setProductWarranty(rs.getInt("productWarranty"));
                product.setPromotionID(rs.getInt("promotionID"));
                product.setPictureLink(rs.getString("pictureLink"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(con);
        }
        return product;
    }

    public int getSearchPages(String name, String manufacture, String category,
            int productsPerPage, int choose) {
        int pages = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sql = SqlUtil.getSearchPagesSqlString(name, category, manufacture, productsPerPage, choose);
        Statement s = null;
        ResultSet rs = null;
        try {
            s = con.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                pages = rs.getInt("pages");
                int lastPage = rs.getInt("lastPage");
                if (lastPage != 0) {
                    pages = pages + 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeStatement(s);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(con);
        }
        if (pages != 0) {
            pages = pages - 1;
        }
        return pages;
    }

    public ArrayList<Products> searchProducts(String name, String manufacture,
            String category, int page, int productsPerPage, int choose) {
        ArrayList<Products> productsList = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sql = SqlUtil.getSearchProductsSqlString(name, category, manufacture, page, productsPerPage, choose);

        Statement s = null;
        ResultSet rs = null;
        try {
            s = con.createStatement();
            rs = s.executeQuery(sql);
            Products product;
            while (rs.next()) {
                product = new Products();
                product.setProductID(rs.getInt("productID"));
                product.setCategoryID(rs.getInt("categoryID"));
                product.setManufactureID(rs.getInt("manufactureID"));
                product.setProductName(rs.getString("productName"));
                product.setProductDescription(rs.getString("productDescription"));
                product.setProductPrice(rs.getDouble("productPrice"));
                product.setModelYear(rs.getInt("modelYear"));
                product.setQuantityInStore(rs.getInt("quantityInStore"));
                product.setProductWarranty(rs.getInt("productWarranty"));
                product.setPromotionID(rs.getInt("promotionID"));
                product.setPictureLink(rs.getString("pictureLink"));

                productsList.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeStatement(s);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(con);
        }
        return productsList;
    }

    public boolean insertProduct(Products product) {
        boolean insert = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "insert into Products"
                + "(productID,categoryID,manufactureID,productName,"
                + "productDescription,productPrice,modelYear,quantityInStore,"
                + "productWarranty,promotionID,pictureLink)"
                + " values(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, product.getProductID());
            ps.setInt(2, product.getCategoryID());
            ps.setInt(3, product.getManufactureID());
            ps.setString(4, product.getProductName());
            ps.setString(5, product.getProductDescription());
            ps.setDouble(6, product.getProductPrice());
            ps.setInt(7, product.getModelYear());
            ps.setInt(8, product.getQuantityInStore());
            ps.setInt(9, product.getProductWarranty());
            ps.setInt(10, product.getPromotionID());
            ps.setString(11, product.getPictureLink());
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

    public boolean updateQuantityInStore(int productID, int quantity) {
        boolean update = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "update Products set quantityInStore ="
                + " (quantityInStore - (?)) where productID = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, quantity);
            ps.setInt(2, productID);
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

    public boolean checkQuantityInStore(int productID, int quantity) {
        boolean isHave = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select quantityInStore from Products where productID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, productID);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("quantityInStore") >= quantity) {
                    isHave = true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(con);
        }
        return isHave;
    }
    
    public boolean updateProduct(Products product,int productID) {
        boolean update = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "update Products set categoryID="+product.getCategoryID()+","
                + "manufactureID="+product.getManufactureID()+","
                + "productName='"+product.getProductName()+"',"
                + "productDescription=N'"+product.getProductDescription()+"',"
                + "productPrice="+product.getProductPrice()+","
                + "modelYear="+product.getModelYear()+","
                + "quantityInStore="+product.getQuantityInStore()+","
                + "productWarranty="+product.getProductWarranty()+","
                + "promotionID="+product.getPromotionID()+","
                + "pictureLink='"+product.getPictureLink()+"'"
                + " where productID="+productID+"";
        Statement s = null;
        try {
            s = con.createStatement();            
            s.executeUpdate(sqlString);
            update = true;

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeStatement(s);
            pool.freeConnection(con);
        }
        return update;
    }
    
    public boolean deleteProduct(int productID) {
        boolean delete = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "delete from Products where productID=?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, productID);          
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

}
