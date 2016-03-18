package controller;

import business.Invoices;
import data.OrderDB;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

@WebServlet(name = "PrintServlet", urlPatterns = {"/print"})
public class PrintServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        ArrayList<Invoices> invoicesList = new ArrayList<>();
        OrderDB oDB = new OrderDB();
        if (action == null) {
            action = "";
        }
        if (action.equals("print")) {
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Orders table");
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("The Orders Table");
            invoicesList = oDB.getAllOrders(0, 0, true);
            row = sheet.createRow(1);
            row.createCell(0).setCellValue("Invoice ID");
            row.createCell(1).setCellValue("Customer Name");
            row.createCell(2).setCellValue("Customer Email");
            row.createCell(3).setCellValue("Invoice Date");
            row.createCell(4).setCellValue("Invoice Total");
            row.createCell(5).setCellValue("Shipping Process");
            row.createCell(6).setCellValue("Shipping Date");
            row.createCell(7).setCellValue("Is Paid");
            for (int i = 1; i <= invoicesList.size(); i++) {
                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(invoicesList.get(i-1).getInvoiceID());
                row.createCell(1).setCellValue(invoicesList.get(i-1).getCustomer().getCustomerName());
                row.createCell(2).setCellValue(invoicesList.get(i-1).getCustomer().getCustomerEmail());
                row.createCell(3).setCellValue(invoicesList.get(i-1).getInvoiceDate().toString());
                row.createCell(4).setCellValue(invoicesList.get(i-1).getInvoiceTotal());
                row.createCell(5).setCellValue(invoicesList.get(i-1).getShippingProcess());
                String shippingDate = "";
                if((invoicesList.get(i-1).getShippingDate()) != null){
                    shippingDate = invoicesList.get(i-1).getShippingDate().toString();
                }
                row.createCell(6).setCellValue(shippingDate);
                row.createCell(7).setCellValue(invoicesList.get(i-1).isIsPaid());
            }
            
            response.setHeader("content-disposition", "attachment; filename=orders.xls");
            response.setHeader("cache-control", "no-cache");

            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
        }

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
