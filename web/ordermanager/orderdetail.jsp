<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/header_management.jsp"/>
<c:import url="left_aside_order.jsp"/>

<section class="management_page">
    <h1>${updatemessage}</h1>
    <p><strong>Invoice ID : </strong>${invoice.invoiceID}</p><br>
    <p><strong>Customer Name : </strong>${invoice.customer.customerName}</p><br>
    <p><strong>Customer Email : </strong>${invoice.customer.customerEmail}</p><br>
    <p><strong>Customer Address : </strong>${invoice.customer.customerAddress}</p><br>
    <p><strong>Customer Phone : </strong>${invoice.customer.customerPhone}</p><br>
    <p><strong>Customer Note : </strong>${invoice.customer.customerNote}</p><br>
    <p><strong>Invoice Date : </strong>${invoice.invoiceDate}</p><br>
    <p><strong>Invoice Total : </strong>${invoice.formatInvoiceTotal}</p><br>
    <p><strong>Shipping Process : </strong>${invoice.shippingProcess}</p><br>
    <p><strong>Shipping Date : </strong>${invoice.shippingDate}</p><br>
    <p><strong>Is Paid : </strong>${invoice.isPaid}</p><br>
    <table>
        <tr>
            <th>Quantity</th>
            <th>Product Name</th>
            <th>Unit Price</th>
            <th>Total</th>       
        </tr>
        <c:forEach var="item" items="${invoice.items}">
            <tr>
                <td>${item.quantity}</td>
                <td>${item.product.productName}</td>
                <td>${item.product.formatProductPrice}</td>
                <td>${item.formatLineItemAmount}</td>
            </tr>
        </c:forEach>
    </table><br>
    <form action="<c:url value="/ordermanager"/>" method="post">
        <input type="hidden" name="action" value="setispaid">
        <input type="hidden" name="invoiceID" 
               value="<c:out value='${invoice.invoiceID}'/>">                       
        <input type="submit" value="Set is Paid">
    </form>   
    <form action="<c:url value="/ordermanager"/>" method="post">
        <input type="hidden" name="action" value="setshippingprocess">
        <input type="hidden" name="invoiceID" 
               value="<c:out value='${invoice.invoiceID}'/>"> 
        <select name="shippingprocess">
            <option value="is_shipping">is_shipping</option>
            <option value="delivered">delivered</option>
        </select>
        <input type="submit" value="Set shipping Process">
    </form>
    <form action="<c:url value="/ordermanager"/>" method="post">
        <input type="hidden" name="action" value="setshippingdate">
        <input type="text" name="shippingdate" placeholder="2015-12-31" required
                   pattern="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))">
        <input type="hidden" name="invoiceID" 
               value="<c:out value='${invoice.invoiceID}'/>">                       
        <input type="submit" value="Set shipping Date">
    </form>   

</section>

<c:import url="/footer.jsp"/>