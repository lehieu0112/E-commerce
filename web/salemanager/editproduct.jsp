<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/header_management.jsp"/>
<c:import url="left_aside_manager.jsp"/>

<section class="management_page">
    <form class="edit_product" action="productmanager" method="post">
        <input type="hidden" name="action" value="updateproduct">
        <div id="profile_image" style="width: 200px;height: 200px;"><img src="<c:url value="${product.pictureLink}"/>"
                                     width="200" height="200"></div>

        <label>Change image:</label>
        <input type="text" name="pictureLink" value="${product.pictureLink}"><br>
        <label>Product ID : </label><p>${product.productID}</p><br>
        <label>Category :</label>
        <select name="categoryID" required>
            <c:forEach var="cat" items="${categoryList}">
                <option value="${cat.categoryID}" <c:if test="${categoryName eq cat.categoryName}">selected</c:if>>${cat.categoryName}</option>
            </c:forEach>
            
        </select><span class="required">*</span><br>
        <label>Manufacture :</label>      
        <select name="manufactureID" required>
            <c:forEach var="manu" items="${manufactureList}">
                <option value="${manu.manufactureID}" <c:if test="${manufactureName eq manu.manufactureName}">selected</c:if>>${manu.manufactureName}</option>
            </c:forEach>
            
        </select><span class="required">*</span><br>
        <label>Product Name :</label>
        <input class="editproductInput" type="text" name="productName" value="${product.productName}" required><span class="required">*</span><br>     
        <label>Edit Descriptions :</label>
        <textarea class="editproductInput" cols="40" rows="5" name="productDescription"><c:out value="${product.productDescription}"/></textarea><span class="required">*</span><br>
        <label>Price :</label>
        <input type="text" name="productPrice" value="${product.productPrice}" required><span class="required">*</span><br>
        <label>Model Year :</label>
        <input type="text" name="modelYear" value="${product.modelYear}" required><span class="required">*</span><br>
        <label>Quantity In Store :</label>
        <input type="text" name="quantityInStore" value="${product.quantityInStore}" required><span class="required">*</span><br>
        <label>Warranty :</label>
        <input type="text" name="productWarranty" value="${product.productWarranty}" required><span class="required">*</span><br>
        <label>Promotion ID :</label>
        <input type="text" name="promotionID" value="${product.promotionID}" required><span class="required">*</span><br>
        <label>&nbsp;</label>
        <input type="hidden" name="productID" value="${product.productID}">
        <input type="submit" value="Update Product" id="updatecustomer">
    </form><br>
    <h3>${editmessage}</h3>
</section>

<c:import url="/footer.jsp"/>