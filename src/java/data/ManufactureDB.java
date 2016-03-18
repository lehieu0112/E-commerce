
package data;

import  business.Manufacture;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.DBUtil;

public class ManufactureDB {
    public ArrayList<Manufacture> getManufactureList(){
        ArrayList<Manufacture> manufactureList = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select * from Manufacture";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = con.prepareStatement(sqlString);
            rs = ps.executeQuery();
            Manufacture manufacture;
            while(rs.next()){
                manufacture = new Manufacture();
                manufacture.setManufactureID(rs.getInt("manufactureID"));
                manufacture.setManufactureName(rs.getString("manufactureName"));
                
                manufactureList.add(manufacture);
            }
        }catch(SQLException e){
            System.out.println(e);
        }finally{
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(con);
        }  
        return manufactureList;
    }
    
    public String getManufactureName(int manufactureID){
        String manufactureName="";
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select manufactureName from Manufacture where manufactureID=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, manufactureID);
            rs = ps.executeQuery();

            while(rs.next()){
                manufactureName = rs.getString("manufactureName");
            }
        }catch(SQLException e){
            System.out.println(e);
        }finally{
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(con);
        }  
        return manufactureName;
    }
    
    public boolean insertManufacture(String manufactureName){
        boolean insert = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "insert into Manufacture(manufactureName) values(?)";
        PreparedStatement ps=null;
        try{
            ps = con.prepareStatement(sqlString);           
            ps.setString(1, manufactureName);
            ps.executeUpdate();
            insert = true;         
                   
        }catch(SQLException e){
            System.out.println(e);
        }finally{
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);            
        }
        return insert;
    }
    public boolean updateManufacture(String manufactureName,int manufactureID){
        boolean update = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "update Manufacture set manufactureName=? where manufactureID=?";
        PreparedStatement ps=null;
        try{
            ps = con.prepareStatement(sqlString);           
            ps.setString(1, manufactureName);
            ps.setInt(2, manufactureID);
            ps.executeUpdate();
            update = true;         
                   
        }catch(SQLException e){
            System.out.println(e);
        }finally{
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);            
        }
        return update;
    }
    
    public boolean deleteManufacture(int manufactureID){
        boolean delete = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "delete from Manufacture where manufactureID=?";
        PreparedStatement ps=null;
        try{
            ps = con.prepareStatement(sqlString);           
            ps.setInt(1, manufactureID);
            ps.executeUpdate();
            delete = true;         
                   
        }catch(SQLException e){
            System.out.println(e);
        }finally{
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);            
        }
        return delete;
    }
}
