<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/header_management.jsp"/>
<c:import url="left_aside_admin.jsp"/>

<section class="management_page">
    <h1>Update User:</h1>
    <form class="edit_product" action="administrator" method="post">
        <input type="hidden" name="action" value="updateuser">
        <label>User ID:</label><p>${editUser.userID}</p><br>
        
        <label>Name:</label>
        <input type="text" name="userName" value="${editUser.userName}" required><span class="required">*</span><br>
        <label>Password:</label>
        <input type="text" name="userPass" value="${editUser.userPass}" required><span class="required">*</span><br>
        <label>Re-Password:</label>
        <input type="text" name="userRePass" value="${editUser.userPass}" required><span class="required">*</span><br>
        <label>Role:</label>
        <input type="text" name="userRole" value="${editUser.userRole}" required><span class="required">*</span><br>

        <label>&nbsp;</label>
        <input type="submit" value="Update User" id="updateuser">
    </form><br>
    <h2>${editmessage}</h2>
</section>

<c:import url="/footer.jsp"/>