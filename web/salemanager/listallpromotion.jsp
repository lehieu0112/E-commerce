<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/header_management.jsp"/>
<c:import url="left_aside_manager.jsp"/>

<section class="management_page">
    <h1>Promotions List</h1>

    <table>
        <tr>
            <th>Promotion ID</th>
            <th>Promotion Name</th>           
            <th>Description</th>
            <th>Start Date</th>
            <th>End Date</th>           
            <th>Discount</th>
            <th>Action</th>
        </tr>
        <c:forEach var="promotion" items="${promotionsList}">
            <tr>             
                <td>${promotion.promotionID}</td>
                <td>${promotion.promotionName}</td>                
                <td>${promotion.promotionDescription}</td>
                <td>${promotion.startDay}</td>
                <td>${promotion.endDay}</td>
                <td>${promotion.promotionDiscount}</td>
                <td>
                    <form class="tablebutton" action="promotionsmanager" method="post">
                        <input type="hidden" name="action" value="editpromotion"> 
                        <input type="hidden" name="promotionID" value="${promotion.promotionID}"> 
                        <input style="width: 3em;" type="submit" value="Edit">
                    </form>
                    
                </td>
            </tr>
        </c:forEach>
    </table>
<!--    <div id="products_button2">
        <c:if test="${pages>0}">
            <c:forEach var="i" begin="0" end="${pages}">
                <form action="<c:url value="/customermanager"/>" method="post">
                    <input type="hidden" name="action" value="${formAction}">                
                    <input type="hidden" name="page" value="${i}">
                    <input type="submit" value="${i+1}">                     
                </form>
            </c:forEach>
        </c:if>
    </div>-->
</section>

<c:import url="/footer.jsp"/>
