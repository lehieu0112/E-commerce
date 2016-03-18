<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/header_management.jsp"/>
<c:import url="left_aside_order.jsp"/>

<section class="management_page">
    <div id="searchorder_form">
        <form style="display: inline-block;" action="<c:url value="/ordermanager"/>" method="post">
            <input type="hidden" name="action" value="searchname">
            <label>Name or email :</label>
            <input type="text" name="name" placeholder="customer name or email" required>
            <input type="submit" value="Search">
        </form>
        <form style="float:right;" action="<c:url value="/print"/>" method="post">
            <input type="hidden" name="action" value="print">           
            <input type="submit" value="Export to Excel">
        </form>
    </div><br>
    <div id="searchorder_form">
        <form action="<c:url value="/ordermanager"/>" method="post">
            <input type="hidden" name="action" value="searchdate">
            <label style="clear:left;width:4em;">From : </label>
            <input type="text" name="startdate" placeholder="2015-12-31" required
                   pattern="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))">
            <label style="clear:left;width:4em;">To : </label>
            <input type="text" name="enddate" placeholder="2015-12-31" required
                   pattern="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))">
            <input type="submit" value="Search">
        </form>
    </div><br>
    <table>
        <tr>
            <th>Invoice ID</th>
            <th>Customer Name</th>
            <th>Customer Email</th>
            <th>Invoice Date</th>
            <th>Invoice Total</th>
            <th>Shipping Process</th>
            <th>Shipping Date</th>
            <th>Is Paid</th>
            <th id="action">Action</th>
        </tr>
        <c:forEach var="invoice" items="${invoicesList}">
            <tr>
                <td>${invoice.invoiceID}</td>
                <td>${invoice.customer.customerName}</td>
                <td>${invoice.customer.customerEmail}</td>
                <td>${invoice.invoiceDate}</td>
                <td>${invoice.formatInvoiceTotal}</td>
                <td>${invoice.shippingProcess}</td>
                <td>${invoice.shippingDate}</td>
                <td>${invoice.isPaid}</td>
                <td>
                    <form id="orderbutton" action="<c:url value="/ordermanager"/>" method="post">
                        <input type="hidden" name="action" value="view">
                        <input type="hidden" name="invoiceID" 
                               value="<c:out value='${invoice.invoiceID}'/>">                       
                        <input type="submit" value="View">
                    </form>                  
                </td>
            </tr>
        </c:forEach>    
    </table>
    <br style="clear: both;">
    <div id="products_button2">
        <c:if test="${pages>0}">
            <c:forEach var="i" begin="0" end="${pages}">
                <form action="<c:url value="/ordermanager"/>" method="post">
                    <input type="hidden" name="action" value="${searchaction}">
                    <input type="hidden" name="name" value="${name}">
                    <input type="hidden" name="startdate" value="${startdate}">
                    <input type="hidden" name="enddate" value="${enddate}">
                    <input type="hidden" name="page" value="${i}">
                    <input type="submit" value="${i+1}">                     
                </form>
            </c:forEach>
        </c:if>
    </div>
</section>

<c:import url="/footer.jsp"/>
