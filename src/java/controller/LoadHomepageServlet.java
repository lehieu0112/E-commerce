package controller;

import business.Categories;
import business.Customer;
import business.Manufacture;
import business.Products;
import business.Promotions;
import data.CategoryDB;
import data.CustomerDB;
import data.ManufactureDB;
import data.ProductDB;
import data.PromotionDB;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoadHomepageServlet", urlPatterns = {"/homepage"})
public class LoadHomepageServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDB pDB = new ProductDB();
        CategoryDB cDB = new CategoryDB();
        CustomerDB cusDB = new CustomerDB();
        ManufactureDB mDB = new ManufactureDB();
        PromotionDB proDB = new PromotionDB();
        HttpSession session = request.getSession();
        String customerEmail;
        Cookie[] cookies;
        cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c != null) {
                    if (c.getName().equals("emailCookie")) {
                        if (c.getValue() != null) {
                            customerEmail = c.getValue();
                            Customer customer = cusDB.customerDetail(customerEmail);
                            session.setAttribute("customer", customer);
                            String isLogin = "true";
                            session.setAttribute("isLogin", isLogin);
                        }
                    }
                }
            }
        }
        String isLogin = (String) session.getAttribute("isLogin");
        if (isLogin == null) {
            isLogin = "false";
            session.setAttribute("isLogin", isLogin);
        }

        ArrayList<Products> fiveBestSellingList = pDB.getProductsList(0, 5, 0);
        ArrayList<Products> fiveBestPromotionList = pDB.getProductsList(0, 5, 4);
        ArrayList<Products> fiveNewProductsList = pDB.getProductsList(0, 5, 2);
        ArrayList<Categories> categoryList = cDB.getCategoriesList();
        ArrayList<Manufacture> manufactureList = mDB.getManufactureList();
        ArrayList<Promotions> promotionsList = proDB.getPromotionList();
        request.setAttribute("fiveBestSellingList", fiveBestSellingList);
        request.setAttribute("fiveBestPromotionList", fiveBestPromotionList);
        request.setAttribute("fiveNewProductsList", fiveNewProductsList);
        session.setAttribute("promotionsList", promotionsList);
        session.setAttribute("categoryList", categoryList);
        session.setAttribute("manufactureList", manufactureList);
        String url = "/homepage.jsp";

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
