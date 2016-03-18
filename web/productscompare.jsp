<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="header.jsp"/>
<main>

    <aside id="products_compare1">
        <h3>Product compare</h3><br>
        <img src="<c:url value="${product1.pictureLink}"/>" width="200" height="200">
        <h3><strong>${product1.productName}</strong></h3><br>
        <p><strong>Mã sản phẩm :</strong> ${product1.productID}</p><br>
        <p><strong>Thông tin sản phẩm :</strong></p><br>
        <c:forTokens var="des" items="${product1.productDescription}" delims="|">
            <c:out value="${des}"/><hr><br>
        </c:forTokens>
        <p><strong>Giá :</strong> ${product1.formatProductPrice} VNĐ</p><br>
        <c:if test="${product1.productPrice != product1.promotionPrice}">
            <p style="color: red;"><strong>Giá khuyến mãi :</strong> ${product1.formatPromotionPrice} VNĐ</p><br>
        </c:if>
        <p><strong>Bảo hành :</strong> ${product1.productWarranty} tháng</p><br>
        <form action="shoppingcart" method="post">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="productID" value="${product1.productID}">
            <input type="submit" name="submit" value="Add to Cart">
        </form>      

    </aside>

    <c:if test="${product2 == null}">
        <aside id="products_compare2">    
            <form action="productsCompare" method="post">
                <input type="hidden" name="action" value="search">
                <input type="hidden" name="product1ID" value="${product1.productID}">
                <input type="text" name="name">
                <input type="submit" name="submit" value="Search">
            </form><br>
            <c:forEach var="product" items="${productsList}">
                <div class="floating-box">
                    <a href="<c:url value="/productsCompare?action=get&productID=${product.productID}&product1ID=${product1.productID}"/>">
                        <img src="<c:url value="${product.pictureLink}"/>"
                             width="120" height="120"></a>  
                    <h5>${product.productName}<br>
                        Mã sản phẩm: ${product.productID}
                        <br>${product.formatProductPrice} VNĐ</h5>
                        <c:if test="${product.productPrice != product.promotionPrice}">
                        <h5 style="color: red;"><strong>Giá khuyến mãi :</strong> ${product.formatPromotionPrice} VNĐ</h5>
                    </c:if>
                </div>
            </c:forEach>
            <br>
            <div id="products_button2">
                <c:if test="${pages>0}">
                    <c:forEach var="i" begin="0" end="${pages}">
                        <form action="productsCompare" method="post">
                            <input type="hidden" name="action" value="search">
                            <input type="hidden" name="product1ID" value="${product1.productID}">
                            <input type="hidden" name="name" value="${name}">                       
                            <input type="hidden" name="page" value="${i}">
                            <input type="submit" value="${i+1}">                     
                        </form>
                    </c:forEach>
                </c:if>
        </aside>
    </c:if>


    <c:if test="${product2 != null}">
        <aside id="products_compare3">
            <form action="productsCompare" method="post">
                <input type="hidden" name="action" value="search">
                <input type="hidden" name="product1ID" value="${product1.productID}">
                <input type="text" name="name">
                <input type="submit" name="submit" value="Search">
            </form><br>
            <img src="<c:url value="${product2.pictureLink}"/>" width="200" height="200">
            <h3><strong>${product2.productName}</strong></h3><br>
            <p><strong>Mã sản phẩm :</strong> ${product2.productID}</p><br>
            <p><strong>Thông tin sản phẩm :</strong></p><br>
            <c:forTokens var="des" items="${product2.productDescription}" delims="|">
                <c:out value="${des}"/><hr><br>
            </c:forTokens>
            <p><strong>Giá :</strong> ${product2.formatProductPrice} VNĐ</p><br>
            <c:if test="${product2.productPrice != product2.promotionPrice}">
                <p style="color: red;"><strong>Giá khuyến mãi :</strong> ${product2.formatPromotionPrice} VNĐ</p><br>
            </c:if>
            <p><strong>Bảo hành :</strong> ${product2.productWarranty} tháng</p><br>
            <form action="shoppingcart" method="post">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="productID" value="${product2.productID}">
                <input type="submit" name="submit" value="Add to Cart">
            </form>
        </aside>
    </c:if>



</main>
<c:import url="footer.jsp"/>
