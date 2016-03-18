package controller;

import business.User;
import data.UserDB;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AdminServlet", urlPatterns = {"/administrator"})
public class AdminServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = "/login";
        String login = (String) session.getAttribute("login");
        String role = (String) session.getAttribute("userRole");
        if ((login != null) && (role != null)) {
            if (login.equals("true")) {
                if (role.equals("administrator")) {

                    String action = request.getParameter("action");
                    url = "/admin/admin.jsp";
                    ArrayList<User> userList = new ArrayList<>();
                    UserDB uDB = new UserDB();
                    if (action == null) {
                        url = "/admin/admin.jsp";
                    } else if (action.equals("home")) {
                        url = "/homepage";
                    } else if (action.equals("viewuser")) {
                        userList = uDB.getUsers();
                        request.setAttribute("userList", userList);
                        url = "/admin/listalluser.jsp";
                    } else if (action.equals("adduser")) {
                        url = "/admin/adduser.jsp";
                    } else if (action.equals("create")) {
                        String userName = request.getParameter("userName");
                        String userPass = request.getParameter("userPass");
                        String userRePass = request.getParameter("userRePass");
                        String userRole = request.getParameter("userRole");
                        String isActiveString = request.getParameter("isActive");
                        boolean isActive = Boolean.parseBoolean(isActiveString);

                        if (userPass.equals(userRePass)) {
                            User user = new User(userName, userPass, userRole, isActive);
                            if (uDB.insertUser(user)) {
                                request.setAttribute("registermessage", "Create Success!");
                            } else {
                                request.setAttribute("registermessage", "Create Failed!");
                            }
                        } else {
                            request.setAttribute("registermessage", "Please sure password and re-password match!");
                        }
                        url = "/admin/adduser.jsp";

                    } else if (action.equals("edituser")) {
                        String userIDString = request.getParameter("userID");
                        int userID = Integer.parseInt(userIDString);
                        String userName = request.getParameter("userName");
                        String userPass = request.getParameter("userPass");
                        String userRole = request.getParameter("userRole");
                        String isActiveString = request.getParameter("isActive");
                        boolean isActive = Boolean.parseBoolean(isActiveString);

                        User editUser = new User(userID, userName, userPass, userRole, isActive);
                        request.setAttribute("editUser", editUser);
                        session.setAttribute("editUser", editUser);
                        url = "/admin/edituser.jsp";

                    } else if (action.equals("updateuser")) {
                        User oldUser = (User) session.getAttribute("editUser");
                        int oldID = oldUser.getUserID();

                        String userIDString = request.getParameter("userID");
                        int userID = Integer.parseInt(userIDString);
                        String userName = request.getParameter("userName");
                        String userPass = request.getParameter("userPass");
                        String userRePass = request.getParameter("userRePass");
                        String userRole = request.getParameter("userRole");
                        String isActiveString = request.getParameter("isActive");
                        boolean isActive = Boolean.parseBoolean(isActiveString);

                        if (userPass.equals(userRePass)) {
                            User newUser = new User(userID, userName, userPass, userRole, isActive);
                            if (uDB.updateUser(newUser, oldID)) {
                                request.setAttribute("editmessage", "Update Success!");
                                newUser = uDB.userDetail(userID);
                                session.setAttribute("editUser", newUser);
                            } else {
                                request.setAttribute("editmessage", "Update Failed!");
                            }
                        } else {
                            request.setAttribute("editmessage", "Please sure password and re-password match!");
                        }
                        url = "/admin/edituser.jsp";

                    } else if (action.equals("activeuser")) {
                        String userIDString = request.getParameter("userID");
                        int userID = Integer.parseInt(userIDString);
                        String userName = request.getParameter("userName");
                        String userPass = request.getParameter("userPass");
                        String userRole = request.getParameter("userRole");
                        String isActiveString = request.getParameter("isActive");
                        boolean isActive = Boolean.parseBoolean(isActiveString);

                        User user = new User(userID, userName, userPass, userRole, isActive);
                        if (uDB.isActiveUser(user, userID)) {
                            request.setAttribute("editmessage", "Active Success!");
                            userList = uDB.getUsers();
                            request.setAttribute("userList", userList);
                        } else {
                            request.setAttribute("editmessage", "Active Failed!");
                        }
                        url = "/admin/listalluser.jsp";

                    } else if (action.equals("deactiveuser")) {
                        String userIDString = request.getParameter("userID");
                        int userID = Integer.parseInt(userIDString);
                        String userName = request.getParameter("userName");
                        String userPass = request.getParameter("userPass");
                        String userRole = request.getParameter("userRole");
                        String isActiveString = request.getParameter("isActive");
                        boolean isActive = Boolean.parseBoolean(isActiveString);

                        User user = new User(userID, userName, userPass, userRole, isActive);
                        if (uDB.deActiveUser(user, userID)) {
                            request.setAttribute("editmessage", "deActive Success!");
                            userList = uDB.getUsers();
                            request.setAttribute("userList", userList);
                        } else {
                            request.setAttribute("editmessage", "deActive Failed!");
                        }
                        url = "/admin/listalluser.jsp";

                    } else if (action.equals("search")) {
                        String searchText = request.getParameter("searchText");
                        userList = uDB.searchUser(searchText);
                        request.setAttribute("userList", userList);
                        url = "/admin/listalluser.jsp";
                    }
                }
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
