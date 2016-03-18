<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/header_management.jsp"/>
<c:import url="left_aside_manager.jsp"/>

<section class="management_page">
    <form class="edit_product" action="customermanager" method="post" onsubmit="return checkForm(this);">
        <input type="hidden" name="action" value="updateCustomer">
        <div id="profile_image"><img src="<c:url value="${editCustomer.avatarLink}"/>"
                                     width="80" height="80"></div>

        <label>Change image:</label>
        <input type="text" name="avatarLink" value="${editCustomer.avatarLink}"><br>
        <label>Full Name:</label>
        <input type="text" name="customerName" value="${editCustomer.customerName}" required><span class="required">*</span><br>
        <label>Email:</label>
        <input type="email" name="customerEmail" value="${editCustomer.customerEmail}" required><span class="required">*</span><br>
        <label>Password:</label>
        <input type="password" name="customerPassword" value="${editCustomer.customerPassword}" required><span class="required">*</span><br>
        <label>Re-Password:</label>
        <input type="password" name="customerRePassword" value="${editCustomer.customerPassword}" required><span class="required">*</span><br>
        <label>Address:</label>
        <input type="text" name="customerAddress" value="${editCustomer.customerAddress}" required><span class="required">*</span><br>
        <label>Phone Number:</label>
        <input type="text" name="customerPhone" value="${editCustomer.customerPhone}" required><span class="required">*</span><br>     
        <label>Edit Notes:</label>
        <textarea name="customerNote" rows="8" cols="30" >${editCustomer.customerNote}</textarea><br>

        <label>&nbsp;</label>
        <input type="submit" value="Update Customer" id="updatecustomer">
    </form><br>
    <h3>${editmessage}</h3>
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
<c:import url="/footer.jsp"/>