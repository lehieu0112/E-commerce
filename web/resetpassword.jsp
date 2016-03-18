<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="header.jsp"/>

<section id="register_page">
    <h2>Please input your password and confirm password to reset your password.</h2><br>
    <form action="activationemail" method="post" onsubmit="return checkForm(this);">
        <input type="hidden" name="action" value="resetPassword">
        <input type="hidden" name="customerEmail" value="${customerEmail}">
        <label>New Password:</label>
        <input type="password" name="customerPassword"
               placeholder="New Password" required><span class="required">*</span><br>
        <label>Confirm Password:</label>
        <input type="password" name="customerRePassword" placeholder="Confirm Password" required><span class="required">*</span><br>
        <input type="submit" value="Reset Password">
    </form>

    <h2>${errormessage}</h2>
    <a href="homepage">Return Homepage</a>
</section>
<script type="text/javascript">
    function checkForm(form)
    {
        if (form.customerPassword.value !== "" && form.customerPassword.value === form.customerRePassword.value) {
            if (form.customerPassword.value.length < 6) {
                alert("Error: Password must contain at least 6 characters!");
                form.customerPassword.focus();
                return false;
            }
        } else {
            alert("Error: Please check that you've entered and confirmed your password!");
            form.customerPassword.focus();
            return false;
        }
        return true;
    }
</script>
<c:import url="footer.jsp"/>