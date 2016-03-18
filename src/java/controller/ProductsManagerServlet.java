package controller;

import business.Categories;
import business.Manufacture;
import business.Products;
import business.Promotions;
import data.CategoryDB;
import data.ManufactureDB;
import data.ProductDB;
import data.PromotionDB;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.FormValidator;

@WebServlet(name = "ProductsManagerServlet", urlPatterns = {"/productmanager"})
public class ProductsManagerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        CategoryDB caDB = new CategoryDB();
        ManufactureDB mDB = new ManufactureDB();
        PromotionDB proDB = new PromotionDB();
        String url = "/login";
        String login = (String) session.getAttribute("login");
        String role = (String) session.getAttribute("userRole");
        if ((login != null) && (role != null)) {
            if (login.equals("true")) {
                if ((role.equals("salemanager"))) {

                    String action = request.getParameter("action");
                    ArrayList<Products> productsList = new ArrayList<>();
                    ProductDB pDB = new ProductDB();
                    FormValidator validator = new FormValidator();
                    url = "/salemanager/salemanager.jsp";
                    int productsPerPage = 10;
                    int pages = 0;
                    int page;
                    if (action == null) {
                        action = "";
                    }
                    if (action.equals("listproducts")) {
                        pages = pDB.getListPages(productsPerPage, 0);
                        page = getPage(request.getParameter("page"));
                        productsList = pDB.getProductsList(page, productsPerPage, 0);
                        request.setAttribute("productsList", productsList);
                        request.setAttribute("pages", pages);
                        request.setAttribute("action", action);
                        url = "/salemanager/listallproducts.jsp";

                    } else if (action.equals("searchproduct")) {
                        String searchText = request.getParameter("searchText");
                        pages = pDB.getSearchPages(searchText, "", "", productsPerPage, 0);
                        page = getPage(request.getParameter("page"));
                        productsList = pDB.searchProducts(searchText, "", "", page, productsPerPage, 0);
                        request.setAttribute("productsList", productsList);
                        request.setAttribute("pages", pages);
                        request.setAttribute("action", action);
                        request.setAttribute("searchText", searchText);
                        url = "/salemanager/listallproducts.jsp";

                    } else if (action.equals("editproduct")) {
                        String productIDString = request.getParameter("productID");
                        if ((productIDString != null) && (validator.isPresent(productIDString))
                                && (validator.isInterger(productIDString))) {
                            int productID = Integer.parseInt(productIDString);
                            Products product = pDB.getProduct(productID);
                            String categoryName = caDB.getCategoryName(product.getCategoryID());
                            String manufactureName = mDB.getManufactureName(product.getManufactureID());
                            request.setAttribute("categoryName", categoryName);
                            request.setAttribute("manufactureName", manufactureName);
                            request.setAttribute("product", product);
                            url = "/salemanager/editproduct.jsp";
                            ArrayList<Categories> categoryList = caDB.getCategoriesList();
                            ArrayList<Manufacture> manufactureList = mDB.getManufactureList();
                            request.setAttribute("categoryList", categoryList);
                            request.setAttribute("manufactureList", manufactureList);
                        }
                    } else if (action.equals("updateproduct")) {
                        String categoryIDString = request.getParameter("categoryID");
                        String manufactureIDString = request.getParameter("manufactureID");
                        String productName = request.getParameter("productName");
                        String productDescription = request.getParameter("productDescription");
                        String productPriceString = request.getParameter("productPrice");
                        String modelYearString = request.getParameter("modelYear");
                        String quantityInStoreString = request.getParameter("quantityInStore");
                        String productWarrantyString = request.getParameter("productWarranty");
                        String promotionIDString = request.getParameter("promotionID");
                        String pictureLink = request.getParameter("pictureLink");
                        if ((validator.isPresent(categoryIDString)) && (validator.isPresent(manufactureIDString))
                                && (validator.isPresent(productName)) && (validator.isPresent(productDescription))
                                && (validator.isPresent(productPriceString)) && (validator.isPresent(modelYearString))
                                && (validator.isPresent(quantityInStoreString)) && (validator.isPresent(productWarrantyString))
                                && (validator.isPresent(promotionIDString)) && (validator.isPresent(pictureLink))) {
                            if ((validator.isInterger(categoryIDString)) && (validator.isInterger(manufactureIDString))
                                    && (validator.isInterger(modelYearString)) && (validator.isInterger(quantityInStoreString))
                                    && (validator.isInterger(productWarrantyString)) && (validator.isInterger(promotionIDString))
                                    && (validator.isDouble(productPriceString))) {

                                int categoryID = Integer.parseInt(categoryIDString);
                                int manufactureID = Integer.parseInt(manufactureIDString);
                                double productPrice = Double.parseDouble(productPriceString);
                                int modelYear = Integer.parseInt(modelYearString);
                                int quantityInStore = Integer.parseInt(quantityInStoreString);
                                int productWarranty = Integer.parseInt(productWarrantyString);
                                int promotionID = Integer.parseInt(promotionIDString);
                                Products product = new Products(categoryID, manufactureID,
                                        productName, productDescription, productPrice,
                                        modelYear, quantityInStore, productWarranty,
                                        promotionID, pictureLink);
                                String productIDString = request.getParameter("productID");
                                if ((validator.isPresent(productIDString))
                                        && (validator.isInterger(productIDString))) {
                                    int productID = Integer.parseInt(productIDString);
                                    if (pDB.updateProduct(product, productID)) {
                                        request.setAttribute("editmessage", "Update Success !");
                                    } else {
                                        request.setAttribute("editmessage", "Update Failed !");
                                    }
                                    Products product1 = pDB.getProduct(productID);
                                    request.setAttribute("product", product1);
                                    url = "/salemanager/editproduct.jsp";
                                    ArrayList<Categories> categoryList = caDB.getCategoriesList();
                                    ArrayList<Manufacture> manufactureList = mDB.getManufactureList();
                                    request.setAttribute("categoryList", categoryList);
                                    request.setAttribute("manufactureList", manufactureList);
                                    String categoryName = caDB.getCategoryName(product1.getCategoryID());
                                    String manufactureName = mDB.getManufactureName(product1.getManufactureID());
                                    request.setAttribute("categoryName", categoryName);
                                    request.setAttribute("manufactureName", manufactureName);
                                }
                            }
                        }
                    } else if (action.equals("addnewproduct")) {
                        url = "/salemanager/addproduct.jsp";
                        ArrayList<Categories> categoryList = caDB.getCategoriesList();
                        ArrayList<Manufacture> manufactureList = mDB.getManufactureList();
                        ArrayList<Promotions> promotionsList = proDB.getPromotionList();
                        request.setAttribute("categoryList", categoryList);
                        request.setAttribute("promotionsList", promotionsList);
                        request.setAttribute("manufactureList", manufactureList);

                    } else if (action.equals("addproduct")) {
                        String productIDString = request.getParameter("productID");
                        String categoryIDString = request.getParameter("categoryID");
                        String manufactureIDString = request.getParameter("manufactureID");
                        String productName = request.getParameter("productName");
                        String productDescription = request.getParameter("productDescription");
                        String productPriceString = request.getParameter("productPrice");
                        String modelYearString = request.getParameter("modelYear");
                        String quantityInStoreString = request.getParameter("quantityInStore");
                        String productWarrantyString = request.getParameter("productWarranty");
                        String promotionIDString = request.getParameter("promotionID");
                        String pictureLink = request.getParameter("pictureLink");
                        if ((validator.isPresent(categoryIDString)) && (validator.isPresent(manufactureIDString))
                                && (validator.isPresent(productName)) && (validator.isPresent(productDescription))
                                && (validator.isPresent(productPriceString)) && (validator.isPresent(modelYearString))
                                && (validator.isPresent(quantityInStoreString)) && (validator.isPresent(productWarrantyString))
                                && (validator.isPresent(promotionIDString)) && (validator.isPresent(pictureLink))
                                && (validator.isPresent(productIDString))) {
                            if ((validator.isInterger(categoryIDString)) && (validator.isInterger(manufactureIDString))
                                    && (validator.isInterger(modelYearString)) && (validator.isInterger(quantityInStoreString))
                                    && (validator.isInterger(productWarrantyString)) && (validator.isInterger(promotionIDString))
                                    && (validator.isDouble(productPriceString)) && (validator.isInterger(productIDString))) {
                                int productID = Integer.parseInt(productIDString);
                                int categoryID = Integer.parseInt(categoryIDString);
                                int manufactureID = Integer.parseInt(manufactureIDString);
                                double productPrice = Double.parseDouble(productPriceString);
                                int modelYear = Integer.parseInt(modelYearString);
                                int quantityInStore = Integer.parseInt(quantityInStoreString);
                                int productWarranty = Integer.parseInt(productWarrantyString);
                                int promotionID = Integer.parseInt(promotionIDString);
                                Products product = new Products(categoryID, manufactureID,
                                        productName, productDescription, productPrice,
                                        modelYear, quantityInStore, productWarranty,
                                        promotionID, pictureLink);
                                product.setProductID(productID);
                                if (pDB.insertProduct(product)) {
                                    request.setAttribute("addmessage", "Add Product Success !");
                                } else {
                                    request.setAttribute("addmessage", "Add Product Failed !");
                                }
                                url = "/salemanager/addproduct.jsp";
                                ArrayList<Categories> categoryList = caDB.getCategoriesList();
                                ArrayList<Manufacture> manufactureList = mDB.getManufactureList();
                                request.setAttribute("categoryList", categoryList);
                                request.setAttribute("manufactureList", manufactureList);
                            }
                        }
                    } else if (action.equals("deleteproduct")) {
                        String productIDString = request.getParameter("productID");
                        if ((validator.isPresent(productIDString)) && (validator.isInterger(productIDString))) {
                            int productID = Integer.parseInt(productIDString);
                            if (pDB.deleteProduct(productID)) {
                                request.setAttribute("deletemessage", "Delete Success !");
                            } else {
                                request.setAttribute("deletemessage", "Delete Failed !");
                            }
                            url = "/productmanager?action=listproducts";
                        }
                    } else if (action.equals("listcategory")) {
                        ArrayList<Categories> categoryList = caDB.getCategoriesList();
                        request.setAttribute("categoryList", categoryList);
                        url = "/salemanager/listcategories.jsp";
                    } else if (action.equals("listmanufacture")) {
                        ArrayList<Manufacture> manufactureList = mDB.getManufactureList();
                        request.setAttribute("manufactureList", manufactureList);
                        url = "/salemanager/listmanufacture.jsp";
                    }else if (action.equals("addmanufacture")) {
                        String manufacture = request.getParameter("manufacture");
                        mDB.insertManufacture(manufacture);
                        ArrayList<Manufacture> manufactureList = mDB.getManufactureList();
                        request.setAttribute("manufactureList", manufactureList);
                        url = "/salemanager/listmanufacture.jsp";
                    }else if (action.equals("addcategory")) {
                        String category = request.getParameter("category");
                        caDB.insertCategory(category);
                        ArrayList<Categories> categoryList = caDB.getCategoriesList();
                        request.setAttribute("categoryList", categoryList);
                        url = "/salemanager/listcategories.jsp";
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
