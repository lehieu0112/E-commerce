<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="header.jsp"/>
<main>
    <section id="register_page">
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
                    <td style="width: 9em;">
                        <form class="orderbutton" action="customer" method="get">
                            <input type="hidden" name="action" value="vieworder">
                            <input type="hidden" name="invoiceID" 
                                   value="<c:out value='${invoice.invoiceID}'/>">                       
                            <input style="width:3em;" type="submit" value="View">
                        </form>
                        <c:if test="${invoice.isPaid == false}">                        
                            <form class="orderbutton" action="customer" method="post">
                                <input type="hidden" name="action" value="payorder">
                                <input type="hidden" name="invoiceID" 
                                       value="<c:out value='${invoice.invoiceID}'/>">                       
                                <input style="width:3em;" type="submit" value="Pay">
                            </form> 
                        </c:if>
                    </td>
                </tr>
            </c:forEach>    
        </table>

    </section>

</main>
<c:import url="footer.jsp"/>
