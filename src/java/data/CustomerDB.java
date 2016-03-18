package data;

import business.ChangeableString;
import util.DBUtil;
import business.Customer;
import business.EmailKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerDB {

    public boolean insertCustomer(Customer c) {
        boolean insert = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        Statement s;
        ResultSet rs = null;
        String sql = "insert into Customers(customerName, customerEmail, customerPassword, "
                + "customerAddress, customerPhone, customerNote, isActive,avatarLink) values(?, ?, ?, ?, ?, ?, ?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getCustomerName());
            ps.setString(2, c.getCustomerEmail());
            ps.setString(3, c.getCustomerPassword());
            ps.setString(4, c.getCustomerAddress());
            ps.setString(5, c.getCustomerPhone());
            ps.setString(6, c.getCustomerNote());
            ps.setBoolean(7, c.isIsActive());
            ps.setString(8, c.getAvatarLink());
            ps.executeUpdate();

            insert = true;

            String identityQuery = "select @@IDENTITY as customerID";
            s = con.createStatement();
            rs = s.executeQuery(identityQuery);
            rs.next();
            int customerID = rs.getInt("customerID");
            rs.close();
            s.close();
            c.setCustomerID(customerID);

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return insert;
    }

    public boolean isActive(String customerEmail) {
        boolean active = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "update Customers set isActive = 1 where customerEmail = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, customerEmail);
            ps.executeUpdate();
            active = true;
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return active;
    }

    public boolean deActive(String customerEmail) {
        boolean deactive = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "update Customers set isActive = 0 where customerEmail = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, customerEmail);
            ps.executeUpdate();
            deactive = true;
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return deactive;
    }

    public boolean updateCustomerForEmail(Customer c, String customerEmail) {
        boolean update = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "update Customers set customerName = ?, customerEmail = ?, "
                + "customerPassword= ?, customerAddress = ?, customerPhone = ?,"
                + " customerNote = ?, avatarLink = ? where customerEmail = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getCustomerName());
            ps.setString(2, c.getCustomerEmail());
            ps.setString(3, c.getCustomerPassword());
            ps.setString(4, c.getCustomerAddress());
            ps.setString(5, c.getCustomerPhone());
            ps.setString(6, c.getCustomerNote());
            ps.setString(7, c.getAvatarLink());
            ps.setString(8, customerEmail);
            ps.executeUpdate();
            update = true;
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return update;
    }

    public boolean updateCustomerForEmailForManager(Customer c, String customerEmail) {
        boolean update = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "update Customers set customerName = ?, customerEmail = ?, "
                + "customerPassword= ?, customerAddress = ?, customerPhone = ?,"
                + " customerNote = ?, isActive = ?, avatarLink = ? where customerEmail = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getCustomerName());
            ps.setString(2, c.getCustomerEmail());
            ps.setString(3, c.getCustomerPassword());
            ps.setString(4, c.getCustomerAddress());
            ps.setString(5, c.getCustomerPhone());
            ps.setString(6, c.getCustomerNote());
            ps.setBoolean(7, c.isIsActive());
            ps.setString(8, c.getAvatarLink());
            ps.setString(9, customerEmail);
            ps.executeUpdate();
            update = true;
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return update;
    }

    public boolean updateCustomerPassword(String customerEmail,
            String customerPassword) {
        boolean update = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "update Customers set customerPassword = ?"
                + " where customerEmail = ? and isActive = 1";
        try {
            ps = con.prepareStatement(sql);
            Customer c = new Customer();
            ps.setString(1, customerPassword);
            ps.setString(2, customerEmail);
            ps.executeUpdate();
            update = true;
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return update;
    }

    public ArrayList<Customer> getCustomers(int page, int customersPerPage) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Customer> customerList = new ArrayList<>();
        String sql = "select customerName, customerEmail, customerPassword, customerAddress, "
                + "customerPhone, customerNote, isActive, avatarLink from Customers order by customerName asc"
                + " offset " + (page * customersPerPage) + " rows fetch next " + customersPerPage + " rows only";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerName(rs.getString("customerName"));
                c.setCustomerEmail(rs.getString("customerEmail"));
                c.setCustomerPassword(rs.getString("customerPassword"));
                c.setCustomerAddress(rs.getString("customerAddress"));
                c.setCustomerPhone(rs.getString("customerPhone"));
                c.setCustomerNote(rs.getString("customerNote"));
                c.setIsActive(rs.getBoolean("isActive"));
                c.setAvatarLink(rs.getString("avatarLink"));
                customerList.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return customerList;
    }

    public int getSearchPage(String text, int customersPerPage) {
        int pages = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select count(*)/" + customersPerPage + " as pages, "
                + "count(*)%" + customersPerPage + " as lastPage"
                + " from Customers where customerName like '%" + text + "%'";

        try {
            ps = con.prepareStatement(sql);
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
            DBUtil.closeStatement(ps);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(con);
        }
        if (pages != 0) {
            pages = pages - 1;
        }
        return pages;
    }

    public ArrayList<Customer> searchCustomer(String text, int page, int customersPerPage) {
        ArrayList<Customer> customerList = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from Customers where customerName like '%" + text + "%'"
                + " order by customerName asc "
                + " offset " + (page * customersPerPage) + " rows fetch next " + customersPerPage + " rows only";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getInt("customerID"));
                c.setCustomerName(rs.getString("customerName"));
                c.setCustomerEmail(rs.getString("customerEmail"));
                c.setCustomerPassword(rs.getString("customerPassword"));
                c.setCustomerAddress(rs.getString("customerAddress"));
                c.setCustomerPhone(rs.getString("customerPhone"));
                c.setCustomerNote(rs.getString("customerNote"));
                c.setIsActive(rs.getBoolean("isActive"));
                c.setAvatarLink(rs.getString("avatarLink"));
                customerList.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return customerList;
    }

    public Customer getCustomer(int customerID) {
        Customer c = new Customer();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from Customers where customerID = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, customerID);
            rs = ps.executeQuery();
            while (rs.next()) {
                c.setCustomerID(rs.getInt("customerID"));
                c.setCustomerName(rs.getString("customerName"));
                c.setCustomerEmail(rs.getString("customerEmail"));
                c.setCustomerPassword(rs.getString("customerPassword"));
                c.setCustomerAddress(rs.getString("customerAddress"));
                c.setCustomerPhone(rs.getString("customerPhone"));
                c.setCustomerNote(rs.getString("customerNote"));
                c.setIsActive(rs.getBoolean("isActive"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return c;
    }

    public Customer customerDetail(String customerEmail) {
        Customer c = new Customer();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from Customers where customerEmail = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, customerEmail);
            rs = ps.executeQuery();
            while (rs.next()) {
                c.setCustomerID(rs.getInt("customerID"));
                c.setCustomerName(rs.getString("customerName"));
                c.setCustomerEmail(rs.getString("customerEmail"));
                c.setCustomerPassword(rs.getString("customerPassword"));
                c.setCustomerAddress(rs.getString("customerAddress"));
                c.setCustomerPhone(rs.getString("customerPhone"));
                c.setCustomerNote(rs.getString("customerNote"));
                c.setIsActive(rs.getBoolean("isActive"));
                c.setAvatarLink(rs.getString("avatarLink"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return c;
    }

    public ArrayList<Customer> searchCustomer(String text) {
        ArrayList<Customer> customerList = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from Customers where customerName"
                + " like '%" + text + "%' or customerEmail like '%" + text + "%'"
                + " or customerAddress like '%" + text + "%'";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getInt("customerID"));
                c.setCustomerName(rs.getString("customerName"));
                c.setCustomerEmail(rs.getString("customerEmail"));
                c.setCustomerPassword(rs.getString("customerPassword"));
                c.setCustomerAddress(rs.getString("customerAddress"));
                c.setCustomerPhone(rs.getString("customerPhone"));
                c.setCustomerNote(rs.getString("customerNote"));
                c.setIsActive(rs.getBoolean("isActive"));
                c.setAvatarLink(rs.getString("avatarLink"));
                customerList.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return customerList;
    }

    public boolean checkLogin(String customerEmail, String customerPassword) {
        boolean login = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select customerEmail, customerPassword from Customers "
                + "where customerEmail = ? and isActive = 1";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, customerEmail);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("customerPassword").equals(customerPassword)) {
                    login = true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return login;
    }

    public boolean isExistCustomer(String customerEmail, ChangeableString idString) {
        boolean isExist = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select customerEmail,customerID from Customers where customerEmail = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, customerEmail);
            rs = ps.executeQuery();
            isExist = rs.next();
            int id = rs.getInt("customerID");
            String tem = Integer.toString(id);
            idString.changeTo(tem);

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return isExist;
    }

    public int getListPages(int customersPerPage) {
        int pages = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select count(*)/" + customersPerPage + " as pages, "
                + "count(*)%" + customersPerPage + " as lastPage from Customers";

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

    public boolean insertKey(EmailKey ek) {
        boolean insert = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "insert into EmailKey(keyID, customerEmail) values(?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ek.getKeyID());
            ps.setString(2, ek.getCustomerEmail());
            ps.executeUpdate();
            insert = true;
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return insert;
    }

    public boolean deleteKey(String customerEmail) {
        boolean delete = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;

        String sql = "DELETE FROM EmailKey WHERE customerEmail =?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, customerEmail);
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

    public EmailKey selectKey(String customerEmail) {
        EmailKey ek = new EmailKey();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from EmailKey where customerEmail = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, customerEmail);
            rs = ps.executeQuery();
            while (rs.next()) {
                ek.setKeyID(rs.getInt("keyID"));
                ek.setCustomerEmail(rs.getString("customerEmail"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return ek;
    }

    public boolean checkActive(String customerEmail) {
        boolean active = false;
        Customer c = new Customer();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select isActive from Customers where customerEmail = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, customerEmail);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getBoolean("isActive") == true) {
                    active = true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return active;
    }

    public boolean checkKey(int keyID, String customerEmail) {
        boolean check = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select keyID, customerEmail from EmailKey where customerEmail = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, customerEmail);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("keyID") == keyID) {
                    check = true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return check;
    }
}
