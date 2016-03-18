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
import util.FormValidator;

@WebServlet(name = "ProductsListServlet", urlPatterns = {"/productsList"})
public class ProductsListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        ProductDB pDB = new ProductDB();
        ArrayList<Products> productsList = new ArrayList<>();
        int pages=0;
        int productsPerPage = 10;
        String url = "/homepage";

        if (action.equals("list")) {
            String listValue = request.getParameter("listValue");
            
            if((listValue.equals("bestselling"))||(listValue.equals("bestpromotion"))
                    ||(listValue.equals("newproducts"))){
                
                if (listValue.equals("bestselling")) {
                    pages = pDB.getListPages(productsPerPage, 0);
                    int page = getPage(request.getParameter("page"));
                    productsList = pDB.getProductsList(page, productsPerPage, 1);
                    request.setAttribute("listValue", "bestselling");
                } else if (listValue.equals("bestpromotion")) {
                    pages = pDB.getListPages(productsPerPage, 0);
                    int page = getPage(request.getParameter("page"));
                    productsList = pDB.getProductsList(page, productsPerPage, 4);                  
                    request.setAttribute("listValue", "bestpromotion");
                } else if (listValue.equals("newproducts")) {
                    pages = pDB.getListPages(productsPerPage, 0);               
                    int page = getPage(request.getParameter("page"));
                    productsList = pDB.getProductsList(page, productsPerPage, 2);                   
                    request.setAttribute("listValue", "newproducts");                    
                }
                
                request.setAttribute("productsList", productsList);
                request.setAttribute("pages", pages);
                request.setAttribute("formAction", "list");
                url = "/products.jsp";
            }
            
        } else if(action.equals("search")){
            String name = request.getParameter("name");
            String category = request.getParameter("category");
            String manufacture = request.getParameter("manufacture");  
            String listValue = request.getParameter("listValue");
            if(listValue == null){
                listValue = getSearchValue(name, category, manufacture);
            }
            
            if((listValue.equals("searchName"))||(listValue.equals("searchCategory"))
                    ||(listValue.equals("searchCategoryAndManufacture"))){
                
                if(listValue.equals("searchName")){
                    pages = pDB.getSearchPages(name, "", "", productsPerPage, 0);
                    int page = getPage(request.getParameter("page"));
                    productsList = pDB.searchProducts(name, "", "", page, productsPerPage, 0);
                    request.setAttribute("listValue", "searchName");
                    request.setAttribute("name", name);
                }else if(listValue.equals("searchCategory")){
                    pages = pDB.getSearchPages("", "", category, productsPerPage, 2);
                    int page = getPage(request.getParameter("page"));
                    productsList = pDB.searchProducts("", "", category, page, productsPerPage, 2);
                    request.setAttribute("listValue", "searchCategory");
                    request.setAttribute("category", category);
                }else if(listValue.equals("searchCategoryAndManufacture")){
                    pages = pDB.getSearchPages("", manufacture, category, productsPerPage, 3);
                    int page = getPage(request.getParameter("page"));
                    productsList = pDB.searchProducts("", manufacture, category, page, productsPerPage, 3);
                    request.setAttribute("listValue", "searchCategoryAndManufacture");
                    request.setAttribute("category", category);
                    request.setAttribute("manufacture", manufacture);
                }
                request.setAttribute("productsList", productsList);
                request.setAttribute("pages", pages); 
                request.setAttribute("formAction", "search");
                url = "/products.jsp";
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
    
    public String getSearchValue(String name,String category,String manufacture){
        String listValue = "";
        
        if((name!=null)&&(category==null)&&(manufacture==null)){
                listValue = "searchName";
            }else if((name==null)&&(category!=null)&&(manufacture==null)){
                listValue = "searchCategory";
            }else if((name==null)&&(category!=null)&&(manufacture!=null)){
                listValue = "searchCategoryAndManufacture";
            }
        return listValue;
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
