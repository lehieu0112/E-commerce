<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="header.jsp"/>

<section id="register_page">
    <h2>Please input your email address to reset your password.</h2><br>
    <form action="activationemail" method="post">
        <input type="hidden" name="action" value="sendEmail">
        <input type="email" name="customerEmail" placeholder="Enter Your Email" required><span class="required">*</span><br>
        <input type="hidden" name="customerEmail" 
               value="<c:out value='${customer.customerID}'/>">
        <input type="submit" value="Send Email">
    </form>
    <p>${checkmessage}</p>
</section>

<c:import url="footer.jsp"/>