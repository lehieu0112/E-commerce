<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/header_management.jsp"/>
<c:import url="left_aside_admin.jsp"/>

<section class="management_page">
    <h1>Create new user:</h1>
    <form class="edit_product" action="administrator" method="post">
        <input type="hidden" name="action" value="create">
        <label>User Name:</label>
        <input type="text" name="userName" placeholder="Your Name" required><span class="required">*</span><br>
        <label>User Password:</label>
        <input type="password" name="userPass" placeholder="User Password" required><span class="required">*</span><br>
        <label>User Re-Password:</label>
        <input type="password" name="userRePass" placeholder="User Re-Password" required><span class="required">*</span><br>
        <label>User Role:</label>    
        <select name="userRole" required>
            <option value="administrator">administrator</option>
            <option value="salemanager">salemanager</option>
            <option value="orderprocessing" selected>orderprocessing</option>
        </select><span class="required">*</span><br>
        <label>Active:</label>
        <select name="isActive">
            <option value="true">Active</option>
            <option value="false" selected>Non-Active</option>
        </select><br>

        <label>&nbsp;</label>
        <input type="submit" value="Sign Up" id="adduser">
    </form><br>
    <h2>${registermessage}</h2>
</section>

<c:import url="/footer.jsp"/>
