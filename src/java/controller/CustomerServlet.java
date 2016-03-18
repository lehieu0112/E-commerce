package controller;

import business.ChangeableString;
import javax.mail.MessagingException;
import business.Customer;
import business.Invoices;
import data.CustomerDB;
import data.OrderDB;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.FormValidator;
import util.MailUtil;

@WebServlet(name = "CustomerServlet", urlPatterns = {"/customer"})
public class CustomerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String url = "/homepage";
        ChangeableString id = new ChangeableString("");
        Customer c;
        CustomerDB cDB = new CustomerDB();
        OrderDB oDB = new OrderDB();
        if (action == null) {
            url = "/homepage";

        } else if (action.equals("registerpage")) {
            url = "/register.jsp";

        } else if (action.equals("register")) {
            String customerName = request.getParameter("customerName");
            String customerEmail = request.getParameter("customerEmail");
            String customerPassword = request.getParameter("customerPassword");
            String customerRePassword = request.getParameter("customerRePassword");
            String customerAddress = request.getParameter("customerAddress");
            String customerPhone = request.getParameter("customerPhone");
            String customerNote = request.getParameter("customerNote");
            
            String isActiveString = request.getParameter("isActive");
            boolean isActive = Boolean.parseBoolean(isActiveString);
            if (customerPassword.equals(customerRePassword)) {
                c = new Customer(customerName, customerEmail, customerPassword,
                        customerAddress, customerPhone, customerNote, isActive);
                if (cDB.isExistCustomer(customerEmail,id)) {
                    request.setAttribute("registermessage", "Email is exist!");
                } else if (cDB.insertCustomer(c)) {
                    request.setAttribute("registermessage", "Register Success!");
                    request.setAttribute("activemessage", "Please check your email to active !");
                    String to = customerEmail;
                    String from = "iviettechwebshopdemo@gmail.com";
                    String subject = "Active Acount Webshop";
                    String body = "Dear " + customerName + ",\n\n"
                            + "Thanks for register from us. "
                            + "You should check this link to active your account to login webshop.\n"
                            + "http://localhost:8080/Project1/activationemail?action=activeEmail&customerEmail="
                            + customerEmail + "\n"
                            + "Please contact us if you have any questions.\n"
                            + "Have a great day and thanks again!\n";

                    boolean isBodyHTML = false;
                    try {
                        MailUtil.sendEmail(to, from, subject, body, isBodyHTML);
                        request.setAttribute("emailmessage", "Send Email Success!");
                    } catch (MessagingException e) {
                        request.setAttribute("emailmessage", "Send Email Failed!");
                    }

                } else {
                    request.setAttribute("registermessage", "Register Failed!");
                }
            } else {
                request.setAttribute("registermessage", "Please sure password and re-password match");
            }
            url = "/register.jsp";

        } else if (action.equals("login")) {
            String customerEmail = request.getParameter("email");
            String customerPassword = request.getParameter("password");
            String rememberMe = request.getParameter("rememberme");
            String isLogin;

            if (cDB.checkLogin(customerEmail, customerPassword)) {
                isLogin = "true";
                c = cDB.customerDetail(customerEmail);
                session.setAttribute("customer", c);
                session.setAttribute("isLogin", isLogin);
                if (rememberMe != null) {
                    if (rememberMe.equals("rememberme")) {
                        Cookie emailCookie = new Cookie("emailCookie", customerEmail);
                        emailCookie.setMaxAge(60 * 60 * 24 * 3);
                        response.addCookie(emailCookie);
                    }
                }
                url = "/homepage";
            } else {
                request.setAttribute("loginmessage", "Email or password is incorrect!");
                request.setAttribute("forgotpassword", "Forgot password?");
            }

        } else if (action.equals("editprofile")) {
            String isLogin = (String) session.getAttribute("isLogin");
            if (isLogin.equals("true")) {
                url = "/editprofile.jsp";
            }

        } else if (action.equals("forgotpassword")) {
            url = "/forgotpassword.jsp";
            
        } else if (action.equals("editorders")) {
            String isLogin = (String) session.getAttribute("isLogin");
            if (isLogin.equals("true")) {
                String customerIDString = request.getParameter("customerID");
                FormValidator validator = new FormValidator();
                if ((customerIDString != null) || (validator.isPresent(customerIDString))
                        || (validator.isInterger(customerIDString))) {
                    int customerID = Integer.parseInt(customerIDString);
                    ArrayList<Invoices> invoicesList = oDB.getInvoicesByCustomerID(customerID);
                    request.setAttribute("invoicesList", invoicesList);
                    url = "/editorders.jsp";
                }
            }

        } else if (action.equals("vieworder")) {
            String isLogin = (String) session.getAttribute("isLogin");
            if (isLogin.equals("true")) {
                String invoiceIDString = request.getParameter("invoiceID");
                FormValidator validator = new FormValidator();
                if ((invoiceIDString != null) || (validator.isPresent(invoiceIDString))
                        || (validator.isInterger(invoiceIDString))) {
                    int invoiceID = Integer.parseInt(invoiceIDString);
                    Invoices invoice = oDB.getInvoice(invoiceID);
                    request.setAttribute("invoice", invoice);
                    url = "/vieworder.jsp";
                }
            }

        } else if (action.equals("updateprofile")) {
            String isLogin = (String) session.getAttribute("isLogin");
            if (isLogin.equals("true")) {
                Customer oldCustomer = (Customer) session.getAttribute("customer");
                String oldEmail = oldCustomer.getCustomerEmail();

                String customerName = request.getParameter("customerName");
                String customerEmail = request.getParameter("customerEmail");
                String customerPassword = request.getParameter("customerPassword");
                String customerRePassword = request.getParameter("customerRePassword");
                String customerAddress = request.getParameter("customerAddress");
                String customerPhone = request.getParameter("customerPhone");
                String customerNote = request.getParameter("customerNote");
                String avatarLink = request.getParameter("avatarLink");
                String isActiveString = request.getParameter("isActive");
                boolean isActive = Boolean.parseBoolean(isActiveString);

                if (customerPassword.equals(customerRePassword)) {
                    Customer newCustomer = new Customer(customerName, customerEmail, customerPassword,
                            customerAddress, customerPhone, customerNote, avatarLink, isActive);
                    if (cDB.updateCustomerForEmail(newCustomer, oldEmail)) {
                        request.setAttribute("editmessage", "Update Success!");
                        newCustomer = cDB.customerDetail(customerEmail);
                        session.setAttribute("customer", newCustomer);
                    } else {
                        request.setAttribute("editmessage", "Update Failed!");
                    }
                } else {
                    request.setAttribute("editmessage", "Please sure password and re-password match!");
                }
            }
            url = "/editprofile.jsp";

        } else if (action.equals("showcart")) {
            url = "/cart.jsp";

        } else if (action.equals("logout")) {
            session.removeAttribute("customer");
            session.setAttribute("isLogin", "false");
            Cookie[] cookies;
            cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie != null) {
                        if (cookie.getName().equals("emailCookie")) {
                            cookie.setValue(null);
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                            break;
                        }
                    }
                }
            }
            url = "/homepage";
        } else if(action.equals("payorder")){
            String invoiceID = request.getParameter("invoiceID");
            url = "/pay.jsp";
            request.setAttribute("invoiceID", invoiceID);
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
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
