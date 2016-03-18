/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.Promotions;
import data.PromotionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "PromotionsManagerServlet", urlPatterns = {"/promotionsmanager"})
public class PromotionsManagerServlet extends HttpServlet {

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
                    PromotionDB proDB = new PromotionDB();
                    if (action.equals("listpromotions")) {
                        ArrayList<Promotions> promotionsList = proDB.getPromotionList();
                        request.setAttribute("promotionsList", promotionsList);
                        url = "/salemanager/listallpromotion.jsp";
                    } else if (action.equals("editpromotion")) {
                        String promotionIDString = request.getParameter("promotionID");
                        int promotionID = Integer.parseInt(promotionIDString);
                        Promotions promotion1 = proDB.getPromotion(promotionID);
                        request.setAttribute("promotion", promotion1);
                        url = "/salemanager/editpromotion.jsp";

                    } else if (action.equals("updatepromotion")) {
                        String promotionIDString = request.getParameter("promotionID");
                        int promotionID = Integer.parseInt(promotionIDString);
                        String pictureLink = request.getParameter("pictureLink");
                        String promotionName = request.getParameter("promotionName");
                        String promotionDescription = request.getParameter("promotionDescription");
                        String startDay = request.getParameter("startDay");
                        Date startdate = Date.valueOf(startDay);
                        String endDay = request.getParameter("endDay");
                        Date enddate = Date.valueOf(endDay);
                        String promotionDiscountString = request.getParameter("promotionDiscount");
                        int promotionDiscount = Integer.parseInt(promotionDiscountString);
                        Promotions promotion = new Promotions(promotionName,
                                promotionDescription, startdate, enddate,
                                pictureLink, promotionDiscount);
                        if (proDB.updatePromotion(promotion, promotionID)) {
                            request.setAttribute("editmessage", "Update success !");
                        } else {
                            request.setAttribute("editmessage", "Update failed !");
                        }
                        Promotions promotion1 = proDB.getPromotion(promotionID);
                        request.setAttribute("promotion", promotion1);
                        url = "/salemanager/editpromotion.jsp";
                    } else if (action.equals("addpromotion")) {
                        url = "/salemanager/addpromotion.jsp";
                        session.setAttribute("fileupload", "promotionimage");
                    } else if (action.equals("addnewpromotion")){
                        String promotionIDString = request.getParameter("promotionID");
                        int promotionID = Integer.parseInt(promotionIDString);
                        String pictureLink = request.getParameter("pictureLink");
                        String promotionName = request.getParameter("promotionName");
                        String promotionDescription = request.getParameter("promotionDescription");
                        String startDay = request.getParameter("startDay");
                        Date startdate = Date.valueOf(startDay);
                        String endDay = request.getParameter("endDay");
                        Date enddate = Date.valueOf(endDay);
                        String promotionDiscountString = request.getParameter("promotionDiscount");
                        int promotionDiscount = Integer.parseInt(promotionDiscountString);
                        Promotions promotion = new Promotions(promotionName,
                                promotionDescription, startdate, enddate,
                                pictureLink, promotionDiscount);
                        promotion.setPromotionID(promotionID);
                        if (proDB.insertPromotion(promotion)) {
                            request.setAttribute("addmessage", "Add promotion success !");
                        } else {
                            request.setAttribute("addmessage", "Add promotion failed !");
                        }
                        url = "/salemanager/addpromotion.jsp";
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
