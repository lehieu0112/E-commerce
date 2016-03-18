package controller;

import business.ChangeableString;
import java.time.LocalDate;
import business.Cart;
import business.Customer;
import business.Invoices;
import business.LineItem;
import data.CustomerDB;
import data.OrderDB;
import data.ProductDB;
import java.io.IOException;
import java.sql.Date;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.MailUtil;

@WebServlet(name = "OrderProcessingServlet", urlPatterns = {"/orderprocessing"})
public class OrderProcessingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        ChangeableString url = new ChangeableString("/homepage");
        String isLogin = (String) session.getAttribute("isLogin");
        Cart cart = (Cart) session.getAttribute("cart");
        ChangeableString ordermessage = new ChangeableString("");
        ChangeableString registermessage = new ChangeableString("");
        ChangeableString paymessage = new ChangeableString("");
        ChangeableString quantitymessage = new ChangeableString("");
        if ((action.equals("saveorder")) && (isLogin.equals("false"))) {
            url.changeTo("/cart.jsp");
            Customer customer = getCustomer(request);
            orderProcessing(session, request, cart, url, ordermessage,
                    registermessage, customer, paymessage, quantitymessage, 0);

        } else if ((action.equals("pay")) && (isLogin.equals("false"))) {
            url.changeTo("/cart.jsp");
            Customer customer = getCustomer(request);

            if (creditCardProcessing(customer.getCustomerName(), customer.getCustomerAddress(),
                    customer.getCustomerPhone(), request.getParameter("cctype"),
                    request.getParameter("ccnumbers"), request.getParameter("expiremonth"),
                    request.getParameter("expireyear"), request.getParameter("cvvnumbers"))) {

                orderProcessing(session, request, cart, url, ordermessage,
                        registermessage, customer, paymessage, quantitymessage, 1);

            } else {
                orderProcessing(session, request, cart, url, ordermessage,
                        registermessage, customer, paymessage, quantitymessage, 0);
                paymessage.changeTo("Your Credit Card is not valid !");
            }

        } else if ((action.equals("saveorder")) && (isLogin.equals("true"))) {
            url.changeTo("/cart.jsp");
            Customer customer = (Customer) session.getAttribute("customer");
            orderProcessing(session, request, cart, url, ordermessage,
                    registermessage, customer, paymessage, quantitymessage, 0);
            registermessage.changeTo("");

        } else if ((action.equals("pay")) && (isLogin.equals("true"))) {
            url.changeTo("/cart.jsp");
            Customer customer = (Customer) session.getAttribute("customer");

            if (creditCardProcessing(request.getParameter("ccName"), request.getParameter("ccAddress"),
                    request.getParameter("ccPhone"), request.getParameter("cctype"),
                    request.getParameter("ccnumbers"), request.getParameter("expiremonth"),
                    request.getParameter("expireyear"), request.getParameter("cvvnumbers"))) {

                orderProcessing(session, request, cart, url, ordermessage,
                        registermessage, customer, paymessage, quantitymessage, 1);

            } else {
                orderProcessing(session, request, cart, url, ordermessage,
                        registermessage, customer, paymessage, quantitymessage, 0);
                paymessage.changeTo("Your Credit Card is not valid !");
            }
            registermessage.changeTo("");
        } else if ((action.equals("paylater")) && (isLogin.equals("true"))) {
            if (creditCardProcessing(request.getParameter("ccName"), request.getParameter("ccAddress"),
                    request.getParameter("ccPhone"), request.getParameter("cctype"),
                    request.getParameter("ccnumbers"), request.getParameter("expiremonth"),
                    request.getParameter("expireyear"), request.getParameter("cvvnumbers"))) {
                OrderDB oDB = new OrderDB();
                int invoiceID = Integer.parseInt(request.getParameter("invoiceID"));
                oDB.updateInvoiceIsPaid(invoiceID);
                paymessage.changeTo("Your Credit Card is valid ! Your order will be shipping soon");
            }else{
                paymessage.changeTo("Your Credit Card is not valid !");
            }
            url.changeTo("/thanks.jsp");
        }
        request.setAttribute("ordermessage", ordermessage.toString());
        request.setAttribute("registermessage", registermessage.toString());
        request.setAttribute("paymessage", paymessage.toString());
        request.setAttribute("quantitymessage", quantitymessage.toString());

        getServletContext().getRequestDispatcher(url.toString()).forward(request, response);
    }

    public Customer getCustomer(HttpServletRequest request) {
        String customerName = request.getParameter("customerName");
        String customerEmail = request.getParameter("customerEmail");
        String customerPassword = request.getParameter("customerPassword");
        String customerPhone = request.getParameter("customerPhone");
        String customerAddress = request.getParameter("customerAddress");
        String customerNote = request.getParameter("customerNote");
        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        customer.setCustomerEmail(customerEmail);
        customer.setCustomerPassword(customerPassword);
        customer.setCustomerAddress(customerAddress);
        customer.setCustomerPhone(customerPhone);
        customer.setCustomerNote(customerNote);
        return customer;
    }

    public boolean checkQuantity(Cart cart) {
        boolean isHave = true;
        ProductDB pDB = new ProductDB();
        for (LineItem item : cart.getItems()) {
            if (!pDB.checkQuantityInStore(item.getProduct().getProductID(),
                    item.getQuantity())) {
                isHave = false;
            }
        }
        return isHave;
    }

    public void orderProcessing(HttpSession session, HttpServletRequest request, Cart cart,
            ChangeableString url, ChangeableString ordermessage, ChangeableString registermessage, Customer customer,
            ChangeableString paymessage, ChangeableString quantitymessage, int choose) {
        CustomerDB cDB = new CustomerDB();
        ChangeableString idString = new ChangeableString("");
        if (cart != null) {
            int quantity = 0;
            Invoices invoice = new Invoices();;
            for (LineItem item : cart.getItems()) {
                quantity += item.getQuantity();
            }
            if ((quantity > 0) && (checkQuantity(cart))) {
                if (cDB.isExistCustomer(customer.getCustomerEmail(), idString)) {
                    int id = Integer.parseInt(idString.toString());
                    customer.setCustomerID(id);
                    registermessage.changeTo("Your email is exist in my system !");
                    addOrder(session, invoice, customer, cart, ordermessage, paymessage, choose);
                } else if (cDB.insertCustomer(customer)) {
                    registermessage.changeTo("Register success !Please check your email to active !");

                    String to = customer.getCustomerEmail();
                    String from = "iviettechwebshopdemo@gmail.com";
                    String subject = "Active Acount Webshop";
                    String body = "Dear " + customer.getCustomerName() + ",\n\n"
                            + "Thanks for register from us. "
                            + "You should check this link to active your account to login webshop.\n"
                            + "http://localhost:8080/Project1/activationemail?action=activeEmail&customerEmail="
                            + customer.getCustomerEmail() + "\n"
                            + "Please contact us if you have any questions.\n"
                            + "Have a great day and thanks again!\n";

                    boolean isBodyHTML = false;
                    try {
                        MailUtil.sendEmail(to, from, subject, body, isBodyHTML);
                        request.setAttribute("emailmessage", "Send Email Success!");
                    } catch (MessagingException e) {
                        request.setAttribute("emailmessage", "Send Email Failed!");
                    }

                    addOrder(session, invoice, customer, cart, ordermessage, paymessage, choose);
                } else {
                    registermessage.changeTo("Register failed !");
                    ordermessage.changeTo("Saving order is failed !");
                }
                url.changeTo("/thanks.jsp");
            }
            if (!checkQuantity(cart)) {
                quantitymessage.changeTo("Quantity in Store is not enough !");
            }
            if (quantity <= 0) {
                ordermessage.changeTo("Your Cart is empty !");
            }
        } else {
            ordermessage.changeTo("Your Cart is empty !");
        }
    }

    public void addOrder(HttpSession session, Invoices invoice, Customer customer,
            Cart cart, ChangeableString ordermessage, ChangeableString paymessage, int choose) {
        OrderDB oDB = new OrderDB();
        invoice.setCustomer(customer);
        invoice.setItems(cart.getItems());
        LocalDate currentDate = LocalDate.now();
        Date date = Date.valueOf(currentDate);
        invoice.setInvoiceDate(date);
        invoice.setShippingProcess("not_shipping");
        invoice.setIsPaid(false);
        double invoiceTotal = 0;
        for (LineItem item : cart.getItems()) {
            invoiceTotal += item.getLineItemAmount();
        }
        invoice.setInvoiceTotal(invoiceTotal);
        if (choose == 0) {
            invoice.setIsPaid(false);
        } else if (choose == 1) {
            invoice.setIsPaid(true);
        }
        if (oDB.insertInvoice(invoice)) {
            ordermessage.changeTo("Saving order is success !");
            if (choose == 0) {
                paymessage.changeTo("");
            } else if (choose == 1) {
                paymessage.changeTo("Your Credit Card is valid ! Your order will be shipping soon");
            }
            Cart newCart = new Cart();
            session.setAttribute("cart", newCart);
            Invoices newInvoice = new Invoices();
            invoice.setItems(newCart.getItems());

        } else {
            ordermessage.changeTo("Saving order is failed !");
        }
    }

    public boolean creditCardProcessing(String ccName, String ccAddress,
            String ccPhone, String cctype, String ccnumbers, String expiremonth,
            String expireyear, String cvvnumbers) {
        boolean isValid = false;
        isValid = true;
        return isValid;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
