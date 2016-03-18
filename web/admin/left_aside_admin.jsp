<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside class="sidebarA">
    <nav class="left_menu">
        <ul>
            <li><a href="<c:url value="/homepage"/>">Home</a></li>
                <c:if test="${login != null}">
                    <c:if test="${login eq 'true'}">
                        <c:if test="${userRole eq 'administrator'}">
                        <li><a href="<c:url value="/administrator?action=adduser"/>">Create User</a></li>
                        <li><a href="<c:url value="/administrator?action=viewuser"/>">List All Users</a></li>
                        </c:if>
                        <c:if test="${userRole eq 'salemanager'}">
                        <li><a href="<c:url value="/customermanager?action=viewCustomer"/>">List All Customers</a></li></a></li>
                        <li><a href="<c:url value="/productmanager?action=listproducts"/>">List All Products</a></li>
                        <li><a href="<c:url value="/productmanager?action=addnewproduct"/>">Add new Product</a></li>            
                        <li><a href="<c:url value="/ordermanager?action=search"/>">List new Orders</a></li>
                        <li><a href="<c:url value="/productmanager?action=listcategory"/>">List all Categories</a></li>
                        <li><a href="<c:url value="/productmanager?action=listmanufacture"/>">List all Manufacture</a></li>
                        <li><a href="<c:url value="/promotionsmanager?action=listpromotions"/>">List all Promotions</a></li>
                        </c:if>
                        <c:if test="${userRole eq 'orderprocessing'}">            
                        <li><a href="<c:url value="/ordermanager?action=search"/>">List new Orders</a></li>
                        </c:if>
                </c:if>
            </c:if>

        </ul>
    </nav>
</aside>