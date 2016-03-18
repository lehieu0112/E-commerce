<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="header.jsp"/>
<main>
    <section id="register_page">
        <h3>Update your information here : </h3><br>
        <form style="width:530px;" action="fileupload" method="post" enctype="multipart/form-data">
            <input style="width:500px;" type="file" name="file"><br>          
            <input type="submit" value="Upload Image" >
        </form> 
        <form action="customer" method="post" onsubmit="return checkForm(this);">

            <input type="hidden" name="action" value="updateprofile">
            <div id="profile_image"><img src="<c:url value="${customer.avatarLink}"/>" height="80" width="80"/></div>
            <label>Image url:</label>
            <c:if test="${fileName==null}">
                <input type="text" name="avatarLink" value="${customer.avatarLink}"><br>
            </c:if>
            <c:if test="${fileName!=null}">
                <input type="text" name="avatarLink" value="${fileName}"><br>
            </c:if>
            <label>Full Name:</label>
            <input type="text" name="customerName" value="${customer.customerName}" required><br>
            <label>Email:</label>
            <input type="email" name="customerEmail" value="${customer.customerEmail}" required><br>
            <label>Password:</label>
            <input type="password" name="customerPassword" value="${customer.customerPassword}" required><br>
            <label>Re-Password:</label>
            <input type="password" name="customerRePassword" value="${customer.customerPassword}" required><br>
            <label>Address:</label>
            <input type="text" name="customerAddress" value="${customer.customerAddress}" required><br>
            <label>Phone Number:</label>
            <input type="text" name="customerPhone" value="${customer.customerPhone}" required><br>
            <label>Notes:</label>
            <div id="note"><p>${customer.customerNote}</p></div><br>
            <label>Edit Notes:</label>
            <textarea name="customerNote" rows="10" cols="30" ></textarea><br>

            <label>&nbsp;</label>
            <input type="submit" value="Update Profile" id="signup">

        </form><br>
        <h2>${editmessage}</h2>
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
