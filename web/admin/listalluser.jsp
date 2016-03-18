<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/header_management.jsp"/>
<c:import url="left_aside_admin.jsp"/>

<section class="management_page">
    <h1>User List</h1>
    <section id="search_form" title="Tìm kiếm sản phẩm">
        <form action="administrator" method="post">
            <input type="hidden" name="action" value="search">
            <input type="text" name="searchText" placeholder="Search customer">
            <input type="submit" value="Search">
        </form>
    </section>
    <table>
        <tr>
            <th>User ID</th>
            <th>Name</th>
            <th>Password</th>
            <th>Role</th>
            <th>Active</th>
            <th id="action">Action</th>
        </tr>
        <c:forEach var="user" items="${userList}">
            <tr>
                <td>${user.userID}</td>
                <td>${user.userName}</td>
                <td>${user.userPass}</td>
                <td>${user.userRole}</td>
                <td>${user.isActive}</td>
                <td>
                    <form class="tablebutton" action="administrator" method="post">
                        <input type="hidden" name="action" value="edituser">
                        <input type="hidden" name="userID" 
                               value="<c:out value='${user.userID}'/>">
                        <input type="hidden" name="userName" 
                               value="<c:out value='${user.userName}'/>">
                        <input type="hidden" name="userPass" 
                               value="<c:out value='${user.userPass}'/>">
                        <input type="hidden" name="userRole" 
                               value="<c:out value='${user.userRole}'/>">
                        <input type="hidden" name="isActive" 
                               value="<c:out value='${user.isActive}'/>">
                        <input type="submit" value="Update" id="edituser">
                    </form>
                    <form  class="tablebutton" action="administrator" method="post">
                        <input type="hidden" name="action" value="activeuser">
                        <input type="hidden" name="userID" 
                               value="<c:out value='${user.userID}'/>">
                        
                        <input type="submit" value="Active" id="activeuser">
                    </form>
                    <form  class="tablebutton" action="administrator" method="post">
                        <input type="hidden" name="action" value="deactiveuser">
                        <input type="hidden" name="userID" 
                               value="<c:out value='${user.userID}'/>">
                        
                        <input type="submit" value="DeActive" id="deactiveuser">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>

<c:import url="/footer.jsp"/>