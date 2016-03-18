<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/header_management.jsp"/>
<c:import url="left_aside_manager.jsp"/>

<section class="management_page">
    <h1>Products List</h1>
    <section id="search_form" title="Tìm kiếm sản phẩm">
        <form action="productmanager" method="post">
            <input type="hidden" name="action" value="searchproduct">
            <input type="text" name="searchText" placeholder="Search Product">
            <input type="submit" value="Search">
        </form>
    </section>
    <h1>${deletemessage}</h1>
    <table>
        <tr>
            <th>Product ID</th>
            <th>Product Name</th>
            <th>Product Price</th>           
            <th>Quantity In Store</th>
            <th>Action</th>
        </tr>
        <c:forEach var="product" items="${productsList}">
            <tr>
                <td>${product.productID}</td>
                <td>${product.productName}</td>                
                <td>${product.formatProductPrice}</td>
                <td>${product.quantityInStore}</td>
                <td id="listproductbutton">
                    <form class="tablebutton" action="productmanager" method="get">
                        <input type="hidden" name="action" value="editproduct">
                        <input type="hidden" name="productID" 
                               value="<c:out value='${product.productID}'/>">                       
                        <input type="submit" value="Edit">
                    </form>
<!--                    <form class="tablebutton" action="productmanager" method="post">
                        <input type="hidden" name="action" value="deleteproduct">                       
                        <input type="hidden" name="productID" 
                               value="<c:out value='${product.productID}'/>">                      
                        <input type="submit" value="Delete">
                    </form>                   -->
                </td>
            </tr>
        </c:forEach>
    </table>
    <br style="clear: both;">
    <div id="products_button2">
        <c:if test="${pages>0}">
            <c:forEach var="i" begin="0" end="${pages}">
                <form action="<c:url value="/productmanager"/>" method="post">
                    <input type="hidden" name="action" value="${action}">
                    <input type="hidden" name="searchText" value="${searchText}">
                    <input type="hidden" name="page" value="${i}">
                    <input type="submit" value="${i+1}">                     
                </form>
            </c:forEach>
        </c:if>
    </div>
</section>

<c:import url="/footer.jsp"/>