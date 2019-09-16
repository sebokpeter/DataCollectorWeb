/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seacon.datacollectorweb;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet generates a form for inputting descriptor values.
 * @author Peter
 */
@WebServlet(name = "descriptor_servlet", urlPatterns = {"/descriptor_servlet"})
public class descriptor_servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ask for the number of entries, and generate a form
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Create descriptor</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<form name=\"number_form\" method=\"post\" action=\"descriptor_servlet\"><br>");
            out.println("Number of entries: <input type=\"number\" name=\"set_number\">");
            out.println("<input type=\"submit\" value=\"Set\">");
            out.println("</body>");
            out.println("</html>");
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
        String numberStr = request.getParameter("set_number");
        
        System.out.println("Number set: " + numberStr);
        
        if(!Utils.Utility.tryParseInteger(numberStr)) {
                try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Create descriptor</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Please enter a number!</h1>");
                    out.println("<a href=\"descriptor_servlet\"");
                    out.println("</body>");
                    out.println("</html>");
            }
        } else {
            int number = Integer.parseInt(numberStr);

                try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Create descriptor</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<form name=\"d_form\" method=\"post\" action=\"descriptor_save_servlet\"><br>");
                    out.println("Name: <input type=\"text\" name=\"name\"><br>");
                    out.println("Table name: <input type=\"text\" name=\"table_name\"><br>");
                    for (int i = 0; i < number; i++) { // Create input fields for the specified number of entities
                        out.println("Database Field: <input type=\"text\" name=\"db_field\">");
                        out.println("Database Field Type: <select name=\"type\">");
                        out.println("<option value=\"STRING\">String</option>");
                        out.println("<option value=\"INTEGER\">Integer</option>");
                        out.println("<option value=\"REAL\">Real</option>");
                        out.println("<option value=\"BOOLEAN\">Boolean</option>");
                        out.println("</select>");
                        out.println("OPC Node Namespace: <input type=\"number\" name=\"ns\">");
                        out.println("OPC Node ID: <input type=\"text\"name=\"nid\">");
                        out.println("OPC Node ID Type: <select name=\"id_type\">");
                        out.println("<option value=\"string\">String</option>");
                        out.println("<option value=\"int\">Integer</option>");
                        out.println("</select>");
                        out.println("Order: <input type=\"number\" name=\"order\"><br>");
                    }
                    out.println("<input type=\"submit\" value=\"Save\">");
                    out.print("<a href=descriptor_servlet>Back</a>");
                    out.println("</body>");
                    out.println("</html>");
                }
        }
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
