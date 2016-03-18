<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/header_management.jsp"/>
<c:import url="left_aside_manager.jsp"/>

<section class="management_page">
    <form style="width:530px;" action="fileupload2" method="post" enctype="multipart/form-data">
            <input style="width:500px;" type="file" name="file"><br>
            <input type="submit" value="Upload Image" >
        </form> 
    <form class="edit_product" action="productmanager" method="post">
        <input type="hidden" name="action" value="addproduct">
        <div style="width: 200px;height: 200px;"><img src="<c:url value="${pictureLink}"/>" height="200" width="200"/></div><br>
        
        <label>Product Image Link:</label>
        <input type="text" name="pictureLink" value="${pictureLink}" required><span class="required">*</span><br>
        <label>Product ID :</label>
        <input type="text" name="productID" required><span class="required">*</span><br>
        <label>Category :</label>
        <select name="categoryID" required>
            <c:forEach var="cat" items="${categoryList}">
                <option value="${cat.categoryID}">${cat.categoryName}</option>
            </c:forEach>
            
        </select><span class="required">*</span><br>
        <label>Manufacture :</label>
        <select name="manufactureID" required>
            <c:forEach var="manu" items="${manufactureList}">
                <option value="${manu.manufactureID}">${manu.manufactureName}</option>
            </c:forEach>
            
        </select><span class="required">*</span><br>
        <label>Product Name :</label>
        <input class="editproductInput" type="text" name="productName" required><span class="required">*</span><br>     
        <label>Descriptions :</label>
        <textarea class="editproductInput" cols="40" rows="5" name="productDescription" required></textarea><span class="required">*</span><br>
        <label>Price :</label>
        <input type="text" name="productPrice" required><span class="required">*</span><br>
        <label>Model Year :</label>
        <input type="text" name="modelYear" required><span class="required">*</span><br>
        <label>Quantity In Store :</label>
        <input type="text" name="quantityInStore" required><span class="required">*</span><br>
        <label>Warranty :</label>
        <input type="text" name="productWarranty" required><span class="required">*</span><br>
        <label>Promotion ID :</label>
        <select name="promotionID" required>
            <c:forEach var="promotion" items="${promotionsList}">
                <option value="${promotion.promotionID}">${promotion.promotionName}</option>
            </c:forEach>
            
        </select><span class="required">*</span><br>
        <label>&nbsp;</label>

        <input type="submit" value="Add Product" id="updatecustomer">
    </form><br>
    <h3>${addmessage}</h3>
</section>

<c:import url="/footer.jsp"/>