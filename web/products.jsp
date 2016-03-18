<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="header.jsp"/>
<main>
    <section id="products_page">
        <c:forEach var="product" items="${productsList}">
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
        <br>
        <div id="products_button2">
            <c:if test="${pages>0}">
                <c:forEach var="i" begin="0" end="${pages}">
                    <form action="productsList" method="post">
                        <input type="hidden" name="action" value="${formAction}">
                        <input type="hidden" name="name" value="${name}">
                        <input type="hidden" name="category" value="${category}">
                        <input type="hidden" name="manufacture" value="${manufacture}">
                        <input type="hidden" name="listValue" value="${listValue}">
                        <input type="hidden" name="page" value="${i}">
                        <input type="submit" value="${i+1}">                     
                    </form>
                </c:forEach>
            </c:if>         
        </div>

    </section>

</main>
<c:import url="footer.jsp"/>
