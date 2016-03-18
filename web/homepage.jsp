<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="header.jsp"/>
<main>
    <section id="homepage">
        <div id="div1">
            <section id="product_selling">
                <a href="productsList?action=list&listValue=bestselling"><h2>Best Selling</h2></a>
                <c:forEach var="product" items="${fiveBestSellingList}">
                    <div class="floating-box">
                        <a href="<c:url value="/productDetail?action=get&productID=${product.productID}"/>">
                            <img src="<c:url value="${product.pictureLink}"/>"
                                 width="120" height="120"></a>  
                        <h5>${product.productName}<br>
                            Mã sản phẩm: ${product.productID}
                            <br><span style="color: blue;">${product.formatProductPrice} VNĐ</span></h5>
                            <c:if test="${product.productPrice != product.promotionPrice}">
                            <h5 style="color: red;"><strong>Giá khuyến mãi :</strong> ${product.formatPromotionPrice} VNĐ</h5>
                        </c:if>
                    </div>
                </c:forEach>
            </section>
        </div>
        <div id="div2">
            <section id="product_promotion">
                <a href="productsList?action=list&listValue=bestpromotion"><h2>Best Promotion</h2></a>
                <c:forEach var="product" items="${fiveBestPromotionList}">
                    <div class="floating-box">
                        <a href="<c:url value="/productDetail?action=get&productID=${product.productID}"/>">
                            <img src="<c:url value="${product.pictureLink}"/>"
                                 width="120" height="120"></a>  
                        <h5>${product.productName}<br>
                            Mã sản phẩm: ${product.productID}
                            <br><span style="color: blue;">${product.formatProductPrice} VNĐ</span></h5>
                            <c:if test="${product.productPrice != product.promotionPrice}">
                            <h5 style="color: red;"><strong>Giá khuyến mãi :</strong> ${product.formatPromotionPrice} VNĐ</h5>
                        </c:if>
                    </div>
                </c:forEach>
            </section>
        </div>
        <div id="div3">
            <section id="product_new">
                <a href="productsList?action=list&listValue=newproducts"><h2>New Products</h2></a>
                <c:forEach var="product" items="${fiveNewProductsList}">
                    <div class="floating-box">
                        <a href="<c:url value="/productDetail?action=get&productID=${product.productID}"/>">
                            <img src="<c:url value="${product.pictureLink}"/>"
                                 width="120" height="120"></a>  
                        <h5>${product.productName}<br>
                            Mã sản phẩm: ${product.productID}
                            <br><span style="color: blue;">${product.formatProductPrice} VNĐ</span></h5>
                            <c:if test="${product.productPrice != product.promotionPrice}">
                            <h5 style="color: red;"><strong>Giá khuyến mãi :</strong> ${product.formatPromotionPrice} VNĐ</h5>
                        </c:if>
                    </div>
                </c:forEach>
            </section>
        </div>
    </section>   

</main>
<c:import url="footer.jsp"/>

