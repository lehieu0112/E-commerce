<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="header.jsp"/>
<setion id="products_detail">
    <aside id="products_information">
        <h3><strong>${product.productName}</strong></h3><br>
        <form action="shoppingcart" method="post">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="productID" value="${product.productID}">
            <input type="submit" id="submit" value="Add to Cart">
        </form>
        <form action="productsCompare" method="post">
            <input type="hidden" name="action" value="compare">
            <input type="hidden" name="productID" value="${product.productID}">
            <input type="submit" id="submit" value="Compare">
        </form>
        <img src="<c:url value="${product.pictureLink}"/>" width="360" height="360">
        <p><strong>Mã sản phẩm :</strong> ${product.productID}</p><br>
        <p><strong>Giá :</strong> ${product.formatProductPrice} VNĐ</p><br>
        <c:if test="${product.productPrice != product.promotionPrice}">
            <p style="color: red;"><strong>Giá khuyến mãi :</strong> ${product.formatPromotionPrice} VNĐ</p><br>
        </c:if>
        
        <p><strong>Bảo hành :</strong> ${product.productWarranty} tháng</p><br>
        <c:if test="${product.productPrice != product.promotionPrice}">
            <p><strong>Khuyến mãi :</strong> ${promotion.promotionName} <br> ${promotion.promotionDescription}</p><br>
        </c:if>
    </aside>
    <aside id="products_description">
        <h2><strong>Thông tin sản phẩm :</strong></h2><br>
        <c:forTokens var="des" items="${product.productDescription}" delims="|">
            <c:out value="${des}"/><hr><br>
        </c:forTokens>
    </aside>

</section>

<c:import url="footer.jsp"/>