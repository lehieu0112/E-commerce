package data;

import business.Customer;
import business.Invoices;
import business.LineItem;
import business.Products;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import util.DBUtil;

public class OrderDB {

    public boolean insertInvoice(Invoices invoice) {
        boolean insert = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "insert into Invoices"
                + "(customerID,invoiceDate,invoiceTotal,"
                + "shippingProcess,isPaid) values(?,?,?,?,?)";
        PreparedStatement ps = null;
        Statement s;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, invoice.getCustomer().getCustomerID());
            ps.setDate(2, invoice.getInvoiceDate());
            ps.setDouble(3, invoice.getInvoiceTotal());
            ps.setString(4, invoice.getShippingProcess());
            ps.setBoolean(5, invoice.isIsPaid());
            ps.executeUpdate();
            insert = true;

            String identityQuery = "select @@IDENTITY as invoiceID";
            s = con.createStatement();
            rs = s.executeQuery(identityQuery);
            rs.next();
            int invoiceID = rs.getInt("invoiceID");
            rs.close();
            s.close();
            for (LineItem item : invoice.getItems()) {
                item.setInvoiceID(invoiceID);
                insertLineItem(item);
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);

        }
        return insert;
    }
    
    public ArrayList<Invoices> getInvoicesByCustomerID(int customerID) {
        ArrayList<Invoices> invoicesList = new ArrayList<>();
        Invoices invoice;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select * from Invoices"
                + " where customerID =?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, customerID);
            rs = ps.executeQuery();
            CustomerDB cDB = new CustomerDB();
            Customer customer;
            while (rs.next()) {
                invoice = new Invoices();
                invoice.setInvoiceID(rs.getInt("invoiceID"));
                customer = cDB.getCustomer(rs.getInt("customerID"));
                invoice.setCustomer(customer);
                invoice.setItems(getItems(rs.getInt("invoiceID")));
                invoice.setInvoiceDate(rs.getDate("invoiceDate"));
                invoice.setInvoiceTotal(rs.getDouble("invoiceTotal"));
                invoice.setShippingProcess(rs.getString("shippingProcess"));
                invoice.setShippingDate(rs.getDate("shippingDate"));
                invoice.setIsPaid(rs.getBoolean("isPaid"));
                invoicesList.add(invoice);
            }            
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return invoicesList;
    }

    public Invoices getInvoice(int invoiceID) {
        Invoices invoice = new Invoices();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select * from Invoices"
                + " where invoiceID =?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, invoiceID);
            rs = ps.executeQuery();
            CustomerDB cDB = new CustomerDB();
            Customer customer;

            while (rs.next()) {
                invoice.setInvoiceID(rs.getInt("invoiceID"));
                customer = cDB.getCustomer(rs.getInt("customerID"));
                invoice.setCustomer(customer);
                invoice.setItems(getItems(rs.getInt("invoiceID")));
                invoice.setInvoiceDate(rs.getDate("invoiceDate"));
                invoice.setInvoiceTotal(rs.getDouble("invoiceTotal"));
                invoice.setShippingProcess(rs.getString("shippingProcess"));
                invoice.setShippingDate(rs.getDate("shippingDate"));
                invoice.setIsPaid(rs.getBoolean("isPaid"));
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return invoice;
    }

    public ArrayList<LineItem> getItems(int invoiceID) {
        ArrayList<LineItem> items = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select * from LineItem where invoiceID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ProductDB pDB = new ProductDB();
        LineItem item;
        Products product;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, invoiceID);         
            rs = ps.executeQuery();
            while (rs.next()){
                item = new LineItem();
                item.setInvoiceID(rs.getInt("invoiceID"));               
                product = pDB.getProduct(rs.getInt("productID"));
                item.setProduct(product);
                item.setQuantity(rs.getInt("quantity"));
                item.setLineItemAmount(rs.getDouble("lineItemAmount"));
                items.add(item);
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return items;
    }

    public void insertLineItem(LineItem item) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "insert into LineItem"
                + "(invoiceID,productID,quantity,lineItemAmount) values(?,?,?,?)";
        PreparedStatement ps = null;
        ProductDB pDB = new ProductDB();
        try {
            if (pDB.updateQuantityInStore(item.getProduct().getProductID(),
                    item.getQuantity())) {
                ps = con.prepareStatement(sqlString);
                ps.setInt(1, item.getInvoiceID());
                ps.setInt(2, item.getProduct().getProductID());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getLineItemAmount());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e);
            int i = -(item.getQuantity());
            pDB.updateQuantityInStore(item.getProduct().getProductID(), i);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
    }

    public boolean updateInvoiceIsPaid(int invoiceID) {
        boolean update = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "update Invoices set isPaid = 1 where invoiceID = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, invoiceID);
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

    public boolean updateInvoiceShippingProcess(String shippingProcess, int invoiceID) {
        boolean update = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "update Invoices set shippingProcess = ? where invoiceID = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setString(1, shippingProcess);
            ps.setInt(2, invoiceID);
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

    public boolean updateInvoiceShippingDate(Date shippingDate, int invoiceID) {
        boolean update = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "update Invoices set shippingDate = ? where invoiceID = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sqlString);
            ps.setDate(1, shippingDate);
            ps.setInt(2, invoiceID);
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

    public int getListPages(int invoicesPerPage) {
        int pages = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select count(*)/" + invoicesPerPage + " as pages,"
                + "count(*)%" + invoicesPerPage + " as lastPage"
                + " from Invoices";

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

    public ArrayList<Invoices> getAllOrders(int page, int invoicesPerPage,boolean print) {
        ArrayList<Invoices> invoicesList = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select * from Invoices order by invoiceDate desc";
        String offset = " offset " + (page * invoicesPerPage) + " rows"
                + " fetch next " + invoicesPerPage + " rows only";
        if(!print){
            sqlString = sqlString + offset;
        }        
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sqlString);
            rs = ps.executeQuery();
            Invoices invoice;
            CustomerDB cDB = new CustomerDB();
            Customer customer;
            while (rs.next()) {
                invoice = new Invoices();
                invoice.setInvoiceID(rs.getInt("invoiceID"));
                customer = cDB.getCustomer(rs.getInt("customerID"));
                invoice.setCustomer(customer);
                invoice.setInvoiceDate(rs.getDate("invoiceDate"));
                invoice.setInvoiceTotal(rs.getDouble("invoiceTotal"));
                invoice.setShippingProcess(rs.getString("shippingProcess"));
                invoice.setShippingDate(rs.getDate("shippingDate"));
                invoice.setIsPaid(rs.getBoolean("isPaid"));

                invoicesList.add(invoice);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(con);
        }

        return invoicesList;
    }

    public int getSearchPages(int invoicesPerPage, String name, Date startDate, Date endDate, int choose) {
        int pages = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select count(*)/" + invoicesPerPage + " as pages,"
                + "count(*)%" + invoicesPerPage + " as lastPage"
                + " from Invoices i join Customers c on i.customerID=c.customerID"
                + " where (c.customerName like '%" + name + "%')"
                + " or (c.customerEmail like '%" + name + "%')";
        String sqlString1 = "select count(*)/" + invoicesPerPage + " as pages,"
                + "count(*)%" + invoicesPerPage + " as lastPage"
                + " from Invoices where (invoiceDate>='" + startDate + "')"
                + " and (invoiceDate<='" + endDate + "')";
        if (choose == 1) {
            sqlString = sqlString1;
        }
        Statement s = null;
        ResultSet rs = null;
        try {
            s = con.createStatement();
            rs = s.executeQuery(sqlString);
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

    public ArrayList<Invoices> getSearchOrders(int page, int invoicesPerPage,
            String name, Date startDate, Date endDate, int choose) {
        ArrayList<Invoices> invoicesList = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String offset = " offset " + (page * invoicesPerPage) + " rows"
                + " fetch next " + invoicesPerPage + " rows only";
        String sqlString = "select * from Invoices i join Customers c"
                + " on i.customerID=c.customerID"
                + " where (c.customerName like '%" + name + "%')"
                + " or (c.customerEmail like '%" + name + "%')"
                + " order by invoiceDate desc";
        String sqlString1 = "select * from Invoices "
                + "where (invoiceDate>='" + startDate + "')"
                + " and (invoiceDate<='" + endDate + "')"
                + " order by invoiceDate desc";
        if (choose == 1) {
            sqlString = sqlString1;
        }
        sqlString = sqlString + offset;

        Statement s = null;
        ResultSet rs = null;
        try {
            s = con.createStatement();
            rs = s.executeQuery(sqlString);
            Invoices invoice;
            CustomerDB cDB = new CustomerDB();
            Customer customer;
            while (rs.next()) {
                invoice = new Invoices();
                invoice.setInvoiceID(rs.getInt("invoiceID"));
                customer = cDB.getCustomer(rs.getInt("customerID"));
                invoice.setCustomer(customer);
                invoice.setInvoiceDate(rs.getDate("invoiceDate"));
                invoice.setInvoiceTotal(rs.getDouble("invoiceTotal"));
                invoice.setShippingProcess(rs.getString("shippingProcess"));
                invoice.setShippingDate(rs.getDate("shippingDate"));
                invoice.setIsPaid(rs.getBoolean("isPaid"));

                invoicesList.add(invoice);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeStatement(s);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(con);
        }

        return invoicesList;
    }

}
