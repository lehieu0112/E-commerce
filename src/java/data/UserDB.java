package data;

import business.ChangeableString;
import business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import util.DBUtil;

public class UserDB {

    public ArrayList<User> getUsers() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> userList = new ArrayList<>();
        String sql = "select * from Users";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("userID"));
                u.setUserName(rs.getString("userName"));
                u.setUserPass(rs.getString("userPass"));
                u.setUserRole(rs.getString("userRole"));
                u.setIsActive(rs.getBoolean("isActive"));
                userList.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return userList;
    }

    public User userDetail(int userID) {
        User u = new User();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from Users where userID = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            while (rs.next()) {
                u.setUserID(rs.getInt("userID"));
                u.setUserName(rs.getString("userName"));
                u.setUserPass(rs.getString("userPass"));
                u.setUserRole(rs.getString("userRole"));
                u.setIsActive(rs.getBoolean("isActive"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return u;
    }

    public boolean insertUser(User u) {
        boolean insert = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        Statement s;
        ResultSet rs = null;
        String sql = "insert into Users(userName, userPass, userRole, isActive) values(?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getUserPass());
            ps.setString(3, u.getUserRole());
            ps.setBoolean(4, u.getIsActive());
            ps.executeUpdate();
            insert = true;

            String identityQuery = "select @@IDENTITY as userID";
            s = con.createStatement();
            rs = s.executeQuery(identityQuery);
            rs.next();
            int userID = rs.getInt("userID");
            rs.close();
            s.close();
            u.setUserID(userID);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return insert;
    }

    public boolean updateUser(User u, int userID) {
        boolean update = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "update Users set userName = ?, userPass = ?, userRole = ? where userID = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getUserPass());
            ps.setString(3, u.getUserRole());
            ps.setInt(4, userID);
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

    public boolean isActiveUser(User u, int userID) {
        boolean update = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "update Users set isActive = 1 where userID = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, userID);
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

    public boolean deActiveUser(User u, int userID) {
        boolean update = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "update Users set isActive = 0 where userID = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, userID);
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

    public ArrayList<User> searchUser(String text) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> userList = new ArrayList<>();
        String sql = "select * from Users where userName like '%" + text + "%' "
                + "or userPass like '%" + text + "%' or userRole like '%" + text + "%'";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("userID"));
                u.setUserName(rs.getString("userName"));
                u.setUserPass(rs.getString("userPass"));
                u.setUserRole(rs.getString("userRole"));
                u.setIsActive(rs.getBoolean("isActive"));
                userList.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
        }
        return userList;
    }
    
    public boolean checkLogin(String userName, String userPassword, ChangeableString role) {
        boolean login = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from Users "
                + "where userName = ? and isActive = 1";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, userName);            
            rs = ps.executeQuery();
            if(rs.next()){
                if(rs.getString("userPass").equals(userPassword)){
                    login = true;
                    role.changeTo(rs.getString("userRole"));
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
}
