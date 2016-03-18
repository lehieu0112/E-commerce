package controller;

import business.ChangeableString;
import business.Customer;
import business.EmailKey;
import data.CustomerDB;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.MailUtil;

@WebServlet(urlPatterns = {"/activationemail"})
public class ActivationEmailServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        CustomerDB cDB = new CustomerDB();
        ChangeableString id = new ChangeableString("");
        Customer c = new Customer();
        int keyID;
        if (action == null) {
            action = "active";
        }
        String url = "/homepage";
        if (action.equals("activeEmail")) {
            String customerEmail = request.getParameter("customerEmail");
            if (cDB.isActive(customerEmail)) {
                request.setAttribute("activemessage", "Active Success!");
            } else {
                request.setAttribute("activemessage", "Active Failed!");
            }
            url = "/homepage";
        } else if (action.equals("sendEmail")) {
            String customerEmail = request.getParameter("customerEmail");
            if (!cDB.isExistCustomer(customerEmail, id)) {
                request.setAttribute("checkmessage", "Email is not exist!");
            } else if (!cDB.checkActive(customerEmail)) {
                request.setAttribute("checkmessage", "Account is not active, please check your email again!");
            } else {
                keyID = (int) (Math.random() * 1000);
                EmailKey ek = new EmailKey(keyID, customerEmail);
                cDB.insertKey(ek);

                String to = customerEmail;
                String from = "iviettechwebshopdemo@gmail.com";
                String subject = "Reset Your Password from Webshop";
                String body = "Thanks for use webshop from us. "
                        + "You must check this link to reset your password to login webshop.\n"
                        + "http://localhost:8080/Project1/activationemail?action=resetform&customerEmail=" + customerEmail
                        + "&keyID=" + keyID + "\n"
                        + "Please contact us if you have any questions.\n"
                        + "Have a great day and thanks again!\n";

                boolean isBodyHTML = false;
                try {
                    MailUtil.sendEmail(to, from, subject, body, isBodyHTML);
                    request.setAttribute("emailmessage", "Send Email Success!");
                } catch (MessagingException e) {
                    request.setAttribute("emailmessage", "Send Email Failed!");
                }
                request.setAttribute("checkmessage", "Please check your email to reset password");

            }
            url = "/forgotpassword.jsp";
        } else if (action.equals("resetform")) {
            String customerEmail = request.getParameter("customerEmail");
            String ekString = request.getParameter("keyID");
            keyID = Integer.parseInt(ekString);

            if (cDB.checkKey(keyID, customerEmail)) {

                url = "/resetpassword.jsp";
                request.setAttribute("customerEmail", customerEmail);
            } else {
                url = "/homepage";
            }

        } else if (action.equals("resetPassword")) {
            String customerEmail = request.getParameter("customerEmail");
            String customerPassword = request.getParameter("customerPassword");
            String customerRePassword = request.getParameter("customerRePassword");
            if (customerPassword.equals(customerRePassword)) {
                if (cDB.updateCustomerPassword(customerEmail, customerPassword)) {
                    request.setAttribute("errormessage", "Reset Password Success!");
                    cDB.deleteKey(customerEmail);
                }else{
                    request.setAttribute("errormessage", "Reset Password Failed! Please try again");
                }
            } else {
                request.setAttribute("errormessage", "Password and re-password does not match!");
            }
            url = "/resetpassword.jsp";
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
