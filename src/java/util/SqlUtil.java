
package util;

public class SqlUtil {
    public static String getSearchPagesSqlString(String name,String category,
            String manufacture,int productsPerPage,int choose){
        String sql ="";
        String sqlString = "select count(*)/"+productsPerPage+" as pages,"
                + "count(*)%"+productsPerPage+" as lastPage"
                + " from Products where productName like '%" + name + "%'";
        String sqlString1 = "select count(*)/"+productsPerPage+" as pages,"
                + "count(*)%"+productsPerPage+" as lastPage"
                + " from Products p join Manufacture m"
                + " on p.manufactureID = m.manufactureID"
                + " where manufactureName='" + manufacture + "'";
        String sqlString2 = "select count(*)/"+productsPerPage+" as pages,"
                + "count(*)%"+productsPerPage+" as lastPage"
                + " from Products p join Categories c"
                + " on p.categoryID = c.categoryID"
                + " where categoryName='" + category + "'";
        String sqlString3 = "select count(*)/"+productsPerPage+" as pages,"
                + "count(*)%"+productsPerPage+" as lastPage"
                + " from Products p join Manufacture m"
                + " on p.manufactureID = m.manufactureID"
                + " join Categories c on p.categoryID = c.categoryID"
                + " where manufactureName='" + manufacture + "'"
                + " and categoryName='" + category + "'";
        String sqlString4 = "select count(*)/"+productsPerPage+" as pages,"
                + "count(*)%"+productsPerPage+" as lastPage"
                + " from Products p join Manufacture m"
                + " on p.manufactureID = m.manufactureID"
                + " where manufactureName='" + manufacture + "'"
                + " and productName like '%" + name + "%'";
        String sqlString5 = "select count(*)/"+productsPerPage+" as pages,"
                + "count(*)%"+productsPerPage+" as lastPage"
                + " from Products p join Categories c"
                + " on p.categoryID = c.categoryID"
                + " where categoryName='" + category + "'"
                + " and productName like '%" + name + "%'";
        String sqlString6 = "select count(*)/"+productsPerPage+" as pages,"
                + "count(*)%"+productsPerPage+" as lastPage"
                + " from Products p join Manufacture m"
                + " on p.manufactureID = m.manufactureID"
                + " join Categories c on p.categoryID = c.categoryID"
                + " where manufactureName='" + manufacture + "'"
                + " and categoryName='" + category + "'"
                + " and productName like '%" + name + "%'";
        
        switch (choose) {
            case 0:
                sql = sqlString;
                break;
            case 1:
                sql = sqlString1;
                break;
            case 2:
                sql = sqlString2;
                break;
            case 3:
                sql = sqlString3;
                break;
            case 4:
                sql = sqlString4;
                break;
            case 5:
                sql = sqlString5;
                break;
            case 6:
                sql = sqlString6;
                break;
            default:
                break;
        }
        return sql;
    }
    public static String getSearchProductsSqlString(String name,String category,
            String manufacture,int page,int productsPerPage,int choose){
        String sql ="";
        String sqlString = "select * from Products where productName like '%" + name + "%'"
                 + " order by productID";
        String sqlString1 = "select p.* from Products p join Manufacture m"
                + " on p.manufactureID = m.manufactureID"
                + " where manufactureName='" + manufacture + "'"
                + " order by productID";
        String sqlString2 = "select p.* from Products p join Categories c"
                + " on p.categoryID = c.categoryID"
                + " where categoryName='" + category + "'"
                + " order by productID";
        String sqlString3 = "select p.* from Products p join Manufacture m"
                + " on p.manufactureID = m.manufactureID"
                + " join Categories c on p.categoryID = c.categoryID"
                + " where manufactureName='" + manufacture + "'"
                + " and categoryName='" + category + "'"
                + " order by productID";
        String sqlString4 = "select p.* from Products p join Manufacture m"
                + " on p.manufactureID = m.manufactureID"
                + " where manufactureName='" + manufacture + "'"
                + " and productName like '%" + name + "%'"
                + " order by productID";
        String sqlString5 = "select p.* from Products p join Categories c"
                + " on p.categoryID = c.categoryID"
                + " where categoryName='" + category + "'"
                + " and productName like '%" + name + "%'"
                + " order by productID";
        String sqlString6 = "select p.* from Products p join Manufacture m"
                + " on p.manufactureID = m.manufactureID"
                + " join Categories c on p.categoryID = c.categoryID"
                + " where manufactureName='" + manufacture + "'"
                + " and categoryName='" + category + "'"
                + " and productName like '%" + name + "%'"
                + " order by productID";
        switch (choose) {
            case 0:
                sql = sqlString;
                break;
            case 1:
                sql = sqlString1;
                break;
            case 2:
                sql = sqlString2;
                break;
            case 3:
                sql = sqlString3;
                break;
            case 4:
                sql = sqlString4;
                break;
            case 5:
                sql = sqlString5;
                break;
            case 6:
                sql = sqlString6;
                break;
            default:
                break;
        }
        String offset = " offset " + (page * productsPerPage) + " rows"
                + " fetch next " + productsPerPage + " rows only";
        return sql + offset;
    }
}
