
package controller;

import business.Products;
import data.ProductDB;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.FormValidator;


@WebServlet(name = "ProductsCompareServlet", urlPatterns = {"/productsCompare"})
public class ProductsCompareServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        ProductDB pDB = new ProductDB();
        String url = "/homepage";
        ArrayList<Products> productsList = new ArrayList<>();
        HttpSession session = request.getSession();
        
        if (action.equals("compare")){
            String productIDString = request.getParameter("productID");
            int productID = Integer.parseInt(productIDString);
            Products product1 = pDB.getProduct(productID);
            request.setAttribute("product1", product1);
            url = "/productscompare.jsp";
            int pages = pDB.getListPages(8, 0);
            int page = getPage(request.getParameter("page"));
            productsList = pDB.getProductsList(page, 8, 0);
            request.setAttribute("productsList", productsList);
            request.setAttribute("pages", pages);
            
        }else if(action.equals("search")){
            String product1IDString = request.getParameter("product1ID");
            int product1ID = Integer.parseInt(product1IDString);
            Products product1 = pDB.getProduct(product1ID);
            String name = request.getParameter("name");
            int pages = pDB.getSearchPages(name,"","",8, 0);
            int page = getPage(request.getParameter("page"));
            productsList = pDB.searchProducts(name,"","",page,8,0);
            request.setAttribute("productsList", productsList);
            request.setAttribute("pages", pages);
            request.setAttribute("product1", product1);
            request.setAttribute("name", name);
            url = "/productscompare.jsp";
            
        }else if(action.equals("get")){ 
            String product1IDString = request.getParameter("product1ID");
            String productIDString = request.getParameter("productID");
            int productID = Integer.parseInt(productIDString);
            int product1ID = Integer.parseInt(product1IDString);
            Products product2 = pDB.getProduct(productID);
            Products product1 = pDB.getProduct(product1ID);
            request.setAttribute("product1", product1);
            request.setAttribute("product2", product2);
            url = "/productscompare.jsp";
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
