package controller;

import business.Invoices;
import data.OrderDB;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.FormValidator;

@WebServlet(name = "OrderManagerServlet", urlPatterns = {"/ordermanager"})
public class OrderManagerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = "/login";
        String login = (String) session.getAttribute("login");
        String role = (String) session.getAttribute("userRole");
        if ((login != null) && (role != null)) {
            if (login.equals("true")) {
                if ((role.equals("orderprocessing"))
                        || (role.equals("salemanager"))) {

                    String action = request.getParameter("action");
                    ArrayList<Invoices> invoicesList = new ArrayList<>();
                    OrderDB oDB = new OrderDB();
                    url = "/ordermanager/ordermanager.jsp";
                    int invoicesPerPage = 20;
                    int pages = 0;
                    int page;
                    if (action == null) {
                        action = "";
                    }
                    if ((action.equals("search")) || (action.equals("searchname"))
                            || (action.equals("searchdate"))) {
                        if (action.equals("searchname")) {
                            String name = request.getParameter("name");
                            pages = oDB.getSearchPages(invoicesPerPage, name, null, null, 0);
                            page = getPage(request.getParameter("page"));
                            invoicesList = oDB.getSearchOrders(page, invoicesPerPage, name, null, null, 0);
                            request.setAttribute("name", name);
                            request.setAttribute("invoicesList", invoicesList);
                            request.setAttribute("pages", pages);
                            request.setAttribute("searchaction", action);
                        } else if (action.equals("searchdate")) {
                            String name = request.getParameter("name");
                            String startdateString = request.getParameter("startdate");
                            Date startdate = Date.valueOf(startdateString);
                            String enddateString = request.getParameter("startdate");
                            Date enddate = Date.valueOf(enddateString);
                            pages = oDB.getSearchPages(invoicesPerPage, name, startdate, enddate, 1);
                            page = getPage(request.getParameter("page"));
                            invoicesList = oDB.getSearchOrders(page, invoicesPerPage, name, startdate, enddate, 1);
                            request.setAttribute("name", name);
                            request.setAttribute("startdate", startdate);
                            request.setAttribute("enddate", enddate);
                            request.setAttribute("invoicesList", invoicesList);
                            request.setAttribute("pages", pages);
                            request.setAttribute("searchaction", action);
                        } else if (action.equals("search")) {
                            pages = oDB.getListPages(invoicesPerPage);
                            page = getPage(request.getParameter("page"));
                            invoicesList = oDB.getAllOrders(page, invoicesPerPage, false);
                            request.setAttribute("invoicesList", invoicesList);
                            request.setAttribute("pages", pages);
                        }

                        request.setAttribute("searchaction", action);
                        url = "/ordermanager/searchorder.jsp";

                    } else if (action.equals("view")) {
                        String invoiceIDString = request.getParameter("invoiceID");
                        FormValidator validator = new FormValidator();
                        if ((invoiceIDString != null) && (validator.isPresent(invoiceIDString))
                                && (validator.isInterger(invoiceIDString))) {
                            int invoiceID = Integer.parseInt(invoiceIDString);
                            Invoices invoice = oDB.getInvoice(invoiceID);
                            request.setAttribute("invoice", invoice);
                            url = "/ordermanager/orderdetail.jsp";
                        }
                    } else if (action.equals("setispaid")) {
                        String invoiceIDString = request.getParameter("invoiceID");
                        FormValidator validator = new FormValidator();
                        if ((invoiceIDString != null) && (validator.isPresent(invoiceIDString))
                                && (validator.isInterger(invoiceIDString))) {
                            int invoiceID = Integer.parseInt(invoiceIDString);
                            if (oDB.updateInvoiceIsPaid(invoiceID)) {
                                request.setAttribute("updatemessage", "Update success !");
                            } else {
                                request.setAttribute("updatemessage", "Update failed !");
                            }
                            Invoices invoice = oDB.getInvoice(invoiceID);
                            request.setAttribute("invoice", invoice);

                            url = "/ordermanager/orderdetail.jsp";
                        }
                    } else if (action.equals("setshippingprocess")) {
                        String invoiceIDString = request.getParameter("invoiceID");
                        FormValidator validator = new FormValidator();
                        if ((invoiceIDString != null) && (validator.isPresent(invoiceIDString))
                                && (validator.isInterger(invoiceIDString))) {
                            String shippingProcess = request.getParameter("shippingprocess");
                            int invoiceID = Integer.parseInt(invoiceIDString);
                            if (oDB.updateInvoiceShippingProcess(shippingProcess, invoiceID)) {
                                request.setAttribute("updatemessage", "Update success !");
                            } else {
                                request.setAttribute("updatemessage", "Update failed !");
                            }
                            Invoices invoice = oDB.getInvoice(invoiceID);
                            request.setAttribute("invoice", invoice);

                            url = "/ordermanager/orderdetail.jsp";
                        }
                    } else if (action.equals("setshippingdate")) {
                        String invoiceIDString = request.getParameter("invoiceID");
                        FormValidator validator = new FormValidator();
                        if ((invoiceIDString != null) && (validator.isPresent(invoiceIDString))
                                && (validator.isInterger(invoiceIDString))) {
                            String shippingDateString = request.getParameter("shippingdate");
                            Date shippingDate = Date.valueOf(shippingDateString);
                            int invoiceID = Integer.parseInt(invoiceIDString);
                            if (oDB.updateInvoiceShippingDate(shippingDate, invoiceID)) {
                                request.setAttribute("updatemessage", "Update success !");
                            } else {
                                request.setAttribute("updatemessage", "Update failed !");
                            }
                            Invoices invoice = oDB.getInvoice(invoiceID);
                            request.setAttribute("invoice", invoice);

                            url = "/ordermanager/orderdetail.jsp";
                        }
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
