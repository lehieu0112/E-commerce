<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/header_management.jsp"/>
<c:import url="left_aside_manager.jsp"/>

<section class="management_page">
    <h1>Customer List</h1>
    <section id="search_form" title="Tìm kiếm sản phẩm">
        <form action="customermanager" method="post">
            <input type="hidden" name="action" value="search">
            <input type="text" name="searchText" placeholder="Search customer">
            <input type="submit" value="Search">
        </form>
    </section>

    <table>
        <tr>
            <th>Full Name</th>
            <th>Email</th>           
            <th>Address</th>
            <th>Phone Number</th>
            <th>Note</th>
            <th>Active</th>
            <th>Action</th>
        </tr>
        <c:forEach var="customer" items="${customerList}">
            <tr>             
                <td>${customer.customerName}</td>
                <td>${customer.customerEmail}</td>                
                <td>${customer.customerAddress}</td>
                <td>${customer.customerPhone}</td>
                <td>${customer.customerNote}</td>
                <td>${customer.isActive}</td>
                <td style="width: 12em;">
                    <form class="tablebutton" action="customermanager" method="post">
                        <input type="hidden" name="action" value="editCustomer">
                        <input type="hidden" name="avatarLink" 
                               value="<c:out value='${customer.avatarLink}'/>">
                        <input type="hidden" name="customerName" 
                               value="<c:out value='${customer.customerName}'/>">
                        <input type="hidden" name="customerEmail" 
                               value="<c:out value='${customer.customerEmail}'/>">
                        <input type="hidden" name="customerPassword" 
                               value="<c:out value='${customer.customerPassword}'/>">
                        <input type="hidden" name="customerAddress" 
                               value="<c:out value='${customer.customerAddress}'/>">
                        <input type="hidden" name="customerPhone" 
                               value="<c:out value='${customer.customerPhone}'/>">
                        <input type="hidden" name="customerNote" 
                               value="<c:out value='${customer.customerNote}'/>">
                        <input type="hidden" name="isActive" 
                               value="<c:out value='${customer.isActive}'/>">
                        <input style="width: 3em;" type="submit" value="Edit">
                    </form>
                    <form class="tablebutton" action="customermanager" method="post">
                        <input type="hidden" name="action" value="activeCustomer">
                        
                        <input type="hidden" name="customerEmail" 
                               value="<c:out value='${customer.customerEmail}'/>">
                        
                        <input  style="width: 3.3em;" type="submit" value="Active">
                    </form>
                    <form class="tablebutton" action="customermanager" method="post">
                        <input type="hidden" name="action" value="deactiveCustomer">
                        
                        <input type="hidden" name="customerEmail" 
                               value="<c:out value='${customer.customerEmail}'/>">                     
                        
                        <input style="width: 4.7em;" type="submit" value="DeActive">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div id="products_button2">
        <c:if test="${pages>0}">
            <c:forEach var="i" begin="0" end="${pages}">
                <form action="<c:url value="/customermanager"/>" method="post">
                    <input type="hidden" name="action" value="${formAction}"> 
                    <input type="hidden" name="searchText" value="${searchText}"> 
                    <input type="hidden" name="page" value="${i}">
                    <input type="submit" value="${i+1}">                     
                </form>
            </c:forEach>
        </c:if>
    </div>
</section>

<c:import url="/footer.jsp"/>