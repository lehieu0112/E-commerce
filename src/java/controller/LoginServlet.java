package controller;

import business.ChangeableString;
import data.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "/admin/login.html";
        if (action == null) {
            action = "";
        }
        if (action.equals("login")) {
            String userName = request.getParameter("username");
            String passwords = request.getParameter("password");
            HttpSession session = request.getSession();
            String login = "false";
            String userRole = "";

            UserDB uDB = new UserDB();
            ChangeableString role = new ChangeableString("");
            if (uDB.checkLogin(userName, passwords, role)) {
                login = "true";
                if (role.toString().equals("administrator")) {
                    url = "/admin/admin.jsp";
                } else if (role.toString().equals("orderprocessing")) {
                    url = "/orderprocessing/ordermanager.jsp";
                } else if (role.toString().equals("salemanager")) {
                    url = "/salemanager/salemanager.jsp";
                }
                userRole = role.toString();
                session.setAttribute("login", login);
                session.setAttribute("userRole", userRole);
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
