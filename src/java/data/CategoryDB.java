
package data;

import business.Categories;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.DBUtil;

public class CategoryDB {
    public ArrayList<Categories> getCategoriesList(){
        ArrayList<Categories> categoriesList = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select * from Categories";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = con.prepareStatement(sqlString);
            rs = ps.executeQuery();
            Categories category;
            while(rs.next()){
                category = new Categories();
                category.setCategoryID(rs.getInt("categoryID"));
                category.setCategoryName(rs.getString("categoryName"));
                
                categoriesList.add(category);
            }
        }catch(SQLException e){
            System.out.println(e);
        }finally{
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(con);
        }  
        return categoriesList;
    }
    
    public String getCategoryName(int categoryID){
        String categoryName="";
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "select categoryName from Categories where categoryID=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = con.prepareStatement(sqlString);
            ps.setInt(1, categoryID);
            rs = ps.executeQuery();

            while(rs.next()){
                categoryName = rs.getString("categoryName");
            }
        }catch(SQLException e){
            System.out.println(e);
        }finally{
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeResultSet(rs);
            pool.freeConnection(con);
        }  
        return categoryName;
    }
    
    public boolean insertCategory(String categoryName){
        boolean insert = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "insert into Categories(categoryName) values(?)";
        PreparedStatement ps=null;
        try{
            ps = con.prepareStatement(sqlString);           
            ps.setString(1, categoryName);
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
    public boolean updateCategory(String categoryName,int categoryID){
        boolean update = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "update Categories set categoryName=? where categoryID=?";
        PreparedStatement ps=null;
        try{
            ps = con.prepareStatement(sqlString);           
            ps.setString(1, categoryName);
            ps.setInt(2, categoryID);
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
    public boolean deleteCategory(int categoryID){
        boolean delete = false;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        String sqlString = "delete from Categories where categoryID=?";
        PreparedStatement ps=null;
        try{
            ps = con.prepareStatement(sqlString);           
            ps.setInt(1, categoryID);
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
