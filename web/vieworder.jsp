<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="header.jsp"/>
<main>
    <section id="register_page">
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
                    <td>${item.product.productPrice}</td>
                    <td>${item.formatLineItemAmount}</td>
                </tr>
            </c:forEach>
        </table><br>

    </section>

</main>
<c:import url="footer.jsp"/>
