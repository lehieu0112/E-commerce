package controller;

import business.Cart;
import business.LineItem;
import business.Products;
import data.ProductDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.FormValidator;

@WebServlet(name = "ShoppingCartServlet", urlPatterns = {"/shoppingcart"})
public class ShoppingCartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        ProductDB pDB = new ProductDB();
        Products product;
        String url = "/homepage";
        if ((action.equals("add")) || (action.equals("update"))
                || (action.equals("remove"))) {
            String productIDString = request.getParameter("productID");
            FormValidator validator = new FormValidator();
            
            if ((validator.isPresent(productIDString))
                    && (validator.isInterger(productIDString))) {
                
                int productID = Integer.parseInt(productIDString);
                String quantityString = request.getParameter("quantity");
                HttpSession session = request.getSession();
                Cart cart = (Cart) session.getAttribute("cart");
                if (cart == null) {
                    cart = new Cart();
                }
                int quantity;
                try {
                    quantity = Integer.parseInt(quantityString);
                    if (quantity < 0) {
                        quantity = 1;
                    }
                } catch (NumberFormatException e) {
                    quantity = 1;
                }
                product = pDB.getProduct(productID);

                LineItem lineItem = new LineItem();
                lineItem.setProduct(product);
                lineItem.setQuantity(quantity);
                lineItem.setLineItemAmount((lineItem.getQuantity())
                        *(lineItem.getProduct().getPromotionPrice()));
                
                if ((quantity > 0) && (action.equals("add"))) {
                    cart.addItem(lineItem);
                } else if ((quantity > 0) && (action.equals("update"))) {
                    cart.updateItem(lineItem);
                } else if (quantity == 0) {
                    cart.removeItem(lineItem);
                }
                session.setAttribute("cart", cart);
                double orderTotal = 0;
                for(LineItem item: cart.getItems()){
                    orderTotal+=item.getLineItemAmount();
                }
                NumberFormat c = NumberFormat.getNumberInstance();
                request.setAttribute("orderTotal", c.format(orderTotal));
                
                url = "/cart.jsp";
            }
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
