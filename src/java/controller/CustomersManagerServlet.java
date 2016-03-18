package controller;

import business.Customer;
import data.CustomerDB;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.FormValidator;

@WebServlet(name = "CustomersManagerServlet", urlPatterns = {"/customermanager"})
public class CustomersManagerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String url = "/login";
        String login = (String) session.getAttribute("login");
        String role = (String) session.getAttribute("userRole");
        if ((login != null) && (role != null)) {
            if (login.equals("true")) {
                if ((role.equals("salemanager"))) {

                    String action = request.getParameter("action");
                    url = "/salemanager/salemanager.jsp";
                    CustomerDB cDB = new CustomerDB();
                    int pages = 0;
                    int customersPerPage = 7;
                    ArrayList<Customer> customerList = new ArrayList<>();
                    if (action == null) {
                        action = "";
                    } else if (action.equals("login")) {

                    } else if (action.equals("home")) {
                        url = "/homepage";
                    } else if (action.equals("viewCustomer")) {
                        pages = cDB.getListPages(customersPerPage);
                        int page = getPage(request.getParameter("page"));
                        customerList = cDB.getCustomers(page, customersPerPage);

                        request.setAttribute("customerList", customerList);
                        request.setAttribute("pages", pages);
                        request.setAttribute("formAction", "viewCustomer");
                        url = "/salemanager/listallcustomer.jsp";

                    } else if (action.equals("editCustomer")) {
                        String customerName = request.getParameter("customerName");
                        String customerEmail = request.getParameter("customerEmail");
                        String customerPassword = request.getParameter("customerPassword");
                        String customerAddress = request.getParameter("customerAddress");
                        String customerPhone = request.getParameter("customerPhone");
                        String customerNote = request.getParameter("customerNote");
                        String avatarLink = request.getParameter("avatarLink");
                        String isActiveString = request.getParameter("isActive");
                        boolean isActive = Boolean.parseBoolean(isActiveString);

                        Customer editCustomer = new Customer(customerName, customerEmail, customerPassword,
                                customerAddress, customerPhone, customerNote, avatarLink, isActive);
                        request.setAttribute("editCustomer", editCustomer);
                        session.setAttribute("editCustomer", editCustomer);
                        url = "/salemanager/editcustomer.jsp";

                    } else if (action.equals("updateCustomer")) {
                        Customer oldCustomer = (Customer) session.getAttribute("editCustomer");
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
                            if (cDB.updateCustomerForEmailForManager(newCustomer, oldEmail)) {
                                request.setAttribute("editmessage", "Edit Success!");
                                newCustomer = cDB.customerDetail(customerEmail);
                                session.setAttribute("editCustomer", newCustomer);
                            } else {
                                request.setAttribute("editmessage", "Edit Failed!");
                            }
                        } else {
                            request.setAttribute("editmessage", "Please sure password and re-password match!");
                        }

                        url = "/salemanager/editcustomer.jsp";
                    } else if (action.equals("search")) {
                        String searchText = request.getParameter("searchText");
                        pages = cDB.getSearchPage(searchText, customersPerPage);
                        int page = getPage(request.getParameter("page"));
                        customerList = cDB.searchCustomer(searchText, page, customersPerPage);
                        request.setAttribute("customerList", customerList);
                        request.setAttribute("pages", pages);
                        request.setAttribute("formAction", "viewCustomer");
                        request.setAttribute("searchText", searchText);
                        url = "/salemanager/listallcustomer.jsp";

                    } else if (action.equals("promotion")) {
                        url = "/salemanager/promotion.jsp";

                    } else if (action.equals("activeCustomer")) {
                        String customerEmail = request.getParameter("customerEmail");
                        if (cDB.isActive(customerEmail)) {
                            request.setAttribute("editmessage", "Active Success!");
                            pages = cDB.getListPages(customersPerPage);
                            int page = getPage(request.getParameter("page"));
                            customerList = cDB.getCustomers(page, customersPerPage);
                            request.setAttribute("customerList", customerList);
                            request.setAttribute("pages", pages);
                            request.setAttribute("formAction", "viewCustomer");
                        } else {
                            request.setAttribute("editmessage", "Active Failed!");
                        }
                        url = "/salemanager/listallcustomer.jsp";
                    } else if (action.equals("deactiveCustomer")) {
                        String customerEmail = request.getParameter("customerEmail");
                        if (cDB.deActive(customerEmail)) {
                            request.setAttribute("editmessage", "DeActive Success!");
                            pages = cDB.getListPages(customersPerPage);
                            int page = getPage(request.getParameter("page"));
                            customerList = cDB.getCustomers(page, customersPerPage);
                            request.setAttribute("customerList", customerList);
                            request.setAttribute("pages", pages);
                            request.setAttribute("formAction", "viewCustomer");
                        } else {
                            request.setAttribute("editmessage", "DeActive Failed!");
                        }
                        url = "/salemanager/listallcustomer.jsp";
                    }
                }
            }
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    public int getPage(String pageString) {
        FormValidator validator = new FormValidator();
        if ((pageString == null) || (!validator.isPresent(pageString))
                || (!validator.isInterger(pageString))) {
            pageString = "0";
        }
        int page = Integer.parseInt(pageString);
        return page;
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
