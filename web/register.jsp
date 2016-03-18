<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="header.jsp"/>
<main>
    <section id="register_page">
        <a href="homepage">Return Homepage</a>
        <h1>Join our shop</h1>
        <h2>${activemessage}</h2>
        <p>To join our shop and buy everything, enter information below :</p>

        <form action="customer" method="post" onsubmit="return checkForm(this);">
            <input type="hidden" name="action" value="register">
            <label>Full Name:</label>
            <input type="text" name="customerName" placeholder="Your name" required><span class="required">*</span><br>
            <label>Email:</label>
            <input type="email" name="customerEmail" placeholder="Your email address" required><span class="required">*</span><br>
            <label>Password:</label>
            <input type="password" name="customerPassword" placeholder="Your password" required><span class="required">*</span><br>
            <label>Re-Password:</label>
            <input type="password" name="customerRePassword" placeholder="Re-password" required><span class="required">*</span><br>
            <label>Address:</label>
            <input type="text" name="customerAddress" placeholder="Your address" required><span class="required">*</span><br>
            <label>Mobile Phone:</label>
            <input type="text" name="customerPhone" placeholder="Your phonenumber" required><span class="required">*</span><br>
            <label>Notes:</label>
            <textarea name="customerNote" rows="10" cols="30"></textarea><br>
            <label>&nbsp;</label>
            <input type="submit" value="Sign Up" id="submit">
        </form><br>
        <h2>${registermessage}</h2>
        
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
                if (form.customerPassword.value === form.customerName.value) {
                    alert("Error: Password must be different from Username!");
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
</main>
<c:import url="footer.jsp"/>
